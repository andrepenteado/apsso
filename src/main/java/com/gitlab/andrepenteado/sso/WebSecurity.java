package com.gitlab.andrepenteado.sso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@Order(1)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final String userQuery = "SELECT Login, Senha, 1 FROM Usuario WHERE Login = ? AND (Expiracao IS NULL OR Expiracao > CURRENT_DATE)";

    private final String roleQuery = "SELECT u.Login, p.Perfil FROM Perfil_Usuario p, Usuario u WHERE u.Id = p.Id_Usuario AND u.Login = ?";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(userQuery)
                .authoritiesByUsernameQuery(roleQuery)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers("/home", "/oauth/authorize")
            .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
            .and()
                .formLogin().loginPage("/home").permitAll();
    }
}
