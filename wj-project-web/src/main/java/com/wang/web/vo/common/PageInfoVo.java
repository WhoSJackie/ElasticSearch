package com.wang.web.vo.common;

import lombok.Data;

@Data
public class PageInfoVo<T> {

    private String keyWord;

    private Long currentPage;

    private Long pageSize;


}
