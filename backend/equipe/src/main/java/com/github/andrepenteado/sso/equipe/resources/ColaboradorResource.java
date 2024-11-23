package com.github.andrepenteado.sso.equipe.resources;


import br.unesp.fc.andrepenteado.core.web.dto.UserLogin;
import com.github.andrepenteado.sso.core.domain.entities.Colaborador;
import com.github.andrepenteado.sso.core.services.ColaboradorService;
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

import static com.github.andrepenteado.sso.equipe.EquipeApplication.PERFIL_GESTOR;

@RestController
@RequestMapping("/colaboradores")
@RequiredArgsConstructor
@Observed
@Slf4j
public class ColaboradorResource {

    private final ColaboradorService service;

    @GetMapping
    @Secured({ PERFIL_GESTOR })
    public List<Colaborador> listar() {
        log.info("Listar todos colaboradores");
        return service.listar();
    }

    @GetMapping("/{id}")
    @Secured({ PERFIL_GESTOR })
    public Colaborador buscar(@PathVariable Long id) {
        log.info("Buscar colaborador por ID #{}", id);
        return service.buscar(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Colaborador de ID #%d não encontrado", id)));
    }

    @PostMapping
    @Secured({ PERFIL_GESTOR })
    public Colaborador incluir(@RequestBody @Valid Colaborador colaborador, BindingResult validacao, @AuthenticationPrincipal UserLogin userLogin) {
        log.info("Incluir novo colaborador {}", colaborador);

        colaborador.setDataCadastro(LocalDateTime.now());
        colaborador.setUsuarioCadastro(userLogin.getNome());

        return service.incluir(colaborador, validacao);
    }

    @PutMapping("/{id}")
    @Secured({ PERFIL_GESTOR })
    public Colaborador alterar(@PathVariable Long id, @RequestBody @Valid Colaborador colaborador, BindingResult validacao, @AuthenticationPrincipal UserLogin userLogin) {
        log.info("Alterar colaborador {}", colaborador);

        Colaborador colaboradorAtual = buscar(id);
        colaborador.setDataCadastro(colaboradorAtual.getDataCadastro());
        colaborador.setUsuarioCadastro(colaboradorAtual.getUsuarioCadastro());
        colaborador.setDataUltimaAtualizacao(LocalDateTime.now());
        colaborador.setUsuarioUltimaAtualizacao(userLogin.getNome());

        return service.alterar(colaborador, id, validacao);
    }

    @DeleteMapping("/{id}")
    @Secured({ PERFIL_GESTOR })
    public void excluir(@PathVariable Long id) {
        log.info("Excluir colaborador ID #{}", id);
        service.excluir(id);
    }

}
