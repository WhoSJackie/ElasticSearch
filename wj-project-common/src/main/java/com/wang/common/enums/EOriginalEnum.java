package com.wang.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum EOriginalEnum {

    ORIGINAL("1"),
    UNORIGINAL ("0");

    private final String value;


}
