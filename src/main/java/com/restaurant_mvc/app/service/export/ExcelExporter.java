package com.restaurant_mvc.app.service.export;

import com.restaurant_mvc.app.domain.Order;
import com.restaurant_mvc.app.utils.Constants;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter implements Exporter{

    @Override
    public void exportFile(List<Order> orders) throws IOException {
        Workbook workbook = new XSSFWorkbook ();
        Sheet sheet = workbook.createSheet("Orders");

        String[] headers = {"ID", "Total", "Order Date", "Status"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowNum = 1;
        for (Order order : orders) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(order.getId());
            row.createCell(1).setCellValue(order.getTotal());
            row.createCell(2).setCellValue(order.getOrderDateTime().toString());
            row.createCell(3).setCellValue(order.getStatus().toString ());
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(Constants.DEFAULT_PATH)) {
            workbook.write(fileOutputStream);
        }
    }
}
