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
"alias"
})

public class Alias {

@JsonProperty("alias")
private String aliasVal;
@JsonIgnore
private Map<String, Object> additionalProperties = new LinkedHashMap<>();

@JsonProperty("alias")
public String getAliasVal() {
return aliasVal;
}

@JsonProperty("alias")
public void setAliasVal(String aliasVal) {
this.aliasVal = aliasVal;
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