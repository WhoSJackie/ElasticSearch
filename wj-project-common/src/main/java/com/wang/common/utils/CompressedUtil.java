package com.wang.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Slf4j
public class CompressedUtil {

    public static String compressed(String token){
        Deflater deflater = new Deflater();
        deflater.setInput(token.getBytes());
        deflater.finish();
        byte[] output = new byte[token.getBytes(StandardCharsets.UTF_8).length];
        int deflateLength = deflater.deflate(output);
        byte[] CompressedResult = new byte[deflateLength];
        System.arraycopy(output,0,CompressedResult,0,deflateLength);
        return new String(CompressedResult);
    }

    public static String deCompressed(String compressedToken){
        Inflater inflater = new Inflater();
        byte[] input = compressedToken.getBytes(StandardCharsets.UTF_8);
        inflater.setInput(input);
        byte[] deCompressedResult = new byte[input.length*2];
        try{
            int inflate = inflater.inflate(deCompressedResult);
            byte[] finalResult = new byte[inflate];
            System.arraycopy(deCompressedResult,0,finalResult,0,inflate);
            return new String(finalResult);
        } catch(Exception e){
            log.info("token解压缩错误");
        }
        return null;
    }

}
