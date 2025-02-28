package com.github.andrepenteado.sso.api.resources;

import br.unesp.fc.andrepenteado.core.web.dto.UserLogin;
import com.github.andrepenteado.sso.api.ApiApplication;
import com.github.andrepenteado.sso.core.domain.entities.Usuario;
import com.github.andrepenteado.sso.core.services.UsuarioService;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v1/usuarios")
@RequiredArgsConstructor
@Observed
@Slf4j
public class UsuariosResource {

    private final UsuarioService service;

    @GetMapping("/{username}")
    @Secured({ ApiApplication.PERFIL_CONSULTAR_API })
    public Usuario buscar(@PathVariable String username) {
        log.info("Buscar usuário {}", username);
        return service.buscar(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("Usuário ID %s não encontrado", username)));
    }

}
