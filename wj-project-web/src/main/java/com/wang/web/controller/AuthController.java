package com.wang.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.business.mysqldao.UserDao;
import com.wang.business.service.AdminService;
import com.wang.business.service.CategoryMenuService;
import com.wang.business.service.RoleService;
import com.wang.business.service.SystemConfigService;
import com.wang.common.constants.Constants;
import com.wang.common.constants.RedisConst;
import com.wang.common.enums.StatusEnum;
import com.wang.common.feign.FileFeignClient;
import com.wang.common.jwt.Audience;
import com.wang.common.jwt.JwtTokenUtil;
import com.wang.common.object.entity.*;
import com.wang.common.object.req.FileRequest;
import com.wang.common.object.vo.*;
import com.wang.common.utils.CheckUtils;
import com.wang.common.utils.IpUtils;
import com.wang.common.utils.RedisUtil;
import com.wang.common.utils.StrUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth/")
@Slf4j
public class AuthController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CategoryMenuService categoryMenuService;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Value("${isRememberMeExpiresSecond}")
    private long isRememberMeExpiresSecond;

    @Value("${tokenHead}")
    private String tokenHead;

    @Autowired
    private Audience audience;

    @Autowired
    private JwtTokenUtil jwtTokenUtils;

    @Autowired
    private FileFeignClient fileFeignClient;


