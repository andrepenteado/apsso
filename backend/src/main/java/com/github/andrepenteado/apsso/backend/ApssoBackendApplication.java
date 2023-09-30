package com.github.andrepenteado.apsso.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.validation.BindingResult;
import org.springframework.web.cors.CorsConfiguration;

@SpringBootApplication(scanBasePackages = "com.github.andrepenteado.apsso")
@EntityScan(basePackages = "com.github.andrepenteado.apsso")
@EnableJpaRepositories(basePackages = "com.github.andrepenteado.apsso")
public class ApssoBackendApplication {
    
    public static void main(String[] args) {
		SpringApplication.run(ApssoBackendApplication.class, args);
	}

    @Bean
    @Profile("!test")
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorize) ->
                authorize.anyRequest().authenticated()
            )
            .oauth2Login(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .cors(httpSecurityCorsConfigurer ->
                httpSecurityCorsConfigurer.configurationSource(request ->
                    new CorsConfiguration().applyPermitDefaultValues()
                ));

        return http.build();
    }

}
