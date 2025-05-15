package com.wang.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.common.object.entity.Picture;
import com.wang.common.object.vo.PictureVo;

import java.util.List;

/**
* @author jiami
* @description 针对表【t_picture(图片表)】的数据库操作Service
* @createDate 2025-04-05 10:57:23
*/
public interface PictureService extends IService<Picture> {

    IPage<Picture> getPicPage(PictureVo pictureVo);

    int addPicture(List<PictureVo> pictureVoList);

    int deleteBatchPictures(String picUrls);

    List<Picture> selectAllPic();

}
