package com.github.andrepenteado.sso.equipe.resources;

import com.github.andrepenteado.sso.core.domain.entities.Empresa;
import com.github.andrepenteado.sso.core.services.EmpresaService;
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

import static com.github.andrepenteado.sso.equipe.EquipeApplication.PERFIL_GESTOR;

@RestController
@RequestMapping("/empresas")
@RequiredArgsConstructor
@Observed
@Slf4j
public class EmpresaResource {

    private final EmpresaService service;

    @GetMapping
    @Secured({ PERFIL_GESTOR })
    public List<Empresa> listar() {
        log.info("Listar todas empresas");
        return service.listar();
    }

    @GetMapping("/{id}")
    @Secured({ PERFIL_GESTOR })
    public Empresa buscar(@PathVariable Long id) {
        log.info("Buscar empresa por ID #{}", id);
        return service.buscar(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Empresa de ID #%d não encontrada", id)));
    }

}