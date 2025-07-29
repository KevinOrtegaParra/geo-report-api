package com.geo_report_api.services.Impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geo_report_api.dtos.user.UserRequestDTO;
import com.geo_report_api.dtos.user.UserRequestUpdateDTO;
import com.geo_report_api.dtos.user.UserResponseDTO;
import com.geo_report_api.exception.BadRequestException;
import com.geo_report_api.exception.ErrorDto;
import com.geo_report_api.exception.InternalServerErrorException;
import com.geo_report_api.exception.NotFoundException;
import com.geo_report_api.exception.RestException;
import com.geo_report_api.model.Role;
import com.geo_report_api.model.UserEntity;
import com.geo_report_api.repositories.IUserRepository;
import com.geo_report_api.services.ifaces.IEmailService;
import com.geo_report_api.services.ifaces.IUserService;
import com.geo_report_api.util.Messages;
import com.geo_report_api.util.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private IEmailService emailService;

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getUsers() throws RestException {
        try {
            return userMapper.toUserResponseDTOList(userRepository.findAll());

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
    @Transactional
    public UserResponseDTO register(UserRequestDTO userRequestDTO) throws RestException {
        UserEntity userDB = userRepository.findByEmail(userRequestDTO.getEmail());

        if (userDB != null) {
            throw new BadRequestException(
                    ErrorDto.builder()
                            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                            .message(Messages.USER_EXISTS)
                            .status(HttpStatus.BAD_REQUEST.value())
                            .date(LocalDateTime.now())
                            .build());
        }

        try {
            userDB = userMapper.toUser(userRequestDTO);
            userDB.setRoles(Collections.singleton(new Role(2L)));

            String passwordEncoded = passwordEncoder.encode(userDB.getPassword());
            userDB.setPassword(passwordEncoded);

            userDB = userRepository.save(userDB);
            if (userDB != null) {
                if (emailService.sendMail(userDB.getEmail(), userDB.getName())) {
                    log.info("Mensaje enviado");
                } else {
                    log.warn("Mensaje no enviado");
                }
            }

            return userMapper.toUserResponseDTO(userDB);

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
    @Transactional
    public UserResponseDTO update(UserRequestUpdateDTO user, Authentication authentication) throws RestException {
        UserEntity userDB = userRepository.findByEmail(authentication.getName());

        if (userDB == null) {
            throw new NotFoundException(
                    ErrorDto.builder()
                            .error(Messages.NOT_FOUND)
                            .message(Messages.USER_NOT_EXIST)
                            .status(HttpStatus.NOT_FOUND.value())
                            .date(LocalDateTime.now())
                            .build());
        }

        userDB.setName(user.getName() != null ? user.getName() : userDB.getName());
        if (user.getPassword() != null) {
            userDB.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        try {
            return userMapper.toUserResponseDTO(userRepository.save(userDB));
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
    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) throws RestException {
        return userMapper.toUserResponseDTO(userRepository.findById(id).orElseThrow(() -> new NotFoundException(
                ErrorDto.builder()
                        .error(Messages.NOT_FOUND)
                        .message(Messages.USER_NOT_EXIST)
                        .status(HttpStatus.NOT_FOUND.value())
                        .date(LocalDateTime.now())
                        .build())));
    }

    @Override
    public void deletById(Long id) throws RestException {
        userRepository.deleteById(id);
    }

}
