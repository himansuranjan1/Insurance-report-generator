package com.demospring.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.demospring.entity.Report_entity;

public interface planrepo extends JpaRepository<Report_entity, Integer> {

    
    @Query("SELECT DISTINCT r.planname FROM Report_entity r")
    List<String> getplansname();


    @Query("SELECT DISTINCT r.planstatus FROM Report_entity r")
    List<String> getplanstatus();
}
