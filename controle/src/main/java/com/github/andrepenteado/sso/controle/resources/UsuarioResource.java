package com.github.andrepenteado.sso.controle.resources;

import com.github.andrepenteado.core.web.dto.UserLogin;
import com.github.andrepenteado.sso.controle.services.PermissaoService;
import com.github.andrepenteado.sso.services.UsuarioService;
import com.github.andrepenteado.sso.services.entities.Usuario;
import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Observed
@Slf4j
public class UsuarioResource {

    private final UsuarioService usuarioService;

    private final PermissaoService permissaoService;

    @GetMapping
    public List<Usuario> listar(JwtAuthenticationToken auth) {
        log.info("Listar usuários");
        try {
            UserLogin userLogin = new UserLogin(auth);
            if (!permissaoService.isPermitido(Objects.requireNonNull(userLogin.perfis())))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permissão negada");
            return usuarioService.listar();
        }
        catch (ResponseStatusException rsex) {
            throw rsex;
        }
        catch (Exception ex) {
            log.error("Erro no processamento", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento");
        }
    }

    @GetMapping("/{username}")
    public Usuario buscar(@PathVariable String username, JwtAuthenticationToken auth) {
        log.info("Buscar usuário {}", username);
        try {
            UserLogin userLogin = new UserLogin(auth);
            if (!permissaoService.isPermitido(Objects.requireNonNull(userLogin.perfis())))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permissão negada");
            return usuarioService.buscar(username)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Usuário ID %s não encontrado", username)));
        }
        catch (ResponseStatusException rsex) {
            throw rsex;
        }
        catch (Exception ex) {
            log.error("Erro no processamento", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento");
        }
    }

    @PostMapping
    public Usuario incluir(@RequestBody @Valid Usuario usuario, BindingResult validacao, JwtAuthenticationToken auth) {
        log.info("Incluir novo usuário {}", usuario);
        try {
            UserLogin userLogin = new UserLogin(auth);
            if (!permissaoService.isPermitido(Objects.requireNonNull(userLogin.perfis())))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permissão negada");
            return usuarioService.incluir(usuario, validacao);
        }
        catch (ResponseStatusException rsex) {
            throw rsex;
        }
        catch (Exception ex) {
            log.error("Erro no processamento", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento");
        }
    }

    @PutMapping("/{username}")
    public Usuario alterar(@PathVariable String username, @RequestBody @Valid Usuario usuario, BindingResult validacao, JwtAuthenticationToken auth) {
        log.info("Alterar dados do usuário {}", usuario);
        try {
            UserLogin userLogin = new UserLogin(auth);
            if (!permissaoService.isPermitido(Objects.requireNonNull(userLogin.perfis())))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permissão negada");
            return usuarioService.alterar(usuario, username, validacao);
        }
        catch (ResponseStatusException rsex) {
            throw rsex;
        }
        catch (Exception ex) {
            log.error("Erro no processamento", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento");
        }
    }

    @DeleteMapping("/{username}")
    public void excluir(@PathVariable String username, JwtAuthenticationToken auth) {
        log.info("Excluir usuário {}", username);
        try {
            UserLogin userLogin = new UserLogin(auth);
            if (!permissaoService.isPermitido(Objects.requireNonNull(userLogin.perfis())))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permissão negada");
            usuarioService.excluir(username);
        }
        catch (ResponseStatusException rsex) {
            throw rsex;
        }
        catch (Exception ex) {
            log.error("Erro no processamento", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento");
        }
    }

}
