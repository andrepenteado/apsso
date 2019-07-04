package com.gitlab.andrepenteado.sso.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Slf4j
public class UserController {

    @GetMapping("/user")
    public Principal user(Principal principal) {
        log.info("Usuário autenticado: " + principal.getName());
        return principal;
    }

}
