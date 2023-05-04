package com.trueid.aml.casemanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.google.gson.Gson;
import com.trueid.aml.algo.data.PosidexData;
import com.trueid.aml.algo.dto.LoadListDataIntoCSV;
import com.trueid.aml.algo.dto.LoadListDataIntoLocalCSV;
import com.trueid.aml.algo.dto.MatchCriteria;
import com.trueid.aml.algo.dto.PythonResponseDetails;
import com.trueid.aml.algo.dto.PythonResponseModel;
import com.trueid.aml.algo.dto.RequestModel;
import com.trueid.aml.algo.dto.ResponseModel;
import com.trueid.aml.algo.dto.ResponseTargetNames;
import com.trueid.aml.utills.ResponseElastic;

@Service
public class ServiceConsumerService {
	
	private static final Logger log = LoggerFactory.getLogger(ServiceConsumerService.class);

	@Autowired
	private RestTemplate template;

//	@Value("${posidex.api.url}")
	private String posidexUrl;

	//@Value("${python.api.url}")
	private String pythonUrl;

	public String consumerService(RequestModel model) {
		LoadListDataIntoCSV dataIntoCSV = new LoadListDataIntoCSV();
		LoadListDataIntoLocalCSV localDataInfo = new LoadListDataIntoLocalCSV();
		List<PosidexData> posidexList = new ArrayList<>();
		try {
			posidexList = callPosidexService(model);
		} catch (Exception e) {
			e.printStackTrace();
		}


		List<ResponseElastic> rosetteList = new ArrayList<>();
		List<ResponseElastic> elasticAliasList = new ArrayList<>();
		try {
			rosetteList.addAll(elasticAliasList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Call Python Service
		List<ResponseTargetNames> targetList = new ArrayList<>();
		localDataInfo.setRosetteDetails(rosetteList);
		dataIntoCSV.setPythonDetails(targetList);
		dataIntoCSV.setRosetteDetails(rosetteList);

		// Grouping the Uid and stored the data into CSV
		dataInAlgorithms(dataIntoCSV);

		return "";

	}

	public void dataInAlgorithms(LoadListDataIntoCSV loadList) {

	}

	public List<PosidexData> callPosidexService(RequestModel model) {
		HttpEntity<RequestModel> requestEntity = new HttpEntity<>(model);

		ResponseEntity<String> response = null;
		ObjectMapper objectMapper = new ObjectMapper();
		List<PosidexData> listResponse = new ArrayList<>();
		Gson gson = new Gson();
		List<MatchCriteria> match = new ArrayList<>();
		String matchNames = "";
		String strengths = "";
		try {

			response = template.exchange(posidexUrl, HttpMethod.POST, requestEntity, String.class);
			ResponseModel object = objectMapper.readValue(response.getBody(), ResponseModel.class);
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

		} catch (Exception e) {
			e.printStackTrace();
		}

		String jsonCartList = gson.toJson(listResponse);
		log.info("jsonCartList   --", jsonCartList);
		return listResponse;
	}

	public List<PythonResponseDetails> callPythonService(RequestModel model) {
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
		List<PythonResponseDetails> listResponse = new ArrayList<>();

		try {

			response = template.postForEntity(pythonUrl, httpEntity, String.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listResponse;
	}

	public List<ResponseTargetNames> callPythonServiceReq(RequestModel model) {
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
		List<ResponseTargetNames> targetNamesList = new ArrayList<>();
		try {

			response = template.postForEntity(pythonUrl, httpEntity, String.class);
			object = objectMapper.readValue(response.getBody(), PythonResponseModel.class);
			for (ResponseTargetNames targetNames : object.getTargetNames()) {
				ResponseTargetNames names = new ResponseTargetNames();
				names.setJaroWinkler(targetNames.getJaroWinkler());
				names.setLevenshTein(targetNames.getLevenshTein());
				names.setQratio(targetNames.getQratio());
				names.setSetRatio(targetNames.getSetRatio());
				names.setSortRatio(targetNames.getSortRatio());
				targetNamesList.add(names);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return targetNamesList;
	}

}
