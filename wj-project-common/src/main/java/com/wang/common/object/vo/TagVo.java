package com.wang.common.object.vo;

import lombok.Data;

@Data
public class TagVo extends BaseVo<TagVo> {

    private static final long serialVersionUID = 1L;

    /**
     * 分类名称
     */
    private String content;


    /**
     * OrderBy排序字段（desc: 降序）
     */
    private String orderByDescColumn;

    /**
     * OrderBy排序字段（asc: 升序）
     */
    private String orderByAscColumn;

    public TagVo(){
    }

}
