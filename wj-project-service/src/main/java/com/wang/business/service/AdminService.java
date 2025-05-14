package com.wang.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.common.object.entity.Admin;

import java.util.List;

public interface AdminService extends IService<Admin> {

    Admin getOneAdmin(String uid);

    void addOnlineAdmin(Admin admin, Long expireSecond);

}
