package com.geo_report_api.dtos.report;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ReportRequestUpdateDTO implements Serializable{
    
    static final long serialVersionUID = 1L;

    String descriptio;

    private Double latitud;
    
    private Double longitud;
}
