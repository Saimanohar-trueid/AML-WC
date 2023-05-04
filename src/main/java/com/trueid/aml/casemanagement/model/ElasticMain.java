package com.trueid.aml.casemanagement.model;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "rni-worldcheckdata-eng")
public class ElasticMain {

	@Field(type = FieldType.Text, name = "score")
	private String score;

	@Field(type = FieldType.Nested, name = "data")
	private ElasticData data;

	@Field(type = FieldType.Nested, name = "aliases")
	private List<Aliases> aliases;

}
