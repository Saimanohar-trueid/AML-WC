package com.trueid.aml.casemanagement.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1392233342545078702L;

	@JsonProperty("fn")
	private List<String> fn;
	
	@JsonProperty("lf")
	private List<String> lf;
	
	public PersonData() {
	}

	public PersonData(List<String> fn, List<String> lf) {
		this.fn = fn;
		this.lf = lf;
	}

	public List<String> getFn() {
		return fn;
	}

	public void setFn(List<String> fn) {
		this.fn = fn;
	}

	public List<String> getLf() {
		return lf;
	}

	public void setLf(List<String> lf) {
		this.lf = lf;
	}
	
	
	
	

}
