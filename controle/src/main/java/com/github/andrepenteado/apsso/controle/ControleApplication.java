package com.github.andrepenteado.apsso.controle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "com.github.andrepenteado.apsso", "com.github.andrepenteado.core.web" })
@EntityScan(basePackages = "com.github.andrepenteado.apsso")
@EnableJpaRepositories(basePackages = "com.github.andrepenteado.apsso")
public class ControleApplication {
    
    public static void main(String[] args) {
		SpringApplication.run(ControleApplication.class, args);
	}

}

