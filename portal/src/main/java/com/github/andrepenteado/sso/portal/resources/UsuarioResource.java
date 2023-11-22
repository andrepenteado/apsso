package com.github.andrepenteado.sso.portal.resources;

import com.github.andrepenteado.sso.portal.services.PermissaoService;
import com.github.andrepenteado.sso.services.UsuarioService;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Observed
@Slf4j
public class UsuarioResource {

    private final UsuarioService usuarioService;

    private final PermissaoService permissaoService;

    @PutMapping("/alterar-senha")
    public void alterarSenha(@RequestBody String senha, @AuthenticationPrincipal OidcUser principal) {
        log.info("Alterar senha");
        try {
            if (!permissaoService.isPermitido(Objects.requireNonNull(principal.getAttribute("perfis"))))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permissão negada");
            usuarioService.alterarSenha(principal.getName(), senha);
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
