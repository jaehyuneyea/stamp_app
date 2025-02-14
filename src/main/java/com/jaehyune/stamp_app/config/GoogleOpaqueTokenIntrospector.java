package com.jaehyune.stamp_app.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.client.RestTemplate;


import java.time.Instant;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Google's access tokens are Opaque by default. Since Google doesn't provide a proper introspection,
 * we need to verify the access token by manually calling the endpoint.
 */
public class GoogleOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final String expectedClientId;
    private final RestTemplate restTemplate = new RestTemplate();

    public GoogleOpaqueTokenIntrospector(String expectedClientId) {
        this.expectedClientId = expectedClientId;
    }

    /**
     * Sends the access token received from the client to the google authorization server as an HTTP request with
     * the token in the URL.
     * @param token the access token that is received from the client. It is Google's opaque token.
     * @return If successfully validated with proper scopes, it will return an OAuth2AuthenticatedPrincipal which
     * describes the email of the user, the parsed human-readable version of the token, and the scopes.
     */
    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        Map<String, Object> response = restTemplate.getForObject("https://www.googleapis.com/oauth2/v3/tokeninfo?access_token={token}",
                Map.class,
                token
        );

        Map<String, Object> convertedClaims = convertClaims(response);
        validateToken(convertedClaims);

        String scope = (String) convertedClaims.get("scope");
        List<GrantedAuthority> authorities = parseScopes(scope);
        return new DefaultOAuth2AuthenticatedPrincipal(
                convertedClaims.get("email").toString(),
                convertedClaims,
                authorities
        );
    }

    private Map<String, Object> convertClaims(Map<String, Object> originalClaims) {
        Map<String, Object> converted = new HashMap<>(originalClaims);

        // Convert expiration time (expires_in seconds → exp Instant)
        long expiresIn = Long.parseLong(originalClaims.get("expires_in").toString());
        Instant expiration = Instant.now().plusSeconds(expiresIn);
        converted.put("exp", expiration);

        // Convert other timestamps if needed (e.g., iat, nbf)
        if (originalClaims.containsKey("iat")) {
            converted.put("iat", Instant.ofEpochSecond(Long.parseLong(originalClaims.get("iat").toString())));
        }

        return converted;
    }

    /**
     * Checks the response to ensure that a proper response was received with a proper audience. This is checked
     * first by the introspect method after receiving the response from the authorization server.
     * @param response The response that is received from the Google authorization server.
     */
    private void validateToken(Map<String, Object> response) {
        if (response == null) {
            throw new RuntimeException("Token validation failed");
        }

        // Check client ID
        if (!expectedClientId.equals(response.get("aud"))) {
            throw new RuntimeException("Invalid audience");
        }
        String audience = response.get("aud").toString();
        if (!expectedClientId.equals(audience)) {
            throw new RuntimeException("Invalid audience");
        }
    }

    /**
     * Called by the introspect method. It expects a string that describes the scope of the token that was
     * passed in and returns a functional authority described by the scope
     */
    private List<GrantedAuthority> parseScopes(String scope) {
        if (scope == null || scope.isEmpty()) {
            return Collections.emptyList();
        }
        System.out.println(Arrays.stream(scope.split(" "))
                .map(scopeName -> new SimpleGrantedAuthority("SCOPE_" + scopeName))
                .collect(Collectors.toList()));
        return Arrays.stream(scope.split(" "))
                .map(scopeName -> new SimpleGrantedAuthority("SCOPE_" + scopeName))
                .collect(Collectors.toList());
    }
}
