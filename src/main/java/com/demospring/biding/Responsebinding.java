package com.demospring.biding;

import java.time.LocalDate;
import lombok.Data;

@Data
public class Responsebinding {
    
    private String gender;
    private String planName;
    private String planStatus;
    private LocalDate startDate;
    private LocalDate endDate;
}

