package com.wang.common.object.entity;



import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wang.common.object.vo.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import java.util.Date;

/**
* 图片表
* @TableName t_picture
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_picture")
public class Picture extends SuperEntity<Picture> {


    /**
    * 图片uid
    */
    private String fileUid;
    /**
    * 图片名
    */
    private String picName;
    /**
    * 分类uid
    */
    private String pictureSortUid;


    @TableField(exist=false)
    private String pictureUrl;

}
