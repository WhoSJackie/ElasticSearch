package com.wang.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.business.mysqldao.PictureDao;
import com.wang.business.service.PictureService;
import com.wang.common.constants.Constants;
import com.wang.common.enums.EStatusEnum;
import com.wang.common.feign.FileFeignClient;
import com.wang.common.object.entity.File;
import com.wang.common.object.entity.Picture;
import com.wang.common.object.entity.PictureSort;
import com.wang.common.object.req.FileRequest;
import com.wang.common.object.vo.PictureVo;
import com.wang.common.object.vo.ResVo;
import com.wang.common.utils.StrUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
* @description 针对表【t_picture(图片表)】的数据库操作Service实现
* @createDate 2025-04-05 10:57:23
*/
@Service
public class PictureServiceImpl extends ServiceImpl<PictureDao, Picture> implements PictureService {

    @Autowired
    private PictureDao pictureDao;
    @Autowired
    private FileFeignClient fileFeignClient;


    @Override
    public IPage<Picture> getPicPage(PictureVo pictureVo) {
        Page page = new Page();
        page.setCurrent(1);
        page.setSize(10);
        LambdaQueryWrapper<Picture> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Picture::getStatus, EStatusEnum.ENABLE.getValue());
        if (!StringUtils.isEmpty(pictureVo.getPictureSortUid())){
            queryWrapper.eq(Picture::getPictureSortUid,pictureVo.getPictureSortUid());
        }
        if (pictureVo.getPageSize()!=0 && pictureVo.getCurrentPage()!=0){
            page.setCurrent(pictureVo.getCurrentPage());
            page.setSize(pictureVo.getPageSize());
        }
        IPage picPage = pictureDao.selectPage(page, queryWrapper);
        List<Picture> picList = picPage.getRecords();
        List<String> fileUids = new ArrayList<>();
        if (!CollectionUtils.isEmpty(picList)) {
            picList.forEach(i->{
                fileUids.add(i.getFileUid());
            });
            FileRequest request = new FileRequest();
            request.setUidList(fileUids);
            ResVo<List<File>> pictureRes = fileFeignClient.getPictureByUids(request);
            List<File> pictureList = pictureRes.getData();
            Map<String,String> fileMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(pictureList)){
                fileMap = pictureList.stream().collect(Collectors.toMap(File::getUid, File::getPicUrl,(o, n)->o));
            }
            for (Picture i:picList) {
                String s = i.getFileUid();
                if (fileMap.containsKey(s)){
                    i.setPictureUrl(fileMap.get(s));
                }
            }
        }
        picPage.setRecords(picList);
        return picPage;
    }

    @Override
    public int addPicture(List<PictureVo> pictureVoList) {
        int cnt=0;
        for (PictureVo pictureVo : pictureVoList) {
            Picture picture = new Picture();
            picture.setPictureSortUid(pictureVo.getPictureSortUid());
            picture.setPicName(pictureVo.getPicName());
            picture.setFileUid(pictureVo.getFileUid());
            cnt+=pictureDao.insert(picture);
        }
        return cnt;
    }

    @Override
    public int deleteBatchPictures(String picUrls) {
        if (StringUtils.isEmpty(picUrls)){
            return 0;
        }
        // 参数使用逗号分隔多个uid
        List<String> picList = StrUtils.StringToList(picUrls, Constants.SYMBOL_COMMA);
        LambdaQueryWrapper<Picture> queryWrapper = new LambdaQueryWrapper<>();
        int cnt=0;
        for (String s : picList) {
            queryWrapper.eq(Picture::getUid,s);
            queryWrapper.eq(Picture::getStatus,EStatusEnum.ENABLE.getValue());
            queryWrapper.last("limit 1");
            Picture picture = pictureDao.selectOne(queryWrapper);
            if (picture!=null){
                picture.setStatus(EStatusEnum.DISABLE.getValue());
                cnt+= pictureDao.updateById(picture);
            }
        }
        return cnt;
    }

    @Override
    public List<Picture> selectAllPic() {
        return pictureDao.selectList(null);
    }
}
