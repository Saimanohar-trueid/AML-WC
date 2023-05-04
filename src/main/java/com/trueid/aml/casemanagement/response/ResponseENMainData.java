package com.trueid.aml.casemanagement.response;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseENMainData {
	
	
	private ResponseENData data;
	
	private List<Aliases> aliases;
	
	public ResponseENMainData() {
	}

}
