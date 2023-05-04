package com.trueid.aml.casemanagement.controller;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.keycloak.RSATokenVerifier;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trueid.aml.casemanagement.service.AmlNameMatchingService;


@RestController
@RequestMapping("/consumer")
public class ServiceConsumerController {
	private static final Logger log = LoggerFactory.getLogger(ServiceConsumerController.class);

	@Autowired
	private AmlNameMatchingService service;

	@Value("${keycloak.auth-server-url}")
	private String keycloakUrl;

	@Value("${keycloak.realm}")
	private String relamName;

	@SuppressWarnings("deprecation")
	@PostMapping(value = "/service")
	public ResponseEntity<String> callService(@RequestHeader(value = "Authorization") String authHeader) {

		String response = "";

		authHeader = authHeader.replace("Bearer ", "");
		try {
			 /**
			   * @deprecated  As of release 1.7, replaced by {@link #doTheThingBetter()}
			   */
			AccessToken token = RSATokenVerifier.create(authHeader).getToken();
			log.info("Expiry time = ", token.getExpiration());
			log.info("User Name = ", token.getPreferredUsername());
			log.info("type = ", token.getType());
			log.info("Subject = ", token.getSubject());
			log.info("Session ID = ", token.getSessionId());
			
		} catch (VerificationException e) {
			e.printStackTrace();
		}

		try {
			response = service.consumerService();
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/servicefile")
	public ResponseEntity<String> callServiceFile() {
		String response = "";

		try {
			 response = service.consumerService();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@SuppressWarnings("deprecation")
	public static AccessToken decodeToken(String tokenString, PublicKey realmPublicKey) throws VerificationException {
		TokenVerifier<AccessToken> verifier = TokenVerifier.create(tokenString, AccessToken.class);
		return verifier.realmUrl("https://your-keycloak-server/auth/realms/your-realm")
				.publicKey(realmPublicKey).verify().getToken();
	}

	public static PublicKey convertStringToPublicKey(String publicKeyString) throws Exception {
		byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA"); // Or "EC" or "DSA"
		return keyFactory.generatePublic(keySpec);
	}

}
