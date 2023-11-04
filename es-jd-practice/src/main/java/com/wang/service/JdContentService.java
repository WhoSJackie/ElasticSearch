package com.wang.service;

import com.wang.pojo.QueryJdContentRes;

import java.io.IOException;

public interface JdContentService {

    boolean saveJdContent(String keyWord);

    QueryJdContentRes queryJdContent(String keyWord, int pageNo, int pageSize) throws IOException;

}
