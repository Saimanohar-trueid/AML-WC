package com.trueid.aml.casemanagement.model;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AliasesData {
	
	@Field(type = FieldType.Text, name = "data")
		private String data;

	@Field(type = FieldType.Text, name = "entityType")
	private String entityType;

}
