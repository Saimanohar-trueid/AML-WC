package com.trueid.aml.casemanagement.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserData implements Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2827001459397687317L;
	
	private List<UserData> userData;


	@JsonProperty("uid")
	private String uid;
	
	@JsonProperty("person")
	private PersonData pData;
	
	@JsonProperty("aliases")
	private List<String> aliases;
	
	public UserData() {
	}

	

	public UserData(List<UserData> userdata, String uid, PersonData pData, List<String> aliases) {
		this.userData = userdata;
		this.uid = uid;
		this.pData = pData;
		this.aliases = aliases;
	}
	

	public List<UserData> getUserData() {
		return userData;
	}

	public void setUserdata(List<UserData> userdata) {
		this.userData = userdata;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public PersonData getpData() {
		return pData;
	}

	public void setpData(PersonData pData) {
		this.pData = pData;
	}

	public List<String> getAliases() {
		return aliases;
	}

	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}
	
	

}
