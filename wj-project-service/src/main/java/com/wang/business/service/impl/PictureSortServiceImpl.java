package com.wang.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.business.mysqldao.PictureDao;
import com.wang.business.mysqldao.PictureSortDao;
import com.wang.business.service.PictureSortService;
import com.wang.business.utils.FieldsUtil;
import com.wang.common.constants.Constants;
import com.wang.common.enums.EStatusEnum;
import com.wang.common.enums.StatusEnum;
import com.wang.common.feign.FileFeignClient;
import com.wang.common.object.entity.File;
import com.wang.common.object.entity.Picture;
import com.wang.common.object.entity.PictureSort;
import com.wang.common.object.req.FileRequest;
import com.wang.common.object.vo.PictureSortVo;
import com.wang.common.object.vo.ResVo;
import com.wang.common.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private PictureSortDao pictureSortDao;

    @Autowired
    private FileFeignClient fileFeignClient;

    @Autowired
    private FieldsUtil fieldsUtil;

    @Autowired
    private PictureDao pictureDao;


    @Override
    public IPage<PictureSort> getPageInfo(PictureSortVo pictureSortVo) throws IllegalAccessException {
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
            ResVo<List<File>> pictureRes = fileFeignClient.getPictureByUids(request);
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

    @Override
    public int updatePictureSort(PictureSortVo pictureSortVo) throws IllegalAccessException {
        if (ObjectUtils.isEmpty(pictureSortVo)){
            log.info("没有修改的值！");
            return 0;
        }
        LambdaQueryWrapper<PictureSort> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PictureSort::getUid,pictureSortVo.getUid());
        queryWrapper.eq(PictureSort::getStatus,EStatusEnum.ENABLE.getValue());
        PictureSort pictureSort = pictureSortDao.selectOne(queryWrapper);
        if (pictureSort==null){
            log.info("未在服务器中查到对应的值！");
            return 0;
        }
        if (!StringUtils.isEmpty(pictureSortVo.getName())) {
            pictureSort.setName(pictureSortVo.getName());
        }
        if (!StringUtils.isEmpty(pictureSortVo.getFileUid())){
            pictureSort.setFileUid(pictureSortVo.getFileUid());
        }
        if (!ObjectUtils.isEmpty(pictureSortVo.getSort())){
            pictureSort.setSort(pictureSortVo.getSort());
        }
        return pictureSortDao.updateById(pictureSort);
    }

    @Override
    public int addPictureSort(PictureSortVo pictureSortVo) throws IllegalAccessException {
        if (ObjectUtils.isEmpty(pictureSortVo)){
            log.info("没有增加的值！");
            return 0;
        }
        LambdaQueryWrapper<PictureSort> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PictureSort::getUid,pictureSortVo.getUid());
        queryWrapper.eq(PictureSort::getStatus,EStatusEnum.ENABLE.getValue());
        PictureSort pictureSort = pictureSortDao.selectOne(queryWrapper);
        if (pictureSort!=null){
            log.info("查到对应的值，直接更新!");
            return updatePictureSort(pictureSortVo);
        }
        pictureSort = new PictureSort();
        pictureSort.setName(pictureSortVo.getName());
        pictureSort.setSort(pictureSortVo.getSort());
        if (!StringUtils.isEmpty(pictureSortVo.getFileUid())){
            pictureSort.setFileUid(pictureSortVo.getFileUid());
        }
        return pictureSortDao.insert(pictureSort);
    }

    @Override
    public ResVo<String> deletePictureSort(String uid) {
        // 先查找该分类下是否还有图片
        LambdaQueryWrapper<Picture> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Picture::getPictureSortUid,uid);
        queryWrapper.eq(Picture::getStatus,EStatusEnum.ENABLE.getValue());
        Long cnt = pictureDao.selectCount(queryWrapper);
        if (cnt>0){
            return ResVo.buildErrRes("该分类下还有图片！");
        }
        LambdaQueryWrapper<PictureSort> delQueryWrapper = new LambdaQueryWrapper<>();
        delQueryWrapper.eq(PictureSort::getUid,uid);
        delQueryWrapper.eq(PictureSort::getStatus,EStatusEnum.ENABLE.getValue());
        PictureSort pictureSort = pictureSortDao.selectOne(delQueryWrapper);
        int i = 0;
        if (pictureSort!=null){
            pictureSort.setStatus(EStatusEnum.DISABLE.getValue());
            i = pictureSortDao.updateById(pictureSort);
        }
        return i>0? ResVo.buildSuccessMsgRes("删除成功!"):ResVo.buildErrRes("删除失败!");
    }
}




