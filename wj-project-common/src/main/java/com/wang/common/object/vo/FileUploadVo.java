package com.wang.common.object.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadVo{

    private List<MultipartFile> filedatas;

    private String source;

    private String userUid;

    private String adminUid;

    private String projectName;

    private String sortName;

    private String token;

}
