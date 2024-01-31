package com.wang.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.business.mysqldao.AdminDao;
import com.wang.business.pojo.mysql.Admin;
import com.wang.business.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminDao,Admin> implements AdminService {

    @Autowired
    AdminDao adminDao;


    @Override
    public List<Admin> queryItemList() {
        return adminDao.queryItems();
    }
}
