package com.wang.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum BlogRecommendEnum {

    FIRST_RECOMMEND(1),
    SECOND_RECOMMEND(2),
    THIRD_RECOMMEND(3),
    FOUTH_RECOMMEND(4);

    private final int value;


}
