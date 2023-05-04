package com.trueid.aml.casemanagement.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "AMLWORLDCHECK_ARABICNAMES")
public class ArabicNameToEnglishEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String NAME_IN_ARABIC;
	private String NAME_IN_ENGLLISH;
}
