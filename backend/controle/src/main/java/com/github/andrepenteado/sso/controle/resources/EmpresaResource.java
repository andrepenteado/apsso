package com.github.andrepenteado.sso.controle.resources;

import br.unesp.fc.andrepenteado.core.web.dto.UserLogin;
import com.github.andrepenteado.sso.controle.ControleApplication;
import com.github.andrepenteado.sso.core.domain.entities.Empresa;
import com.github.andrepenteado.sso.core.services.EmpresaService;
import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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

    @GetMapping("/{id}")
    @Secured({ ControleApplication.PERFIL_ARQUITETO })
    public Empresa buscar(@PathVariable Long id) {
        log.info("Buscar empresa por ID #{}", id);
        return service.buscar(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Empresa de ID #%s não encontrada", id)));
    }

    @PostMapping
    @Secured({ ControleApplication.PERFIL_ARQUITETO })
    public Empresa incluir(@RequestBody @Valid Empresa empresa, BindingResult validacao, @AuthenticationPrincipal UserLogin userLogin) {
        log.info("Incluir nova empresa {}", empresa);

        empresa.setDataCadastro(LocalDateTime.now());
        empresa.setUsuarioCadastro(userLogin.getNome());

        return service.incluir(empresa, validacao);
    }

    @PutMapping("/{id}")
    @Secured({ ControleApplication.PERFIL_ARQUITETO })
    public Empresa alterar(@PathVariable Long id, @RequestBody @Valid Empresa empresa, BindingResult validacao, @AuthenticationPrincipal UserLogin userLogin) {
        log.info("Alterar empresa {}", empresa);

        Empresa empresaAtual = buscar(id);
        empresa.setDataCadastro(empresaAtual.getDataCadastro());
        empresa.setUsuarioCadastro(empresaAtual.getUsuarioCadastro());
        empresa.setDataUltimaAtualizacao(LocalDateTime.now());
        empresa.setUsuarioUltimaAtualizacao(userLogin.getNome());

        return service.alterar(empresa, id, validacao);
    }

    @DeleteMapping("/{id}")
    @Secured({ ControleApplication.PERFIL_ARQUITETO })
    public void excluir(@PathVariable Long id) {
        log.info("Excluir empresa ID #{}", id);
        service.excluir(id);
    }

}
