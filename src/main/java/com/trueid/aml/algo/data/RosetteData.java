package com.trueid.aml.algo.data;

import lombok.Data;

@Data
public class RosetteData extends MatchedData {
    String matchedName;
    Float score;
    Long uid;

    public RosetteData(){
        super("ROSETTE");
    }
    @Override
    public String toString() {
        return  uid+","+matchedName.replace(",", ";")+","+score;
    }

}
