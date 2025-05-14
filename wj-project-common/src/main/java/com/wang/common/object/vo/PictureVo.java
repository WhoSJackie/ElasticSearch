package com.wang.common.object.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureVo extends BaseVo<PictureVo>{

    /**
     * 图片UID
     */
    private String fileUid;

    /**
     * 图片UIDs
     */
    private String fileUids;

    /**
     * 图片名称
     */
    private String picName;

    /**
     * 所属相册分类UID
     */
    private String pictureSortUid;

}
