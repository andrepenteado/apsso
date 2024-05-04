package com.github.andrepenteado.sso.services;

import com.github.andrepenteado.sso.services.entities.Sistema;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

public interface SistemaService {

    List<Sistema> listar();

    Optional<Sistema> buscar(String id);

    Sistema incluirOuAlterar(Sistema sistema, BindingResult validacao);

    void excluir(String id);

}
