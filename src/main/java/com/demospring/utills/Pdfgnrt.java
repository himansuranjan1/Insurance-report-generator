package com.demospring.utills;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demospring.biding.Responsebinding;
import com.demospring.entity.Report_entity;
import com.demospring.service.Reportervice;
import java.awt.Color; 


import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component
public class Pdfgnrt {

    @Autowired
    private Reportervice reportService;

    public byte[] generatePdf(Responsebinding request) throws Exception {
        List<Report_entity> plans = reportService.search(request);

        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Insurance Plans Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20f);
        document.add(title);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        addTableHeader(table);
        addTableRows(table, plans);

        document.add(table);
        document.close();

        return out.toByteArray();
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("ID", "Plan Name", "Plan Status", "Holder Name", "Start Date", "End Date")
                .forEach(columnTitle -> {
                	PdfPCell header = new PdfPCell();
                	header.setBackgroundColor(Color.LIGHT_GRAY); // ✅ This works with OpenPDF
                	header.setHorizontalAlignment(Element.ALIGN_CENTER);
                	header.setPhrase(new Phrase(columnTitle));
                	table.addCell(header);
                });
    }

    private void addTableRows(PdfPTable table, List<Report_entity> plans) {
        for (Report_entity plan : plans) {
            table.addCell(String.valueOf(plan.getId()));
            table.addCell(plan.getPlanname());
            table.addCell(plan.getPlanstatus());
            table.addCell(plan.getName());
            table.addCell(plan.getStartdate() != null ? plan.getStartdate().toString() : "");
            table.addCell(plan.getEnddate() != null ? plan.getEnddate().toString() : "");
        }
    }
}
