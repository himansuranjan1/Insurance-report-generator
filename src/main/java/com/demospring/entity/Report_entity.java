package com.demospring.entity;

import lombok.Data;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "report_entity")
@Data
public class Report_entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremented ID
    private Integer id;

    private String name;
    private String gender;
    private String planname;
    private String planstatus;
    private String deniedreason;
    private String terminatedreason;
    private LocalDate startdate;
    private LocalDate enddate;
    private Integer benift;
}

