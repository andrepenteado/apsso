package com.github.andrepenteado.apsso.portal.resources;

import com.github.andrepenteado.apsso.portal.services.PermissaoService;
import com.github.andrepenteado.apsso.services.PerfilSistemaService;
import com.github.andrepenteado.apsso.services.SistemaService;
import com.github.andrepenteado.apsso.services.models.PerfilSistema;
import com.github.andrepenteado.apsso.services.models.Sistema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/sistemas")
@RequiredArgsConstructor
@Slf4j
public class SistemaResource {

    private final SistemaService sistemaService;

    private final PermissaoService permissaoService;

    @GetMapping
    public List<Sistema> listar(@AuthenticationPrincipal DefaultOidcUser principal) {
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
    public Sistema buscar(@PathVariable  String id, @AuthenticationPrincipal DefaultOidcUser principal) {
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

}
