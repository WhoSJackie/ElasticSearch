package com.wang.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.common.object.entity.SystemConfig;

public interface SystemConfigService extends IService<SystemConfig> {

    SystemConfig getOne();


}
