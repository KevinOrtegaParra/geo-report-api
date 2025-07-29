package com.geo_report_api.dtos.comment;

import java.time.LocalDateTime;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CommentResponseDTO {

    Long id;

    String content;

    String userName;

    String report;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;


}
