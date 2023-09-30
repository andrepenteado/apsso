package com.github.andrepenteado.apsso.backend.resources;

import com.github.andrepenteado.apsso.services.UsuarioService;
import com.github.andrepenteado.apsso.services.models.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Slf4j
public class UsuarioResource {

    private final UsuarioService usuarioService;

    @GetMapping
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
    public Usuario buscar(@PathVariable String username) {
        log.info("Buscar usuário " + username);
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
    public Usuario incluir(@RequestBody @Valid Usuario usuario, BindingResult validacao) {
        log.info("Incluir novo usuário " + usuario);
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
    public Usuario alterar(@PathVariable String username, @RequestBody @Valid Usuario usuario, BindingResult validacao) {
        log.info("Alterar dados do usuário " + usuario);
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
    public void excluir(@PathVariable String username) {
        log.info("Excluir usuário " + username);
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
