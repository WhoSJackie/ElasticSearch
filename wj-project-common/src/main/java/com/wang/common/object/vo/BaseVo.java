package com.wang.common.object.vo;

import lombok.Data;

@Data
public class BaseVo<T> extends PageInfo<T> {

    private String uid;

    private Integer status;

}
