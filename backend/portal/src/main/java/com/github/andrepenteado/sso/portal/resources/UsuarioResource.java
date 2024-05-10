package com.github.andrepenteado.sso.portal.resources;

import com.github.andrepenteado.core.web.dto.UserLogin;
import com.github.andrepenteado.sso.services.UsuarioService;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.github.andrepenteado.sso.portal.PortalApplication.PERFIL_USUARIO;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Observed
@Slf4j
public class UsuarioResource {

    private final UsuarioService usuarioService;

    @PutMapping("/alterar-senha")
    @Secured({ PERFIL_USUARIO })
    public void alterarSenha(
        @RequestBody
        String senha, UserLogin userLogin) {
        log.info("Alterar senha");
        usuarioService.alterarSenha(userLogin.getLogin(), senha);
    }

    @PutMapping("/atualizar-foto")
    @Secured({ PERFIL_USUARIO })
    public void atualizarFoto(
        @RequestBody
        String uuidFoto, UserLogin userLogin) {
        log.info("Atualizar foto");
        usuarioService.atualizarFoto(userLogin.getLogin(), UUID.fromString(uuidFoto));
    }

}
