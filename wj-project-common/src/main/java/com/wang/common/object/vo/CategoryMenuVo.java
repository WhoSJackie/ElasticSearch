package com.wang.common.object.vo;

import com.wang.common.object.entity.CategoryMenu;
import lombok.Data;

import java.util.List;

@Data
public class CategoryMenuVo {

    List<CategoryMenu> sonList;

    List<CategoryMenu> parentList;

    List<CategoryMenu> buttonList;

}
