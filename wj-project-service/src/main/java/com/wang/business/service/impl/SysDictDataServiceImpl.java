package com.wang.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.business.mysqldao.SysDictDataDao;
import com.wang.business.mysqldao.SysDictTypeDao;
import com.wang.business.service.SysDictDataService;
import com.wang.common.constants.SqlConstant;
import com.wang.common.enums.DefaultEumn;
import com.wang.common.enums.EPublishEnum;
import com.wang.common.enums.EStatusEnum;
import com.wang.common.object.entity.SysDictData;
import com.wang.common.object.entity.SysDictSingleInfo;
import com.wang.common.object.entity.SysDictType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataDao, SysDictData> implements SysDictDataService {

    @Autowired
    private SysDictTypeDao sysDictTypeDao;

    @Autowired
    private SysDictDataDao sysDictDataDao;

    @Override
    public Map<String,Object> getDictListByType(String dictType){
        Map<String,Object> resMap = new HashMap<>();
        if (StringUtils.isEmpty(dictType)){
            log.warn("未传入参数！");
            return resMap;
        }
        LambdaQueryWrapper<SysDictType> dictQueryWrapper = new LambdaQueryWrapper<>();
        dictQueryWrapper.eq(SysDictType::getDictType,dictType);
        dictQueryWrapper.eq(SysDictType::getStatus, EStatusEnum.ENABLE.getValue());
        dictQueryWrapper.eq(SysDictType::getIsPublish, EPublishEnum.ENABLE.getValue());
        dictQueryWrapper.last(SqlConstant.LIMIT_ONE);
        SysDictType sysDictType = sysDictTypeDao.selectOne(dictQueryWrapper);
        if (sysDictType==null) return resMap;

        LambdaQueryWrapper<SysDictData> dictDataQueryWrapper = new LambdaQueryWrapper<>();
        dictDataQueryWrapper.eq(SysDictData::getDictTypeUid,sysDictType.getUid());
        dictDataQueryWrapper.eq(SysDictData::getStatus, EStatusEnum.ENABLE.getValue());
        dictDataQueryWrapper.eq(SysDictData::getIsPublish, EPublishEnum.ENABLE.getValue());
        List<SysDictData> sysDictDataList = sysDictDataDao.selectList(dictDataQueryWrapper);

        // 获取字典默认值
        String defaultValue = "";
        for (SysDictData sysDictData : sysDictDataList) {
            if (sysDictData.getIsDefault()== DefaultEumn.YES.getValue()){
                defaultValue = sysDictData.getDictValue();
                break;
            }
        }
        SysDictSingleInfo sysDictSingleInfo = new SysDictSingleInfo();
        sysDictSingleInfo.setDictValueList(sysDictDataList);
        sysDictSingleInfo.setDefaultValue(defaultValue);
        resMap.put(dictType,sysDictSingleInfo);
        return resMap;
    }

    @Override
    public Map<String, Object> getDictListByTypeList(List<String> dictTypeList) {
        Map<String,Object> resMap = new HashMap<>();
        if (CollectionUtils.isEmpty(dictTypeList)) {
            log.warn("未传入参数！");
            return resMap;
        }
        for (String s : dictTypeList) {
            resMap.putAll(getDictListByType(s));
        }
        return resMap;
    }


}
