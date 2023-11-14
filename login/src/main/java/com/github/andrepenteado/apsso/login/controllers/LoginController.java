package com.github.andrepenteado.apsso.login.controllers;

import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Observed
public class LoginController {

    @RequestMapping("/login")
    public String customLogin() {
        return "login";
    }

}
