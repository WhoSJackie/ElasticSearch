package com.wang.business.pojo.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SysDictItemPojo {

    private Long id;

    private Integer dictItem;

    private String subitem;

    private String subitemName;

}
