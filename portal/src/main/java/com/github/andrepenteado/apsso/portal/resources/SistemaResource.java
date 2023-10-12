package com.github.andrepenteado.apsso.portal.resources;

import com.github.andrepenteado.apsso.services.PerfilSistemaService;
import com.github.andrepenteado.apsso.services.SistemaService;
import com.github.andrepenteado.apsso.services.models.PerfilSistema;
import com.github.andrepenteado.apsso.services.models.Sistema;
import jakarta.validation.Valid;
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

}
