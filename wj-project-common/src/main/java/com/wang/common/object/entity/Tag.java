package com.wang.common.object.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wang.common.object.vo.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_tag")
public class Tag extends SuperEntity<Tag> {

    private static final long serialVersionUID = 1L;

    /**
     * 标签内容
     */
    private String content;

    /**
     * 标签简介
     */
    private int clickCount;

    /**
     * 排序字段，数值越大，越靠前
     */
    private int sort;


}
