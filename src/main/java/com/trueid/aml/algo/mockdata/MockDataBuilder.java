package com.trueid.aml.algo.mockdata;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.trueid.aml.algo.data.PosidexData;
import com.trueid.aml.algo.data.PythonJaroData;
import com.trueid.aml.algo.data.PythonLevenData;
import com.trueid.aml.algo.data.PythonQratioData;
import com.trueid.aml.algo.data.PythonSetRatioData;
import com.trueid.aml.algo.data.PythonSortRationData;
import com.trueid.aml.algo.data.RosetteData;

public class MockDataBuilder {
	private static final Logger log = LoggerFactory.getLogger(MockDataBuilder.class);
    public List buildData(String fileName, Class<?> cls) {
        log.info("Rading File:",fileName);
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        JsonReader reader = new JsonReader(streamReader);

        List<Object> list = null;
        if (cls.equals(PosidexData.class)) {
            list = Arrays.asList(new Gson().fromJson(reader, PosidexData[].class));
        } else if (cls.equals(RosetteData.class)) {
            list = Arrays.asList(new Gson().fromJson(reader, RosetteData[].class));
        } else if (cls.equals(PythonJaroData.class)) {
            list = Arrays.asList(new Gson().fromJson(reader, PythonJaroData[].class));
        } else if (cls.equals(PythonLevenData.class)) {
            list = Arrays.asList(new Gson().fromJson(reader, PythonLevenData[].class));
        } else if (cls.equals(PythonQratioData.class)) {
            list = Arrays.asList(new Gson().fromJson(reader, PythonQratioData[].class));
        } else if (cls.equals(PythonSetRatioData.class)) {
            list = Arrays.asList(new Gson().fromJson(reader, PythonSetRatioData[].class));
        } else if (cls.equals(PythonSortRationData.class)) {
            list = Arrays.asList(new Gson().fromJson(reader, PythonSortRationData[].class));
        }

        return list;
    }

    public Map<String, List<?>> prepareMockData() {
        HashMap<String, List<?>> dataMap = new HashMap<>();

        try {

                @SuppressWarnings("unchecked")
				List<PosidexData> pData = buildData(
                        "json/posidex.json",
                        PosidexData.class);

                dataMap.put("POSIDEX", pData);
           

                @SuppressWarnings("unchecked")
				List<RosetteData> rData = buildData(
                        "json/rosette.json",
                        RosetteData.class);

                dataMap.put("ROSETTE", rData);
                @SuppressWarnings("unchecked")
				List<PythonJaroData> rData1 = buildData(
                        "json/pythonJaro.json",
                        PythonJaroData.class);

                dataMap.put("JARO", rData1);
                @SuppressWarnings("unchecked")
				List<PythonLevenData> rData11 = buildData(
                        "json/pythonLeven.json",
                        PythonLevenData.class);

                dataMap.put("LEVENSHTEIN", rData11);
           
                @SuppressWarnings("unchecked")
				List<PythonQratioData> rData111 = buildData(
                        "json/pythonQratio.json",
                        PythonQratioData.class);

                dataMap.put("QRATIO", rData111);
            
                @SuppressWarnings("unchecked")
				List<PythonSetRatioData> rData1111 = buildData(
                        "json/pythonSetRatio.json",
                        PythonSetRatioData.class);

                dataMap.put("SETRATIO", rData1111);
           
                @SuppressWarnings("unchecked")
				List<PythonSortRationData> rData11111 = buildData(
                        "json/pythonSortRatio.json",
                        PythonSortRationData.class);

                dataMap.put("SORTRATIO", rData11111);
            

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataMap;
    }
}
