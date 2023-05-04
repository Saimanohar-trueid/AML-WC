package com.trueid.aml.utills;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseElastic {
	
	String uid;
	String nameStrength;
	String fullname;
	String requestedName;
	Double score; 
	

}
