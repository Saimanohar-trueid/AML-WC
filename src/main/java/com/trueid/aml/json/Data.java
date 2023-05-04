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
"uid",
"firstname",
"lastname",
"fullname",
"entityType"
})

public class Data {

@JsonProperty("uid")
private String uid;
@JsonProperty("firstname")
private String firstname;
@JsonProperty("lastname")
private String lastname;
@JsonProperty("fullname")
private String fullname;
@JsonProperty("entityType")
private String entityType;
@JsonIgnore
private Map<String, Object> additionalProperties = new LinkedHashMap<>();

@JsonProperty("uid")
public String getUid() {
return uid;
}

@JsonProperty("uid")
public void setUid(String uid) {
this.uid = uid;
}

@JsonProperty("firstname")
public String getFirstname() {
return firstname;
}

@JsonProperty("firstname")
public void setFirstname(String firstname) {
this.firstname = firstname;
}

@JsonProperty("lastname")
public String getLastname() {
return lastname;
}

@JsonProperty("lastname")
public void setLastname(String lastname) {
this.lastname = lastname;
}

@JsonProperty("fullname")
public String getFullname() {
return fullname;
}

@JsonProperty("fullname")
public void setFullname(String fullname) {
this.fullname = fullname;
}

@JsonProperty("entityType")
public String getEntityType() {
return entityType;
}

@JsonProperty("entityType")
public void setEntityType(String entityType) {
this.entityType = entityType;
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
