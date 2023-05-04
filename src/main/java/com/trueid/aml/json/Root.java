package com.trueid.aml.json;

import lombok.Data;

@Data
public class Root {
	
    int took;
    boolean timedOut;
    Shards shards;
    Hits hits;

}
