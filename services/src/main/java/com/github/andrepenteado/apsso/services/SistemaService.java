package com.github.andrepenteado.apsso.services;

import com.github.andrepenteado.apsso.services.entities.Sistema;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

public interface SistemaService {

    List<Sistema> listar();

    Optional<Sistema> buscar(String id);

    Sistema incluirOuAlterar(Sistema sistema, BindingResult validacao);

    void excluir(String id);

}
