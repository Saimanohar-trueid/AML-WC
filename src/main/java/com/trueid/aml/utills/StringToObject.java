package com.trueid.aml.utills;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StringToObject {
	
	private Double score;
	
	private List<String> extendedInformation;

}
