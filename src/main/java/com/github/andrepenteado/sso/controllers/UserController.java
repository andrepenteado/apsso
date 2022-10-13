package com.github.andrepenteado.sso.controllers;

import com.github.andrepenteado.sso.UserLogin;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@Slf4j
public class UserController {

    @GetMapping("/user")
    public UserLogin user(Authentication auth, HttpServletRequest request) {
        UserLogin userLogin = new UserLogin();
        userLogin.setUsername(auth.getName());
        userLogin.setRole(auth.getAuthorities().stream().map(role -> role.getAuthority().toString()).collect(Collectors.joining()));
        userLogin.setIp(request.getRemoteAddr());
        log.info("Usuário requisitado: " + userLogin.getUsername());
        return userLogin;
    }
}
