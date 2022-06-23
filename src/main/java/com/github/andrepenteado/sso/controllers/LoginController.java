package com.github.andrepenteado.sso.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@Slf4j
public class LoginController {

    @RequestMapping("/login")
    public String login(Principal principal) {
        if (principal == null)
            return "login";
        log.info("Usuário autenticado: " + principal.getName());
        return "home";
    }

    @RequestMapping("/erro")
    public String erro() {
        return "erro";
    }
}
