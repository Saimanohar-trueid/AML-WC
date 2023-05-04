package com.trueid.aml.casemanagement.model;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class ARIndex {
	
	@Field(type = FieldType.Text, name = "primary_name")
	private String primaryName;
	
	@Field(type = FieldType.Object, name = "data")
	private ElasticARData data;
	
	public ARIndex() {
	}

	public ARIndex(String primaryName, ElasticARData data) {
		this.primaryName = primaryName;
		this.data = data;
	}

	public String getPrimaryName() {
		return primaryName;
	}

	public void setPrimaryName(String primaryName) {
		this.primaryName = primaryName;
	}

	public ElasticARData getData() {
		return data;
	}

	public void setData(ElasticARData data) {
		this.data = data;
	}
	
	

}
