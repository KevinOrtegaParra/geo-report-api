package com.geo_report_api.dtos.report;

import java.time.LocalDateTime;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ReportRequestDTO {
    
    static final long serialVersionUID = 1L;

    String title;

    String descriptio;
   
    LocalDateTime reportDate;

    @NotNull(message = "requires a type of incident")
    @JsonProperty("Incident_id")
    Long incidentType;

    @NotNull
    private Double latitud;
    
    @NotNull
    private Double longitud;

    @JsonIgnore
    @JsonProperty("users_id")
    Long userId;
}
