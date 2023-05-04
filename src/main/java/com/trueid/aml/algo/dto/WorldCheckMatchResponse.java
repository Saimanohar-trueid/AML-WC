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
@JsonPropertyOrder({ "MATCH_CRITERIA_LIST" })
@Getter
@Setter
@ToString
public class WorldCheckMatchResponse implements Serializable {

	@JsonProperty("MATCH_CRITERIA_LIST")
	private List<MatchCriteria> matchCriteriaList;
	private static final long serialVersionUID = 1331153156249784665L;

}
