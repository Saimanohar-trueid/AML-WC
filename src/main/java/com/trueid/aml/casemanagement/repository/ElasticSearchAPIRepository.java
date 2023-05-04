package com.trueid.aml.casemanagement.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trueid.aml.algo.data.RosetteData;
import com.trueid.aml.json.Alias;
import com.trueid.aml.json.ArAlias;
import com.trueid.aml.json.Example;
import com.trueid.aml.json.Hit;

@Repository
public class ElasticSearchAPIRepository {
	
	private static final Logger log = LoggerFactory.getLogger(ElasticSearchAPIRepository.class);

	private static final String matchQueryFname = "{\n \"query\" : {\n \"match\" : {\n \"data.fullname\" : \"{\\\"data\\\" : \\\"fname\\\", \\\"entityType\\\" : \\\"PERSON\\\"}\" \n }\n },\n \"rescore\" : {\n \"window_size\" : 200,\n \"rni_query\" : {\n \"rescore_query\" : {\n \"rni_function_score\" : {\n \"name_score\" : {\n \"field\" : \"data.fullname\",\n \"query_name\" : {\"data\" : \"fname\", \"entityType\":\"PERSON\"},\n \"score_to_rescore_restriction\": 1.0,\n \"window_size_allowance\": 0.5\n }\n }\n },\n \"query_weight\" : 0.0,\n \"rescore_query_weight\" : 1.0\n }\n }\n}";

	private static final String matchQueryEngAlias = "{\r\n \"query\": {\r\n \"bool\": {\r\n \"should\": [\r\n {\r\n \"nested\": {\r\n \"path\": \"aliases\",\r\n \"query\": {\r\n \"match\": { \"alias\": \"Aname\"} \r\n }\r\n } \r\n }, \r\n {\r\n \"match\":{\r\n\"data.fullname\" : \"{\\\"data\\\" : \\\"Aname\\\", \\\"entityType\\\" : \\\"PERSON\\\"}\"\r\n }\r\n }\r\n ]\r\n }\r\n },\r\n \"rescore\": [\r\n { \r\n   \"window_size\" : 200,\r\n \"rni_query\": {\r\n \"rescore_query\": {\r\n \"nested\": {\r\n \"score_mode\": \"max\",\r\n \"path\": \"aliases\",\r\n \"query\": {\r\n \"rni_function_score\": {\r\n \"name_score\": {\r\n \"field\": \"aliases.alias\",\r\n \"query_name\": \"Aname\",\r\n \"window_size_allowance\": 1\r\n }\r\n }\r\n }\r\n }\r\n },\r\n \"query_weight\": 0.0,\r\n \"rescore_query_weight\": 1.0\r\n }\r\n },\r\n {\r\n   \"window_size\" : 200,\r\n   \"rni_query\" : {\r\n \"rescore_query\" : {\r\n \"rni_function_score\" : {\r\n \"name_score\" : {\r\n \"field\" : \"data.fullname\",\r\n \"query_name\" : {\"data\" : \"Aname\", \"entityType\":\"PERSON\"},\r\n \"score_to_rescore_restriction\": 1.0,\r\n \"window_size_allowance\": 0.5\r\n }\r\n }\r\n },\r\n \"query_weight\" : 0.0,\r\n \"rescore_query_weight\" : 1.0\r\n }\r\n }\r\n ]\r\n}";

