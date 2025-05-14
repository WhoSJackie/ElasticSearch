package com.wang.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.business.mysqldao.AdminDao;
import com.wang.common.enums.EStatusEnum;
import com.wang.common.object.entity.OnlineAdmin;
import com.wang.common.object.entity.Admin;
import com.wang.business.service.AdminService;
import com.wang.common.holder.HttpHolder;
import com.wang.common.utils.DateUtils;
import com.wang.common.utils.IpUtils;
import com.wang.common.utils.JsonUtil;
import com.wang.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminDao,Admin> implements AdminService {

    @Autowired
    AdminDao adminDao;

    @Autowired
    RedisUtil redisUtil;


    @Override
    public Admin getOneAdmin(String uid) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getStatus, EStatusEnum.ENABLE.getValue());
        queryWrapper.eq(Admin::getUid, uid);
        return adminDao.selectOne(queryWrapper);
    }

    @Override
    public void addOnlineAdmin(Admin admin, Long expireSecond) {
        HttpServletRequest request = HttpHolder.getRequest();
        Map<String, String> info = IpUtils.getOsAndBrowserInfo(request);
        String browser = info.get("BROSWER");
        String os = info.get("OS");
        String ip = IpUtils.getIpAddr(request);
        OnlineAdmin onlineAdmin = new OnlineAdmin();
        onlineAdmin.setAdminUid(admin.getUid());
        onlineAdmin.setTokenId(admin.getTokenUid());
        onlineAdmin.setToken(admin.getValidCode());
        onlineAdmin.setOs(os);
        onlineAdmin.setBrowser(browser);
        onlineAdmin.setIpaddr(ip);
        onlineAdmin.setLoginTime(DateUtils.getNowTime());
        onlineAdmin.setRoleName(admin.getRole().getRoleName());
        onlineAdmin.setUserName(admin.getUserName());
        onlineAdmin.setExpireTime(DateUtils.getDateStr(new Date(), expireSecond));
        // 从redis中拿去信息
        String addrResult = redisUtil.get("IP_ADDRESS:"+ip);
        // 如果没有拿到，则重新获取
        if (addrResult==null){
            // todo:根据ip获取地址

        } else{
            onlineAdmin.setLoginLocation(addrResult);
        }
        // 将管理员信息存储到在线信息表中
        redisUtil.setExTime("LOGIN_TOKEN_KEY:"+admin.getValidCode(), JsonUtil.toJsonString(onlineAdmin),expireSecond, TimeUnit.SECONDS);
        // 该表用户uuid-token互换
        redisUtil.setExTime("LOGIN_UUID_KEY:"+admin.getTokenUid(), admin.getValidCode(),expireSecond, TimeUnit.SECONDS);
    }
}

