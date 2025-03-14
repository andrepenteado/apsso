package com.github.andrepenteado.sso.login.services;

import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    private final Configuration freemarkerConfig;

    public void confirmarNovoCadastro() {}

    public void esqueciMinhaSenha() {}

}
