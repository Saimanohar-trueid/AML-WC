package com.trueid.aml.casemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trueid.aml.casemanagement.entity.AMLWorldCheckCaseData;





public interface AMLCaseDataRepo extends JpaRepository<AMLWorldCheckCaseData,Long>{
	@Query(nativeQuery = true, value = "SELECT child.s_id,child.uuid,child.score,child.name_match,child.algorithem,child.status FROM AMLWORLD_CHECK_CASE_DATA child\r\n"
			+ "LEFT JOIN amlworld_check_case_managment parent\r\n"
			+ "  ON child.case_id_fk = parent.caseid\r\n"
			+ "WHERE child.case_id_fk = ?")
	List<AMLWorldCheckCaseData> findAllAmlWorldCheckCaseDatas(@Param("caseId") Long  caseId);
}
