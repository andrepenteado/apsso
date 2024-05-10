package com.github.andrepenteado.sso.portal.resources;

import com.github.andrepenteado.sso.services.SistemaService;
import com.github.andrepenteado.sso.services.entities.Sistema;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.github.andrepenteado.sso.portal.PortalApplication.PERFIL_USUARIO;

@RestController
@RequestMapping("/sistemas")
@RequiredArgsConstructor
@Observed
@Slf4j
public class SistemaResource {

    private final SistemaService sistemaService;

    @GetMapping
    @Secured({ PERFIL_USUARIO })
    public List<Sistema> listar() {
        log.info("Listar sistemas");
        return sistemaService.listar();
    }

    @GetMapping("/{id}")
    @Secured({ PERFIL_USUARIO })
    public Sistema buscar(@PathVariable String id) {
        log.info("Buscar sistema de ID: #{}", id);
        return sistemaService.buscar(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("Sistema de ID %s não encontrado", id)));
    }

}
