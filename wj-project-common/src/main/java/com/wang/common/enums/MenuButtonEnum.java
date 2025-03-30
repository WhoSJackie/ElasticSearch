package com.wang.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MenuButtonEnum {


    IS_BUTTON(1),

    NOT_BUTTON(0);

    private final Integer value;

}
