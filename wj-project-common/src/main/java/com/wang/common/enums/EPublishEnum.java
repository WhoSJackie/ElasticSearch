package com.wang.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EPublishEnum {

    /**
     * 否
     */
    DISABLE(0),
    /**
     * 是
     */
    ENABLE(1);

    private final int value;


}
