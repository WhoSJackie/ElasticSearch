package com.wang.common.object.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Update;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    /**
     * 排序权重
     */
    private Integer sort;


}
