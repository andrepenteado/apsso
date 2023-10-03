package com.github.andrepenteado.apsso.login.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.security.KeyStore;
import java.util.Arrays;

@Configuration
public class AuthorizationServer {

    @Bean
    @Order(1)
    SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration
            .applyDefaultSecurity(http);

        http
            .getConfigurer(OAuth2AuthorizationServerConfigurer.class)
            .oidc(Customizer.withDefaults());

        http
            .exceptionHandling((exceptions) -> exceptions
                .authenticationEntryPoint(
                    new LoginUrlAuthenticationEntryPoint("/login"))
            )
            .oauth2ResourceServer(
                oauth2ResourceServer -> oauth2ResourceServer.jwt(Customizer.withDefaults())
            );

        return http.build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(CorsConfigurer::disable)
            .csrf(CsrfConfigurer::disable)
            .authorizeHttpRequests((authorize) -> authorize
                .anyRequest().permitAll()
            )
            .formLogin(form -> {
                form
                    .loginPage("/login")
                    .permitAll();
            });
        return http.build();
    }

    @Bean
    RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
        /*RegisteredClientRepository registeredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);

        // Sistema APsso
        RegisteredClient apSSO = RegisteredClient
            .withId("1")
            .clientId("com.github.andrepenteado.apsso")
            .clientSecret("{noop}apsso-secret")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .redirectUri("http://apsso-backend:30001/login/oauth2/code/apsso-oidc")
            .redirectUri("http://apsso-backend:30001/authorized")
            .postLogoutRedirectUri("http://apsso-backend:30001/logout")
            //.redirectUri("https://oidcdebugger.com/debug")
            .scope(OidcScopes.OPENID)
            //.scope("static")
            .tokenSettings(TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofMinutes(15))
                .refreshTokenTimeToLive(Duration.ofDays(1))
                .reuseRefreshTokens(false)
                .build())
            .clientSettings(ClientSettings.builder()
                .requireAuthorizationConsent(false)
                .build())
            .build();
        registeredClientRepository.save(apSSO);

        // Sistema AProove
        RegisteredClient apRoove = RegisteredClient
            .withId("2")
            .clientId("com.github.andrepenteado.aproove")
            .clientSecret("{noop}aproove-secret")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .redirectUri("http://aproove:30002/login/oauth2/code/aproove-oidc")
            .redirectUri("http://aproove:30002/authorized")
            .postLogoutRedirectUri("http://aproove:30002/logout")
            //.redirectUri("https://oidcdebugger.com/debug")
            .scope(OidcScopes.OPENID)
            //.scope("static")
            .tokenSettings(TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofMinutes(15))
                .refreshTokenTimeToLive(Duration.ofDays(1))
                .reuseRefreshTokens(false)
                .build())
            .clientSettings(ClientSettings.builder()
                .requireAuthorizationConsent(false)
                .build())
            .build();
        registeredClientRepository.save(apRoove);

        return registeredClientRepository;*/
    }

    @Bean
    JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    OAuth2AuthorizationService oAuth2AuthorizationService(JdbcTemplate jdbcTemplate) {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository(jdbcTemplate));
    }

    @Bean
    OAuth2AuthorizationConsentService oAuth2AuthorizationConsentService(JdbcTemplate jdbcTemplate) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository(jdbcTemplate));
    }

    @Bean
    JWKSource<SecurityContext> jwkSource(GlobalProperties properties) throws Exception {
        final var jksProp = properties.getJks();
        final var jksFile = new ClassPathResource(jksProp.getPath()).getInputStream();
        final var keyStore = KeyStore.getInstance("JKS");

        keyStore.load(jksFile, jksProp.getStorepass().toCharArray());
        final var rsaKey = RSAKey.load(keyStore, jksProp.getAlias(), jksProp.getKeypass().toCharArray());

        return (((jwkSelector, securityContext) -> jwkSelector.select(new JWKSet(rsaKey))));
    }

    @Bean
    AuthorizationServerSettings authorizationServerSettings(GlobalProperties properties) {
        return AuthorizationServerSettings.builder().issuer(properties.getUri()).build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        //
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }

}
