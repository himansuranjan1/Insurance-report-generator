package com.demospring.utills;

import com.demospring.biding.Responsebinding;
import com.demospring.entity.Report_entity;
import com.demospring.service.Reportervice;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class Excelgnrt {

    @Autowired
    private Reportervice reportService;

    public byte[] generateExcel(Responsebinding request) throws IOException {
        List<Report_entity> plans = reportService.search(request);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Insurance Plans");

            // Header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Plan Name", "Plan Status", "Holder Name", "Start Date", "End Date"};

            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Data rows
            int rowIdx = 1;
            for (Report_entity plan : plans) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(plan.getId());
                row.createCell(1).setCellValue(plan.getPlanname());
                row.createCell(2).setCellValue(plan.getPlanstatus());
                row.createCell(3).setCellValue(plan.getName());
                row.createCell(4).setCellValue(plan.getStartdate() != null ? plan.getStartdate().toString() : "");
                row.createCell(5).setCellValue(plan.getEnddate() != null ? plan.getEnddate().toString() : "");
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }
}
