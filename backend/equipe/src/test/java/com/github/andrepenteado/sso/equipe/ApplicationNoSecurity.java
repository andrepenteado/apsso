package com.github.andrepenteado.sso.equipe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("test")
class ApplicationNoSecurity {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorize) ->
                authorize
                    .anyRequest().permitAll()
            )
            .cors((cors) -> cors.disable())
            .csrf((csrf) -> csrf.disable());

        return http.build();
    }

}
