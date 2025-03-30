package com.wang.common.object.entity.test;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("sys_dict_item")
public class SysDictItemPojo {

    private Long id;

    private Integer dictItem;

    private String subitem;

    private String subitemName;

}
