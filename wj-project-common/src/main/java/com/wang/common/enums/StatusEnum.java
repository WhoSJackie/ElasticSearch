package com.wang.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum StatusEnum {


    ENABLE(1),

    DISABLE(0);

    private int value;

}
