package com.github.andrepenteado.sso.portal.resources;

import com.github.andrepenteado.core.web.dto.UserLogin;
import com.github.andrepenteado.sso.services.UsuarioService;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Observed
@Slf4j
public class UsuarioResource {

    private final UsuarioService usuarioService;

    @PutMapping("/alterar-senha")
    @Secured({"ROLE_Portal_USUARIO"})
    public void alterarSenha(@RequestBody String senha, UserLogin userLogin) {
        log.info("Alterar senha");
        try {
            usuarioService.alterarSenha(userLogin.getLogin(), senha);
        }
        catch (ResponseStatusException rsex) {
            throw rsex;
        }
        catch (Exception ex) {
            log.error("Erro no processamento", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento");
        }
    }

    @PutMapping("/atualizar-foto")
    @Secured({"ROLE_Portal_USUARIO"})
    public void atualizarFoto(@RequestBody String uuidFoto, UserLogin userLogin) {
        log.info("Atualizar foto");
        try {
            usuarioService.atualizarFoto(userLogin.getLogin(), UUID.fromString(uuidFoto));
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
