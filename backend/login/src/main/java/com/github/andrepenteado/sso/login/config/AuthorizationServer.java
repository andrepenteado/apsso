package com.github.andrepenteado.sso.login.config;

import com.github.andrepenteado.sso.core.services.UsuarioService;
import com.github.andrepenteado.sso.core.domain.entities.PerfilSistema;
import com.github.andrepenteado.sso.core.domain.entities.Usuario;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.security.KeyStore;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class AuthorizationServer {

    private final UsuarioService usuarioService;

    @Bean
    @Order(1)
    SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration
            .applyDefaultSecurity(http);

        http
            .getConfigurer(OAuth2AuthorizationServerConfigurer.class)
            .oidc(Customizer.withDefaults());

        http
            .cors(Customizer.withDefaults());

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
            .authorizeHttpRequests((authorize) -> authorize
                .anyRequest().permitAll()
            )
            .cors(
                Customizer.withDefaults()
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
    }

    @Bean
    public UserDetailsService userDetailsService(JdbcTemplate jdbcTemplate) {
        return new JdbcUserDetailsManager(jdbcTemplate.getDataSource());
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
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer(@Qualifier("userDetailsService") UserDetailsService userDetailsService) {
        return (context) -> {
            UserDetails userDetails = userDetailsService.loadUserByUsername(context.getPrincipal().getName());

            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            context.getClaims().claims(claims ->
                claims.put("authorities", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())));

            final Usuario userEntity = usuarioService.buscar(userDetails.getUsername()).orElseThrow();

            Map<String, String> perfis = new HashMap<>();
            for (PerfilSistema perfil : userEntity.getPerfis()) {
                perfis.put(perfil.getAuthority(), perfil.getDescricao());
            }

            context.getClaims().claim("login", userEntity.getUsername());
            context.getClaims().claim("nome", userEntity.getNome());
            context.getClaims().claim("cpf", Objects.isNull(userEntity.getCpf()) ? "" : Long.toString(userEntity.getCpf()));
            context.getClaims().claim("uuidFoto", Objects.isNull(userEntity.getFoto()) ? "" : userEntity.getFoto().toString());
            context.getClaims().claim("perfis", perfis);
        };
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return new CorsFilter(source);
    }

}
