package com.wang.business.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wang.business.mysqldao.PictureDao;
import com.wang.business.service.PictureService;
import com.wang.common.feign.FileFeignClient;
import com.wang.common.object.entity.File;
import com.wang.common.object.entity.Picture;
import com.wang.common.object.vo.ResVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@EnableScheduling
public class DelUnusedFileJob {


    @Autowired
    private PictureService pictureService;

    @Value("${file.uploadPath}")
    private String uploadPath;
    @Scheduled(cron = "0 0 15 5 * ?")
    public void delTask(){
        // 仅删除未关联picture的文件，软删除的不管
        List<Picture> pictureList = pictureService.selectAllPic();
        if (CollectionUtils.isEmpty(pictureList)){
            log.info("没有需要删除的文件");
            return;
        }
        Set<String> pictureNameList = pictureList.stream().map(Picture::getPicName).collect(Collectors.toSet());
        java.io.File  fileDir = new java.io.File(uploadPath);
        java.io.File[] files = fileDir.listFiles();
        for (java.io.File file : files) {
            try{
                if (!pictureNameList.contains(file.getName())){
                    // 进行删除
                    boolean delFlag = file.delete();
                    if (!delFlag) log.info("路径为{}的文件删除失败",file.getName());
                }
            } catch(Exception e){
                log.info("名称为{}的文件删除失败,失败原因{}",file.getName(),e);
            }
        }
    }

}
