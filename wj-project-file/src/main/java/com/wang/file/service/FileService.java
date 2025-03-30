package com.wang.file.service;

import com.wang.common.object.entity.File;

import java.util.List;

public interface FileService {

    List<File> getPicture(List<String> uids);

    List<File> getAllPicture();

}
