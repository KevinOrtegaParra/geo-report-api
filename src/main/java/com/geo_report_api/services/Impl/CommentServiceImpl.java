package com.geo_report_api.services.Impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.geo_report_api.dtos.comment.CommentRequestDTO;
import com.geo_report_api.dtos.comment.CommentResponseDTO;
import com.geo_report_api.exception.ErrorDto;
import com.geo_report_api.exception.InternalServerErrorException;
import com.geo_report_api.exception.NotFoundException;
import com.geo_report_api.exception.RestException;
import com.geo_report_api.model.Comment;
import com.geo_report_api.model.Report;
import com.geo_report_api.model.UserEntity;
import com.geo_report_api.repositories.ICommentRepository;
import com.geo_report_api.repositories.IRepotRepository;
import com.geo_report_api.repositories.IUserRepository;
import com.geo_report_api.services.ifaces.ICommentService;
import com.geo_report_api.util.CommentMapper;
import com.geo_report_api.util.Messages;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICommentRepository commentRepository;

    @Autowired
    private IRepotRepository repotRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public CommentResponseDTO postComment(CommentRequestDTO commentRequestDTO, Authentication authentication)
            throws RestException {
        UserEntity userDB = userRepository.findByEmail(authentication.getName());

        Report reportDB = repotRepository.findById(commentRequestDTO.getReportId())
                .orElseThrow(() -> new NotFoundException(
                        ErrorDto.builder()
                                .error(Messages.NOT_FOUND)
                                .message(Messages.REPORT_NOT_EXIST)
                                .status(HttpStatus.NOT_FOUND.value())
                                .date(LocalDateTime.now())
                                .build()));

        Comment comment = commentMapper.toComment(commentRequestDTO);
        comment.setUser(userDB);
        comment.setReport(reportDB);

        try {

            return commentMapper.toCommentResponseDTO(commentRepository.save(comment));
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
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentResponseDTO> getComments(Long reportId) throws RestException {
        try {
            return commentMapper.toCommentResponseDTOList(commentRepository.findByReportId(reportId));
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

}
