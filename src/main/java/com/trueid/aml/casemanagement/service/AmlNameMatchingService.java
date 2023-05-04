package com.trueid.aml.casemanagement.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trueid.aml.algo.data.PosidexData;
import com.trueid.aml.algo.data.PythonJaroData;
import com.trueid.aml.algo.data.PythonLevenData;
import com.trueid.aml.algo.data.PythonQratioData;
import com.trueid.aml.algo.data.PythonSetRatioData;
import com.trueid.aml.algo.data.PythonSortRationData;
import com.trueid.aml.algo.data.RosetteData;
import com.trueid.aml.algo.dto.MatchCriteria;
import com.trueid.aml.algo.dto.PythonResponseModel;
import com.trueid.aml.algo.dto.RequestModel;
import com.trueid.aml.algo.dto.ResponseJaroWinkler;
import com.trueid.aml.algo.dto.ResponseLevenshtein;
import com.trueid.aml.algo.dto.ResponseModel;
import com.trueid.aml.algo.dto.ResponseQRatio;
import com.trueid.aml.algo.dto.ResponseSetRatio;
import com.trueid.aml.algo.dto.ResponseSortRatio;
import com.trueid.aml.algo.dto.ResponseTargetNames;
import com.trueid.aml.algo.export.MatchingResultsExporter;
import com.trueid.aml.casemanagement.repository.ElasticSearchAPIRepository;

@Service
public class AmlNameMatchingService {
	
	private static final Logger log = LoggerFactory.getLogger(AmlNameMatchingService.class);

	@Autowired
	private RestTemplate template;

	@Value("${posidex.api.url}")
	private String posidexUrl;

	@Value("${python.api.url}")
	private String pythonUrl;

	@Autowired
	private ElasticSearchAPIRepository repository;

