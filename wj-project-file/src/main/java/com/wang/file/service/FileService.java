package com.wang.file.service;

import com.wang.common.object.entity.File;
import com.wang.common.object.resp.FileResponse;
import com.wang.common.object.vo.FileUploadVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    List<File> getPicture(List<String> uids);

    List<File> getAllPicture();

    FileResponse uploadPicture(FileUploadVo fileUploadVo);

}
