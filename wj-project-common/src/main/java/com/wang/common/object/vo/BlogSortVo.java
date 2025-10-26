package com.wang.common.object.vo;

import com.wang.common.object.entity.BlogSort;
import com.wang.common.object.entity.Tag;
import lombok.Data;

import java.util.List;

@Data
public class BlogSortVo extends BaseVo<BlogSortVo> {

    private static final long serialVersionUID = 1L;

    /**
     * 分类名称
     */
    private String sortName;

    /**
     * 分类介绍
     */
    private String content;

    /**
     * 排序
     */
    private Integer sort;


    /**
     * OrderBy排序字段（desc: 降序）
     */
    private String orderByDescColumn;

    /**
     * OrderBy排序字段（asc: 升序）
     */
    private String orderByAscColumn;

    public BlogSortVo(){
    }

}
