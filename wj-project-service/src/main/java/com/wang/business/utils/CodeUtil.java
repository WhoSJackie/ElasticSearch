package com.wang.business.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CodeUtil {

    @Value("${codeLength}")
    private int codeLength;
    public  String getRandomValidCode(){
        char[] alphabet = new char[62];
        // 生成所有字母和数字
        for (int i = 0; i < 26; i++) {
            alphabet[i] = (char)('a'+i);
            alphabet[26+i] = (char)('A'+i);
            if (i<10){
                alphabet[52+i] = (char)('0'+i);
            }
        }

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            int ix = random.nextInt(alphabet.length);
            sb.append(alphabet[ix]);
        }
        return sb.toString();
    }

}
