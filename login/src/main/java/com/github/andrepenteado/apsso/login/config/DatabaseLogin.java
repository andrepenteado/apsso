package com.github.andrepenteado.apsso.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
public class DatabaseLogin {

    @Bean
    public UserDetailsService userDetailsService(JdbcTemplate jdbcTemplate) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(jdbcTemplate.getDataSource());
        return users;
    }


}
