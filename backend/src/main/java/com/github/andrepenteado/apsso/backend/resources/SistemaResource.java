package com.github.andrepenteado.apsso.backend.resources;

import com.github.andrepenteado.apsso.backend.models.PerfilSistema;
import com.github.andrepenteado.apsso.backend.models.Sistema;
import com.github.andrepenteado.apsso.backend.services.PerfilSistemaService;
import com.github.andrepenteado.apsso.backend.services.SistemaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/sistemas")
@RequiredArgsConstructor
@Slf4j
public class SistemaResource {

    private final SistemaService sistemaService;
    private final PerfilSistemaService perfilSistemaService;

    @GetMapping
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
    public Sistema buscar(@PathVariable  String id) {
        log.info("Buscar sistema de ID: #" + id);
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
    public Sistema incluir(@RequestBody Sistema sistema, BindingResult validacao) {
        log.info("Incluir/Alterar sistema " + sistema);
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
    public List<PerfilSistema> listarPerfisPorSistema(@PathVariable String id) {
        log.info("Listar perfis do sistema " + id);
        try {
            return perfilSistemaService.listarPorSistema(id);
        }
        catch (Exception ex) {
            log.error("Erro no processamento", ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no processamento");
        }
    }

    @GetMapping("/perfis")
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
    public PerfilSistema incluirPerfil(@RequestBody PerfilSistema perfilSistema, BindingResult validacao) {
        log.info("Incluir novo perfil de sistema " + perfilSistema);
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

    @DeleteMapping("/perfil/{id}")
    public void excluirPerfil(@PathVariable Long id) {
        log.info("Excluir perfil de sistema de ID #" + id);
        try {
            perfilSistemaService.excluir(id);
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
