package com.trueid.aml.algo.dto;

import java.util.List;

import com.trueid.aml.utills.ResponseElastic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadListDataIntoCSV {

	private List<PosidexResponseDetails> posidexDetails;
	private List<ResponseElastic> rosetteDetails;
	private List<ResponseElastic> rosetteDetailsAlias;
	private List<ResponseTargetNames> pythonDetails;
}
