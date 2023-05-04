package com.trueid.aml.json;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"data",
"aliases",
"ar_aliases"
})

public class Source {

@JsonProperty("data")
private Data data;
@JsonProperty("aliases")
private List<Alias> aliases;
@JsonProperty("ar_aliases")
private List<ArAlias> arAliases;
@JsonIgnore
private Map<String, Object> additionalProperties = new LinkedHashMap<>();

@JsonProperty("data")
public Data getData() {
return data;
}

@JsonProperty("data")
public void setData(Data data) {
this.data = data;
}

@JsonProperty("aliases")
public List<Alias> getAliases() {
return aliases;
}

@JsonProperty("aliases")
public void setAliases(List<Alias> aliases) {
this.aliases = aliases;
}

@JsonProperty("ar_aliases")
public List<ArAlias> getArAliases() {
return arAliases;
}

@JsonProperty("ar_aliases")
public void setArAliases(List<ArAlias> arAliases) {
this.arAliases = arAliases;
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