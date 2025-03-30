package com.wang.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum BlogLevelEnum {

    LEVEL_ONE(1),
    LEVEL_TWO(2),
    LEVEL_THREE(3),
    LEVEL_FOUR(4);

    private final int value;

    public static BlogLevelEnum getEnumByValue(int val){
        return Stream.of(BlogLevelEnum.values()).filter(i->i.getValue()==val).findFirst().orElse(null);
    }


}
