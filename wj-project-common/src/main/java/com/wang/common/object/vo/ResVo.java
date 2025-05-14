package com.wang.common.object.vo;

import com.wang.common.enums.ResCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResVo<T> {


    private String msg;

    private T data;

    private int code;

    public static <T> ResVo<T> buildErrRes(String msg){
        ResVo<T> res = new ResVo<T>();
        res.setData(null);
        res.setMsg(msg);
        res.setCode(ResCodeEnum.ERROR.getCode());
        return res;
    }

    public static <T> ResVo<T> buildSuccessRes(T data){
        ResVo<T> res = new ResVo<T>();
        res.setData(data);
        res.setMsg("操作成功!");
        res.setCode(ResCodeEnum.SUCCESS.getCode());
        return res;
    }

    public static <T>ResVo<T> buildSuccessMsgRes(String msg){
        ResVo<T> res = new ResVo<T>();
        res.setData(null);
        res.setMsg(msg);
        res.setCode(ResCodeEnum.SUCCESS.getCode());
        return res;
    }


}
