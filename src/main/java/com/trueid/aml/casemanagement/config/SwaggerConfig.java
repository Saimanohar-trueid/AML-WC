package com.trueid.aml.casemanagement.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	public static final String AUTHORIZATION_HEADER = "Authorization";

	private static final Set<String> DEFAULT_PRODUCES_CONSUMES = new HashSet<>(Arrays.asList("application/json"));

	  @Bean
	  public Docket api() {
	    ParameterBuilder parameterBuilder = new ParameterBuilder();
	    parameterBuilder.name("Authorization")
			    .modelRef(new ModelRef("string"))
			    .parameterType("header")
			    .description("JWT token")
			    .required(true)
			    .build();
	    List<springfox.documentation.service.Parameter> parameters = new ArrayList<>();
	    parameters.add(parameterBuilder.build());
	    return new Docket(DocumentationType.SWAGGER_2)
		    .produces(DEFAULT_PRODUCES_CONSUMES)
		    .consumes(DEFAULT_PRODUCES_CONSUMES)
		    .select()
		    .build()
		    // Setting globalOperationParameters ensures that authentication header is applied to all APIs
		    .globalOperationParameters(parameters);
	  }

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	}
}