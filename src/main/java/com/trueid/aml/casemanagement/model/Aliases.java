package com.trueid.aml.casemanagement.model;


import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Aliases {
	
	@Field(type = FieldType.Flattened, name = "alias")
	private String alias;

	
}
