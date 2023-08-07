package com.github.andrepenteado.apsso.login.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String customLogin() {
        return "login";
    }

}
