package com.github.andrepenteado.sso.login.controllers;

import br.unesp.fc.andrepenteado.core.upload.Upload;
import com.github.andrepenteado.sso.core.domain.entities.Token;
import com.github.andrepenteado.sso.core.domain.entities.Usuario;
import com.github.andrepenteado.sso.core.domain.enums.TipoToken;
import com.github.andrepenteado.sso.core.domain.repositories.EmpresaRepository;
import com.github.andrepenteado.sso.core.domain.repositories.TokenRepository;
import com.github.andrepenteado.sso.core.services.UsuarioService;
import com.github.andrepenteado.sso.login.services.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UsuarioController {

    private final UsuarioService usuarioService;

    private final EmpresaRepository empresaRepository;

    private final TokenRepository tokenRepository;

    private final EmailService emailService;

    @GetMapping("/novo-usuario")
    public String novoUsuario(Model model, HttpServletRequest request) {
        log.info("Abrir cadastro de novo usuário");

        Upload logotipo = empresaRepository.buscarLogotipoEmpresaPorUrlLogin(
            request.getRequestURL().toString().replace(request.getRequestURI(), "")
        );
        if (logotipo != null) {
            log.info("Logotipo: {}", logotipo);
            model.addAttribute("logotipo", logotipo);
        }

        return "novo-usuario";
    }

    @PostMapping("/gravar-novo-usuario")
    public String gravarNovoUsuario(Model model, HttpServletRequest request) {
        log.info("Gravar novo usuário");

        String username = request.getParameter("username");
        String nome = request.getParameter("nome");
        String senha = request.getParameter("senha");
        String confirmarSenha = request.getParameter("confirmar_senha");
        String cpf = request.getParameter("cpf");
        String email = request.getParameter("email");

        try {
            if (!senha.equals(confirmarSenha)) {
                throw new Exception("Senhas não conferem");
            }
            if (senha.length() < 6) {
                throw new Exception("Senha precisa ter no mínimo 6 caracteres");
            }
            if (cpf.length() != 11) {
                throw new Exception("CPF precisa ter 11 caracteres numéricos");
            }

            Usuario usuario = new Usuario();
            usuario.setUsername(username);
            usuario.setPassword(senha);
            usuario.setNome(nome);
            usuario.setCpf(Long.parseLong(cpf));
            usuario.setEmail(email);
            usuario.setDataCadastro(LocalDateTime.now());
            usuario.setUsuarioCadastro("Portal de Sistemas");
            usuario.setEnabled(false);
            Usuario novoUsuario = usuarioService.incluir(usuario, null);

            Token token = new Token();
            token.setUsuario(novoUsuario);
            token.setToken(UUID.randomUUID());
            token.setTipo(TipoToken.NOVO_USUARIO);
            token.setDataCriacao(LocalDateTime.now());
            token.setDataExpiracao(LocalDateTime.now().plusMinutes(30));
            token.setUtilizado(false);
            Token novoToken = tokenRepository.save(token);

            emailService.confirmarNovoCadastro(novoToken);

            model.addAttribute("mensagemInfo", "Seu usuário foi cadastrado com sucesso!<br><br>Em breve receberá um e-mail para ativar seu usuário.");
        }
        catch (Exception ex) {
            model.addAttribute("mensagemErro", ex.getMessage());
            model.addAttribute("username", username);
            model.addAttribute("nome", nome);
            model.addAttribute("senha", senha);
            model.addAttribute("confirmar_senha", confirmarSenha);
            model.addAttribute("cpf", cpf);
            model.addAttribute("email", email);

            Upload logotipo = empresaRepository.buscarLogotipoEmpresaPorUrlLogin(
                request.getRequestURL().toString().replace(request.getRequestURI(), "")
            );
            if (logotipo != null) {
                model.addAttribute("logotipo", logotipo);
            }

            return "novo-usuario";
        }

        return "mensagem";
    }

    @GetMapping("/confirmar-novo-usuario/{uuidToken}")
    public String confirmarNovoUsuario(Model model, @PathVariable String uuidToken) {
        log.info("Confirmar e-mail de novo usuário");

        Token token = tokenRepository.findByToken(UUID.fromString(uuidToken));

        if (token == null || token.getTipo() != TipoToken.NOVO_USUARIO) {
            model.addAttribute("mensagemInfo", "Token inválido");
        }
        else if (token.getDataExpiracao().isBefore(LocalDateTime.now())) {
            model.addAttribute("mensagemInfo", "Token expirado");
        }
        else if (token.getUtilizado()) {
            model.addAttribute("mensagemInfo", "Token utilizado");
        }
        else {
            Usuario usuario = token.getUsuario();
            usuario.setEnabled(true);
            usuarioService.alterar(usuario, usuario.getUsername(), null);

            token.setUtilizado(true);
            tokenRepository.save(token);

            model.addAttribute("mensagemInfo", "Usuário ativado com sucesso!");
        }

        return "mensagem";
    }

    @RequestMapping("/esqueci-minha-senha")
    public String esqueciMinhaSenha(Model model, HttpServletRequest request) {
        log.info("Abrir formulário de esqueci minha senha");

        Upload logotipo = empresaRepository.buscarLogotipoEmpresaPorUrlLogin(
            request.getRequestURL().toString().replace(request.getRequestURI(), "")
        );
        if (logotipo != null) {
            model.addAttribute("logotipo", logotipo);
        }

        return "esqueci-minha-senha";
    }

    @PostMapping("/gravar-esqueci-minha-senha")
    public String gravarEsqueciMinhaSenha(Model model, HttpServletRequest request) {
        log.info("Gravar esqueci minha senha");

        Usuario usuario = usuarioService.buscarPorEmail(request.getParameter("email")).orElse(null);
        if (usuario == null) {
            model.addAttribute("mensagemErro", "E-mail não encontrado");

            Upload logotipo = empresaRepository.buscarLogotipoEmpresaPorUrlLogin(
                    request.getRequestURL().toString().replace(request.getRequestURI(), "")
            );
            if (logotipo != null) {
                model.addAttribute("logotipo", logotipo);
            }

            return "esqueci-minha-senha";
        }

        Token token = new Token();
        token.setUsuario(usuario);
        token.setToken(UUID.randomUUID());
        token.setTipo(TipoToken.ESQUECI_MINHA_SENHA);
        token.setDataCriacao(LocalDateTime.now());
        token.setDataExpiracao(LocalDateTime.now().plusMinutes(30));
        token.setUtilizado(false);
        Token novoToken = tokenRepository.save(token);

        emailService.esqueciMinhaSenha(novoToken);

        model.addAttribute("mensagemInfo", "Solicitação de alteração de senha realizada com sucesso!<br><br>Em breve receberá um e-mail para completar a alteração da senha.");

        return "mensagem";
    }

    @GetMapping("/confirmar-esqueci-minha-senha/{uuidToken}")
    public String confirmarEsqueciMinhaSenha(Model model, HttpServletRequest request, @PathVariable String uuidToken) {
        log.info("Confirmar e-mail para alterar senha");

        Token token = tokenRepository.findByToken(UUID.fromString(uuidToken));

        if (token == null || token.getTipo() != TipoToken.ESQUECI_MINHA_SENHA) {
            model.addAttribute("mensagemInfo", "Token inválido");
        }
        else if (token.getDataExpiracao().isBefore(LocalDateTime.now())) {
            model.addAttribute("mensagemInfo", "Token expirado");
        }
        else if (token.getUtilizado()) {
            model.addAttribute("mensagemInfo", "Token utilizado");
        }
        else {
            Upload logotipo = empresaRepository.buscarLogotipoEmpresaPorUrlLogin(
                request.getRequestURL().toString().replace(request.getRequestURI(), "")
            );
            if (logotipo != null) {
                model.addAttribute("logotipo", logotipo);
            }

            token.setUtilizado(true);
            tokenRepository.save(token);

            model.addAttribute("username", token.getUsuario().getUsername());

            return "alterar-senha";
        }

        return "mensagem";
    }

    @PostMapping("/gravar-alterar-senha")
    public String gravarAlterarSenha(Model model, HttpServletRequest request) {
        log.info("Gravar alterar senha");

        String username = request.getParameter("username");
        String senha = request.getParameter("senha");
        String confirmarSenha = request.getParameter("confirmar_senha");

        try {
            if (!senha.equals(confirmarSenha)) {
                throw new Exception("Senhas não conferem");
            }
            usuarioService.alterarSenha(username, senha);

            model.addAttribute("mensagemInfo", "Senha alterada com sucesso!");
        }
        catch (Exception ex) {
            model.addAttribute("mensagemErro", ex.getMessage());
            model.addAttribute("username", username);
            model.addAttribute("senha", senha);
            model.addAttribute("confirmar_senha", confirmarSenha);
            return "alterar-senha";
        }

        return "mensagem";
    }

}
