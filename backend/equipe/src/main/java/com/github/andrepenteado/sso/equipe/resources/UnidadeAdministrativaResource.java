package com.github.andrepenteado.sso.equipe.resources;

import com.github.andrepenteado.sso.core.domain.entities.UnidadeAdministrativa;
import com.github.andrepenteado.sso.core.services.UnidadeAdministrativaService;
import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.github.andrepenteado.sso.equipe.EquipeApplication.PERFIL_GESTOR;

@RestController
@RequestMapping("/unidades-administrativas")
@RequiredArgsConstructor
@Observed
@Slf4j
public class UnidadeAdministrativaResource {

    private final UnidadeAdministrativaService service;

    @GetMapping
    @Secured({ PERFIL_GESTOR })
    public List<UnidadeAdministrativa> listar() {
        log.info("Listar todas unidades administrativas");
        return service.listar();
    }

    @GetMapping("/empresa/{idEmpresa}")
    @Secured({ PERFIL_GESTOR })
    public List<UnidadeAdministrativa> listarPorEmpresa(@PathVariable Long idEmpresa) {
        log.info("Listar unidades administrativas por empresa de ID #{}", idEmpresa);
        return service.listarPorEmpresa(idEmpresa);
    }

    @GetMapping("/{id}")
    @Secured({ PERFIL_GESTOR })
    public UnidadeAdministrativa buscar(@PathVariable Long id) {
        log.info("Buscar unidade administrativa por ID #{}", id);
        return service.buscar(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Empresa de ID #%n não encontrada", id)));
    }

    @PostMapping
    @Secured({ PERFIL_GESTOR })
    public UnidadeAdministrativa incluir(@Valid @RequestBody UnidadeAdministrativa unidadeAdministrativa, BindingResult validacao) {
        log.info("Incluir nova unidade administrativa {}", unidadeAdministrativa);
        return service.incluir(unidadeAdministrativa, validacao);
    }

    @PutMapping("/{id}")
    @Secured({ PERFIL_GESTOR })
    public UnidadeAdministrativa alterar(@PathVariable Long id, @Valid @RequestBody UnidadeAdministrativa unidadeAdministrativa, BindingResult validacao) {
        log.info("Alterar unidade administrativa {}", unidadeAdministrativa);
        return service.alterar(unidadeAdministrativa, id, validacao);
    }

    @DeleteMapping("/{id}")
    @Secured({ PERFIL_GESTOR })
    public void excluir(@PathVariable Long id) {
        log.info("Excluir unidade administrativa ID #{}", id);
        service.excluir(id);
    }

}
