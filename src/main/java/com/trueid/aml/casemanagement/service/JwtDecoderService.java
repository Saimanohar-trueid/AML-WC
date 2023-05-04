package com.trueid.aml.casemanagement.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.Base64;


//@Service
public class JwtDecoderService {
	
	private static final Key secret = MacProvider.generateKey(SignatureAlgorithm.HS256);
    private static final byte[] secretBytes = secret.getEncoded();
    private static final String BASE64SECRETBYTES = Base64.getEncoder().encodeToString(secretBytes);


    public Claims decodeJwt(String jwt) {
        return Jwts.parser().setSigningKey(BASE64SECRETBYTES)
        		.parseClaimsJwt(jwt).getBody();
    }
}
