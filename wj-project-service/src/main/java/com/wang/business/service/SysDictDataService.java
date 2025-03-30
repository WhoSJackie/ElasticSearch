package com.wang.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.common.object.entity.Admin;
import com.wang.common.object.entity.SysDictData;

import java.util.List;
import java.util.Map;

public interface SysDictDataService extends IService<SysDictData>{

    Map<String,Object> getDictListByType(String dictType);

    Map<String,Object> getDictListByTypeList(List<String> dictTypeList);


}
