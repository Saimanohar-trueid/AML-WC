package com.trueid.aml.algo.data;

import lombok.Data;

@Data
public class PosidexData extends MatchedData {
    String matchedName;
    Float score;
    Long uid;

    public PosidexData(){
        super("POSIDEX");
    }
    @Override
    public String toString() {
        return  uid+","+matchedName+","+score;
    }

    
}
