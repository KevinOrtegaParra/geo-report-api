package com.geo_report_api.util;

import java.util.List;

import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.geo_report_api.dtos.report.ReportRequestDTO;
import com.geo_report_api.dtos.report.ReportResponseDTO;
import com.geo_report_api.model.Incident;
import com.geo_report_api.model.Report;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportMapper {

    Report toReport(ReportRequestDTO reportRequestDTO);

    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "incidentType.nombre", target = "incidentType")
    @Mapping(source = "ubication", target = "latitud", qualifiedByName = "pointToLat")
    @Mapping(source = "ubication", target = "longitud", qualifiedByName = "pointToLng")
    ReportResponseDTO toReportResponseDTO(Report report);

    List<ReportResponseDTO> toReportResponseDTOList(List<Report> reports);

    default Incident map(Long id) {
        if (id == null)
            return null;
        Incident incident = new Incident();
        incident.setId(id);
        return incident;
    }

    @Named("pointToLat")
    static Double mapLat(Point point) {
        return point != null ? point.getY() : null;
    }

    @Named("pointToLng")
    static Double mapLng(Point point) {
        return point != null ? point.getX() : null;
    }
}
