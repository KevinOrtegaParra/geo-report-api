package com.geo_report_api.services.ifaces;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.geo_report_api.dtos.comment.CommentRequestDTO;
import com.geo_report_api.dtos.comment.CommentResponseDTO;
import com.geo_report_api.exception.RestException;

public interface ICommentService {

    List<CommentResponseDTO> getComments(Long reportId)throws RestException;

    CommentResponseDTO postComment(CommentRequestDTO commentRequestDTO, Authentication authentication)throws RestException;

    void deleteComment(Long id);

}
