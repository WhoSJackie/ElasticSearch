package com.wang.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wang.business.mysqldao.UserDao;
import com.wang.business.pojo.mysql.Admin;
import com.wang.business.pojo.mysql.Role;
import com.wang.business.pojo.mysql.User;
import com.wang.business.service.AdminService;
import com.wang.business.service.RoleService;
import com.wang.common.constants.RedisConst;
import com.wang.common.enums.EStatusEnum;
import com.wang.common.enums.StatusEnum;
import com.wang.common.jwt.Audience;
import com.wang.common.jwt.JwtTokenUtils;
import com.wang.common.utils.CheckUtils;
import com.wang.common.utils.IpUtils;
import com.wang.common.utils.RedisUtil;
import com.wang.common.utils.StringUtils;
import com.wang.web.utils.ResUtil;
import com.wang.web.vo.ResVo;
import com.wang.web.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
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
public class LoginController {

    @Autowired
    UserDao userDao;

    @GetMapping("loginTest")
    public String testLogin(){
        System.out.println("这里是controller...");
        return "controller";
    }

    @RequestMapping("login")
    public ResVo<String> userLogin(@RequestBody UserVo userVo){
        String userName = userVo.getUserName();
        String password = userVo.getPassWord();
        if (userName.isEmpty()|| password.isEmpty()){
            return ResVo.buildErrRes("用户名,邮箱,密码不能为空!");
        }
        // 验证用户名或者邮箱是否正确
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",userName).or().eq("email",userName);
        wrapper.last("limit 1");
        User user = userDao.selectOne(wrapper);
        if (user==null || (EStatusEnum.DISABLE.getValue())==user.getStatus()){
            return ResVo.buildErrRes("该用户尚未注册，请先进行注册!");
        }
        if (EStatusEnum.FREEZE.getValue()==user.getStatus()){
            return ResVo.buildErrRes("该用户尚未激活!");
        }
        if (!password.equals(user.getPassWord())){
            return ResVo.buildErrRes("密码输入错误!");
        } else{
            // 登陆成功，更新用户登录信息
            String token =StringUtils.getUUID();
            return ResVo.buildSuccessRes(token);
        }
    }

    @PostMapping("register")
    public ResVo<String> userRegister(@RequestBody UserVo userVo){
         String userName = userVo.getUserName();
         String passWord = userVo.getPassWord();
         String nickName = userVo.getNickName();
         String email = userVo.getEmail();
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,userName).or().eq(User::getEmail,email);
        wrapper.eq(User::getStatus,EStatusEnum.ENABLE);
        User userOne = userDao.selectOne(wrapper);
        if (userOne!=null){
            return ResVo.buildErrRes("数据库已经存在该用户");
        }
        User user = new User();
        user.setUserName(userName);
        user.setPassWord(passWord);
        user.setEmail(email);
        user.setNickName(nickName);
        int i = userDao.insert(user);
        if (i>0){
            return ResVo.buildSuccessRes("插入成功!");
        }
        return ResVo.buildErrRes("插入失败");
    }


