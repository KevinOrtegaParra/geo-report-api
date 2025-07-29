package com.geo_report_api.dtos.comment;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CommentRequestDTO implements Serializable{

    static final long serialVersionUID = 1L;

    String content;

    @JsonProperty("report_id")
    Long reportId;

}
