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
        return usuarioService.listar();
    }

    @GetMapping("/{username}")
    @Secured({"ROLE_Controle_ARQUITETO"})
    public Usuario buscar(@PathVariable String username) {
        log.info("Buscar usuário {}", username);
        return usuarioService.buscar(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Usuário ID %s não encontrado", username)));
    }

    @PostMapping
    @Secured({"ROLE_Controle_ARQUITETO"})
    public Usuario incluir(@RequestBody @Valid Usuario usuario, BindingResult validacao) {
        log.info("Incluir novo usuário {}", usuario);
        return usuarioService.incluir(usuario, validacao);
    }

    @PutMapping("/{username}")
    @Secured({"ROLE_Controle_ARQUITETO"})
    public Usuario alterar(@PathVariable String username, @RequestBody @Valid Usuario usuario, BindingResult validacao) {
        log.info("Alterar dados do usuário {}", usuario);
        return usuarioService.alterar(usuario, username, validacao);
    }

    @DeleteMapping("/{username}")
    @Secured({"ROLE_Controle_ARQUITETO"})
    public void excluir(@PathVariable String username) {
        log.info("Excluir usuário {}", username);
        usuarioService.excluir(username);
    }

}
