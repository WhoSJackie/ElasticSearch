package com.wang.common.utils;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ExcelUtil {

    public static Map<String,String> readExcel(int sheetNo,int startRow,String filename){
        File file = new File(filename);
        Map<String,String> cells = new TreeMap<>();
        FileInputStream inputStream = null;
        Workbook workbook = null;
        try {
            inputStream  = new FileInputStream(file);
            workbook = WorkbookFactory.create(inputStream);
            // 可以读取大excel文件,但只能用迭代器读取，没有getRow方法（直接抛异常）
//            workbook =  StreamingReader.builder()
//                    .bufferSize(4096) // 设置缓存的大小
//                    .rowCacheSize(100) // 缓存行的数量，也就是每次读取多少行到内存中，而不是一下子全都加载进内存
//                    .open(inputStream); // 设置要打开的文件
            Sheet sheet = workbook.getSheetAt(sheetNo);
            for (int i= startRow;i<=sheet.getLastRowNum();i++){
                Row row = sheet.getRow(i);
                Iterator<Cell> cellIterator = row.cellIterator();
                List<String> temp = new ArrayList<>();
                while (cellIterator.hasNext()) {
                    Cell next = cellIterator.next();
                    String value = "";
                    if (next!=null){
                        value = new DataFormatter().formatCellValue(next);
                    }
                    if (!("".equals(value))) {

                        temp.add(value);
                    }
                }
                cells.put(temp.get(0),temp.get(1));
            }
            // 进行一级二级拼接,treemap有顺序，因此先二级后三级的方式
            for (Map.Entry<String, String> entry : cells.entrySet()) {
                String areaCode = entry.getKey();
                if (!"0000".equals(areaCode.substring(2,6))){
                    // 先处理二级
                    if ("00".equals(areaCode.substring(4,6))){
                        cells.put(areaCode,cells.get(areaCode.substring(0,2)+"0000")+cells.get(areaCode));
                    }
                    // 三级直接拼接二级
                    if (!"00".equals(areaCode.substring(4,6))){
                        cells.put(areaCode,cells.get(areaCode.substring(0,4)+"00")+cells.get(areaCode));
                    }
                }
                System.out.println(entry.getKey()+"->"+entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if (inputStream!=null){
                    inputStream.close();
                }
                if (workbook!=null){
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cells;
    }

    public static void writeExcel(Map<String,String> value,String filename) {
        FileOutputStream fileOutputStream = null;
        Workbook workbook = null;
        try {
             workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("sheet1");
            int index=0;
            for (Map.Entry<String, String> entry : value.entrySet()) {
                Row row = sheet.createRow(index);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(entry.getKey());
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(entry.getValue());
                index++;
            }
            fileOutputStream = new FileOutputStream(filename);
            workbook.write(fileOutputStream);
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        Map<String, String> map = readExcel(5, 2, "C:\\Users\\jiami\\Desktop\\资管产品统计校验规则-20240115.xlsx");
        // writeExcel(map,"C:\\Users\\jiami\\Desktop\\81245.xlsx");
    }

}
