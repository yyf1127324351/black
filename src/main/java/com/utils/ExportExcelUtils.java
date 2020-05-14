package com.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * Excel导出工具类
 */
@Slf4j
public class ExportExcelUtils {

    public static void exportXlsxFile(HttpServletResponse response,XSSFWorkbook wb, String fileName) {
        try {
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
        } catch (final Exception e) {
            e.printStackTrace();
            log.error("exportFileException:", e.getMessage());
        }
    }


    /**
     * 创建excel表格的列
     */
    public static void buildCell(XSSFRow row, int cellNum, String value) {
        if (row != null) {
            XSSFCell cell = row.createCell(cellNum);
            cell.setCellValue(new XSSFRichTextString(getNotNullStr(value)));
        }
    }
    public static void buildTitleCell(XSSFRow row, String title) {
        List<String> list = Arrays.asList(title.split(","));
        for (int i = 0; i < list.size(); i++) {
            buildCell(row, i, list.get(i));
        }
    }

    public static int buildCellNew(XSSFRow row, int cellNum, Object object) {
        if (row != null) {
            XSSFCell cell = row.createCell(cellNum);
            String value = "";
            if (null != object){
                value = object.toString();
            }
            cell.setCellValue(new XSSFRichTextString(value));
            return cellNum +1;
        }else {
            return cellNum;
        }
    }

    private static String getNotNullStr(String value) {
        return (value != null) ? value : "";
    }

    public static XSSFSheet initWorkBookSheet(XSSFWorkbook workbook, String sheetName, String title) {
        XSSFSheet sheet = workbook.createSheet(sheetName);
        for (int i = 0; i < 100; i++) {
            sheet.setColumnWidth(i, 5000);
        }
        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 9);
        font.setFontName("宋体");
        XSSFRow row = sheet.createRow(0);
        ExportExcelUtils.buildTitleCell(row,title);

        return sheet;
    }
}