	public String consumerService() {

		String line = "";

		try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/MC-SAMPLE-NAMES2.csv"))) {
			while ((line = br.readLine()) != null) {

				String[] data = line.split(",");

				String product = "";

				for (String string : data) {
					product = product + " " + string;
				}

				String input = "";
				String engName = "";

				String arName = product.replaceAll("\\s", "");
				HashMap<String, List<?>> dataMap = new HashMap<>();

			
				List<RosetteData> rosetteList = new ArrayList<>();
				List<RosetteData> rosetteEngList = new ArrayList<>();

				String[] strArr = input.split(",");

				RequestModel model = new RequestModel();

				log.info("After Fetch Entire Data ", rosetteList);

				 dataMap.put("ROSETTE", rosetteList);


				for (int s = 0; s < data.length; s++) {
					model.setFirstNameEnglish(data[s]);
				}

				if (this.isAlpha(arName)) {
				if (data.length == 4) {
					model.setTribeNameEnglish(data[3]);
					if (data.length - 1 == 3)
						model.setThirdNameEnglish(data[2]);
					if (data.length - 2 == 2)
						model.setSecondNameEnglish(data[1]);
					if (data.length - 3 == 1)
						model.setFirstNameEnglish(data[0]);
				}
				if (data.length == 3) {
					model.setThirdNameEnglish(data[2]);
					if (data.length - 1 == 2)
						model.setSecondNameEnglish(data[1]);
					if (data.length - 2 == 1)
						model.setFirstNameEnglish(data[0]);
				}
				if (data.length == 2) {
					model.setSecondNameEnglish(data[1]);
					if (data.length - 1 == 1)
						model.setFirstNameEnglish(data[0]);
				}
				if (data.length == 1) {
					model.setFirstNameEnglish(data[0]);
				}


				// Calling English name to elastic search
				log.info("After Fetch English Name ", rosetteEngList);

				dataMap.put("ROSETTE", rosetteEngList);
				}
				
				model.setFirstNameEnglish(strArr[0]);
				model.setSecondNameEnglish(strArr[1]);
				model.setThirdNameEnglish(strArr[2]);
				model.setTribeNameEnglish(strArr[3]);
				
				
				List<PosidexData> posidexList = new ArrayList<>();

				model.setRequestId(new Random().nextLong(5000000) + "");

				try {
					posidexList = this.callPosidexService(model);
					dataMap.put("POSIDEX", posidexList);
				} catch (Exception e) {
					e.printStackTrace();
				}

				HashMap<String, List<?>> pythonMap = new HashMap<>();
				dataMap.putAll(pythonMap);


				try {
					engName = model.getFirstNameEnglish() + " " + model.getSecondNameEnglish() + " "
							+ model.getThirdNameEnglish() + " " + model.getTribeNameEnglish();
					MatchingResultsExporter exporter = new MatchingResultsExporter();
					exporter.export(dataMap, engName,product);
					model = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} 

		return "Data Export Successfully";
	}

	public List<PosidexData> callPosidexService(RequestModel model) {
		HttpEntity<RequestModel> requestEntity = new HttpEntity<>(model);

		ResponseEntity<String> response = null;
		ObjectMapper objectMapper = new ObjectMapper();
		List<PosidexData> listResponse = new ArrayList<>();
		List<MatchCriteria> match = new ArrayList<>();
		String matchNames = "";
		String strengths = "";

		try {

			response = template.exchange(posidexUrl, HttpMethod.POST, requestEntity, String.class);
			ResponseModel object = objectMapper.readValue(response.getBody(), ResponseModel.class);
			if (!"".equals(object.getResponse().getWorldcheckmatchresponse()+"")
					&& !object.getResponse().getWorldcheckmatchresponse().getMatchCriteriaList().isEmpty()) {
				match = object.getResponse().getWorldcheckmatchresponse().getMatchCriteriaList();

				for (MatchCriteria posidex : match) {

					matchNames = posidex.getMatchedName();
					strengths = posidex.getStrengths();
					String[] splitMatchNames = matchNames.split(";");
					String[] splitStrengths = strengths.split(";");

					int x = splitMatchNames.length;
					int y = splitStrengths.length;

					if (x == y) {
						for (int i = 0; (i < x && i < y); i++) {
							PosidexData details = new PosidexData();
							details.setUid(Long.valueOf(posidex.getPrimarypersonality()));
							details.setMatchedName(splitMatchNames[i]);
							details.setScore(Float.valueOf(splitStrengths[i]));
							listResponse.add(details);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("Posidex List  Before --", listResponse);

		return listResponse;
	}

	public Map<String, List<?>> callPythonServiceReq(RequestModel model) {
		String name = model.getFirstNameEnglish() + " " + model.getSecondNameEnglish() + " "
				+ model.getThirdNameEnglish() + " " + model.getTribeNameEnglish();

		// adding the formdata into headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
		multipartBodyBuilder.part("lang_val", "1");
		multipartBodyBuilder.part("uinput_val", name);

		// multipart/form-data request body
		MultiValueMap<String, HttpEntity<?>> multipartBody = multipartBodyBuilder.build();

		// The complete http request body.
		HttpEntity<MultiValueMap<String, HttpEntity<?>>> httpEntity = new HttpEntity<>(multipartBody, headers);

		ResponseEntity<String> response = null;
		ObjectMapper objectMapper = new ObjectMapper();
		PythonResponseModel object = new PythonResponseModel();
		List<PythonJaroData> pData = new ArrayList<>();
		List<PythonLevenData> rData = new ArrayList<>();
		List<PythonQratioData> qData = new ArrayList<>();
		List<PythonSetRatioData> setData = new ArrayList<>();
		List<PythonSortRationData> sortData = new ArrayList<>();
		HashMap<String, List<?>> dataMap = new HashMap<>();
		try {

			response = template.postForEntity(pythonUrl, httpEntity, String.class);
			object = objectMapper.readValue(response.getBody(), PythonResponseModel.class);
			for (ResponseTargetNames targetNames : object.getTargetNames()) {

				for (ResponseJaroWinkler winkler : targetNames.getJaroWinkler()) {
					PythonJaroData jaroData = new PythonJaroData();
					jaroData.setUid(winkler.getUid());
					jaroData.setMatchedName(winkler.getPersonName());
					jaroData.setScore(winkler.getScore());

					pData.add(jaroData);
				}

				for (ResponseLevenshtein winkler : targetNames.getLevenshTein()) {
					PythonLevenData pyData = new PythonLevenData();
					pyData.setUid(winkler.getUid());
					pyData.setMatchedName(winkler.getPersonName());
					pyData.setScore(winkler.getScore());

					rData.add(pyData);
				}

				for (ResponseQRatio winkler : targetNames.getQratio()) {
					PythonQratioData qrData = new PythonQratioData();
					qrData.setUid(winkler.getUid());
					qrData.setMatchedName(winkler.getPersonName());
					qrData.setScore(winkler.getScore());

					qData.add(qrData);
				}

				for (ResponseSetRatio winkler : targetNames.getSetRatio()) {
					PythonSetRatioData setRData = new PythonSetRatioData();
					setRData.setUid(winkler.getUid());
					setRData.setMatchedName(winkler.getPersonName());
					setRData.setScore(winkler.getScore());

					setData.add(setRData);
				}

				for (ResponseSortRatio winkler : targetNames.getSortRatio()) {
					PythonSortRationData sortRData = new PythonSortRationData();
					sortRData.setUid(winkler.getUid());
					sortRData.setMatchedName(winkler.getPersonName());
					sortRData.setScore(winkler.getScore());

					sortData.add(sortRData);
				}

			}

			dataMap.put("JARO", pData);
			dataMap.put("LEVENSHTEIN", rData);
			dataMap.put("QRATIO", qData);
			dataMap.put("SETRATIO", setData);
			dataMap.put("SORTRATIO", sortData);

			log.info("data map ",dataMap);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataMap;
	}

	public boolean isAlpha(String s) {
		if (s == null) {
			return false;
		}

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z')) {
				return false;
			}
		}
		return true;
	}

}
