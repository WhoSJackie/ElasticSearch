package com.wang.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.business.pojo.mysql.Admin;
import com.wang.business.service.AdminService;
import com.wang.common.constants.RedisConst;
import com.wang.common.enums.StatusEnum;
import com.wang.common.utils.CheckUtils;
import com.wang.common.utils.IpUtils;
import com.wang.common.utils.RedisUtil;
import com.wang.web.dto.ResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth/")
@Slf4j
public class LoginController {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    AdminService adminService;

    @RequestMapping("loginTest")
    public String testLogin(){
        System.out.println("这里是controller...");
        return "aaa";
    }

    @RequestMapping("login")
    public ResDto<String> loginApi(HttpServletRequest request,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam boolean isReme
                           ){
        if (StringUtils.isEmpty(username)|| StringUtils.isEmpty(password)){
            return new ResDto<>(false,"用户名和密码不能为空",null);
        }

        String ipAddr = IpUtils.getIpAddr(request);
        String loginLimit = redisUtil.get(RedisConst.LOGIN_LIMIT+RedisConst.SEGMENTATION+ipAddr);
        if (!StringUtils.isEmpty(loginLimit)){
            if (Integer.parseInt(loginLimit)>5){
                return new ResDto<>(false,"密码输入错误超过五次",null);
            }
        }

        boolean emailFlag = CheckUtils.checkEmail(username);
        boolean phoneFlag = CheckUtils.checkPhoneNumber(username);
        // 查询用户
        QueryWrapper<Admin> wrapper = new QueryWrapper<Admin>();
        // 如果是邮箱
        if (emailFlag){
            wrapper.eq("email",username);
        } else if (phoneFlag){
            // 电话号码
            wrapper.eq("mobile",username);
        } else{
            wrapper.eq("username",username);
        }

        wrapper.last("limit 1");
        wrapper.eq("status", StatusEnum.ENABLE);

        Admin admin = adminService.getOne(wrapper);
        // 用户不存在数据库
        if (admin==null){
            log.info("用户不存在");
            return ResDto.buildErrRes(String.format("用户名或者密码输错%d次之后，系统将锁定30分钟...",setLoginErrLock(request)));
        }

        //对密码进行加盐加密验证，采用SHA-256 + 随机盐【动态加盐】 + 密钥对密码进行加密
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean passFlag = encoder.matches(password,admin.getPassWord());

        // 密码错误
        if (!passFlag){
            log.info("密码错误");
            return ResDto.buildErrRes(String.format("用户名或者密码输错%d次之后，系统将锁定30分钟...",setLoginErrLock(request)));
        }



        
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
            redisUtil.setExTime(RedisConst.LOGIN_LIMIT+RedisConst.SEGMENTATION+ip,String.valueOf(surplusTimes),30, TimeUnit.MINUTES);
        } else{
            // 如果是第一次
            surplusTimes = surplusTimes-1;
            redisUtil.setExTime(RedisConst.LOGIN_LIMIT+RedisConst.SEGMENTATION+ip,String.valueOf(1),30, TimeUnit.MINUTES);
        }
        return surplusTimes;
    }

}
