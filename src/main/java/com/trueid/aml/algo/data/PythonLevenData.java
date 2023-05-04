package com.trueid.aml.algo.data;
import lombok.Data;

@Data
public class PythonLevenData extends MatchedData{
    String matchedName;
    Float score;
    Long uid;
    
    public PythonLevenData(){
        super("LEVENSHTEIN");
    }
    @Override
    public String toString() {
        return  uid+","+matchedName.replace(",", ";")+","+score;
    }

}