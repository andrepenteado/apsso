package com.github.andrepenteado.sso.controle.resources;

import com.github.andrepenteado.sso.services.UsuarioService;
import com.github.andrepenteado.sso.services.entities.Usuario;
import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Observed
@Slf4j
public class UsuarioResource {

    private final UsuarioService usuarioService;

    @GetMapping
    @Secured({"ROLE_Controle_ARQUITETO"})
    public List<Usuario> listar() {
        log.info("Listar usuários");
        try {
            return usuarioService.listar();
        }
        catch (Exception ex) {
            log.error("Erro no processamento", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento");
        }
    }

    @GetMapping("/{username}")
    @Secured({"ROLE_Controle_ARQUITETO"})
    public Usuario buscar(@PathVariable String username) {
        log.info("Buscar usuário {}", username);
        try {
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
    @Secured({"ROLE_Controle_ARQUITETO"})
    public Usuario incluir(@RequestBody @Valid Usuario usuario, BindingResult validacao) {
        log.info("Incluir novo usuário {}", usuario);
        try {
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
    @Secured({"ROLE_Controle_ARQUITETO"})
    public Usuario alterar(@PathVariable String username, @RequestBody @Valid Usuario usuario, BindingResult validacao) {
        log.info("Alterar dados do usuário {}", usuario);
        try {
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
    @Secured({"ROLE_Controle_ARQUITETO"})
    public void excluir(@PathVariable String username) {
        log.info("Excluir usuário {}", username);
        try {
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
