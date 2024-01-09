package com.wang.business.utils;

import com.wang.business.pojo.TxtInsertPojo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TxtFileUtils {


    public static List<TxtInsertPojo> convertToPojo(String filename){
        File file = new File(filename);
        List<String> strList = new ArrayList<>();
        List<TxtInsertPojo> txtInsertPojoList = new ArrayList<>();
        BufferedReader bufferedReader=null;
        int i=0;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            while (line!=null){
                TxtInsertPojo item = new TxtInsertPojo();
                String[] strs = line.split("，");
                if (strs.length==2){
                    item.setProwno(strs[0].trim());
                    item.setZcol(strs[1].trim());
                } else{
                    throw new RuntimeException("string length error");
                }
                txtInsertPojoList.add(item);
                i++;
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return txtInsertPojoList;
    }



    public static void main(String[] args) {
        List<TxtInsertPojo> txtInsertPojoList = convertToPojo("C:\\Users\\jiami\\Desktop\\InsertFile.txt");
        for (int i = 0; i < txtInsertPojoList.size(); i++) {
            TxtInsertPojo txtInsertPojo = txtInsertPojoList.get(i);
            System.out.println((i+1)+"->"+txtInsertPojo.getProwno()+"->"+txtInsertPojo.getZcol());
        }
    }


}
