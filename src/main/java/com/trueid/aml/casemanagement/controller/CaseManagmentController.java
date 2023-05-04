package com.trueid.aml.casemanagement.controller;

import static com.trueid.aml.casemanagement.config.SecurityConfig.SECURITY_CONFIG_NAME;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trueid.aml.casemanagement.authmodel.UserDTO;
import com.trueid.aml.casemanagement.entity.AMLWorldCheckCaseData;
import com.trueid.aml.casemanagement.entity.AMLWorldCheckCaseManagment;
import com.trueid.aml.casemanagement.service.AMLCaseManagmentService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Api(value = "CaseManagmentController", description = "REST APIs related to AML Case Managment")
@RestController
@RequestMapping("/api/caseManagment")
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
public class CaseManagmentController {

	@Autowired
	AMLCaseManagmentService amlService;

	private static final Logger log = LoggerFactory.getLogger(CaseManagmentController.class);

	@Value("${spring.security.oauth2.client.registration.oauth2-client-credentials.client-secret}")
	private String SECRETKEY;

	@Value("${spring.security.oauth2.client.registration.oauth2-client-credentials.client-id}")
	private String CLIENTID;

	@Value("${keycloak.auth-server-url}")
	private String AUTHURL;

	@Value("${keycloak.realm}")
	private String REALM;
	
	@Value("${keyclaok.admin.secretkey}")
	private String ADMINSECRETKEY;
	
	private String role = "user";

	@PostMapping(path = "/create")
	public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
		log.info("CaseManagmentController createUser() Method Execution Started ");
		Keycloak keycloak = KeycloakBuilder.builder().serverUrl(AUTHURL).grantType(OAuth2Constants.PASSWORD)
				.realm("master").clientId("admin-cli").clientSecret(ADMINSECRETKEY).username("admin").password("admin")
				.resteasyClient(new ResteasyClientBuilderImpl().connectionPoolSize(10).build()).build();

		keycloak.tokenManager().getAccessToken();

		UserRepresentation user = new UserRepresentation();

		user.setEnabled(true);
		user.setUsername(userDTO.getUserName());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmailAddress());

		// Get realm RealmResource
		RealmResource realmResource = keycloak.realm(REALM);
		UsersResource usersRessource = realmResource.users();

		Response response = usersRessource.create(user);
		
		userDTO.setStatusCode(response.getStatus());
		userDTO.setStatus(response.getStatusInfo().toString());
		log.info("CaseManagmentController createUser() Method Object Respnose Status::-" + response.getStatus());

		if (response.getStatus() == 201) {

			String userId = CreatedResponseUtil.getCreatedId(response);

			log.info("Created userId {}", userId);
		
			// create password credential
			CredentialRepresentation passwordCred = new CredentialRepresentation();
			passwordCred.setTemporary(false);
			passwordCred.setType(CredentialRepresentation.PASSWORD);
			passwordCred.setValue(userDTO.getPassword());

			UserResource userResource = usersRessource.get(userId);

			// Set password credential
			userResource.resetPassword(passwordCred);

			// Get realm role student
			RoleRepresentation realmRoleUser = realmResource.roles().get(role).toRepresentation();

			// Assign realm role student to user
			userResource.roles().realmLevel().add(Arrays.asList(realmRoleUser));
		}
		log.info("CaseManagmentController createUser() Method Execution End ");
		return ResponseEntity.ok(userDTO);
	}


	@PostMapping(path = "/signin")
	public ResponseEntity<?> signin(@RequestBody UserDTO userDTO) {
		log.info("CaseManagmentController signin() Method Execution Start ");
		Map<String, Object> clientCredentials = new HashMap<>();
		clientCredentials.put("secret", SECRETKEY);
		clientCredentials.put("grant_type", "password");

		Configuration configuration = new Configuration(AUTHURL, REALM, CLIENTID, clientCredentials, null);
		AuthzClient authzClient = AuthzClient.create(configuration);

		AccessTokenResponse response = authzClient.obtainAccessToken(userDTO.getEmailAddress(), userDTO.getPassword());
		log.info("CaseManagmentController signin() Method Execution End ");
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/unprotected-data")
	public String getName() {
		return "Hello, this api is not protected.";
	}

	@GetMapping(value = "/protected-data")
	public String getEmail() {
		return "Hello, this api is protected.";
	}

	@PostMapping("/amlCreateCase")
	public ResponseEntity<Object> amlCreateCase() {
		log.info("CaseManagmentController amlCreateCase() Method Execution Start ");
		String response = amlService.amlCreateCase();
		log.info("CaseManagmentController amlCreateCase() Method Execution End ");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/amlUpdateCase")
	public ResponseEntity<Object> amlUpdateCase(@RequestBody List<AMLWorldCheckCaseData> amlCaseDetails) {
		log.info("CaseManagmentController amlCreateCase() Method Execution Start ");
		return new ResponseEntity<>(amlService.amlUpdateCase(amlCaseDetails), HttpStatus.UPGRADE_REQUIRED);
	}

	@PutMapping("/amlMangUpdateCase")
	public ResponseEntity<Object> amlMangUpdateCase(@RequestBody AMLWorldCheckCaseManagment amlMangCaseDetails) {
		return new ResponseEntity<>(amlService.amlUpdateCaseMange(amlMangCaseDetails), HttpStatus.UPGRADE_REQUIRED);
	}

	@GetMapping("/amlGetAllCases")
	public ResponseEntity<Object> amlGetAllCases() {
		return new ResponseEntity<>(amlService.amlGetAllCases(), HttpStatus.OK);
	}

	@PostMapping("/amlGetPendingCases")
	public ResponseEntity<Object> amlGetPendingCases(@RequestParam(name = "status") String caseStatus) {
		return new ResponseEntity<>(amlService.amlGetByCaseStatus(caseStatus), HttpStatus.OK);
	}

	@PostMapping("/amlGetPendingCasesByCaseId")
	public ResponseEntity<Object> amlGetPendingCasesByCaseId(@RequestParam(name = "caseId") Long caseId) {
		return new ResponseEntity<>(amlService.amlGetCaseById(caseId), HttpStatus.OK);
	}
}
