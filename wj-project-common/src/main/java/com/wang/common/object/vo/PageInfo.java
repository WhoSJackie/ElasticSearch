package com.wang.common.object.vo;

import lombok.Data;

@Data
public class PageInfo<T> {

    private String keyWord;

    private Long currentPage;

    private Long pageSize;

}
