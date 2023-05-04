package com.trueid.aml.casemanagement.model;

import java.io.Serializable;
import java.util.List;

public class UserARData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8263764522953497612L;
	
	private String uuid;
	
	private String aliasNames;
	
	private List<UserARData> userData;
	
	public UserARData() {
	}

	public UserARData(String uuid, String aliasNames, List<UserARData> userData) {
		super();
		this.uuid = uuid;
		this.aliasNames = aliasNames;
		this.userData = userData;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAliasNames() {
		return aliasNames;
	}

	public void setAliasNames(String aliasNames) {
		this.aliasNames = aliasNames;
	}

	public List<UserARData> getUserData() {
		return userData;
	}

	public void setUserData(List<UserARData> userData) {
		this.userData = userData;
	}

	

}
