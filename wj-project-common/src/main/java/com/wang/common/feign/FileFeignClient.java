package com.wang.common.feign;

import com.wang.common.object.entity.File;
import com.wang.common.object.req.FileRequest;
import com.wang.common.object.vo.ResVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@FeignClient(name = "wjfile")
public interface  FileFeignClient{

    @PostMapping("/file/getPicture")
    ResVo<List<File>> getPicture(@RequestBody FileRequest request);

}
