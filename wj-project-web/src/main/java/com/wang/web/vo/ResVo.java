package com.wang.web.vo;

import com.wang.common.enums.ResCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResVo<T> {


    private String errMsg;

    private T data;

    private int code;

    public static <T> ResVo<T> buildErrRes(String msg){
        ResVo res = new ResVo();
        res.setData(null);
        res.setErrMsg(msg);
        res.setCode(ResCodeEnum.ERROR.getCode());
        return res;
    }

    public static <T> ResVo<T> buildSuccessRes(T data){
        ResVo res = new ResVo();
        res.setData(data);
        res.setErrMsg("success");
        res.setCode(ResCodeEnum.SUCCESS.getCode());
        return res;
    }


}
