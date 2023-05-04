package com.trueid.aml.casemanagement.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "KEYCLOAK_TOKEN_DTLS_bkp")
@Data
public class KeycloakUserDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long tokenId;
	
	private String userName;
	
	@Lob
	private String authToken;
	
	private Date createdDate;


}
