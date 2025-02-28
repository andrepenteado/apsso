package com.github.andrepenteado.sso.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "com.github.andrepenteado.sso" })
@EntityScan(basePackages = { "com.github.andrepenteado.sso" })
@EnableJpaRepositories(basePackages = { "com.github.andrepenteado.sso" })
public class ApiApplication {

    public static final String PERFIL_CONSULTAR_API = "ROLE_com.github.andrepenteado.sso.api_CONSULTAR_API";

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}