//    @RequestMapping("login")
//    public ResVo<String> userLogin(@RequestBody UserVo userVo){
//        String userName = userVo.getUserName();
//        String password = userVo.getPassWord();
//        if (userName.isEmpty()|| password.isEmpty()){
//            return ResVo.buildErrRes("用户名,邮箱,密码不能为空!");
//        }
//        // 验证用户名或者邮箱是否正确
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.eq("user_name",userName).or().eq("email",userName);
//        wrapper.last("limit 1");
//        User user = userDao.selectOne(wrapper);
//        if (user==null || (EStatusEnum.DISABLE.getValue())==user.getStatus()){
//            return ResVo.buildErrRes("该用户尚未注册，请先进行注册!");
//        }
//        if (EStatusEnum.FREEZE.getValue()==user.getStatus()){
//            return ResVo.buildErrRes("该用户尚未激活!");
//        }
//        if (!password.equals(user.getPassWord())){
//            return ResVo.buildErrRes("密码输入错误!");
//        } else{
//            // 登陆成功，更新用户登录信息
//            String token = StrUtils.getUUID();
//            return ResVo.buildSuccessRes(token);
//        }
//    }
//
//    @PostMapping("register")
//    public ResVo<String> userRegister(@RequestBody UserVo userVo){
//         String userName = userVo.getUserName();
//         String passWord = userVo.getPassWord();
//         String nickName = userVo.getNickName();
//         String email = userVo.getEmail();
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(User::getUserName,userName).or().eq(User::getEmail,email);
//        wrapper.eq(User::getStatus,EStatusEnum.ENABLE);
//        User userOne = userDao.selectOne(wrapper);
//        if (userOne!=null){
//            return ResVo.buildErrRes("数据库已经存在该用户");
//        }
//        User user = new User();
//        user.setUserName(userName);
//        user.setPassWord(passWord);
//        user.setEmail(email);
//        user.setNickName(nickName);
//        int i = userDao.insert(user);
//        if (i>0){
//            return ResVo.buildSuccessRes("插入成功!");
//        }
//        return ResVo.buildErrRes("插入失败");
//    }


    @ApiOperation(value = "用户登录", notes = "用户登录")
    @GetMapping("login")
    public ResVo<LoginVo> loginApi(HttpServletRequest request,
                                   @RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   @RequestParam("rememberMeFlag") boolean rememberMeFlag
                           ){
        if (StringUtils.isEmpty(username)|| StringUtils.isEmpty(password)){
            return  ResVo.buildErrRes("用户名和密码不能为空");
        }
        String ipAddr = IpUtils.getIpAddr(request);
        String loginLimit = redisUtil.get(RedisConst.LOGIN_LIMIT+ RedisConst.SEGMENTATION+ipAddr);
        // todo:后面放开
//        if (!StringUtils.isEmpty(loginLimit)){
//            if (Integer.parseInt(loginLimit)>5){
//                return ResVo.buildErrRes("密码输入错误超过五次");
//            }
//        }
        boolean emailFlag = CheckUtils.checkEmail(username);
        boolean phoneFlag = CheckUtils.checkPhoneNumber(username);
        // 查询用户
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<Admin>();
        // 如果是邮箱
        if (emailFlag){
            wrapper.eq(Admin::getEmail,username);
        } else if (phoneFlag){
            // 电话号码
            wrapper.eq(Admin::getMobile,username);
        } else{
            wrapper.eq(Admin::getUserName,username);
        }
        wrapper.eq(Admin::getStatus, StatusEnum.ENABLE.getValue());
        wrapper.last("limit 1");
        Admin admin = adminService.getOne(wrapper);
        // 用户不存在数据库
        if (admin==null){
            log.info("用户不存在");
            return ResVo.buildErrRes(String.format("用户名或者密码输错%d次之后，系统将锁定30分钟...",setLoginErrLock(request)));
        }
        //对密码进行加盐加密验证，采用SHA-256 + 随机盐【动态加盐】 + 密钥对密码进行加密
        // 匹配时，将输入的明文密码进行相同的hash运算，与数据库的密码hash值进行比对
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean passFlag = encoder.matches(password,admin.getPassWord());
        // 密码错误
        if (!passFlag){
            log.info("密码错误");
            return ResVo.buildErrRes(String.format("用户名或者密码输错%d次之后，系统将锁定30分钟...",setLoginErrLock(request)));
        }

        List<String> roles = new ArrayList<>(Arrays.asList(admin.getRoleUid().split(",")));
        List<Role> rolesList = (List<Role>)roleService.listByIds(roles);
        if (rolesList.size()<=0){
            return ResVo.buildErrRes("该用户没有角色");
        }
        // 取第一个角色
        StringBuilder sb = new StringBuilder();
        for (Role role : rolesList) {
            sb.append(role.getRoleName()).append(",");
        }
        String roleName = sb.substring(0,sb.length()-2);
        long expireTime = rememberMeFlag?isRememberMeExpiresSecond:audience.getExpiresSecond();
        String jwtToken = jwtTokenUtils.createJwt(admin.getUserName(),
                admin.getUid(),
                roleName,
                audience.getClientId(),
                audience.getName(),
                expireTime * 1000,
                audience.getBase64Secret());
        jwtToken = tokenHead+jwtToken;
        // 更新用户登录信息，并且将用户添加到在线用户表中
        admin.setLoginCount(admin.getLoginCount()+1);
        admin.setLastLoginIp(IpUtils.getIpAddr(request));
        admin.setLastLoginTime(new Date());
        adminService.updateById(admin);
        // 将token和tokenuid传给在线用户设置方法中，数据库中无这三个字段，只是为了方便传递。
        admin.setValidCode(jwtToken);
        String tokenUuid = StrUtils.getUUID();
        admin.setTokenUid(tokenUuid);
        admin.setRole(rolesList.get(0));
        // 添加在线用户到Redis中【设置token过期时间等】
        adminService.addOnlineAdmin(admin, expireTime);
        LoginVo loginVo = new LoginVo(jwtToken);
        return ResVo.buildSuccessRes(loginVo);
    }

    /**
     * 设置登录限制，返回剩余次数
     * 密码错误五次，将会锁定30分钟
     *
     * @param request
     */
    private Integer setLoginErrLock(HttpServletRequest request){
        String ip = IpUtils.getIpAddr(request);
        String loginLimit = redisUtil.get(RedisConst.LOGIN_LIMIT+RedisConst.SEGMENTATION+ip);
        Integer surplusTimes = 5;
        //如果存在错误次数
        if (!StringUtils.isEmpty(loginLimit)){
            Integer time = Integer.valueOf(loginLimit)+1;
            surplusTimes = surplusTimes-time;
            redisUtil.setExTime(RedisConst.LOGIN_LIMIT+RedisConst.SEGMENTATION+ip,String.valueOf(time),30, TimeUnit.MINUTES);
        } else{
            // 如果是第一次
            surplusTimes = surplusTimes-1;
            redisUtil.setExTime(RedisConst.LOGIN_LIMIT+RedisConst.SEGMENTATION+ip,String.valueOf(1),30, TimeUnit.MINUTES);
        }
        return surplusTimes;
    }

    @GetMapping("getMenu")
    public ResVo<CategoryMenuVo> getMenu(HttpServletRequest request){
        return ResVo.buildSuccessRes(categoryMenuService.getMenu(request));
    }

    @GetMapping("getSystemConfig")
    public ResVo<SystemConfig> getSystemConfig(){
        return ResVo.buildSuccessRes(systemConfigService.getOne());
    }

    @GetMapping("getUserInfo")
    public ResVo<InfoVo> getUserInfo(HttpServletRequest request, @RequestParam(value = "token",required = false) String token){
        InfoVo infoVo = new InfoVo();
        String adminUid = request.getAttribute(Constants.ADMIN_UID).toString();
        if (StringUtils.isEmpty(adminUid)){
            return ResVo.buildErrRes("未找到用户信息!");
        }
        Admin admin = adminService.getOneAdmin(adminUid);
        // 找到avatar
        String avatars = admin.getAvatar();
        if (StringUtils.isNotEmpty(avatars)){
            List<String> avatarList = Arrays.asList(avatars.split(","));
            FileRequest fileRequest = new FileRequest(avatarList);
            ResVo<List<File>> picture = fileFeignClient.getPicture(fileRequest);
            if (picture!=null && picture.getData()!=null){
                List<File> data = picture.getData();
                if (data.size()>0){
                    infoVo.setAvatar(data.get(0).getPicUrl());
                } else{
                    // todo: 后期改成其他的
                    infoVo.setAvatar("https://gitee.com/moxi159753/wx_picture/raw/master/picture/favicon.png");
                }
            }
        }
        Role role = roleService.getById(admin.getRoleUid());
        infoVo.setRoles(Arrays.asList(role));
        infoVo.setToken(token);
        return ResVo.buildSuccessRes(infoVo);
    }

}
