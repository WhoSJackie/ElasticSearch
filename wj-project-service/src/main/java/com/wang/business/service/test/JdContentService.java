package com.wang.business.service.test;

import com.wang.common.object.entity.test.QueryJdContentRes;

import java.io.IOException;

public interface JdContentService {

    boolean saveJdContent(String keyWord);

    QueryJdContentRes queryJdContent(String keyWord, int pageNo, int pageSize) throws IOException;

}
