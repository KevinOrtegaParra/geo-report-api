package com.geo_report_api.services.ifaces;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.geo_report_api.dtos.report.ReportRequestUpdateDTO;
import com.geo_report_api.dtos.report.ReportRequestDTO;
import com.geo_report_api.dtos.report.ReportResponseDTO;
import com.geo_report_api.exception.RestException;

public interface IReportService {
    
    List<ReportResponseDTO> getReports()throws RestException;

    ReportResponseDTO postReport(ReportRequestDTO reportRequestDTO, Authentication authentication)throws RestException;

    ReportResponseDTO PutReport(ReportRequestUpdateDTO reportRequestUpdateDTO, Long id, Authentication authentication)throws RestException;

    ReportResponseDTO findById(Long id)throws RestException;
    
    void deletById(Long id)throws RestException;
}
