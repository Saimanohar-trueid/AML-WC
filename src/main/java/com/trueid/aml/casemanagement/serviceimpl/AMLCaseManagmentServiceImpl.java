package com.trueid.aml.casemanagement.serviceimpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trueid.aml.casemanagement.entity.AMLWorldCheckCaseData;
import com.trueid.aml.casemanagement.entity.AMLWorldCheckCaseManagment;
import com.trueid.aml.casemanagement.repository.AMLCaseDataRepo;
import com.trueid.aml.casemanagement.repository.AMLCaseManagmentRepo;
import com.trueid.aml.casemanagement.service.AMLCaseManagmentService;




@Service
public class AMLCaseManagmentServiceImpl implements AMLCaseManagmentService {
	private static final Logger log = LoggerFactory.getLogger(AMLCaseManagmentServiceImpl.class);
	@Autowired
	AMLCaseManagmentRepo amlCaseMangRepo;

	@Autowired
	AMLCaseDataRepo amlCaseDataRepo;

	@Override
	public String amlCreateCase() {
		String line = "";
		int count = 0;
		int count1 = 0;
		int numcount = 0;
		List<Integer> uidList = new ArrayList<>();
		List<String> matchedNameList = new ArrayList<>();
		List<Float> scoreList = new ArrayList<>();
		String user = "Admin";
		String status = "Approved";
		String orgName = "TrueId";
		float maxScore = 0;
		float minScore = 0;
		List<AMLWorldCheckCaseManagment> amlListCaseMang = new ArrayList<>();
		String response = "Successfully Uploaded..!";
		try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/WorldCheckResponse16.csv"))) {
			while ((line = br.readLine()) != null) {
				String matchedData = "";
				Object[] data = line.split(",");
				AMLWorldCheckCaseManagment amlCaseMang = new AMLWorldCheckCaseManagment();
				List<AMLWorldCheckCaseData> listName = new ArrayList<>();
				for (int i = 2; i < data.length; i++) {
					if (!data[i].equals("")) {
						matchedData = (String) data[i];
						if (numcount == 3) {
							numcount = 0;
						}
						if (numcount == 0) {
							uidList.add(Integer.parseInt(matchedData));
						}
						if (numcount == 1) {
							matchedNameList.add(matchedData);
						}
						if (numcount == 2) {
							scoreList.add(Float.parseFloat(matchedData));
						}
						numcount++;
					}
				}
				for (int j = 0; j < scoreList.size(); j++) {
					AMLWorldCheckCaseData amlCaseDataObj = new AMLWorldCheckCaseData();
					amlCaseDataObj.setAlgorithem(data[0].toString());
					amlCaseMang.setScreenedName(data[1].toString());
					amlCaseDataObj.setUuid(Long.valueOf(uidList.get(count1)));
					amlCaseDataObj.setScore(String.valueOf(scoreList.get(count1)));
					amlCaseDataObj.setNameMatch(matchedNameList.get(count1));
					amlCaseDataObj.setStatus(status);
					count1++;
					listName.add(amlCaseDataObj);
				}
				count1 = 0;

				maxScore = Collections.max(scoreList);
				minScore = Collections.min(scoreList);
				amlCaseMang.setScoreMax(String.valueOf(maxScore));
				amlCaseMang.setScoreMin(String.valueOf(minScore));
				amlCaseMang.setStatus(status);
				amlCaseMang.setUser(user);
				amlCaseMang.setCasesData(listName);
				amlCaseMang.setOrganization(orgName);
				amlListCaseMang.add(amlCaseMang);

				log.info("Case Mange  Return::", amlCaseMang);
				log.info("Count ", count++);
			}
			amlCaseMangRepo.saveAll(amlListCaseMang);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public List<AMLWorldCheckCaseManagment> amlGetAllCases() {
		return amlCaseMangRepo.findAll();
	}

	@Override
	public List<AMLWorldCheckCaseManagment> amlGetByCaseStatus(String caseStatus) {
		List<AMLWorldCheckCaseManagment> amlListCaseMang = new ArrayList<>();

		amlListCaseMang = amlCaseMangRepo.findByStatus(caseStatus);
		return amlListCaseMang;
	}

	@Override
	public List<AMLWorldCheckCaseData> amlGetCaseById(Long caseId) {
		List<AMLWorldCheckCaseData> amlListCase = new ArrayList<>();
		amlListCase = amlCaseDataRepo.findAllAmlWorldCheckCaseDatas(caseId);
		return amlListCase;
	}

	@Override
	public List<AMLWorldCheckCaseData> amlUpdateCase(List<AMLWorldCheckCaseData> amlCaseDetails) {

		return amlCaseDataRepo.saveAll(amlCaseDetails);
	}

	@Override
	public AMLWorldCheckCaseManagment amlUpdateCaseMange(AMLWorldCheckCaseManagment amlMangCaseDetails) {
		return amlCaseMangRepo.save(amlMangCaseDetails);
	}

}
