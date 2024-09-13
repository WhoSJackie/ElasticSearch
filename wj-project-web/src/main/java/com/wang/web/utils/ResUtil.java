package com.wang.web.utils;


import com.wang.common.enums.ResCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResUtil<T> {

    String message;

    T data;

    int code;

}
