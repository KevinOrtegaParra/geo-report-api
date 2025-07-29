package com.geo_report_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geo_report_api.model.UserEntity;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long>{

    UserEntity findByEmail(String email);   
}
