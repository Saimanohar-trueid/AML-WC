package com.trueid.aml.algo.dto;

import java.util.List;

import com.trueid.aml.utills.ResponseElastic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadListDataIntoLocalCSV {

	private List<PosidexResponseDetails> posidexDetails;
	private List<ResponseElastic> rosetteDetails;
	private List<ResponseJaroWinkler> jaroListDetails;
	private List<ResponseLevenshtein> levenshteinListDetails;
	private List<ResponseQRatio> qratioListDetails;
	private List<ResponseSetRatio> setRatioList;
	private List<ResponseSortRatio> sortRatioList;
	
}
