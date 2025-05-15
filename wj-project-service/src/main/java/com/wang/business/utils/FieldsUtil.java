package com.wang.business.utils;

import com.wang.common.feign.FileFeignClient;
import com.wang.common.object.entity.File;
import com.wang.common.object.entity.test.JdContent;
import com.wang.common.object.req.FileRequest;
import com.wang.common.object.vo.ResVo;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FieldsUtil {

    @Autowired
    FileFeignClient fileFeignClient;
    public void setFileListToObj(Object obj,String fieldName,List<String> fileUids) throws IllegalAccessException {
        FileRequest fileRequest = new FileRequest();
        fileRequest.setUidList(fileUids);
        ResVo<List<File>> picture = fileFeignClient.getPictureByUids(fileRequest);
        if (Collections.isEmpty(picture.getData())) return;
        Class<?> aClass = obj.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getName().equals(fieldName)){
                field.setAccessible(true);
                field.set(obj,picture);
            }
        }
    }


}