	private static final String matchQueryArAlias = "{\n \"query\": {\n \"bool\": {\n \"should\": [\n {\n \"nested\": {\n \"path\": \"aliases\",\n \"query\": {\n \"match\": { \"alias\": \"Aname\"} \n }\n } \n },\n {\n \"nested\": {\n \"path\":\"ar_aliases\",\n \"query\": {\n \"match\": {\"alias\": \"Aname\"} \n }\n }\n },\n {\n \"match\":{\n\"data.fullname\" : \"{\\\"data\\\" : \\\"Aname\\\", \\\"entityType\\\" : \\\"PERSON\\\"}\"\n }\n }\n ]\n }\n },\n \"rescore\": [\n { \n   \"window_size\" : 200,\n \"rni_query\": {\n \"rescore_query\": {\n \"nested\": {\n \"score_mode\": \"max\",\n \"path\": \"aliases\",\n \"query\": {\n \"rni_function_score\": {\n \"name_score\": {\n \"field\": \"aliases.alias\",\n \"query_name\": \"Aname\",\n \"window_size_allowance\": 1\n }\n }\n }\n }\n },\n \"query_weight\": 0.0,\n \"rescore_query_weight\": 1.0\n }\n },\n { \n   \"window_size\" : 200,\n \"rni_query\": {\n \"rescore_query\": {\n \"nested\": {\n \"score_mode\": \"max\",\n \"path\": \"ar_aliases\",\n \"query\": {\n \"rni_function_score\": {\n \"name_score\": {\n \"field\": \"ar_aliases.alias\",\n \"query_name\": \"Aname\",\n \"window_size_allowance\": 1\n }\n }\n }\n }\n },\n \"query_weight\": 0.0,\n \"rescore_query_weight\": 1.0\n }\n },\n {\n   \"window_size\" : 200,\n   \"rni_query\" : {\n \"rescore_query\" : {\n \"rni_function_score\" : {\n \"name_score\" : {\n \"field\" : \"data.fullname\",\n \"query_name\" : {\"data\" : \"Aname\", \"entityType\":\"PERSON\"},\n \"score_to_rescore_restriction\": 1.0,\n \"window_size_allowance\": 0.5\n }\n }\n },\n \"query_weight\" : 0.0,\n \"rescore_query_weight\" : 1.0\n }\n }\n ]\n}";

	private static final String matchQueryAliases = "{\r\n \"query\" : {\r\n \"nested\" : {\r\n \"path\" : \"aliases\",\r\n \"query\" : {\r\n \"match\" : { \"aliases.alias\": \"ARNAME\" }\r\n }\r\n }\r\n },\r\n \"rescore\" : {\r\n \"query\" : {\r\n \"rescore_query\" : {\r\n \"nested\" : {\r\n \"path\" : \"aliases\",\r\n \"score_mode\" : \"max\",\r\n \"query\" : {\r\n \"function_score\" : {\r\n \"name_score\" : {\r\n \"field\" : \"aliases.alias\",\r\n \"query_name\" : \"ARNAME\"\r\n }\r\n }\r\n }\r\n }\r\n },\r\n \"query_weight\" : 0.0,\r\n \"rescore_query_weight\" : 1.0\r\n }\r\n }\r\n}";

