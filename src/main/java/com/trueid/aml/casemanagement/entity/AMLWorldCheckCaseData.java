package com.trueid.aml.casemanagement.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "AMLWorldCheckCaseData_bkp")
public class AMLWorldCheckCaseData {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long sId;
	private Long uuid;
	private String score;
	private String algorithem;
	@Lob
	private String nameMatch;
	private String status;

}
