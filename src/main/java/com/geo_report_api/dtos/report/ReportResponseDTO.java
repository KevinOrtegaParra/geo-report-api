package com.geo_report_api.dtos.report;

import java.time.LocalDateTime;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ReportResponseDTO {

    Long id;

    String title;

    String descriptio;

    LocalDateTime reportDate;

    String incidentType;

    private Double latitud;
    
    private Double longitud;

    String userName;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
