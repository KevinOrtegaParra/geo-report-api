package com.geo_report_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geo_report_api.model.Report;

@Repository
public interface IRepotRepository extends JpaRepository<Report, Long>{
    
}
