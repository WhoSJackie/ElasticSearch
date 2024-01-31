package com.wang.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.business.pojo.mysql.Role;

public interface RoleService extends IService<Role> {

    Role queryItem(Long id);


}
