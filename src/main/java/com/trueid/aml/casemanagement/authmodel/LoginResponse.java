package com.trueid.aml.casemanagement.authmodel;

import lombok.Data;

@Data
public class LoginResponse {
	
	private String accessToken;
	private String refreshToken;
	private String expiresIn;
	private String refreshExpiresIn;
	private String tokenType;
	
}
