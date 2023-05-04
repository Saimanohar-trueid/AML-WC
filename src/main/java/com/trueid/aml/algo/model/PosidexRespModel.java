package com.trueid.aml.algo.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "POSIDEX_MATCH_RESPONSE")
public class PosidexRespModel {
	

	@Id
	Long uuid;
	String matchedName;
	String score;
	

}
