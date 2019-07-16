package com.gitlab.andrepenteado.sso.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Slf4j
public class UserController {

    @GetMapping("/user")
    public Principal user(Principal principal) {
        log.info("Usuário requisitado: " + principal.getName());
        return principal;
    }

}
