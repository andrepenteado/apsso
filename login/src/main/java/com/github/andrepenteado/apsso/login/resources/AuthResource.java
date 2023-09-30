package com.github.andrepenteado.apsso.login.resources;

import com.github.andrepenteado.apsso.services.UsuarioService;
import com.github.andrepenteado.apsso.services.models.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
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
    public Usuario usuario(Authentication authentication) {
        String username = authentication.getName();
        Usuario usuario = usuarioService.buscar(username).get();
        return usuario;
    }

}
