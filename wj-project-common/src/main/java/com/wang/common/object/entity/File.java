package com.wang.common.object.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wang.common.object.vo.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_file")
public class File extends SuperEntity<File> {

    private static final long serialVersionUID = 1L;

    private String fileOldName;

    private Long fileSize;

    private String fileSortUid;

    /**
     * 图片扩展名
     */
    private String picExpandedName;

    /**
     * 图片名称
     */
    private String picName;

    /**
     * 图片url地址
     */
    private String picUrl;

    /**
     * 管理员Uid
     */
    private String adminUid;

    /**
     * 用户Uid
     */
    private String userUid;

    /**
     * 七牛云Url
     */
    private String qiNiuUrl;

    /**
     * Minio文件URL
     */
    private String minioUrl;


}
