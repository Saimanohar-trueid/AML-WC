package com.trueid.aml.algo.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Person Name", "Score", "UID"})
@Getter
@Setter
@ToString
public class ResponseLevenshtein implements Serializable {

	@JsonProperty("Person Name")
	public String personName;
	@JsonProperty("Score")
	public Float score;
	@JsonProperty("UID")
	public Long uid;
	private static final long serialVersionUID = 4886613491799221409L;

}