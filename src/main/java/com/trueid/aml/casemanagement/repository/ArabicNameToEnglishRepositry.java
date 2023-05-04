package com.trueid.aml.casemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trueid.aml.casemanagement.model.ArabicNameToEnglishEntity;





public interface ArabicNameToEnglishRepositry extends JpaRepository<ArabicNameToEnglishEntity, Long>{
	@Query("SELECT data FROM ArabicNameToEnglishEntity data WHERE " +
            "data.NAME_IN_ARABIC LIKE CONCAT('%',:query, '%')")
    List<ArabicNameToEnglishEntity> searchBasedArabicToEnglish(String query);
	
	
	@Query(value = "SELECT * FROM amlworldcheck_arabicnames WHERE NAME_IN_ARABIC = ?1", nativeQuery=true)
    List<ArabicNameToEnglishEntity> getEnglishName(String query);
}
