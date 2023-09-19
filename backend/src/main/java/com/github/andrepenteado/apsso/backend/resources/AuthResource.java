package com.github.andrepenteado.apsso.backend.resources;

import com.github.andrepenteado.apsso.backend.models.Usuario;
import com.github.andrepenteado.apsso.backend.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
