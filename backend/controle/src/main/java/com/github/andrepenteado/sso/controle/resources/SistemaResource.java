package com.github.andrepenteado.sso.controle.resources;

import com.github.andrepenteado.sso.services.PerfilSistemaService;
import com.github.andrepenteado.sso.services.SistemaService;
import com.github.andrepenteado.sso.services.entities.PerfilSistema;
import com.github.andrepenteado.sso.services.entities.Sistema;
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

@RestController
@RequestMapping("/sistemas")
@RequiredArgsConstructor
@Observed
@Slf4j
public class SistemaResource {

    private final SistemaService sistemaService;

    private final PerfilSistemaService perfilSistemaService;

    @GetMapping
    @Secured({"ROLE_Controle_ARQUITETO"}) //@RolesAllowed({"Controle_ARQUITETO"})
    public List<Sistema> listar() {
        log.info("Listar sistemas");
        try {
            return sistemaService.listar();
        }
        catch (Exception ex) {
            log.error("Erro no processamento", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento");
        }
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_Controle_ARQUITETO"})
    public Sistema buscar(@PathVariable  String id) {
        log.info("Buscar sistema de ID #{}", id);
        try {
            return sistemaService.buscar(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Sistema de ID %s não encontrado", id)));
        }
        catch (ResponseStatusException rsex) {
            throw rsex;
        }
        catch (Exception ex) {
            log.error("Erro no processamento", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento");
        }
    }

    @PostMapping
    @Secured({"ROLE_Controle_ARQUITETO"})
    public Sistema incluir(@RequestBody @Valid Sistema sistema, BindingResult validacao) {
        log.info("Incluir/Alterar sistema {}", sistema);
        try {
            return sistemaService.incluirOuAlterar(sistema, validacao);
        }
        catch (ResponseStatusException rsex) {
            throw rsex;
        }
        catch (Exception ex) {
            log.error("Erro no processamento", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento");
        }
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_Controle_ARQUITETO"})
    public void excluir(@PathVariable String id) {
        log.info("Excluir sistema de ID #" + id);
        try {
            sistemaService.excluir(id);
        }
        catch (ResponseStatusException rsex) {
            throw rsex;
        }
        catch (Exception ex) {
            log.error("Erro no processamento", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento");
        }
    }

    @GetMapping("/{id}/perfis")
    @Secured({"ROLE_Controle_ARQUITETO"})
    public List<PerfilSistema> listarPerfisPorSistema(@PathVariable String id) {
        log.info("Listar perfis do sistema #{}", id);
        try {
            return perfilSistemaService.listarPorSistema(id);
        }
        catch (Exception ex) {
            log.error("Erro no processamento", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento");
        }
    }

    @GetMapping("/perfis")
    @Secured({"ROLE_Controle_ARQUITETO"})
    public List<PerfilSistema> listarPerfis() {
        log.info("Listar perfis de sistemas");
        try {
            return perfilSistemaService.listar();
        }
        catch (Exception ex) {
            log.error("Erro no processamento", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento");
        }
    }

    @PostMapping("/perfil")
    @Secured({"ROLE_Controle_ARQUITETO"})
    public PerfilSistema incluirPerfil(@RequestBody @Valid PerfilSistema perfilSistema, BindingResult validacao) {
        log.info("Incluir novo perfil de sistema {}", perfilSistema);
        try {
            return perfilSistemaService.incluir(perfilSistema, validacao);
        }
        catch (ResponseStatusException rsex) {
            throw rsex;
        }
        catch (Exception ex) {
            log.error("Erro no processamento", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento");
        }
    }

    @DeleteMapping("/perfil/{authority}")
    @Secured({"ROLE_Controle_ARQUITETO"})
    public void excluirPerfil(@PathVariable String authority) {
        log.info("Excluir perfil de sistema {}", authority);
        try {
            perfilSistemaService.excluir(authority);
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
