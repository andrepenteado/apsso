package com.github.andrepenteado.sso.controle.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/auth")
@Slf4j
public class ControleUserLoginResource {

    @GetMapping
    public RedirectView login(@RequestParam("redirectUrl") String redirectUrl) {
        return new RedirectView(redirectUrl);
    }

    @GetMapping("/usuario-logado")
    public ControleUserLogin usuario(@AuthenticationPrincipal ControleUserLogin userLogin) {
        log.info("Login do usuário {}", userLogin.getLogin());
        return userLogin;
    }

}
