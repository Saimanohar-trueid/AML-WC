package com.trueid.aml.algo.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "SORT_MATCH_RESPONSE")
public class SortRespModel {
	

	@Id
	Long uuid;
	String matchedName;
	String score;
	

}
