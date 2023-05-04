package com.trueid.aml.casemanagement.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@Entity
@Table(name = "AMLWorldCheckCaseManagment_bkp")
public class AMLWorldCheckCaseManagment {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long caseid;
	@Column(name = "user_name")
	private String user;
	@Column(name = "organization_data")
	private String organization;
	@Column(name = "score_max")
	private String scoreMax;
	@Column(name = "score_min")
	private String scoreMin;
	private String screenedName;
	@CreationTimestamp
	private Date createDate;
	private String status;
	@OneToMany(targetEntity = AMLWorldCheckCaseData.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "caseId_fk", referencedColumnName = "caseid")
	@JsonIgnore
	private List<AMLWorldCheckCaseData> casesData;

}
