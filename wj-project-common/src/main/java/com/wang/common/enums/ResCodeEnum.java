package com.wang.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResCodeEnum {

    SUCCESS(0),
    ERROR(-1);

    int code;

}
