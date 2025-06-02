package com.demospring.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.demospring.biding.Responsebinding;
import com.demospring.entity.Report_entity;
import com.demospring.repo.planrepo;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class Serviceimplement implements Reportervice {

    @Autowired
    private planrepo p;

    @Override
    public List<String> searchplanname() {
        return p.getplansname();
    }

    @Override
    public List<String> searchplanstatus() {
        return p.getplanstatus();
    }

    @Override
    public List<Report_entity> search(Responsebinding request) {
        Report_entity probe = new Report_entity();

        if (request.getPlanName() != null && !request.getPlanName().isBlank()) {
            probe.setPlanname(request.getPlanName());
        }

        if (request.getPlanStatus() != null && !request.getPlanStatus().isBlank()) {
            probe.setPlanstatus(request.getPlanStatus());
        }

        if (request.getGender() != null && !request.getGender().isBlank()) {
            probe.setGender(request.getGender());
        }

        if (request.getStartDate() != null) {
            probe.setStartdate(request.getStartDate());
        }

        if (request.getEndDate() != null) {
            probe.setEnddate(request.getEndDate());
        }

        return p.findAll(Example.of(probe));
    }

    @Override
    public void gnrtexcel(List<Report_entity> plans, OutputStream out) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Insurance Plans");

            Row header = sheet.createRow(0);
            String[] columns = { "ID", "Name", "Gender", "Plan Name", "Status", "Start Date", "End Date", "Benefit" };
            for (int i = 0; i < columns.length; i++) {
                header.createCell(i).setCellValue(columns[i]);
            }

            int rowIdx = 1;
            for (Report_entity plan : plans) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(plan.getId());
                row.createCell(1).setCellValue(plan.getName());
                row.createCell(2).setCellValue(plan.getGender());
                row.createCell(3).setCellValue(plan.getPlanname());
                row.createCell(4).setCellValue(plan.getPlanstatus());
                row.createCell(5).setCellValue(String.valueOf(plan.getStartdate()));
                row.createCell(6).setCellValue(String.valueOf(plan.getEnddate()));
                row.createCell(7).setCellValue(plan.getBenift());
            }

            workbook.write(out);
        }
    }

    @Override
    public void gnrtpdf(List<Report_entity> plans, OutputStream out) throws IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        Paragraph title = new Paragraph("Insurance Report");
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" ")); // blank line

        PdfPTable table = new PdfPTable(8);
        Stream.of("ID", "Name", "Gender", "Plan Name", "Status", "Start Date", "End Date", "Benefit")
                .forEach(col -> table.addCell(new PdfPCell(new Phrase(col))));

        for (Report_entity plan : plans) {
            table.addCell(String.valueOf(plan.getId()));
            table.addCell(plan.getName());
            table.addCell(plan.getGender());
            table.addCell(plan.getPlanname());
            table.addCell(plan.getPlanstatus());
            table.addCell(String.valueOf(plan.getStartdate()));
            table.addCell(String.valueOf(plan.getEnddate()));
            table.addCell(String.valueOf(plan.getBenift()));
        }

        document.add(table);
        document.close();
    }
}