	public List<RosetteData> searchByQueryString(String name) throws IOException {

		List<RosetteData> elasticList = new ArrayList<>();

		List<RosetteData> elastic = new ArrayList<>();

		RestClient restClient2 = RestClient.builder(new HttpHost("localhost", 9200)).build();
		try {

			HttpEntity entity = new NStringEntity(matchQueryFname.replace("fname", name),
					ContentType.APPLICATION_JSON);

			Request request = new Request("GET", "rni-amlworldcheckdata-en-ar/_search");
			request.setEntity(entity);

			Response indexResponse = restClient2.performRequest(request);

			ObjectMapper mapper = new ObjectMapper();

			String json = EntityUtils.toString(indexResponse.getEntity());

			JsonNode jsonNode = mapper.readTree(json);

			ObjectMapper om = new ObjectMapper();
			Example root = om.treeToValue(jsonNode, Example.class);


			List<Hit> hits = root.getHits().getHitsData();

			for (int i = 0; i < hits.size(); i++) {
				RosetteData response = new RosetteData();
				response.setUid(Long.valueOf(hits.get(i).getSource().getData().getUid()));
				response.setMatchedName(hits.get(i).getSource().getData().getFullname());
				response.setScore(Float.valueOf(hits.get(i).getScore() * 100));
				elasticList.add(response);
			}

			log.info("Fullname Data --> ", elasticList);
			log.info("After Adding all List Data --> ", elastic);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return elasticList;

	}

	public List<RosetteData> searchByQueryAlias(String name) throws IOException {

		List<RosetteData> elasticList = new ArrayList<>();
		RestClient restClient2 = RestClient.builder(new HttpHost("localhost", 9200)).build();
		try {


			HttpEntity entity = new NStringEntity(matchQueryAliases.replace("ARNAME", name),
					ContentType.APPLICATION_JSON);

			Request request = new Request("GET", "rni-amlworldcheckdata-en-ar/_search");
			request.setEntity(entity);

			Response indexResponse = restClient2.performRequest(request);

			ObjectMapper mapper = new ObjectMapper();

			String json = EntityUtils.toString(indexResponse.getEntity());

			JsonNode jsonNode = mapper.readTree(json);

			ObjectMapper om = new ObjectMapper();
			Example root = om.treeToValue(jsonNode, Example.class);

			List<Hit> hits = root.getHits().getHitsData();
			log.info("Root Data ", hits);

			for (Hit hit : hits) {
				List<Alias> aliases = hit.getSource().getAliases();
				try {
					for (Alias alias : aliases) {
						RosetteData response = new RosetteData();
						response.setUid(Long.valueOf(hit.getSource().getData().getUid()));
						response.setMatchedName(alias.getAliasVal());
						response.setScore(hit.getScore() * 100);

						elasticList.add(response);
					}
				} catch (NullPointerException ne) {
					ne.printStackTrace();
				}

			}

			log.info("Aliases Data -->  ", elasticList);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return elasticList;

	}

	public List<RosetteData> searchByQueryArAliases(String name, String[] strArr) throws IOException {

		List<RosetteData> rosetteListFinal = new ArrayList<>();
		RestClient restClient2 = RestClient.builder(new HttpHost("localhost", 9200)).build();
		try {


			// Starts from Arabic Aliases rni-query
			HttpEntity entity = new NStringEntity(matchQueryArAlias.replace("Aname", name),
					ContentType.APPLICATION_JSON);


			Request request = new Request("GET", "rni-amlworldcheckdata-en-ar/_search");
			request.setEntity(entity);

			Response indexResponse = restClient2.performRequest(request);

			ObjectMapper mapper = new ObjectMapper();

			String json = EntityUtils.toString(indexResponse.getEntity());

			JsonNode jsonNode = mapper.readTree(json);

			ObjectMapper om = new ObjectMapper();
			Example root = om.treeToValue(jsonNode, Example.class);

			List<Hit> hits = root.getHits().getHitsData();
			List<RosetteData> elasticList = new ArrayList<>();
			List<RosetteData> elasticList1 = new ArrayList<>();
			List<RosetteData> elasticList2 = new ArrayList<>();

			TreeSet<Long> set = new TreeSet<>();

			for (int i = 0; i < hits.size(); i++) {


				if (hits.get(i).getSource().getArAliases() != null) {
					for (ArAlias alias : hits.get(i).getSource().getArAliases()) {
						RosetteData response1 = new RosetteData();
						response1.setUid(Long.valueOf(hits.get(i).getSource().getData().getUid()));
						response1.setMatchedName(
								hits.get(i).getSource().getData().getFullname() + " (" + alias.getAlias() + ")");
						response1.setScore(hits.get(i).getScore() * 100);
						elasticList1.add(response1);

					}
				}

				if (hits.get(i).getSource().getAliases() != null) {
					for (Alias aliases : hits.get(i).getSource().getAliases()) {
						RosetteData response2 = new RosetteData();
						response2.setUid(Long.valueOf(hits.get(i).getSource().getData().getUid()));
						response2.setMatchedName(hits.get(i).getSource().getData().getFullname() + " (" + aliases.getAliasVal() + ")");
						response2.setScore(hits.get(i).getScore() * 100);
						elasticList2.add(response2);
					}
				}

				if (hits.get(i).getSource().getData().getFullname() != null) {
					RosetteData response = new RosetteData();
					response.setUid(Long.valueOf(hits.get(i).getSource().getData().getUid()));
					response.setMatchedName(hits.get(i).getSource().getData().getFullname());
					response.setScore(hits.get(i).getScore() * 100);
					elasticList.add(response);
				}

			}

			// adding arabic alias query in main List

			for (RosetteData data : elasticList2) {
				set.add(data.getUid());
			}

			List<?> setList = new ArrayList<>(set);

			List<RosetteData> listRosette = new ArrayList<>();

			int c = 0;
			int x = 0;

			for (int i = 0; i < setList.size(); i++) {
				c = 0;
				x = 0;
				for (int j = 0; j < elasticList2.size(); j++) {
					if ((setList.get(i) + "").equals(elasticList2.get(j).getUid() + "")) {
						c++;
						for (int k = 0; k < strArr.length; k++) {
							if (elasticList2.get(j).getMatchedName().toUpperCase().contains(strArr[k].toUpperCase())) {
								RosetteData response = new RosetteData();
								response.setUid(elasticList2.get(j).getUid());
								response.setMatchedName(elasticList2.get(j).getMatchedName());
								response.setScore(elasticList2.get(j).getScore());

								listRosette.add(response);
								x++;
								break;

							}
							if (x > 0) {
								break;
							}

						}

					}
				}
				if (c == 1 && x == 0) {
					RosetteData response = new RosetteData();
					response.setUid(elasticList2.get(i).getUid());
					response.setMatchedName(elasticList2.get(i).getMatchedName());
					response.setScore(elasticList2.get(i).getScore());

					listRosette.add(response);
				}

			}

			HashMap<Long, RosetteData> map = new HashMap<>();
			Set<?> setData = new HashSet<>();
			for (int i = 0; i < elasticList1.size(); i++) {

				if (i == 0) {
					map.put(elasticList1.get(i).getUid(), elasticList1.get(i));
				} else {
					setData = map.keySet();
					Iterator<?> it = setData.iterator();
					int c1 = 0;
					while (it.hasNext()) {
						long k = (Long) it.next();

						if ((k + "").equals(elasticList1.get(i).getUid() + "")) {
							c1++;
							break;
						}
					}

					if (c1 == 0) {
						map.put(elasticList1.get(i).getUid(), elasticList1.get(i));
					}
				}

			}

			List<RosetteData> rosetteList = new ArrayList<>();

			setData = map.keySet();
			Iterator<?> it = setData.iterator();
			while (it.hasNext()) {
				long k = (Long) it.next();
				rosetteList.add(map.get(k));

			}

			rosetteList.addAll(elasticList);
			rosetteList.addAll(listRosette);


			HashMap<Long, RosetteData> mapData = new HashMap<>();
			for (int i = 0; i < rosetteList.size(); i++) {

				if (i == 0) {
					mapData.put(rosetteList.get(i).getUid(), rosetteList.get(i));
				} else {
					setData = mapData.keySet();
					Iterator<?> it1 = setData.iterator();
					int c1 = 0;
					while (it1.hasNext()) {
						long k = (Long) it1.next();

						if ((k + "").equals(rosetteList.get(i).getUid() + "")) {
							c1++;
							break;
						}
					}

					if (c1 == 0) {
						mapData.put(rosetteList.get(i).getUid(), rosetteList.get(i));
					}
				}

			}

			Set<?> finalSet = new HashSet<>();
			finalSet = mapData.keySet();
			Iterator<?> it2 = finalSet.iterator();
			while (it2.hasNext()) {
				long k = (Long) it2.next();
				rosetteListFinal.add(mapData.get(k));

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return rosetteListFinal;

	}

	public List<RosetteData> searchByQueryEngAliases(String name, String[] strArr) throws IOException {

		List<RosetteData> rosetteListFinal = new ArrayList<>();
		RestClient restClient2 = RestClient.builder(new HttpHost("localhost", 9200)).build();
		try {


			// Starts from Arabic Aliases rni-query
			HttpEntity entity = new NStringEntity(matchQueryEngAlias.replace("Aname", name),
					ContentType.APPLICATION_JSON);


			Request request = new Request("GET", "rni-amlworldcheckdata-en-ar/_search");
			request.setEntity(entity);

			Response indexResponse = restClient2.performRequest(request);

			ObjectMapper mapper = new ObjectMapper();

			String json = EntityUtils.toString(indexResponse.getEntity());

			JsonNode jsonNode = mapper.readTree(json);

			ObjectMapper om = new ObjectMapper();
			Example root = om.treeToValue(jsonNode, Example.class);

			List<Hit> hits = root.getHits().getHitsData();
			List<RosetteData> elasticList = new ArrayList<>();
			List<RosetteData> elasticList2 = new ArrayList<>();

			TreeSet<Long> set = new TreeSet<>();

			for (int i = 0; i < hits.size(); i++) {


				if (hits.get(i).getSource().getAliases() != null) {
					for (Alias aliases : hits.get(i).getSource().getAliases()) {
						RosetteData response2 = new RosetteData();
						response2.setUid(Long.valueOf(hits.get(i).getSource().getData().getUid()));
						response2.setMatchedName( hits.get(i).getSource().getData().getFullname()+" ("+aliases.getAliasVal()+")");
						response2.setScore(hits.get(i).getScore() * 100);
						elasticList2.add(response2);
					}
				}

				if (hits.get(i).getSource().getData().getFullname() != null) {
					RosetteData response = new RosetteData();
					response.setUid(Long.valueOf(hits.get(i).getSource().getData().getUid()));
					response.setMatchedName(hits.get(i).getSource().getData().getFullname());
					response.setScore(hits.get(i).getScore() * 100);
					elasticList.add(response);
				}

			}

			// adding arabic alias query in main List

			for (RosetteData data : elasticList2) {
				set.add(data.getUid());
			}

			List<?> setList = new ArrayList<>(set);

			List<RosetteData> listRosette = new ArrayList<>();

			int c = 0;
			int x = 0;

			for (int i = 0; i < setList.size(); i++) {
				c = 0;
				x = 0;
				for (int j = 0; j < elasticList2.size(); j++) {
					if ((setList.get(i) + "").equals(elasticList2.get(j).getUid() + "")) {
						c++;
						for (int k = 0; k < strArr.length; k++) {
							if (elasticList2.get(j).getMatchedName().toUpperCase().contains(strArr[k].toUpperCase())) {
								RosetteData response = new RosetteData();
								response.setUid(elasticList2.get(j).getUid());
								response.setMatchedName(elasticList2.get(j).getMatchedName());
								response.setScore(elasticList2.get(j).getScore());

								listRosette.add(response);
								x++;
								break;

							}
							if (x > 0) {
								break;
							}

						}

					}
				}
				if (c == 1 && x == 0) {
					RosetteData response = new RosetteData();
					response.setUid(elasticList2.get(i).getUid());
					response.setMatchedName(elasticList2.get(i).getMatchedName());
					response.setScore(elasticList2.get(i).getScore());

					listRosette.add(response);
				}

			}
			List<RosetteData> rosetteList = new ArrayList<>();
			rosetteList.addAll(elasticList);
			rosetteList.addAll(listRosette);

			for (int i = 0; i < rosetteList.size(); i++) {
					
				if (i == 0) { 
					rosetteListFinal.add(rosetteList.get(i));
				} else { 
					Iterator<RosetteData> it =rosetteListFinal.listIterator();
					int c1 = 0;
					while (it.hasNext()) {
						RosetteData k = it.next();

						if ((k.getUid() + "").equals(rosetteList.get(i).getUid() + "")) {
							c1++;
							break;
						}
					}

					if (c1 == 0) { 
						rosetteListFinal.add(rosetteList.get(i));
					}
				}

			}

			log.info("English map Data --> ", rosetteListFinal);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return rosetteListFinal;

	}
	
	
	public List<RosetteData> searchByQueryDataAliases(String name) throws IOException {

		List<RosetteData> elasticList = new ArrayList<>();
		RestClient restClient2 = RestClient.builder(new HttpHost("localhost", 9200)).build();
		try {

				HttpEntity entity = new NStringEntity(matchQueryArAlias.replace("Aname", name),
					ContentType.APPLICATION_JSON);

			Request request = new Request("GET", "rni-amlworldcheckdata-en-ar/_search");
			request.setEntity(entity);

			Response indexResponse = restClient2.performRequest(request);

			ObjectMapper mapper = new ObjectMapper();

			String json = EntityUtils.toString(indexResponse.getEntity());

			JsonNode jsonNode = mapper.readTree(json);

			ObjectMapper om = new ObjectMapper();
			Example root = om.treeToValue(jsonNode, Example.class);

			List<Hit> hits = root.getHits().getHitsData();
			
			for (int i = 0; i < hits.size(); i++) {
				if (hits.get(i).getSource().getData().getFullname() != null) {
					RosetteData response = new RosetteData();
					response.setUid(Long.valueOf(hits.get(i).getSource().getData().getUid()));
					response.setMatchedName(hits.get(i).getSource().getData().getFullname());
					response.setScore(hits.get(i).getScore() * 100);
					elasticList.add(response);
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return elasticList;

	}

}
