package com.geo_report_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.geo_report_api.dtos.report.ReportRequestDTO;
import com.geo_report_api.dtos.report.ReportRequestUpdateDTO;
import com.geo_report_api.dtos.report.ReportResponseDTO;
import com.geo_report_api.exception.RestException;
import com.geo_report_api.services.ifaces.IReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "reports Controller", description = "Operations related to incident reports with geolocation")
@RestController
@RequestMapping("/reports")
public class ReportController {
        @Autowired
        IReportService reportService;

        @PreAuthorize("hasRole('USER')")
        @SecurityRequirement(name = "Authorization")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "401", description = "Unauthorized"),
                        @ApiResponse(responseCode = "500", description = "Internal Error Server")
        })
        @Operation(summary = "Show all incident reports", description = "Endpoint to Show all incident reports")
        @ResponseStatus(HttpStatus.OK)
        @GetMapping
        public ResponseEntity<List<ReportResponseDTO>> getReports() throws RestException {
                return ResponseEntity.ok(reportService.getReports());
        }

        @PreAuthorize("hasRole('USER')")
        @SecurityRequirement(name = "Authorization")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "400", description = "Bad Request"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized"),
                        @ApiResponse(responseCode = "403", description = "Forbidden"),
                        @ApiResponse(responseCode = "404", description = "Not Found"),
                        @ApiResponse(responseCode = "500", description = "Internal Error Server")
        })
        @Operation(summary = "Show a incident reports by their id", description = "Endpoint to Show a incident reports by their id")
        @ResponseStatus(HttpStatus.OK)
        @GetMapping("/{id}")
        public ResponseEntity<ReportResponseDTO> getReport(@PathVariable Long id) throws RestException {
                return ResponseEntity.ok(reportService.findById(id));
        }

        @PreAuthorize("hasRole('USER')")
        @SecurityRequirement(name = "Authorization")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "400", description = "Bad Request"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized"),
                        @ApiResponse(responseCode = "403", description = "Forbidden"),
                        @ApiResponse(responseCode = "500", description = "Internal Error Server")
        })
        @Operation(summary = "Save a incident reports", description = "Endpoint to save a incident reports")
        @ResponseStatus(HttpStatus.CREATED)
        @PostMapping()
        public ResponseEntity<ReportResponseDTO> postReport(@Valid @RequestBody ReportRequestDTO reportRequestDTO,
                        Authentication authentication)
                        throws RestException {
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(reportService.postReport(reportRequestDTO, authentication));
        }

        @PreAuthorize("hasRole('USER')")
        @SecurityRequirement(name = "Authorization")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "400", description = "Bad Request"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized"),
                        @ApiResponse(responseCode = "403", description = "Forbidden"),
                        @ApiResponse(responseCode = "404", description = "Not Found"),
                        @ApiResponse(responseCode = "500", description = "Internal Error Server")
        })
        @Operation(summary = "Update report", description = "Enpoint to Update your repor by id.")
        @ResponseStatus(HttpStatus.CREATED)
        @PutMapping("/{id}")
        public ResponseEntity<ReportResponseDTO> putReport(@Valid @RequestBody ReportRequestUpdateDTO requestUpdateDTO,
                        @PathVariable Long id,
                        Authentication authentication) throws RestException {
                return ResponseEntity.status(HttpStatus.CREATED).body(reportService.PutReport(
                                requestUpdateDTO,id, authentication));

        }

        @PreAuthorize("hasRole('ADMIN')")
        @SecurityRequirement(name = "Authorization")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "400", description = "Bad Request"),
                        @ApiResponse(responseCode = "401", description = "Unauthorized"),
                        @ApiResponse(responseCode = "403", description = "Forbidden"),
                        @ApiResponse(responseCode = "404", description = "Not Found"),
                        @ApiResponse(responseCode = "500", description = "Internal Error Server")
        })
        @Operation(summary = "Delete report", description = "Endpoint to delete report")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteReport(@PathVariable Long id) throws RestException {
                reportService.deletById(id);
                return ResponseEntity.noContent().build();
        }
}
