package com.wang.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.common.object.entity.SysParams;

public interface SysParamsService extends IService<SysParams> {

    String getSysParamValueByKey(String key);


}
