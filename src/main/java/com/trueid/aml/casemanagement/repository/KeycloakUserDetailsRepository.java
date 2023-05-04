package com.trueid.aml.casemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trueid.aml.casemanagement.entity.KeycloakUserDetailsEntity;


public interface KeycloakUserDetailsRepository extends JpaRepository<KeycloakUserDetailsEntity, Long>{

}
