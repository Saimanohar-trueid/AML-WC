package com.trueid.aml.algo.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "PRIMARYPERSONALITY", "MATCHREASON", "RECORDTYPE", "PRIMARY_NAME", "MATCHED_NAME","STRENGTHS", "AliasNames",
		"DOB", "PASSPORT_NO", "ADDRESS", "CITY", "STATE", "COUNTRY", "CATEGORY", "TITLE", "SUB_CATEGORY",
		"RELATED_PERSON_ENTITYID", "E_I", "PLACE_OF_BIRTH", "STATUS", "DECEASED" })
@Getter
@Setter
@ToString
public class MatchCriteria implements Serializable {

	@JsonProperty("PRIMARYPERSONALITY")
	public String primarypersonality;
	@JsonProperty("MATCHREASON")
	public String matchreason;
	@JsonProperty("RECORDTYPE")
	public String recordtype;
	@JsonProperty("PRIMARY_NAME")
	public String primaryName;
	@JsonProperty("MATCHED_NAME")
	public String matchedName;
	@JsonProperty("STRENGTHS")
	public String strengths;
	@JsonProperty("AliasNames")
	public String aliasNames;
	@JsonProperty("DOB")
	public String dob;
	@JsonProperty("PASSPORT_NO")
	public String passportNo;
	@JsonProperty("ADDRESS")
	public String address;
	@JsonProperty("CITY")
	public String city;
	@JsonProperty("STATE")
	public String state;
	@JsonProperty("COUNTRY")
	public String country;
	@JsonProperty("CATEGORY")
	public String category;
	@JsonProperty("TITLE")
	public String title;
	@JsonProperty("SUB_CATEGORY")
	public String subCategory;
	@JsonProperty("RELATED_PERSON_ENTITYID")
	public String relatedPersonEntityid;
	@JsonProperty("E_I")
	public String eI;
	@JsonProperty("PLACE_OF_BIRTH")
	public String placeOfBirth;
	@JsonProperty("STATUS")
	public String status;
	@JsonProperty("DECEASED")
	public String deceased;
	private static final long serialVersionUID = 138629499471700779L;

}