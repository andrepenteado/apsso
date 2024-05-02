package com.github.andrepenteado.sso.portal.resources;

import com.github.andrepenteado.core.web.dto.UserLogin;
import com.github.andrepenteado.sso.portal.services.PermissaoService;
import com.github.andrepenteado.sso.services.UsuarioService;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Observed
@Slf4j
public class UsuarioResource {

    private final UsuarioService usuarioService;

    private final PermissaoService permissaoService;

    @PutMapping("/alterar-senha")
    public void alterarSenha(@RequestBody String senha, JwtAuthenticationToken auth) {
        log.info("Alterar senha");
        try {
            UserLogin userLogin = new UserLogin(auth);
            if (!permissaoService.isPermitido(Objects.requireNonNull(userLogin.perfis())))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permissão negada");
            usuarioService.alterarSenha(userLogin.login(), senha);
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
    public void atualizarFoto(@RequestBody String uuidFoto, JwtAuthenticationToken auth) {
        log.info("Atualizar foto");
        try {
            UserLogin userLogin = new UserLogin(auth);
            if (!permissaoService.isPermitido(Objects.requireNonNull(userLogin.perfis())))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permissão negada");
            usuarioService.atualizarFoto(userLogin.login(), UUID.fromString(uuidFoto));
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
