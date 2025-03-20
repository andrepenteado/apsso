package com.github.andrepenteado.sso.portal.resources;

import br.unesp.fc.andrepenteado.core.web.dto.UserLogin;
import com.github.andrepenteado.sso.core.domain.entities.AmbienteSistema;
import com.github.andrepenteado.sso.core.services.AmbienteSistemaService;
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

import java.util.List;

import static com.github.andrepenteado.sso.portal.PortalApplication.PERFIL_USUARIO;

@RestController
@RequestMapping("/ambientes-sistemas")
@RequiredArgsConstructor
@Observed
@Slf4j
public class AmbienteSistemaResource {

    private final AmbienteSistemaService service;

    @GetMapping
    @Secured({ PERFIL_USUARIO })
    public List<AmbienteSistema> listar(@AuthenticationPrincipal UserLogin userLogin) {
        log.info("Listar ambientes dos sistemas por usuario: {}", userLogin.getLogin());
        return service.pesquisarPorUsuario(userLogin.getLogin());
    }

    @GetMapping("/{id}")
    @Secured({ PERFIL_USUARIO })
    public AmbienteSistema buscar(@PathVariable String id) {
        log.info("Buscar ambiente de sistema de ID: #{}", id);
        return service.buscar(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("Ambiente de sistema de ID %s não encontrado", id)));
    }

}
