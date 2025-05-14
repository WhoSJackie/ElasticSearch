package com.wang.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlogTypeEnum {

    BLOG("0"),
    PROMOTE("1");

    private final String value;
}
