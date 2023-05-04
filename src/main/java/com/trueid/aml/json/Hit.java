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
"_index",
"_id",
"_score",
"_source"
})

public class Hit {

@JsonProperty("_index")
private String index;
@JsonProperty("_id")
private String id;
@JsonProperty("_score")
private Float score;
@JsonProperty("_source")
private Source source;
@JsonIgnore
private Map<String, Object> additionalProperties = new LinkedHashMap<>();

@JsonProperty("_index")
public String getIndex() {
return index;
}

@JsonProperty("_index")
public void setIndex(String index) {
this.index = index;
}

@JsonProperty("_id")
public String getId() {
return id;
}

@JsonProperty("_id")
public void setId(String id) {
this.id = id;
}

@JsonProperty("_score")
public Float getScore() {
return score;
}

@JsonProperty("_score")
public void setScore(Float score) {
this.score = score;
}

@JsonProperty("_source")
public Source getSource() {
return source;
}

@JsonProperty("_source")
public void setSource(Source source) {
this.source = source;
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
