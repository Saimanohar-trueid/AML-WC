package com.trueid.aml.casemanagement.config;

import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticatedActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakSecurityContextRequestFilter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import static io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER;
import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP;
import io.swagger.v3.oas.annotations.security.SecurityScheme;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SecurityScheme(name = SecurityConfig.SECURITY_CONFIG_NAME, in = HEADER, type = HTTP, scheme = "bearer", bearerFormat = "JWT")
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
	private static final String[] AUTH_WHITE_LIST = { "/v3/api-docs/**", "/swagger-ui/**", "/v2/api-docs/**",
			"/swagger-resources/**" };
	public static final String SECURITY_CONFIG_NAME = "App Bearer token";
	
	private static final String[] AUTH_WHITELIST = {
	        "/authenticate",
	        "/swagger-resources/**",
	        "/swagger-ui/**",
	        "/v3/api-docs",
	        "/webjars/**"
	}; 
	private static final String[] SWAGGER_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
    };

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);

		http.cors().and().csrf().disable().sessionManagement().

				sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/api/caseManagment/unprotected-data").permitAll().antMatchers("/api/caseManagment/signin")
				.permitAll().antMatchers("/api/caseManagment/create").permitAll()
				.antMatchers(SWAGGER_WHITELIST).permitAll()
				.antMatchers("/api/caseManagment/login").permitAll()
				.antMatchers("/api/**").permitAll()
				.antMatchers("/consumer/**").hasAnyRole("user").anyRequest()
				.authenticated()
		        .and().sessionManagement()
		        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
		        .sessionFixation().migrateSession()
		        .maximumSessions(1).maxSessionsPreventsLogin(true);
	}
	
	

	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {

		/**
		 * Returning NullAuthenticatedSessionStrategy means app will not remember
		 * session
		 */

		return new NullAuthenticatedSessionStrategy();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) {
		KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();

		keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());

		auth.authenticationProvider(keycloakAuthenticationProvider);
	}

	@Bean
	public FilterRegistrationBean<?> keycloakAuthenticationProcessingFilterRegistrationBean(
			KeycloakAuthenticationProcessingFilter filter) {

		FilterRegistrationBean<?> registrationBean = new FilterRegistrationBean<>(filter);

		registrationBean.setEnabled(false);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<?> keycloakPreAuthActionsFilterRegistrationBean(KeycloakPreAuthActionsFilter filter) {

		FilterRegistrationBean<?> registrationBean = new FilterRegistrationBean<>(filter);
		registrationBean.setEnabled(false);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<?> keycloakAuthenticatedActionsFilterBean(KeycloakAuthenticatedActionsFilter filter) {

		FilterRegistrationBean<?> registrationBean = new FilterRegistrationBean<>(filter);

		registrationBean.setEnabled(false);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<?> keycloakSecurityContextRequestFilterBean(
			KeycloakSecurityContextRequestFilter filter) {

		FilterRegistrationBean<?> registrationBean = new FilterRegistrationBean<>(filter);

		registrationBean.setEnabled(false);

		return registrationBean;
	}

	@Bean
	@Override
	@ConditionalOnMissingBean(HttpSessionManager.class)
	protected HttpSessionManager httpSessionManager() {
		return new HttpSessionManager();
	}
}