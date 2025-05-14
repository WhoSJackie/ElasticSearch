package com.wang.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.common.object.entity.PictureSort;
import com.wang.common.object.vo.PageInfo;
import com.wang.common.object.vo.PictureSortVo;

/**
* @author jiami
* @description 针对表【t_picture_sort(图片分类表)】的数据库操作Service
* @createDate 2025-04-05 11:02:34
*/
public interface PictureSortService extends IService<PictureSort> {

    IPage<PictureSort> getPageInfo(PictureSortVo pictureSortVo);

    PictureSort getSortByUid(PictureSortVo pictureSortVo);

}
