package com.wang.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EStatusEnum {

    /**
     * 删除的
     */
    DISABLE(0),
    /**
     * 激活的
     */
    ENABLE(1),
    /**
     * 冻结的
     */
    FREEZE(2),
    /**
     * 置顶的
     */
    STICK(3);

    private int value;



}
