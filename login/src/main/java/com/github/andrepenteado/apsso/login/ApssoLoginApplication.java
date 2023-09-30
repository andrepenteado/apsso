package com.github.andrepenteado.apsso.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.github.andrepenteado.apsso")
@EntityScan(basePackages = "com.github.andrepenteado.apsso")
@EnableJpaRepositories(basePackages = "com.github.andrepenteado.apsso")
public class ApssoLoginApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ApssoLoginApplication.class, args);
    }

}
