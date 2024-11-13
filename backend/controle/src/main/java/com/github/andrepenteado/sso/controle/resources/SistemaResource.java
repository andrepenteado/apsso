package com.github.andrepenteado.sso.controle.resources;

import br.unesp.fc.andrepenteado.core.web.dto.UserLogin;
import com.github.andrepenteado.sso.core.domain.entities.AmbienteSistema;
import com.github.andrepenteado.sso.core.domain.entities.PerfilSistema;
import com.github.andrepenteado.sso.core.domain.entities.Sistema;
import com.github.andrepenteado.sso.core.services.AmbienteSistemaService;
import com.github.andrepenteado.sso.core.services.PerfilSistemaService;
import com.github.andrepenteado.sso.core.services.SistemaService;
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
import java.util.Objects;

import static com.github.andrepenteado.sso.controle.ControleApplication.PERFIL_ARQUITETO;

@RestController
@RequestMapping("/sistemas")
@RequiredArgsConstructor
@Observed
@Slf4j
public class SistemaResource {

    private final SistemaService sistemaService;

    private final AmbienteSistemaService ambienteSistemaService;

    private final PerfilSistemaService perfilSistemaService;

    @GetMapping
    @Secured({ PERFIL_ARQUITETO }) //@RolesAllowed({"Controle_ARQUITETO"})
    public List<Sistema> listar() {
        log.info("Listar sistemas");
        return sistemaService.listar();
    }

    @GetMapping("/{id}")
    @Secured({ PERFIL_ARQUITETO })
    public Sistema buscar(@PathVariable  Long id) {
        log.info("Buscar sistema de ID #{}", id);
        return sistemaService.buscar(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("Sistema de ID %s não encontrado", id)));
    }

    @PostMapping
    @Secured({ PERFIL_ARQUITETO })
    public Sistema incluirOuAlterar(@RequestBody @Valid Sistema sistema, BindingResult validacao, @AuthenticationPrincipal UserLogin userLogin) {
        log.info("Incluir ou alterar sistema {}", sistema);

        if (Objects.isNull(sistema.getId())) {
            sistema.setDataCadastro(LocalDateTime.now());
            sistema.setUsuarioCadastro(userLogin.getNome());
            sistema.setDataUltimaAtualizacao(null);
            sistema.setUsuarioUltimaAtualizacao(null);
        }
        else {
            Sistema sistemaAtual = sistemaService.buscar(sistema.getId()).get();
            sistema.setDataCadastro(sistemaAtual.getDataCadastro());
            sistema.setUsuarioCadastro(sistemaAtual.getUsuarioCadastro());
            sistema.setDataUltimaAtualizacao(LocalDateTime.now());
            sistema.setUsuarioUltimaAtualizacao(userLogin.getNome());
        }

        return sistemaService.incluirOuAlterar(sistema, validacao);
    }

    @DeleteMapping("/{id}")
    @Secured({ PERFIL_ARQUITETO })
    public void excluir(@PathVariable Long id) {
        log.info("Excluir sistema de ID #{}", id);
        sistemaService.excluir(id);
    }

    @GetMapping("/{id}/perfis")
    @Secured({ PERFIL_ARQUITETO })
    public List<PerfilSistema> listarPerfisPorSistema(@PathVariable Long id) {
        log.info("Listar perfis do sistema #{}", id);
        return perfilSistemaService.filtrarPorSistema(id);
    }

    @GetMapping("/perfis")
    @Secured({ PERFIL_ARQUITETO })
    public List<PerfilSistema> listarPerfis() {
        log.info("Listar perfis de sistemas");
        return perfilSistemaService.listar();
    }

    @PostMapping("/perfil")
    @Secured({ PERFIL_ARQUITETO })
    public PerfilSistema incluirPerfil(@RequestBody @Valid PerfilSistema perfilSistema, BindingResult validacao) {
        log.info("Incluir novo perfil de sistema {}", perfilSistema);
        return perfilSistemaService.incluir(perfilSistema, validacao);
    }

    @DeleteMapping("/perfil/{authority}")
    @Secured({ PERFIL_ARQUITETO })
    public void excluirPerfil(@PathVariable String authority) {
        log.info("Excluir perfil de sistema {}", authority);
        perfilSistemaService.excluir(authority);
    }

    @GetMapping("/{id}/ambientes")
    @Secured({ PERFIL_ARQUITETO })
    public List<AmbienteSistema> listarAmbientesPorSistema(@PathVariable Long id) {
        log.info("Listar ambientes do sistema #{}", id);
        return ambienteSistemaService.filtrarPorSistema(id);
    }

    @PostMapping("/ambiente")
    @Secured({ PERFIL_ARQUITETO })
    public AmbienteSistema incluirAmbiente(@RequestBody @Valid AmbienteSistema ambienteSistema, BindingResult validacao) {
        log.info("Incluir novo ambiente de sistema {}", ambienteSistema);
        return ambienteSistemaService.incluir(ambienteSistema, validacao);
    }

    @DeleteMapping("/ambiente/{id}")
    @Secured({ PERFIL_ARQUITETO })
    public void excluirAmbiente(@PathVariable String id) {
        log.info("Excluir ambiente de sistema {}", id);
        ambienteSistemaService.excluir(id);
    }

}
