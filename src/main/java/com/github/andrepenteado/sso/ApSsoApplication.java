package com.github.andrepenteado.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ApSsoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ApSsoApplication.class, args);
    }

}
