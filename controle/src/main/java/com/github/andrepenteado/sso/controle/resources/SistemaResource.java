package com.github.andrepenteado.sso.controle.resources;

import com.github.andrepenteado.sso.controle.services.PermissaoService;
import com.github.andrepenteado.sso.services.PerfilSistemaService;
import com.github.andrepenteado.sso.services.SistemaService;
import com.github.andrepenteado.sso.services.entities.PerfilSistema;
import com.github.andrepenteado.sso.services.entities.Sistema;
import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/sistemas")
@RequiredArgsConstructor
@Observed
@Slf4j
public class SistemaResource {

    private final SistemaService sistemaService;

    private final PerfilSistemaService perfilSistemaService;

    private final PermissaoService permissaoService;

    @GetMapping
    public List<Sistema> listar(@AuthenticationPrincipal OidcUser principal) {
        log.info("Listar sistemas");
        try {
            if (!permissaoService.isPermitido(Objects.requireNonNull(principal.getAttribute("perfis"))))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permissão negada");
            return sistemaService.listar();
        }
        catch (ResponseStatusException rsex) {
            throw rsex;
        }
        catch (Exception ex) {
            log.error("Erro no processamento", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento");
        }
    }

    @GetMapping("/{id}")
    public Sistema buscar(@PathVariable  String id, @AuthenticationPrincipal OidcUser principal) {
        log.info("Buscar sistema de ID: #" + id);
        try {
            if (!permissaoService.isPermitido(Objects.requireNonNull(principal.getAttribute("perfis"))))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permissão negada");
            return sistemaService.buscar(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Sistema de ID %s não encontrado", id)));
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
    public Sistema incluir(@RequestBody @Valid Sistema sistema, BindingResult validacao, @AuthenticationPrincipal OidcUser principal) {
        log.info("Incluir/Alterar sistema " + sistema);
        try {
            if (!permissaoService.isPermitido(Objects.requireNonNull(principal.getAttribute("perfis"))))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permissão negada");
            return sistemaService.incluirOuAlterar(sistema, validacao);
        }
        catch (ResponseStatusException rsex) {
            throw rsex;
        }
        catch (Exception ex) {
            log.error("Erro no processamento", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento");
        }
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable String id, @AuthenticationPrincipal OidcUser principal) {
        log.info("Excluir sistema de ID #" + id);
        try {
            if (!permissaoService.isPermitido(Objects.requireNonNull(principal.getAttribute("perfis"))))
                throw new Exception("Permissão negada");
            sistemaService.excluir(id);
        }
        catch (ResponseStatusException rsex) {
            throw rsex;
        }
        catch (Exception ex) {
            log.error("Erro no processamento", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento");
        }
    }

    @GetMapping("/{id}/perfis")
    public List<PerfilSistema> listarPerfisPorSistema(@PathVariable String id, @AuthenticationPrincipal OidcUser principal) {
        log.info("Listar perfis do sistema " + id);
        try {
            if (!permissaoService.isPermitido(Objects.requireNonNull(principal.getAttribute("perfis"))))
                throw new Exception("Permissão negada");
            return perfilSistemaService.listarPorSistema(id);
        }
        catch (Exception ex) {
            log.error("Erro no processamento", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento");
        }
    }

    @GetMapping("/perfis")
    public List<PerfilSistema> listarPerfis(@AuthenticationPrincipal OidcUser principal) {
        log.info("Listar perfis de sistemas");
        try {
            if (!permissaoService.isPermitido(Objects.requireNonNull(principal.getAttribute("perfis"))))
                throw new Exception("Permissão negada");
            return perfilSistemaService.listar();
        }
        catch (Exception ex) {
            log.error("Erro no processamento", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento");
        }
    }

    @PostMapping("/perfil")
    public PerfilSistema incluirPerfil(@RequestBody @Valid PerfilSistema perfilSistema, BindingResult validacao, @AuthenticationPrincipal OidcUser principal) {
        log.info("Incluir novo perfil de sistema " + perfilSistema);
        try {
            if (!permissaoService.isPermitido(Objects.requireNonNull(principal.getAttribute("perfis"))))
                throw new Exception("Permissão negada");
            return perfilSistemaService.incluir(perfilSistema, validacao);
        }
        catch (ResponseStatusException rsex) {
            throw rsex;
        }
        catch (Exception ex) {
            log.error("Erro no processamento", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento");
        }
    }

    @DeleteMapping("/perfil/{authority}")
    public void excluirPerfil(@PathVariable String authority, @AuthenticationPrincipal OidcUser principal) {
        log.info("Excluir perfil de sistema " + authority);
        try {
            if (!permissaoService.isPermitido(Objects.requireNonNull(principal.getAttribute("perfis"))))
                throw new Exception("Permissão negada");
            perfilSistemaService.excluir(authority);
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
