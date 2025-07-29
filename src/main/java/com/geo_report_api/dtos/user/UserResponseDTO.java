package com.geo_report_api.dtos.user;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserResponseDTO {
    
    Long id;

    String name;
    
    String email;

    @JsonIgnore
    String password;

    Set<String> roles;

    LocalDateTime createAt;

    LocalDateTime udatedAt;
}
