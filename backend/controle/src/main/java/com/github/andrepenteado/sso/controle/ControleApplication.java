package com.github.andrepenteado.sso.controle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "com.github.andrepenteado.sso", "com.github.andrepenteado.core" })
@EntityScan(basePackages = { "com.github.andrepenteado.sso", "com.github.andrepenteado.core" })
@EnableJpaRepositories(basePackages = { "com.github.andrepenteado.sso", "com.github.andrepenteado.core" })
public class ControleApplication {

	public static final String PERFIL_ARQUITETO = "ROLE_com.github.andrepenteado.sso.controle_ARQUITETO";

    public static void main(String[] args) {
		SpringApplication.run(ControleApplication.class, args);
	}

}

