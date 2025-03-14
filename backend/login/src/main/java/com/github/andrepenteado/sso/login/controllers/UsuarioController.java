package com.github.andrepenteado.sso.login.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class UsuarioController {

    @GetMapping("/novo-usuario")
    public String novoUsuario() {
        log.info("Abrir cadastro de novo usuário");
        return "novo-usuario";
    }

    @PostMapping("/gravar-novo-usuario")
    public String gravarNovoUsuario() {
        log.info("Gravar novo usuário");
        return "mensagem";
    }

    @GetMapping("/confirmar-novo-usuario")
    public String confirmarNovoUsuario() {
        log.info("Confirmar e-mail de novo usuário");
        return "mensagem";
    }

    @RequestMapping("/esqueci-minha-senha")
    public String esqueciMinhaSenha() {
        log.info("Abrir formulário de esqueci minha senha");
        return "esqueci-minha-senha";
    }

    @PostMapping("/gravar-esqueci-minha-senha")
    public String gravarEsqueciMinhaSenha() {
        log.info("Gravar esqueci minha senha");
        return "mensagem";
    }

    @GetMapping("/confirmar-esqueci-minha-senha")
    public String confirmarEsqueciMinhaSenha() {
        log.info("Confirmar e-mail para alterar senha");
        return "alterar-senha";
    }

    @PostMapping("/gravar-alterar-senha")
    public String gravarAlterarSenha() {
        log.info("Gravar alterar senha");
        return "mensagem";
    }

}
