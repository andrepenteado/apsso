package com.github.andrepenteado.apsso.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.validation.BindingResult;

@SpringBootApplication
public class ApssoBackendApplication {
    
    public static void main(String[] args) {
		SpringApplication.run(ApssoBackendApplication.class, args);
	}

    public static String validateModel(BindingResult validacao) {
        String result = null;
        if (validacao.hasErrors()) {
            var erros = new StringBuilder();
            final StringBuilder errosFinal = erros;
            validacao.getFieldErrors().forEach(msg -> {
                errosFinal.append(msg.getDefaultMessage());
            });
            result = errosFinal.toString();
        }
        return result;
    }

    @Bean
    @Profile("!test")
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorize) ->
                authorize
                    .requestMatchers("/home").permitAll()
                    .anyRequest().authenticated()
            )
            .oauth2Login(Customizer.withDefaults())
            .csrf((csrf) -> csrf.disable());

        return http.build();
    }

}