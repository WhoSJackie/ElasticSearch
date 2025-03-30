package com.wang.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum StatusEnum {


    ENABLE(1),

    DISABLE(0);

    private final int value;

}
