package com.trueid.aml.algo.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ROSETTE_MATCH_RESPONSE")
public class RosetteRespModel {
	

	@Id
	Long uuid;
	String matchedName;
	String score;
	

}
