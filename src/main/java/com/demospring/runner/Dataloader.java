package com.demospring.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.demospring.entity.Report_entity;
import com.demospring.repo.planrepo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class Dataloader implements CommandLineRunner {

    @Autowired
    private planrepo planRepo;

    @Override
    public void run(String... args) throws Exception {
        // ❗ Delete all existing data
        planRepo.deleteAll();

        // Create and save first report
        Report_entity report1 = new Report_entity();
        report1.setName("John Doe");
        report1.setGender("Male");
        report1.setPlanname("Health");
        report1.setPlanstatus("Approved");
        report1.setStartdate(LocalDate.now());
        report1.setEnddate(LocalDate.now().plusMonths(6));
        report1.setBenift(12000);

        // Second report
        Report_entity report2 = new Report_entity();
        report2.setName("Jane Smith");
        report2.setGender("Female");
        report2.setPlanname("Food");
        report2.setPlanstatus("Denied");
        report2.setDeniedreason("Incomplete Documentation");

        // Third report
        Report_entity report3 = new Report_entity();
        report3.setName("Bob Johnson");
        report3.setGender("Male");
        report3.setPlanname("Cash");
        report3.setPlanstatus("Terminated");
        report3.setTerminatedreason("Policy Violation");
        report3.setStartdate(LocalDate.now().minusMonths(5));
        report3.setEnddate(LocalDate.now());
        report3.setBenift(8000);

        // Fourth report
        Report_entity report4 = new Report_entity();
        report4.setName("Alice Williams");
        report4.setGender("Female");
        report4.setPlanname("Health Plan D");
        report4.setPlanstatus("Approved");
        report4.setStartdate(LocalDate.now().minusDays(10));
        report4.setEnddate(LocalDate.now().plusMonths(4));
        report4.setBenift(15000);

        // Fifth report
        Report_entity report5 = new Report_entity();
        report5.setName("David Miller");
        report5.setGender("Male");
        report5.setPlanname("Employment");
        report5.setPlanstatus("Terminated");
        report5.setTerminatedreason("Fraudulent Claim");
        report5.setStartdate(LocalDate.now().minusMonths(3));
        report5.setEnddate(LocalDate.now().minusDays(1));
        report5.setBenift(9500);

        // Save all to database
        List<Report_entity> reports = Arrays.asList(report1, report2, report3, report4, report5);
        planRepo.saveAll(reports);

        System.out.println("✅ Sample report data loaded successfully.");
    }
}
