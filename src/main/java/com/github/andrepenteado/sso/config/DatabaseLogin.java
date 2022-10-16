package com.github.andrepenteado.sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
public class DatabaseLogin {

//    private final String usuarioSenhaSql = "SELECT Login, Senha, 1=1 FROM Usuario WHERE Login = ?";
//
//    private final String usuarioSql = "SELECT Login FROM Usuario WHERE Login = ?";
//
//    private final String perfilSql = "SELECT u.Login, p.Nome FROM Perfil p, Usuario u, Perfil_Usuario pu WHERE u.Id = pu.Id_Usuario AND p.Id = pu.Id_Perfil AND u.Login = ?";

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public UserDetailsService userDetailsService(JdbcTemplate jdbcTemplate) {
        /*UserDetails userDetails = User.withDefaultPasswordEncoder()
            .username("admin")
            .password("admin")
            .roles("admin")
            .build();*/

        JdbcUserDetailsManager users = new JdbcUserDetailsManager(jdbcTemplate.getDataSource());
        /*users.updateUser(userDetails);*/
        return users;

        /*return new InMemoryUserDetailsManager(userDetails);*/
    }

//    @Bean
//    public UserDetailsService databaseUserLogin(JdbcTemplate jdbcTemplate) {
//        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(jdbcTemplate.getDataSource());
//        manager.setUsersByUsernameQuery(usuarioSenhaSql);
//        manager.setAuthoritiesByUsernameQuery(perfilSql);
//        manager.setUserExistsSql(usuarioSql);
//        return manager;
//    }

}
