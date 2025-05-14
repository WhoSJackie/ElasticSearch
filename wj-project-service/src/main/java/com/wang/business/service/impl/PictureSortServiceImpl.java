package com.wang.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.business.mysqldao.PictureSortDao;
import com.wang.business.service.PictureSortService;
import com.wang.common.constants.Constants;
import com.wang.common.enums.EStatusEnum;
import com.wang.common.enums.StatusEnum;
import com.wang.common.feign.FileFeignClient;
import com.wang.common.object.entity.File;
import com.wang.common.object.entity.PictureSort;
import com.wang.common.object.req.FileRequest;
import com.wang.common.object.vo.PictureSortVo;
import com.wang.common.object.vo.ResVo;
import com.wang.common.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author jiami
* @description 针对表【t_picture_sort(图片分类表)】的数据库操作Service实现
* @createDate 2025-04-05 11:02:34
*/
@Slf4j
@Service
public class PictureSortServiceImpl extends ServiceImpl<PictureSortDao, PictureSort> implements PictureSortService {

    @Autowired
    PictureSortDao pictureSortDao;

    @Autowired
    FileFeignClient fileFeignClient;

    @Override
    public IPage<PictureSort> getPageInfo(PictureSortVo pictureSortVo) {
        LambdaQueryWrapper<PictureSort> queryWrapper = new LambdaQueryWrapper<>();
        Page page = new Page<>();
        page.setCurrent(1);
        page.setSize(10);
        if (!StringUtils.isEmpty(pictureSortVo.getName())){
            queryWrapper.eq(PictureSort::getName,pictureSortVo.getName());
        }
        if (ObjectUtils.isNotEmpty(pictureSortVo.getIsShow())){
            queryWrapper.eq(PictureSort::getIsShow,pictureSortVo.getIsShow());
        } else{
            queryWrapper.eq(PictureSort::getIsShow, StatusEnum.ENABLE.getValue());
        }
        queryWrapper.eq(PictureSort::getStatus, EStatusEnum.ENABLE.getValue());
        if (pictureSortVo!=null){
            page.setCurrent(pictureSortVo.getCurrentPage());
            page.setSize(pictureSortVo.getPageSize());
        }
        IPage sortPage = pictureSortDao.selectPage(page, queryWrapper);
        // todo:可以抽取成一个方法，用于设置图片列表
        List<PictureSort> sortList = sortPage.getRecords();
        List<String> fileUids = new ArrayList<>();
        if (!CollectionUtils.isEmpty(sortList)) {
            sortList.forEach(i->{
                fileUids.addAll(StrUtils.StringToList(i.getFileUid(),Constants.SYMBOL_COMMA));
            });
            FileRequest request = new FileRequest();
            request.setUidList(fileUids);
            ResVo<List<File>> pictureRes = fileFeignClient.getPicture(request);
            List<File> pictureList = pictureRes.getData();
            Map<String,File> fileMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(pictureList)){
                fileMap = pictureList.stream().collect(Collectors.toMap(File::getUid,i->i,(o,n)->o));
            }
            for (PictureSort i:sortList) {
                List<String> fileUidList = StrUtils.StringToList(i.getFileUid(), Constants.SYMBOL_COMMA);
                List<File> urlList = new ArrayList<>();
                for (String s : fileUidList) {
                    if (fileMap.containsKey(s)){
                        urlList.add(fileMap.get(s));
                    }
                }
                i.setPhotoList(urlList);
            }
        }
        sortPage.setRecords(sortList);
        return sortPage;
    }

    @Override
    public PictureSort getSortByUid(PictureSortVo pictureSortVo) {
        return pictureSortDao.selectById(pictureSortVo.getUid());
    }
}




