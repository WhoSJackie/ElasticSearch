package com.wang.common.feign;

import com.wang.common.object.entity.File;
import com.wang.common.object.req.FileRequest;
import com.wang.common.object.resp.FileResponse;
import com.wang.common.object.vo.FileUploadVo;
import com.wang.common.object.vo.ResVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@FeignClient(name = "wjfile")
public interface  FileFeignClient{

    @PostMapping("/file/getPicture")
    ResVo<List<File>> getPicture(@RequestBody FileRequest request);

    @PostMapping(value="/file/pictures",consumes = "multipart/form-data")
    ResVo<FileResponse> uploadPicture(@RequestPart("files") List<MultipartFile> files,
                                      @RequestParam("source")String source,
                                      @RequestParam("userUid")String userUid,
                                      @RequestParam("adminUid")String adminUid,
                                      @RequestParam("projectName")String projectName,
                                      @RequestParam("sortName")String sortName,
                                      @RequestParam("token") String token);

}
