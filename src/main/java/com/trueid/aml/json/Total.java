package com.trueid.aml.json;

import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"value",
"relation"
})

public class Total {

@JsonProperty("value")
private Integer value;
@JsonProperty("relation")
private String relation;
@JsonIgnore
private Map<String, Object> additionalProperties = new LinkedHashMap<>();

@JsonProperty("value")
public Integer getValue() {
return value;
}

@JsonProperty("value")
public void setValue(Integer value) {
this.value = value;
}

@JsonProperty("relation")
public String getRelation() {
return relation;
}

@JsonProperty("relation")
public void setRelation(String relation) {
this.relation = relation;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}