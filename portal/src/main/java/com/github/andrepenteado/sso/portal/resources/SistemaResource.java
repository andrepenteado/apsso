package com.github.andrepenteado.sso.portal.resources;

import com.github.andrepenteado.sso.portal.services.PermissaoService;
import com.github.andrepenteado.sso.services.SistemaService;
import com.github.andrepenteado.sso.services.UsuarioService;
import com.github.andrepenteado.sso.services.entities.Sistema;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
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
    public Sistema buscar(@PathVariable String id, @AuthenticationPrincipal OidcUser principal) {
        log.info("Buscar sistema de ID: #{}", id);
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

}
