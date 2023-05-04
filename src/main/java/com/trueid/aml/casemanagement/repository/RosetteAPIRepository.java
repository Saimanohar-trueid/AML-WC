package com.trueid.aml.casemanagement.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trueid.aml.algo.data.RosetteData;
import com.trueid.aml.casemanagement.model.Aliases;
import com.trueid.aml.casemanagement.model.ElasticMain;
import com.trueid.aml.casemanagement.response.ResponseEN;
import com.trueid.aml.casemanagement.response.ResponseENAR;
import com.trueid.aml.casemanagement.response.ResponseENMainData;
import com.trueid.aml.utills.ResponseElastic;


@Repository
public class RosetteAPIRepository {
	
	private static final Logger log = LoggerFactory.getLogger(RosetteAPIRepository.class);

	public List<ResponseElastic> searchByMultiMatch(String name) throws IOException {

		List<String> list = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		List<ResponseENMainData> jsonList = new ArrayList<>();
		List<String> fullname = new ArrayList<>();
		List<ResponseElastic> elasticList = new ArrayList<>();

		@SuppressWarnings("deprecation")
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("localhost", 9200, "http")));

		// Multi-match query in elasticsearch
		SearchRequest searchRequest = new SearchRequest("rni-worldcheckdata-en");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(name, "data.fullname", "aliases.alias")
				.operator(Operator.OR);

		searchSourceBuilder.query(multiMatchQuery);
		searchRequest.source(searchSourceBuilder);

		try {
			@SuppressWarnings("deprecation")
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			SearchHit[] hits = searchResponse.getHits().getHits();

			for (SearchHit searchHit : hits) {
				String sourceCode = searchHit.getSourceAsString();
				list.add(sourceCode);
			}

			for (String jsonString : list) {
				ResponseENMainData object = objectMapper.readValue(jsonString, ResponseENMainData.class);

				jsonList.add(object);
			}


			for (int i = 0; i < jsonList.size(); i++) {
				ResponseElastic search = new ResponseElastic();
				fullname.add(jsonList.get(i).getData().getFullname());
				search.setUid(jsonList.get(i).getData().getUid());
				search.setFullname(jsonList.get(i).getData().getFullname());
				elasticList.add(search);
			}



		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			client.close();
		}

		return elasticList;
	}

	public List<RosetteData> searchByQueryString(String name) throws IOException {

		List<String> list = new ArrayList<>();
		List<String> scoreList = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		List<ElasticMain> jsonList = new ArrayList<>();
		List<RosetteData> elasticList = new ArrayList<>();
		@SuppressWarnings("deprecation")
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("localhost", 9200, "http")));

		// Query String in elasticsearch

		SearchRequest searchRequest = new SearchRequest("rni-amlworldcheckdata-en");

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder builder = QueryBuilders.matchQuery("data.fullname", name);

		
		

		searchSourceBuilder.query(builder);
		searchRequest.source(searchSourceBuilder);

		try {
			@SuppressWarnings("deprecation")
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			SearchHit[] hits = searchResponse.getHits().getHits();
			log.info("Rosette object --> ", searchResponse);
			float score = 0f;
			for (SearchHit searchHit : hits) {
				score = searchHit.getScore();
				String responseString = searchHit.getSourceAsString();
				list.add(responseString);
				scoreList.add(String.valueOf(score));

			}

			if (list.size() == scoreList.size()) {
				for (int i = 0; i < list.size(); i++) {
					String jsonString = list.get(i);
					ElasticMain object = objectMapper.readValue(jsonString, ElasticMain.class);
					String scoreVal = scoreList.get(i);
					object.setScore(scoreVal);
					jsonList.add(object);
				}
			}
			for (int i = 0; i < jsonList.size(); i++) {
				RosetteData response = new RosetteData();
				response.setUid(Long.valueOf(jsonList.get(i).getData().getUid()));
				response.setMatchedName(jsonList.get(i).getData().getFullname());
				response.setScore(Float.valueOf(jsonList.get(i).getScore()));
				elasticList.add(response);
			}


			
			  List<RosetteData> listData = searchByQueryAliases(name);
			  
			  elasticList.addAll(listData);
			  
			  log.info("elasticList Aliases ", listData);
			 

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			client.close();
		}

		return elasticList;
	}

	public List<RosetteData> searchByQueryAliases(String name) throws IOException {

		List<String> list = new ArrayList<>();
		List<String> scoreList = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		List<ElasticMain> jsonList = new ArrayList<>();
		List<RosetteData> elasticList = new ArrayList<>();

		@SuppressWarnings("deprecation")
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("localhost", 9200, "http")));

		// Query String in elasticsearch

		SearchRequest searchRequest = new SearchRequest("rni-amlworldcheckdata-en");

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		// QueryStringQueryBuilder queryBuilder =
		MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("aliases.alias", name);
		NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("aliases", matchQuery,ScoreMode.Max);
		matchQuery.boost(1);
		nestedQuery.boost(1);

		searchSourceBuilder.query(nestedQuery);


		try {
			@SuppressWarnings("deprecation")
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			SearchHit[] hits = searchResponse.getHits().getHits();
			float score = 0f;
			for (SearchHit searchHit : hits) {
				score = searchHit.getScore();
				String responseString = searchHit.getSourceAsString();
				list.add(responseString);
				scoreList.add(String.valueOf(score));

			}

			if (list.size() == scoreList.size()) {
				for (int i = 0; i < list.size(); i++) {
					String jsonString = list.get(i);
					ElasticMain object = objectMapper.readValue(jsonString, ElasticMain.class);
					String scoreVal = scoreList.get(i);
					object.setScore(scoreVal);
					jsonList.add(object);
				}
			}

			for (int i = 0; i < jsonList.size(); i++) {

				for (Aliases alias : jsonList.get(i).getAliases()) {
						RosetteData response = new RosetteData();
						response.setUid(Long.valueOf(jsonList.get(i).getData().getUid()));
						response.setMatchedName(alias.getAlias());
						response.setScore(Float.valueOf(jsonList.get(i).getScore()));
						elasticList.add(response);

				}

			}


		} catch (

		IOException e) {
			e.printStackTrace();
		} finally {
			client.close();
		}

		return elasticList;
	}

	public List<String> searchByQuery(String name) throws IOException {

		List<String> list = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		List<ResponseENAR> jsonList = new ArrayList<>();
		List<String> fullname = new ArrayList<>();

		@SuppressWarnings("deprecation")
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("localhost", 9200, "http")));

		// Query String in elasticsearch

		SearchRequest searchRequest = new SearchRequest("rni-worldcheckdataar");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryStringQueryBuilder queryBuilder = QueryBuilders.queryStringQuery("primary_name.aliasnames:" + name);
		searchSourceBuilder.query(queryBuilder);
		searchRequest.source(searchSourceBuilder);

		try {
			@SuppressWarnings("deprecation")
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			SearchHit[] hits = searchResponse.getHits().getHits();
			for (SearchHit searchHit : hits) {
				String responseString = searchHit.getSourceAsString();
				list.add(responseString);

			}

			for (String jsonString : list) {
				ResponseENAR object = objectMapper.readValue(jsonString, ResponseENAR.class);

				jsonList.add(object);
			}
			ResponseENAR data = new ResponseENAR();

			for (int i = 0; i < jsonList.size(); i++) {
				data = jsonList.get(i);
				fullname.add(data.getPrimaryName().getAliasnames());
			}


		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			client.close();
		}

		return fullname;
	}

	public List<String> searchByFuzzyQuery(String name) throws IOException {

		List<String> list = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		List<ResponseEN> jsonList = new ArrayList<>();
		List<String> fullname = new ArrayList<>();

		@SuppressWarnings("deprecation")
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("localhost", 9200, "http")));

		// Query String in elasticsearch

		SearchRequest searchRequest = new SearchRequest("rni-worldcheckdataen");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		FuzzyQueryBuilder qbuilder = QueryBuilders.fuzzyQuery("primary_name.data.fullname:", name)
				.fuzziness(Fuzziness.AUTO).prefixLength(3).maxExpansions(10);
		searchSourceBuilder.query(qbuilder);
		searchRequest.source(searchSourceBuilder);

		try {
			@SuppressWarnings("deprecation")
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			SearchHit[] hits = searchResponse.getHits().getHits();
			for (SearchHit searchHit : hits) {
				String responseString = searchHit.getSourceAsString();
				list.add(responseString);

			}

			for (String jsonString : list) {
				ResponseEN object = objectMapper.readValue(jsonString, ResponseEN.class);

				jsonList.add(object);
			}
			new ResponseEN();

			for (int i = 0; i < jsonList.size(); i++) {
				jsonList.get(i);
			}


		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			client.close();
		}

		return fullname;
	}

}
