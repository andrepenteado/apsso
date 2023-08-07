package com.github.andrepenteado.apsso.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ApssoLoginApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ApssoLoginApplication.class, args);
    }

}
