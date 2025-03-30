package com.wang.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.business.mysqldao.SystemConfigDao;
import com.wang.business.service.SystemConfigService;
import com.wang.common.constants.SqlConstant;
import com.wang.common.enums.EStatusEnum;
import com.wang.common.object.entity.SystemConfig;
import org.apache.lucene.search.similarities.Lambda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemConfigServiceImpl  extends ServiceImpl<SystemConfigDao, SystemConfig> implements SystemConfigService {

    @Autowired
    SystemConfigDao systemConfigDao;

    @Override
    public SystemConfig getOne() {
        LambdaQueryWrapper<SystemConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemConfig::getStatus, EStatusEnum.ENABLE.getValue());
        queryWrapper.last(SqlConstant.LIMIT_ONE);
        return systemConfigDao.selectOne(queryWrapper);
    }
}
