package com.wang.common.object.vo;

import lombok.Data;

@Data
public class PictureSortVo extends BaseVo<PictureSortVo>{

    private String fileUid;

    private String name;

    private Integer sort;

    private Integer isShow;

}
