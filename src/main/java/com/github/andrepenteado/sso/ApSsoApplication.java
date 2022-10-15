package com.github.andrepenteado.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ApSsoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ApSsoApplication.class, args);
    }

}
