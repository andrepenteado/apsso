package com.github.andrepenteado.sso.controle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "com.github.andrepenteado.sso", "com.github.andrepenteado.core.web" })
@EntityScan(basePackages = "com.github.andrepenteado.sso")
@EnableJpaRepositories(basePackages = "com.github.andrepenteado.sso")
public class ControleApplication {
    
    public static void main(String[] args) {
		SpringApplication.run(ControleApplication.class, args);
	}

}

