package com.gitlab.andrepenteado.sso.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private DefaultTokenServices tokenServices;

    @GetMapping("/user")
    public Principal user(Principal principal) {
        log.info("Usuário autenticado: " + principal.getName());
        return principal;
    }

    @RequestMapping("/revoke-token/{tokenId}")
    @ResponseBody
    public void revokeToken(@PathVariable String tokenId) {
        tokenServices.revokeToken(tokenId);
    }

}
