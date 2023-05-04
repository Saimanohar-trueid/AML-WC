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
@JsonPropertyOrder({ "jaro_winkler", "levenshtein","qratio","set_ratio","sort_ratio" })
@Getter
@Setter
@ToString
public class ResponseTargetNames implements Serializable {

	@JsonProperty("jaro_winkler")
	private List<ResponseJaroWinkler> jaroWinkler;
	@JsonProperty("levenshtein")
	private List<ResponseLevenshtein> levenshTein;
	@JsonProperty("qratio")
	private List<ResponseQRatio> qratio;
	@JsonProperty("set_ratio")
	private List<ResponseSetRatio> setRatio;
	@JsonProperty("sort_ratio")
	private List<ResponseSortRatio> sortRatio;
	private static final long serialVersionUID = -7408712712138003342L;

}