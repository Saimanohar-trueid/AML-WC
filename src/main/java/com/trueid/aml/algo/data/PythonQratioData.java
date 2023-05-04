package com.trueid.aml.algo.data;
import lombok.Data;

@Data
public class PythonQratioData extends MatchedData{
    String matchedName;
    Float score;
    Long uid;
    
    public PythonQratioData(){
        super("QRATIO");
    }
    @Override
    public String toString() {
        return  uid+","+matchedName.replace(",", ";")+","+score;
    }

}