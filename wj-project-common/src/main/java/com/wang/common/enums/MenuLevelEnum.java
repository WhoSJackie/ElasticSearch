package com.wang.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MenuLevelEnum {

    ONE("一级菜单",1),
    TWO("二级菜单",2),
    THREE("三级菜单",3);

    private  final String name;
    private  final Integer code;



}
