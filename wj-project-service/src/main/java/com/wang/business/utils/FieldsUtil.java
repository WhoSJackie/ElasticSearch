package com.wang.business.utils;

import com.wang.business.pojo.JdContent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldsUtil {

    public static void fieldSetTool() throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        Class jdContent = JdContent.class;
        List<Map<String,String>> mapList = new ArrayList<>();
        Map<String,String> map1 = new HashMap<String,String>(){{
            put("title","aaa");
            put("img","aaa");
            put("price","12");
        }};
        Map<String,String> map2 = new HashMap<String,String>(){{
            put("title","bbb");
            put("img","bbb");
            put("price","15");
        }};
        Map<String,String> map3 = new HashMap<String,String>(){{
            put("title","ccc");
            put("img","ccc");
            put("Price","18");
        }};
        mapList.add(map1);
        mapList.add(map2);
        mapList.add(map3);
        List<JdContent> contents = new ArrayList<>();
        for (Map<String, String> map : mapList) {
            JdContent instance = (JdContent)jdContent.newInstance();
            Field[] fields = jdContent.getDeclaredFields();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                for (Field field : fields) {
                    String tmpName = field.getName();
                    if (tmpName.equalsIgnoreCase(entry.getKey())){
                        Field f = jdContent.getDeclaredField(tmpName);
                        f.setAccessible(true);
                        f.set(instance,entry.getValue());
                    }
                }
            }
            contents.add(instance);
        }
        for (JdContent instance : contents) {
            System.out.println(instance.getImg()+"-->"+instance.getPrice()+"-->"+instance.getTitle());
        }

    }

    public static void main(String[] args) throws Exception {
        fieldSetTool();
    }


}
