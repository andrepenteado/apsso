package com.github.andrepenteado.sso.controle.resources;

import com.github.andrepenteado.sso.controle.ControleApplication;
import com.github.andrepenteado.sso.core.domain.entities.Empresa;
import com.github.andrepenteado.sso.core.services.EmpresaService;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/empresas")
@RequiredArgsConstructor
@Observed
@Slf4j
public class EmpresaResource {

    private final EmpresaService service;

    @GetMapping
    @Secured({ ControleApplication.PERFIL_ARQUITETO })
    public List<Empresa> listar() {
        log.info("Listar empresas");
        return service.listar();
    }

}
