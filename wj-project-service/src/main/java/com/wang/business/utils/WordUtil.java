package com.wang.business.utils;

import com.wang.common.object.entity.test.CISP002TbPojo;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WordUtil {
    public static Logger log = LoggerFactory.getLogger(WordUtil.class);

    public static List<String> wordReadForRuleItemVerify(String filePath){
        XWPFDocument document = null;
        List<CISP002TbPojo> itemList = new ArrayList<>();
        List<String> resList;
        try{
            document = new XWPFDocument(new FileInputStream(filePath));
            List<IBodyElement> elements = document.getBodyElements();
            for (IBodyElement element : elements) {
                // 如果是表格进行处理
                if (element instanceof XWPFTable){
                    itemList = handleWordTable((XWPFTable)element);
                }
            }
        } catch (Exception e){
            log.info(e.toString());
        } finally{
            try {
                document.close();
            } catch (IOException ioException) {
                log.info(ioException.toString());
            }
        }
        // 对res进行处理
        resList = itemList.stream().filter(i->"Y".equals(i.getNullable())).map(CISP002TbPojo::getEnFieldName).collect(Collectors.toList());
        return resList;
    }

    // 特殊word处理工具类
    private static List<CISP002TbPojo> handleWordTable(XWPFTable table){
        List<CISP002TbPojo> res = new ArrayList<>();
        List<XWPFTableRow> rows = table.getRows();
        int ix1 = 0;
        for (XWPFTableRow row : rows) {
            CISP002TbPojo pojo = new CISP002TbPojo();
            // 重置
            // 跳过第一行表头
            if (ix1++==0) continue;
            List<XWPFTableCell> tableCells = row.getTableCells();
            // todo 设置值，此处是否用反射会更好
            if (tableCells.size()<2) continue;
            pojo.setNo(tableCells.get(0).getText());
            pojo.setFieldDesc(tableCells.get(1).getText());
            pojo.setEnFieldName(tableCells.get(2).getText());
            pojo.setDataType(tableCells.get(3).getText());
            pojo.setNullable(tableCells.get(4).getText());
            pojo.setPkFlag(tableCells.get(5).getText());
            pojo.setDesc(tableCells.get(6).getText());
            res.add(pojo);
        }
        return res;
    }


    public static void main(String[] args) {
//        List<String> res = wordRead("C:\\Users\\jiami\\Desktop\\table.docx");
//        for (String s : res) {
//            System.out.println(s);
//        }
    }


}
