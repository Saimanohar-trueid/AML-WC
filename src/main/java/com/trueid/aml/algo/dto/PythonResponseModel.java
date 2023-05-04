package com.trueid.aml.algo.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Source Name","Target Names" })
@Getter
@Setter
@ToString
public class PythonResponseModel implements Serializable {

	@JsonProperty("Source Name")
	public String sourceName;
	@JsonProperty("Target Names")
	private List<ResponseTargetNames> targetNames;
	private static final long serialVersionUID = -1764475197323276074L;

}
