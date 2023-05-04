package com.trueid.aml.algo.data;

import lombok.Data;

@Data
public class PythonJaroData extends MatchedData{
    String matchedName;
    Float score;
    Long uid;
    
    public PythonJaroData(){
        super("JARO");
    }
    @Override
    public String toString() {
        return  uid+","+matchedName.replace(",", ";")+","+score;
    }

}
