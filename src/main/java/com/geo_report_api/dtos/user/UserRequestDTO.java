package com.geo_report_api.dtos.user;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserRequestDTO implements Serializable{

    static final long serialVersionUID = 1L; 

    @NotBlank(message = "required name")
    String name;
    
    @NotBlank(message = "required Email")
    @Email(message = "you have to have an email")
    String email;

    @NotBlank(message = "required password")
    String password;
    
    @JsonIgnore
    Set<String> roles;
}
