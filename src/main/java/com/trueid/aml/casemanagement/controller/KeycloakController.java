package com.trueid.aml.casemanagement.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trueid.aml.casemanagement.authmodel.LoginRequest;
import com.trueid.aml.casemanagement.authmodel.UserDTO;
import com.trueid.aml.casemanagement.entity.KeycloakUserDetailsEntity;
import com.trueid.aml.casemanagement.repository.KeycloakUserDetailsRepository;
import com.trueid.aml.casemanagement.service.KeyCloakService;




@RestController
@RequestMapping(value = "/keycloak")
public class KeycloakController {

	@Autowired
	KeyCloakService keyClockService;

	@Autowired
	KeycloakUserDetailsRepository repository;

	//@RequestMapping(value = "/token", method = RequestMethod.POST)
	@PostMapping("/token")
	public ResponseEntity<?> getTokenUsingCredentials(@RequestBody LoginRequest userCredentials) {

		String responseToken = null;
		try {

			responseToken = keyClockService.getToken(userCredentials);
			
			KeycloakUserDetailsEntity entity = new KeycloakUserDetailsEntity();
			entity.setUserName(userCredentials.getUsername());
			entity.setAuthToken(responseToken);
			entity.setCreatedDate(new Date(System.currentTimeMillis()));
			
			repository.save(entity);

		} catch (Exception e) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(responseToken, HttpStatus.OK);

	}

	/*
	 * When access token get expired than send refresh token to get new access
	 * token. We will receive new refresh token also in this response.Update
	 * client cookie with updated refresh and access token
	 */
	@GetMapping(value = "/refreshtoken")
	public ResponseEntity<?> getTokenUsingRefreshToken(@RequestHeader(value = "Authorization") String refreshToken) {

		String responseToken = null;
		try {

			responseToken = keyClockService.getByRefreshToken(refreshToken);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(responseToken, HttpStatus.OK);

	}

	/*
	 * Creating user in keycloak passing UserDTO contains username, emailid,
	 * password, firtname, lastname
	 */
	@PostMapping(value = "/create")
	public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
		try {

			keyClockService.createUserInKeyCloak(userDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		}

		catch (Exception ex) {

			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}

	}
}
