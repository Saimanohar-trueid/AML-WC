package com.trueid.aml.casemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trueid.aml.casemanagement.entity.AMLWorldCheckCaseManagment;






public interface AMLCaseManagmentRepo extends JpaRepository<AMLWorldCheckCaseManagment, Long> {

	List<AMLWorldCheckCaseManagment> findByStatus(String status);

	

}
