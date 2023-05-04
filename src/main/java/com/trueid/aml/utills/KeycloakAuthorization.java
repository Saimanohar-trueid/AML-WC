package com.trueid.aml.utills;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.representations.AccessToken;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class KeycloakAuthorization {
	
	private KeycloakAuthorization() {
	}

    private static final String INTROSPECTION_URL = "http://localhost:8085/auth/realms/test-trueid/protocol/openid-connect/token/auth";
    private static final String KEYCLOAK_CLIENT_ID = "client-id";
    private static final String KEYCLOAK_CLIENT_SECRET = "client-secret";

    public static AccessToken authorizeToken(String token) {
        ResteasyClient client = new ResteasyClientBuilderImpl().build();
        ResteasyWebTarget target = client.target(INTROSPECTION_URL);
        target.register(new ClientRequestFilter() {
            @Override
            public void filter(ClientRequestContext requestContext) throws IOException {
                String credentials = KEYCLOAK_CLIENT_ID + ":" + KEYCLOAK_CLIENT_SECRET;
                String authorization = "Basic " + java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
                requestContext.getHeaders().add("Authorization", authorization);
            }
        });
        Form form = new Form();
        form.param("token", token);
        form.param("token_type_hint", "Authorization");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.form(form));
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed to introspect token");
        }
        AccessToken introspectionResponse = response.readEntity(AccessToken.class);
        if (!introspectionResponse.isActive()) {
            throw new RuntimeException("Token is not active");
        }
        return introspectionResponse;
    }
}
