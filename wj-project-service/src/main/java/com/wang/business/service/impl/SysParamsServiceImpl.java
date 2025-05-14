package com.wang.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.business.mysqldao.SysParamsDao;
import com.wang.business.service.SysParamsService;
import com.wang.common.constants.SqlConstant;
import com.wang.common.enums.EStatusEnum;
import com.wang.common.object.entity.SysParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysParamsServiceImpl extends ServiceImpl<SysParamsDao, SysParams> implements SysParamsService {

    @Autowired
    private SysParamsDao sysParamsDao;

    @Override
    public String getSysParamValueByKey(String key) {
        LambdaQueryWrapper<SysParams> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysParams::getParamsKey, key);
        queryWrapper.eq(SysParams::getStatus, EStatusEnum.ENABLE.getValue());
        queryWrapper.last(SqlConstant.LIMIT_ONE);
        SysParams sysParams = sysParamsDao.selectOne(queryWrapper);
        if (sysParams==null) return "";
        return sysParams.getParamsValue();
    }
}
