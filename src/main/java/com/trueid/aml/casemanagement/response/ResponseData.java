package com.trueid.aml.casemanagement.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {
	
	private String uid;
	
	private String requestedName;
	
	private String elasticSearchName;
	
	private Double matchScore
	;
	
	

}
