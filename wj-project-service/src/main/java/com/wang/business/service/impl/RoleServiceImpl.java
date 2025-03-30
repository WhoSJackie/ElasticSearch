package com.wang.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.business.mysqldao.RoleDao;
import com.wang.common.object.entity.Role;
import com.wang.business.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao,Role> implements RoleService {

    @Autowired
    RoleDao roleDao;

    @Override
    public Role queryItem(Long id) {
        return roleDao.queryPojo(id);
    }
}
