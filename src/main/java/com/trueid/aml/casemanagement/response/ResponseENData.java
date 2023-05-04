package com.trueid.aml.casemanagement.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseENData {
	
	
	private String uid;

	private String firstname;

	private String lastname;

	private String fullname;

	
	private String entityType;
	
	public ResponseENData() {
	}
	
	

}
