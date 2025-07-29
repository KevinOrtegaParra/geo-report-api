package com.geo_report_api.services.ifaces;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.geo_report_api.dtos.user.UserRequestDTO;
import com.geo_report_api.dtos.user.UserRequestUpdateDTO;
import com.geo_report_api.dtos.user.UserResponseDTO;
import com.geo_report_api.exception.RestException;

public interface IUserService {
    List<UserResponseDTO> getUsers()throws RestException;
    
    UserResponseDTO register(UserRequestDTO userRequestDTO)throws RestException;
    
    UserResponseDTO update(UserRequestUpdateDTO user, Authentication authentication)throws RestException;
    
    UserResponseDTO findById(Long id)throws RestException;
    
    void deletById(Long id)throws RestException;

}
