package com.demospring.controller;

import java.io.OutputStream;
import java.util.List;

import com.demospring.biding.Responsebinding;
import com.demospring.entity.Report_entity;
import com.demospring.service.Reportervice;
import com.demospring.utills.Excelgnrt;
import com.demospring.utills.Pdfgnrt;
import com.demospring.utills.Mailsndr;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class Reportcontroller {

    @Autowired
    private Reportervice reportService;

    @Autowired
    private Pdfgnrt pdfGenerator;

    @Autowired
    private Excelgnrt excelGenerator;

    @Autowired
    private Mailsndr mailSenderUtil;

    @GetMapping("/")
    public String loadHome(Model model) {
        model.addAttribute("title", "Insurance Report Generator");
        model.addAttribute("search", new Responsebinding());
        init(model);
        return "Home";
    }

    @PostMapping("/search")
    public String searchResults(Model model, @ModelAttribute Responsebinding request) {
        model.addAttribute("search", request);

        List<Report_entity> plans = reportService.search(request);
        model.addAttribute("plans", plans);

        init(model);
        return "Home";
    }

    private void init(Model model) {
        model.addAttribute("planNames", reportService.searchplanname());
        model.addAttribute("planStatuses", reportService.searchplanstatus());
    }

    @PostMapping("/download")
    public String downloadFile(
        @ModelAttribute Responsebinding request,
        @RequestParam("type") String type,
        @RequestParam("email") String email,
        Model model
    ) {
        try {
            byte[] data;

            if ("excel".equalsIgnoreCase(type)) {
                data = excelGenerator.generateExcel(request);
            } else {
                data = pdfGenerator.generatePdf(request);
            }

            if (email != null && !email.trim().isEmpty()) {
                mailSenderUtil.sendFile(email, type, data);
                model.addAttribute("message", "Report sent successfully to " + email);
            } else {
                model.addAttribute("error", "Email is required to send the file.");
            }

        } catch (Exception e) {
            model.addAttribute("error", "Something went wrong while sending the report.");
            e.printStackTrace();
        }

        // Reinitialize page with search data
        model.addAttribute("search", request);
        model.addAttribute("plans", reportService.search(request));
        init(model);

        return "Home"; // Return to the same page
    }

}
