package com.trueid.aml.algo.data;

import lombok.Data;

@Data
public class MatchedData {
     String alogName;
    public MatchedData(String algoName){
        this.alogName = algoName;
    }
}