//    @RequestMapping("login")
//    public ResVo<LoginVo> loginApi(HttpServletRequest request,
//                                  @RequestParam String username,
//                                  @RequestParam String password,
//                                  @RequestParam boolean rememberMeFlag
//                           ){
//        if (StringUtils.isEmpty(username)|| StringUtils.isEmpty(password)){
//            return new ResVo<>(false,"用户名和密码不能为空",null);
//        }
//        String ipAddr = IpUtils.getIpAddr(request);
//        String loginLimit = redisUtil.get(RedisConst.LOGIN_LIMIT+RedisConst.SEGMENTATION+ipAddr);
//        if (!StringUtils.isEmpty(loginLimit)){
//            if (Integer.parseInt(loginLimit)>5){
//                return new ResVo<>(false,"密码输入错误超过五次",null);
//            }
//        }
//        boolean emailFlag = CheckUtils.checkEmail(username);
//        boolean phoneFlag = CheckUtils.checkPhoneNumber(username);
//        // 查询用户
//        QueryWrapper<Admin> wrapper = new QueryWrapper<Admin>();
//        // 如果是邮箱
//        if (emailFlag){
//            wrapper.eq("email",username);
//        } else if (phoneFlag){
//            // 电话号码
//            wrapper.eq("mobile",username);
//        } else{
//            wrapper.eq("username",username);
//        }
//
//        wrapper.last("limit 1");
//        wrapper.eq("status", StatusEnum.ENABLE);
//        Admin admin = adminService.getOne(wrapper);
//        // 用户不存在数据库
//        if (admin==null){
//            log.info("用户不存在");
//            return ResVo.buildErrRes(String.format("用户名或者密码输错%d次之后，系统将锁定30分钟...",setLoginErrLock(request)));
//        }
//        //对密码进行加盐加密验证，采用SHA-256 + 随机盐【动态加盐】 + 密钥对密码进行加密
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//        boolean passFlag = encoder.matches(password,admin.getPassWord());
//        // 密码错误
//        if (!passFlag){
//            log.info("密码错误");
//            return ResVo.buildErrRes(String.format("用户名或者密码输错%d次之后，系统将锁定30分钟...",setLoginErrLock(request)));
//        }
//
//        List<String> roles = new ArrayList<>(Arrays.asList(admin.getRoleUid().split(",")));
//        List<Role> rolesList = (List<Role>)roleService.listByIds(roles);
//        if (rolesList.size()<=0){
//            return ResVo.buildErrRes("该用户没有角色");
//        }
//        // 取第一个角色
//        String firstRole = roles.get(0);
//        long expireTime = rememberMeFlag?isRememberMeExpiresSecond:audience.getExpiresSecond();
//        String jwtToken = jwtTokenUtils.createJwt(admin.getUsername(),
//                admin.getRoleUid(),
//                firstRole,
//                audience.getClientId(),
//                audience.getName(),
//                expireTime * 1000,
//                audience.getBase64Secret());
//        jwtToken = tokenHead+jwtToken;
//        LoginVo loginVo = new LoginVo(jwtToken);
//        // 更新用户登录信息，并且将用户添加到在线用户表中
//        admin.setLoginCount(admin.getLoginCount()+1);
//        admin.setLastLoginIp(IpUtils.getIpAddr(request));
//        admin.setLastLoginTime(new Date());
//        adminService.updateById(admin);
//        admin.setValidCode(jwtToken);
//        admin.setTokenUid(StringUtils.getUUID());
//        admin.setRole(rolesList.get(0));
//        // 添加在线用户到Redis中【设置过期时间】
//        adminService.addOnlineAdmin(admin, expireTime);
//        return ResVo.buildSuccessRes(loginVo);
//    }
//
//    /**
//     * 设置登录限制，返回剩余次数
//     * 密码错误五次，将会锁定30分钟
//     *
//     * @param request
//     */
//    private Integer setLoginErrLock(HttpServletRequest request){
//        String ip = IpUtils.getIpAddr(request);
//        String loginLimit = redisUtil.get(RedisConst.LOGIN_LIMIT+RedisConst.SEGMENTATION+ip);
//        Integer surplusTimes = 5;
//        //如果存在错误次数
//        if (!StringUtils.isEmpty(loginLimit)){
//            Integer time = Integer.valueOf(loginLimit)+1;
//            surplusTimes = surplusTimes-time;
//            redisUtil.setExTime(RedisConst.LOGIN_LIMIT+RedisConst.SEGMENTATION+ip,String.valueOf(surplusTimes),30, TimeUnit.MINUTES);
//        } else{
//            // 如果是第一次
//            surplusTimes = surplusTimes-1;
//            redisUtil.setExTime(RedisConst.LOGIN_LIMIT+RedisConst.SEGMENTATION+ip,String.valueOf(1),30, TimeUnit.MINUTES);
//        }
//        return surplusTimes;
//    }

}
