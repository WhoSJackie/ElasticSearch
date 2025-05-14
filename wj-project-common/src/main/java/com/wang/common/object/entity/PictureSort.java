package com.wang.common.object.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wang.common.object.vo.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 图片分类表
 * @TableName t_picture_sort
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_picture_sort")
public class PictureSort extends SuperEntity<PictureSort> {

    private static final long serialVersionUID = 1L;

    /**
     * 分类图片uid
     */
    private String fileUid;

    /**
     * 分类名
     */
    private String name;

    /**
     *
     */
    private String parentUid;

    /**
     * 排序字段，越大越靠前
     */
    private Integer sort;

    /**
     * 是否显示，1：是，0，否
     */
    private Integer isShow;

    /**
     * 分类图
     */
    @TableField(exist=false)
    private List<File> photoList;


}
