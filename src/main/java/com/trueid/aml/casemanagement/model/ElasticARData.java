package com.trueid.aml.casemanagement.model;

import java.io.Serializable;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "rni-wc-data-ar")
public class ElasticARData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1152841617880748951L;
	
	

	
	@Field(type = FieldType.Nested, name = "data")
	private ElasticARData person;

	@Field(type = FieldType.Text, name = "uuid")	
	private String uuid;
	
	@Field(type = FieldType.Text, name = "aliases")
	private String aliases;
	
	@Field(type = FieldType.Text, name = "entityType")
	private String entityType;
	
	
	public ElasticARData() {
	}

	public ElasticARData(String uuid, String aliasnames, String entityType) {
		this.uuid = uuid;
		this.aliases = aliasnames;
		this.entityType = entityType;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	
	public String getAliases() {
		return aliases;
	}

	public void setAliases(String aliases) {
		this.aliases = aliases;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public ElasticARData getPerson() {
		return person;
	}

	public void setPerson(ElasticARData person) {
		this.person = person;
	}


	
	

}
