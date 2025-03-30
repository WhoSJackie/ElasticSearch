package com.wang.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.common.object.entity.CategoryMenu;
import com.wang.common.object.vo.CategoryMenuVo;

import javax.servlet.http.HttpServletRequest;

public interface CategoryMenuService extends IService<CategoryMenu> {

    CategoryMenuVo getMenu(HttpServletRequest request);


}
