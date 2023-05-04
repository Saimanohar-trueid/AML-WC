package com.trueid.aml.casemanagement.response;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.trueid.aml.casemanagement.model.AliasesData;
import com.trueid.aml.casemanagement.model.FirstNameData;
import com.trueid.aml.casemanagement.model.FullNameData;
import com.trueid.aml.casemanagement.model.LastNameData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEN {
	
	@Field(type = FieldType.Integer, name = "uid")
	private String uid;

	@Field(type = FieldType.Text, name = "FIRST_NAME")
	private FirstNameData firstName;

	@Field(type = FieldType.Text, name = "LAST_NAME")
	private LastNameData lastName;

	@Field(type = FieldType.Text, name = "full_NAME")
	private FullNameData fullName;
	
	@Field(type = FieldType.Flattened, name = "aliases")
	private AliasesData aliases;
	

}
