package com.demospring.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.demospring.biding.Responsebinding;
import com.demospring.entity.Report_entity;



public interface Reportervice {
    List<String> searchplanname();
    List<String> searchplanstatus();
    List<Report_entity> search(Responsebinding request);

    void gnrtexcel(List<Report_entity> plans, OutputStream out) throws IOException;
    void gnrtpdf(List<Report_entity> plans, OutputStream out) throws IOException;
}
