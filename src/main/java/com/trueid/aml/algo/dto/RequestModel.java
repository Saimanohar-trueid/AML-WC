package com.trueid.aml.algo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor	
@ToString
public class RequestModel {
	
	@JsonProperty("FIRST_NAME")
	private String firstName;
	@JsonProperty("SECOND_NAME")
	private String secondName;
	@JsonProperty("THIRD_NAME")
	private String thirdName;
	@JsonProperty("FOURTH_NAME")
	private String fourthName;
	@JsonProperty("TRIBAL_NAME")
	private String tribalName;
	@JsonProperty("MOTHER_FIRST_NAME")
	private String motherFirstName;
	@JsonProperty("MOTHERS_SECOND_NAME")
	private String mothersSecondName;
	@JsonProperty("MOTHER_THIRD_NAME")
	private String motherThirdName;
	@JsonProperty("BIRTHDATE")
	private String birthDate;
	@JsonProperty("GENDER_ID")
	private String genderId;
	@JsonProperty("MOBILE_NUMBER")
	private String mobileNumber;
	@JsonProperty("PASSPORT_NUMBER")
	private String passportNumber;
	@JsonProperty("FIRST_NAME_ENGLISH")
	private String firstNameEnglish;
	@JsonProperty("SECOND_NAME_ENGLISH")
	private String secondNameEnglish;
	@JsonProperty("THIRD_NAME_ENGLISH")
	private String thirdNameEnglish;
	@JsonProperty("TRIBE_NAME_ENGLISH")
	private String tribeNameEnglish;
	@JsonProperty("CIVIL_AFFAIRS_NO")
	private String civilAffairsNo;
	@JsonProperty("NATIONAL_ID_NUMBER")
	private String nationalIdNumber;
	@JsonProperty("ADDRESS")
	private String address;
	@JsonProperty("CITY")
	private String city;
	@JsonProperty("STATE")
	private String state;
	@JsonProperty("COUNTRY")
	private String country;
	@JsonProperty("PLACE_OF_BIRTH")
	private String placeOfBirth;
	@JsonProperty("CITIZENSHIP")
	private String citizenship;
	@JsonProperty("PROFILE_ID")
	private String profileId;
	@JsonProperty("REQUEST_ID")
	private String requestId;
	@JsonProperty("SOURCE_NAME")
	private String sourceName;
	

}
