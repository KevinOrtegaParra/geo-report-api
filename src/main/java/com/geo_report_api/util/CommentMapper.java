package com.geo_report_api.util;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.geo_report_api.dtos.comment.CommentRequestDTO;
import com.geo_report_api.dtos.comment.CommentResponseDTO;
import com.geo_report_api.model.Comment;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    Comment toComment(CommentRequestDTO commentRequestDTO);

    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "report.title", target = "report")
    CommentResponseDTO toCommentResponseDTO(Comment comment);

    List<CommentResponseDTO> toCommentResponseDTOList(List<Comment> comments);
}
