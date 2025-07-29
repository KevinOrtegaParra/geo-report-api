package com.geo_report_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geo_report_api.model.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long>{
    
}
