package com.trueid.aml.casemanagement.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.trueid.aml.casemanagement.model.Aliases;
import com.trueid.aml.casemanagement.model.ElasticARData;
import com.trueid.aml.casemanagement.model.ElasticData;
import com.trueid.aml.casemanagement.model.ElasticMain;
import com.trueid.aml.casemanagement.model.Product;
import com.trueid.aml.casemanagement.model.UserData;
import com.trueid.aml.casemanagement.repository.ElasticSearchQuery;

@RestController
@RequestMapping("/api")
public class ElasticSearchController {
	
	private static final Logger log = LoggerFactory.getLogger(ElasticSearchController.class);

	@Autowired
	private ElasticSearchQuery elasticSearchQuery;

	@PostMapping("/createOrUpdateDocument")
	public ResponseEntity<Object> createOrUpdateDocument(@RequestBody Product product) throws IOException {
		String response = elasticSearchQuery.createOrUpdateDocument(product);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/getDocument/{productId}")
	public ResponseEntity<Object> getDocumentById(@PathVariable String productId) throws IOException {
		Product product = elasticSearchQuery.getDocumentById(productId);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@DeleteMapping("/deleteDocument/{productId}")
	public ResponseEntity<Object> deleteDocumentById(@PathVariable String productId) throws IOException {
		String response = elasticSearchQuery.deleteDocumentById(productId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/searchDocument")
	public ResponseEntity<Object> searchAllDocument() throws IOException {
		List<Product> products = elasticSearchQuery.searchAllDocuments();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@PostMapping("/upload")
	public ResponseEntity<Object> upload() throws IOException, ParseException {

		String data = readJsonFromFile("C:\\Users\\TrueID - Sai\\Desktop\\Data\\wc-names-data1.json");

		log.info(data);

		List<UserData> userDatas = convert(data);
		String response = null;
		ElasticData elasticData = new ElasticData();

		for (int i = 0; i < userDatas.size(); i++) {

			List<Aliases> listAliases = new ArrayList<>();
			List<String> aliaStr = new ArrayList<>();
			ElasticMain main = new ElasticMain();

			UserData userData = userDatas.get(i);
			elasticData.setUid(userData.getUid());
			aliaStr.addAll(userData.getAliases());

			for (String fname : userData.getpData().getFn()) {
				elasticData.setFirstname(fname);
			}
			for (String lname : userData.getpData().getLf()) {
				elasticData.setLastname(lname);
			}

			if (!"".equals(elasticData.getFirstname()) && "".equals(elasticData.getLastname()))
				elasticData.setFullname(elasticData.getFirstname());
			else if ("".equals(elasticData.getFirstname()) && !"".equals(elasticData.getLastname()))
				elasticData.setFullname(elasticData.getLastname());
			else
				elasticData.setFullname(elasticData.getFirstname() + " " + elasticData.getLastname());

			aliaStr = userData.getAliases();

			if (aliaStr != null) {
				for (String string : aliaStr) {
					Aliases aliases = new Aliases();
					if (!"".equals(string)) {
						aliases.setAlias(string);
						listAliases.add(aliases);
					}
					else {
						aliases.setAlias(null);
						listAliases.add(aliases);
					}

				}
			} else {
				Aliases aliases = new Aliases();
				aliases.setAlias(null);
				listAliases.add(aliases);
			}
			elasticData.setEntityType("PERSON");
			main.setData(elasticData);
			main.setAliases(listAliases);
			log.info("Uid  " + elasticData.getUid());
			response = elasticSearchQuery.create(main);

		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/csvUpload")
	public ResponseEntity<Object> csvUpload() {
		String line = "";
		ElasticARData arData = new ElasticARData();
		ElasticARData arData1 = new ElasticARData();
		String response = null;
		try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/AML-AR-NAMES-UUID.CSV"))) {
			while ((line = br.readLine()) != null) {

				String[] data = line.split(",");
				arData.setUuid(data[0]);
				arData.setAliases(data[1]);
				arData1.setPerson(arData);

				response = elasticSearchQuery.createCSV(arData1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	public String readJsonFromFile(String filePath) throws IOException, ParseException {
		String json = Files.readString(Paths.get(filePath));
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(new JSONParser().parse(json));
	}

	public List<UserData> convert(String jsonString) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(List.class,
				UserData.class);
		List<UserData> data = new ArrayList<>();
		try {
			data = om.readValue(jsonString, typeReference);
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return data;
	}

}