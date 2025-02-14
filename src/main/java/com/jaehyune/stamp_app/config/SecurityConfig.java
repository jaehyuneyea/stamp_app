package com.jaehyune.stamp_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // TODO: If first time, create a new User corresponding to the User's google Sub
    // TODO: role based authentication, figure out how to do it native to spring
    @Bean
    public SecurityFilterChain jwtCustomSecurityFilter(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .securityMatcher("/stamps/**")
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest()
                        .hasAuthority("SCOPE_openid"))
                .oauth2ResourceServer(oauth2 -> oauth2
                        .opaqueToken(opaque -> opaque.introspector(customOpaqueTokenIntrospector())));
        return http.build();
    }

    @Bean
    public OpaqueTokenIntrospector customOpaqueTokenIntrospector() {
        return new GoogleOpaqueTokenIntrospector(System.getenv("OAUTH_GOOGLE_CLIENT_ID"));
    }
}
