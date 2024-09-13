package com.wang.web.vo.common;

import lombok.Data;

@Data
public class BaseVo<T> extends PageInfoVo<T>{

    private String uid;

    private Integer status;

}
