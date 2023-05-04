package com.trueid.aml.algo.data;
import lombok.Data;

@Data
public class PythonSetRatioData extends MatchedData{
    String matchedName;
    Float score;
    Long uid;
    
    public PythonSetRatioData(){
        super("SETRATIO");
    }
    @Override
    public String toString() {
        return  uid+","+matchedName.replace(",", ";")+","+score;
    }

}
