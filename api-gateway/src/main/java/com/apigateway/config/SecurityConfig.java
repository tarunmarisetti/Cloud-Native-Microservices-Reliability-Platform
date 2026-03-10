package com.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
//spring cloud gateway build from spring reactive module so we need to import the webflux security
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity){
        serverHttpSecurity.authorizeExchange(exchanges->
                exchanges.pathMatchers(HttpMethod.GET).permitAll()
                        .pathMatchers("/myapp/employee/**").hasRole("EMPLOYEE")
                        .pathMatchers("/myapp/department/**").hasRole("DEPARTMENT")
                        .pathMatchers("/myapp/organization/**").hasRole("ORGANIZATION"))
//                config for resource server
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor())));
//        disable crsf config as there is no browser involved
        serverHttpSecurity.csrf(csrfSpec -> csrfSpec.disable());
        return serverHttpSecurity.build();
    }

    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter =
                new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter
                (new KeyCloakRoleConverter());
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}
