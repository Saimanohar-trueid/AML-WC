package com.trueid.aml.utills;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class KeycloakPublicKeyFetcher {
	
	private KeycloakPublicKeyFetcher() {
	}
	
	public static String fetchRealmPublicKey(String keycloakUrl, String realmName) throws Exception {
        URL url = new URL(keycloakUrl + "/realms/" + realmName + "/protocol/openid-connect/certs");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Failed to fetch realm public key. HTTP error code: " + responseCode);
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String json = response.toString();
        JSONObject jsonObj = new JSONObject(json);
        JSONArray keys = jsonObj.getJSONArray("keys");
        JSONObject key = keys.getJSONObject(0);
        JSONArray x5c =  key.getJSONArray("x5c");
        String pKeyA = x5c.getJSONArray(0).toString();
        return pKeyA.replace("\\n", "");
    }

}
