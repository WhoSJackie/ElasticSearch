package com.wang.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wang.file.dao.FileDao;
import com.wang.common.object.entity.File;
import com.wang.file.service.FileService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileDao fileDao;

    @Override
    public List<File> getPicture(List<String> uids) {
        List<String> filterUids = uids.stream().filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(filterUids)) return new ArrayList<>();
        LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(File::getUid,uids);
        return fileDao.selectList(wrapper);
    }

    @Override
    public List<File> getAllPicture() {
        return fileDao.selectList(null);
    }
}
