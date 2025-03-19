package com.github.andrepenteado.sso.login.services;

import com.github.andrepenteado.sso.core.domain.entities.Token;
import com.github.andrepenteado.sso.login.config.GlobalProperties;
import freemarker.template.Configuration;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    private final Configuration freemarkerConfig;

    private final GlobalProperties properties;

    public void confirmarNovoCadastro(Token token) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, "utf-8");
            mail.setTo(token.getUsuario().getEmail());
            mail.setFrom("no-reply@apcode.com.br");
            mail.setSubject("[Portal de Sistemas] Ativação de cadastro de usuário");
            mail.setText(String.format(
                """
                Prezado %s,
                
                Copie e cole o link abaixo no navegador para ativar seu usuário:
                
                %s/confirmar-novo-usuario/%s
                """, token.getUsuario().getNome(), properties.getUri(), token.getToken()),
            false);
            mailSender.send(mimeMessage);
            log.info("Enviado e-mail de novo usuário");
        } catch (MessagingException e) {
            log.error("Não foi possível enviar e-mail de novo usuário", e);
        }
    }

    public void esqueciMinhaSenha(Token token) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, "utf-8");
            mail.setTo(token.getUsuario().getEmail());
            mail.setFrom("no-reply@apcode.com.br");
            mail.setSubject("[Portal de Sistemas] Esqueci minha senha");
            mail.setText(String.format(
                """
                Prezado %s,
                
                Copie e cole o link abaixo no navegador para alterar sua senha:
                
                %s/confirmar-esqueci-minha-senha/%s
                """, token.getUsuario().getNome(), properties.getUri(), token.getToken()),
            false);
            mailSender.send(mimeMessage);
            log.info("Enviado e-mail de esqueci minha senha");
        } catch (MessagingException e) {
            log.error("Não foi possível enviar e-mail de esqueci minha senha", e);
        }
    }

}
