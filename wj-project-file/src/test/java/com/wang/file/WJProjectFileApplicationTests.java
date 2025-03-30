package com.wang.file;


import com.wang.common.object.entity.File;
import com.wang.file.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class WJProjectFileApplicationTests {

    @Autowired
    FileService fileService;

    @Test
    void contextLoads() {
    }

    @Test
    void getPicTest() {
        List<String> list = new ArrayList<>();
        fileService.getPicture(list);
    }

    @Test
    void getSinglePicTest() {
        List<File> res= fileService.getAllPicture();
        for (File re : res) {
            System.out.println(re.getPicName());
            System.out.println(re.getUid());
        }
    }




}
