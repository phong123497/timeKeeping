package com.timekeeping.management.export;

import com.timekeeping.management.dto.src007.Src007Dto;
import com.timekeeping.management.dto.src007.Src007ListDto;
import com.timekeeping.management.form.Src007Form;
import com.timekeeping.management.service.impl.Src007Service;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class Src007EmployeeExport {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;


    private static final String SHEET_NAME = "従業員のチェックタイム";
    @Autowired
    private Src007Service src007Service;


    public Src007EmployeeExport(){
        workbook = new XSSFWorkbook();
    }
    private void writeHeaderLine() {
        sheet = workbook.createSheet(SHEET_NAME);
        Row row = sheet.createRow(0);


        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Giờ vào làm", style);
        createCell(row, 1, "Giờ tan làm", style);
        createCell(row, 2, "Mã QR đã sử dụng", style);
        createCell(row, 3, "Trạng thái", style);
        createCell(row, 4, "Ghi chú", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof String) {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines(Src007Form src007Form) throws Exception {
        Src007ListDto src007ListEntity = src007Service.getListCheckTimeByEmployee(src007Form);

        List<Src007Dto> listEmployee = src007ListEntity.getListTimeChecked();

        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Src007Dto employ : listEmployee) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, employ.getStartTime(), style);
            createCell(row, columnCount++, employ.getEndTime(), style);
            createCell(row, columnCount++, employ.getQrcode(), style);
            createCell(row, columnCount++, employ.getStatus(), style);
            createCell(row, columnCount++, employ.getNote(), style);
        }
    }
    public byte[] exportExcel(Src007Form src007Form) throws Exception {
        writeHeaderLine();
        writeDataLines(src007Form);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            workbook.close();
            return outputStream.toByteArray();
        }
    }



}
