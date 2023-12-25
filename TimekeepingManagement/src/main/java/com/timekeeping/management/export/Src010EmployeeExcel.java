package com.timekeeping.management.export;

import com.google.zxing.WriterException;
import com.timekeeping.management.form.Src010FormEmployee;
import com.timekeeping.management.service.impl.Src010ServiceImpl;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Component
public class Src010EmployeeExcel {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private static final String SHEET_NAME = "従業員の情報詳細";

    @Autowired
    private Src010ServiceImpl src010Service;


    public Src010EmployeeExcel() {
        workbook = new XSSFWorkbook();
    }

    /**
     * ヘッダ、タイトルの位置やカラ,背景などを設定
     */
    private void writeHeaderLine() {
        sheet = workbook.createSheet(SHEET_NAME);
        // -----------------------------------------title----------------------------------------//
        Row row = sheet.createRow(0);
        // Create cells in the row
        Cell cellA1 = row.createCell(0);
        Cell cellF1 = row.createCell(5);

        // Set cell values if needed
        cellA1.setCellValue("従業員の情報");
        // use XSSFFont when use RGB
        XSSFFont fontTitle = workbook.createFont();
        XSSFCellStyle instructionStyle = workbook.createCellStyle();
        instructionStyle.setWrapText(true);
        XSSFColor myColor = new XSSFColor(new java.awt.Color(254, 229,153));
        instructionStyle.setFillForegroundColor(myColor);
        instructionStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        fontTitle.setBold(false);
        fontTitle.setFontHeight(16);
        instructionStyle.setFont(fontTitle);
        instructionStyle.setAlignment(HorizontalAlignment.CENTER);
        instructionStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // Apply the style to the merged region
        cellA1.setCellStyle(instructionStyle);
        // Merge cells from A1 to F1
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
        // -----------------------------------------header-----------------------------------//

        Row row1 = sheet.createRow(1);
        XSSFCellStyle styleHeader = workbook.createCellStyle();
        XSSFColor headerColor = new XSSFColor(new java.awt.Color(197, 224,179));
        styleHeader.setFillForegroundColor(headerColor);
        styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = workbook.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBold(false);
        font.setFontHeightInPoints((short) 14);
        styleHeader.setFont(font);

        styleHeader.setBorderBottom(BorderStyle.THIN);
        createCell(row1, 0, "ID/ Mã nhân viên", styleHeader);
        createCell(row1, 1, "Tên nhân viên", styleHeader);
        createCell(row1, 2, "Địa chỉ email", styleHeader);
        createCell(row1, 3, "Số điện thoại", styleHeader);
        createCell(row1, 4, "Mã QR hiện tại", styleHeader);
        createCell(row1, 5, "QR Image", styleHeader);
    }

    /**
     *  セルを作成、セルの価に応じて設定
     * @param row     //行
     * @param columnCount　// 行のコラム
     * @param value　　　　//セルのバリュー
     * @param style　　　//　セルのスタイルを設定
     */
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof String) {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    /**
     *　セルのパスコードを設定関数
     * @param row
     * @param columnCount
     * @param qrCodeData
     * @param style
     * @throws IOException
     * @throws WriterException
     */
    private void createQRCodeCell(Row row, int columnCount, String qrCodeData, CellStyle style)
            throws IOException, WriterException {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);

        byte[] qrCodeImage = QrcodeExport.getQRCodeImage(qrCodeData);
        // create the object of ByteArrayInputStream class
        // and initialized it with the byte array.
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inStreambj = new ByteArrayInputStream(qrCodeImage);
        // read image from byte array
        BufferedImage newImage = ImageIO.read(inStreambj);
        if(newImage !=null){
            insertQRCodeImageToCell(workbook,sheet, row, columnCount,newImage);
        }
        cell.setCellStyle(style);
    }

    /**
     * セルにパスコードを挿入関数
     * @param workbook
     * @param sheet
     * @param row
     * @param columnCount
     * @param qrCodeImage
     */
    private void insertQRCodeImageToCell(Workbook workbook, Sheet sheet, Row row, int columnCount,
                                         BufferedImage qrCodeImage) {
        Drawing<?> drawing = sheet.createDrawingPatriarch();
        CreationHelper creationHelper = workbook.getCreationHelper();
        ClientAnchor anchor = creationHelper.createClientAnchor();
        anchor.setCol1(columnCount);
        anchor.setRow1(row.getRowNum());
        anchor.setCol2(columnCount + 1);
        anchor.setRow2(row.getRowNum() + 1);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(qrCodeImage, "png", byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int pictureFormat = Workbook.PICTURE_TYPE_PNG;
        byte[] pictureData = byteArrayOutputStream.toByteArray();
        int pictureIndex = workbook.addPicture(pictureData, pictureFormat);
        drawing.createPicture(anchor, pictureIndex);
    }

    /**
     *セルにデータを記載の関数
     * @param employee
     * @throws Exception
     */
    private void writeDataLines(Src010FormEmployee employee) throws Exception {
        int rowCount = 2;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        Row row = sheet.createRow(rowCount++);
        int columnCount = 0;
        createCell(row, columnCount++, employee.getEmployeeId(), style);
        createCell(row, columnCount++, employee.getName(), style);
        createCell(row, columnCount++, employee.getEmail(), style);
        createCell(row, columnCount++, employee.getPhone(), style);
        createCell(row, columnCount++, employee.getQrcode(), style);
        createQRCodeCell(row, columnCount++,employee.getQrcode(),style);
    }

    /**
     * 従業員のデータがbyte[]型 をエクスポート
     * @param employee //従業員
     * @return
     * @throws Exception
     */
    public byte[] employeeExportExcel(Src010FormEmployee employee) throws Exception {
        writeHeaderLine();
        writeDataLines(employee);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            workbook.close();
            return outputStream.toByteArray();
        }
    }


}
