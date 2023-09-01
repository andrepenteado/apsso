package com.github.andrepenteado.apsso.backend.resources;

import com.github.andrepenteado.apsso.backend.models.PerfilSistema;
import com.github.andrepenteado.apsso.backend.models.PerfilUsuario;
import com.github.andrepenteado.apsso.backend.models.Usuario;
import com.github.andrepenteado.apsso.backend.services.PerfilSistemaService;
import com.github.andrepenteado.apsso.backend.services.PerfilUsuarioService;
import com.github.andrepenteado.apsso.backend.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthResource {

    private final UsuarioService usuarioService;

    private final PerfilUsuarioService perfilUsuarioService;

    private final PerfilSistemaService perfilSistemaService;

    @GetMapping("/usuario")
    public Usuario usuario(Authentication authentication) {
        String username = authentication.getName();
        Usuario usuario = usuarioService.buscar(username).get();
        List<PerfilUsuario> perfis = perfilUsuarioService.listarPorUsuario(username);
        for (PerfilUsuario perfilUsuario: perfis) {
            PerfilSistema perfilSistema = perfilSistemaService.buscar(perfilUsuario.getAuthority());
            perfilUsuario.setPerfilSistema(perfilSistema);
        }
        usuario.setPerfis(perfis);
        return usuario;
    }

}
