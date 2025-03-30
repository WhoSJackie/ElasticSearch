package com.wang.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.business.mysqldao.CategoryMenuDao;
import com.wang.business.service.CategoryMenuService;
import com.wang.common.enums.MenuButtonEnum;
import com.wang.common.enums.MenuLevelEnum;
import com.wang.common.object.entity.CategoryMenu;
import com.wang.common.object.vo.CategoryMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryMenuServiceImpl extends ServiceImpl<CategoryMenuDao, CategoryMenu> implements CategoryMenuService {

    @Autowired
    CategoryMenuDao categoryMenuDao;

    @Override
    public CategoryMenuVo getMenu(HttpServletRequest request) {
        CategoryMenuVo categoryMenuVo = new CategoryMenuVo();
        // todo:先查出所有的数据，后续加入权限
        List<CategoryMenu> categoryMenuList = categoryMenuDao.selectList(null);
        List<CategoryMenu> parentMenuList = new ArrayList<>();
        List<String> parentUids = new ArrayList<>();
        List<CategoryMenu> secondLevelMenuUids;
        List<CategoryMenu> buttonList = new ArrayList<>();
        secondLevelMenuUids = categoryMenuList.stream().filter(i->{
            return MenuLevelEnum.TWO.getCode().equals(i.getMenuLevel());
        }).collect(Collectors.toList());

        parentUids = categoryMenuList.stream().filter(i->{
            return MenuLevelEnum.TWO.getCode().equals(i.getMenuLevel());
        }).map(CategoryMenu::getParentUid).collect(Collectors.toList());

        parentMenuList = categoryMenuDao.selectBatchIds(parentUids);
        buttonList = categoryMenuList.stream().filter(i->{
            return MenuButtonEnum.IS_BUTTON.getValue().equals(i.getMenuType());
        }).collect(Collectors.toList());

        categoryMenuVo.setSonList(secondLevelMenuUids);
        categoryMenuVo.setParentList(parentMenuList);
        categoryMenuVo.setButtonList(buttonList);
        return categoryMenuVo;
    }
}
