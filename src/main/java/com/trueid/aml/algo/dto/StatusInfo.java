package com.trueid.aml.algo.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "REQUESTID", "SOURCESYSTEMNAME", "SOURCEAUTHENTICATIONTOKEN", "STATUS", "STATUSMESSAGE",
		"RESPONSECODE", "DESCRIPTION", "PROFILE_ID", "MATCHCOUNT" })
@Getter
@Setter
@ToString
public class StatusInfo implements Serializable {

	@JsonProperty("REQUESTID")
	public String requestid;
	@JsonProperty("SOURCESYSTEMNAME")
	public String sourcesystemname;
	@JsonProperty("SOURCEAUTHENTICATIONTOKEN")
	public String sourceauthenticationtoken;
	@JsonProperty("STATUS")
	public String status;
	@JsonProperty("STATUSMESSAGE")
	public String statusmessage;
	@JsonProperty("RESPONSECODE")
	public String responsecode;
	@JsonProperty("DESCRIPTION")
	public String description;
	@JsonProperty("PROFILE_ID")
	public String profileId;
	@JsonProperty("MATCHCOUNT")
	public String matchcount;
	private static final long serialVersionUID = 4886613491799221409L;

}