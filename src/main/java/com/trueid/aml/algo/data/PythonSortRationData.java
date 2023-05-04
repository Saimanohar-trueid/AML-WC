package com.trueid.aml.algo.data;
import lombok.Data;

@Data
public class PythonSortRationData extends MatchedData{
    String matchedName;
    Float score;
    Long uid;
    
    public PythonSortRationData(){
        super("SORTRATIO");
    }

    @Override
    public String toString() {
        return  uid+","+matchedName.replace(",", ";")+","+score;
    }
    
}