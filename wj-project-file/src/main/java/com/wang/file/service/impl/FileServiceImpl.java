package com.wang.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wang.common.enums.EStatusEnum;
import com.wang.common.object.entity.FileSort;
import com.wang.common.object.resp.FileResponse;
import com.wang.common.object.vo.FileUploadVo;
import com.wang.common.utils.FileUtils;
import com.wang.file.dao.FileDao;
import com.wang.common.object.entity.File;
import com.wang.file.dao.FileSortDao;
import com.wang.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    FileDao fileDao;

    @Autowired
    FileSortDao fileSortDao;

    @Value("${file.uploadPath}")
    private String uploadPath;

    @Value("${prePicUrl}")
    private String prePicUrl;

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

    @Override
    public FileResponse uploadPicture(FileUploadVo fileUploadVo) {
        FileResponse fileResponse = new FileResponse();
        List<String> errFileList = new ArrayList<>();
        List<File> succFileList = new ArrayList<>();
        // 保存文件到服务器
        List<MultipartFile> filedatas = fileUploadVo.getFiledatas();
        File file = new File();
        LambdaQueryWrapper<FileSort> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(FileSort::getSortName,fileUploadVo.getSortName());
        queryWrapper.eq(FileSort::getStatus, EStatusEnum.ENABLE.getValue());
        FileSort fileSort = fileSortDao.selectOne(queryWrapper);
        file.setFileSortUid(fileSort.getUid());
        // 如果有文件上传失败，则全部失败
        for (MultipartFile filedata : filedatas) {
            String errFile = "";
            try{
                // todo: 如果文件相同该怎么办，继续上传还是...,(按照时间戳重命名,生成新的文件)
                String originName = filedata.getOriginalFilename();
                String newName = FileUtils.genFileName(originName);
                String serverFilePath = uploadPath+newName;
                file.setFileOldName(originName);
                // 后续改成统一名称
                file.setPicName(newName);
                file.setPicUrl(prePicUrl+newName);
                file.setFileSize(filedata.getSize());
                file.setUserUid(fileUploadVo.getUserUid());
                file.setAdminUid(fileUploadVo.getAdminUid());
                file.setPicExpandedName(com.wang.common.utils.FileUtils.getExpandedName(originName));
                filedata.transferTo(new java.io.File(serverFilePath));
                errFile = originName;
                fileDao.insert(file);
                succFileList.add(file);
            } catch(Exception e){
                log.error("保存文件{}失败",errFile);
                errFileList.add(errFile);
            }
        }
        fileResponse.setErrFile(errFileList);
        fileResponse.setSuccFileList(succFileList);
        return fileResponse;
    }

}
