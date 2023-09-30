package com.github.andrepenteado.apsso.backend.resources;

import com.github.andrepenteado.apsso.services.UsuarioService;
import com.github.andrepenteado.apsso.services.models.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthResource {

    private final UsuarioService usuarioService;

    @GetMapping("/usuario")
    public Usuario usuario(Authentication authentication/*, @AuthenticationPrincipal OidcUser user*/) {
        String username = authentication.getName();
        log.info("Buscar usuário logado " + username);
        Usuario usuario = usuarioService.buscar(username).get();
        return usuario;
    }

}
