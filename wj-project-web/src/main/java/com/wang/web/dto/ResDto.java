package com.wang.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResDto<T> {

    private boolean success;

    private String errMsg;

    private T data;

    public static <T>ResDto<T> buildErrRes(String msg){
        ResDto res = new ResDto();
        res.setData(null);
        res.setErrMsg(msg);
        res.setSuccess(false);
        return res;
    }

    public static <T>ResDto<T> buildSuccessRes(T data){
        ResDto res = new ResDto();
        res.setData(data);
        res.setErrMsg("success");
        res.setSuccess(true);
        return res;
    }


}
