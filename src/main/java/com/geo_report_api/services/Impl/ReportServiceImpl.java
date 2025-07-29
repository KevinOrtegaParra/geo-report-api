package com.geo_report_api.services.Impl;

import java.time.LocalDateTime;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.geo_report_api.dtos.report.ReportRequestDTO;
import com.geo_report_api.dtos.report.ReportRequestUpdateDTO;
import com.geo_report_api.dtos.report.ReportResponseDTO;
import com.geo_report_api.exception.ErrorDto;
import com.geo_report_api.exception.InternalServerErrorException;
import com.geo_report_api.exception.NotFoundException;
import com.geo_report_api.exception.RestException;
import com.geo_report_api.exception.UnauthorizedException;
import com.geo_report_api.model.Incident;
import com.geo_report_api.model.Report;
//import com.geo_report_api.model.TypeFile;
import com.geo_report_api.model.UserEntity;
import com.geo_report_api.repositories.IIncidentRepository;
import com.geo_report_api.repositories.IRepotRepository;
import com.geo_report_api.repositories.IUserRepository;
import com.geo_report_api.services.ifaces.IReportService;
import com.geo_report_api.util.Messages;
import com.geo_report_api.util.ReportMapper;

@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    private IRepotRepository repotRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IIncidentRepository incidentRepository;

    @Autowired
    private ReportMapper reportMapper;

    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    @Override
    public List<ReportResponseDTO> getReports() throws RestException {
        try {
            return reportMapper.toReportResponseDTOList(repotRepository.findAll());
        } catch (Exception e) {
            throw new InternalServerErrorException(
                    ErrorDto.builder()
                            .error(Messages.GENERAL_ERROR)
                            .message(e.getMessage())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .date(LocalDateTime.now())
                            .build());
        }
    }

    @Override
    public ReportResponseDTO postReport(ReportRequestDTO reportRequestDTO, Authentication authentication)
            throws RestException {
        UserEntity userDB = userRepository.findByEmail(authentication.getName());
        Incident incidentDB = incidentRepository.findById(reportRequestDTO.getIncidentType())
                .orElseThrow(() -> new NotFoundException(
                        ErrorDto.builder()
                                .error(Messages.NOT_FOUND)
                                .message(Messages.INCIDENT_NOT_EXIST)
                                .status(HttpStatus.NOT_FOUND.value())
                                .date(LocalDateTime.now())
                                .build()));

        Report report = reportMapper.toReport(reportRequestDTO);
        report.setUser(userDB);
        report.setIncidentType(incidentDB);
        report.setUbication(geometryFactory
                .createPoint(new Coordinate(reportRequestDTO.getLongitud(), reportRequestDTO.getLatitud())));

        try {
            return reportMapper.toReportResponseDTO(repotRepository.save(report));
        } catch (Exception e) {
            throw new InternalServerErrorException(
                    ErrorDto.builder()
                            .error(Messages.GENERAL_ERROR)
                            .message(e.getMessage())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .date(LocalDateTime.now())
                            .build());
        }
    }

    @Override
    public ReportResponseDTO PutReport(ReportRequestUpdateDTO reportRequestUpdateDTO, Long id,
            Authentication authentication) throws RestException {
        Report report = repotRepository.findById(id).orElseThrow(() -> new NotFoundException(
                ErrorDto.builder()
                        .error(Messages.NOT_FOUND)
                        .message(Messages.REPORT_NOT_EXIST)
                        .status(HttpStatus.NOT_FOUND.value())
                        .date(LocalDateTime.now())
                        .build()));

        if (!report.getUser().getEmail().equals(authentication.getName())) {
            throw new UnauthorizedException(
                ErrorDto.builder()
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(Messages.REPORT_UPDATE_UNAUTHORIZED)
                .status(HttpStatus.UNAUTHORIZED.value())
                .date(LocalDateTime.now())
                .build());
        }

        report.setDescriptio(reportRequestUpdateDTO.getDescriptio() != null ? reportRequestUpdateDTO.getDescriptio()
                : report.getDescriptio());
        report.setUbication(geometryFactory
                .createPoint(
                        new Coordinate(reportRequestUpdateDTO.getLongitud(), reportRequestUpdateDTO.getLatitud())));
        try {
            return reportMapper.toReportResponseDTO(repotRepository.save(report));
        } catch (Exception e) {
            throw new InternalServerErrorException(
                    ErrorDto.builder()
                            .error(Messages.GENERAL_ERROR)
                            .message(e.getMessage())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .date(LocalDateTime.now())
                            .build());
        }
    }

    @Override
    public ReportResponseDTO findById(Long id) throws RestException {
        return reportMapper.toReportResponseDTO(repotRepository.findById(id).orElseThrow(() -> new NotFoundException(
                ErrorDto.builder()
                        .error(Messages.NOT_FOUND)
                        .message(Messages.REPORT_NOT_EXIST)
                        .status(HttpStatus.NOT_FOUND.value())
                        .date(LocalDateTime.now())
                        .build())));
    }

    @Override
    public void deletById(Long id) throws RestException {
        repotRepository.deleteById(id);
    }

}
