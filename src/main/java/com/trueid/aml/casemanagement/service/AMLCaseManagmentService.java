package com.trueid.aml.casemanagement.service;

import java.util.List;

import com.trueid.aml.casemanagement.entity.AMLWorldCheckCaseData;
import com.trueid.aml.casemanagement.entity.AMLWorldCheckCaseManagment;

public interface AMLCaseManagmentService {

	String amlCreateCase();
	
	List<AMLWorldCheckCaseData> amlUpdateCase(List<AMLWorldCheckCaseData> amlCaseDetails);
	
	AMLWorldCheckCaseManagment amlUpdateCaseMange(AMLWorldCheckCaseManagment amlMangCaseDetails);
	
	List<AMLWorldCheckCaseManagment> amlGetAllCases();
	
	List<AMLWorldCheckCaseManagment> amlGetByCaseStatus(String caseStatus);
	
	List<AMLWorldCheckCaseData> amlGetCaseById(Long caseId);
}
