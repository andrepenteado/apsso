package com.github.andrepenteado.apsso.backend.services;

import com.github.andrepenteado.apsso.backend.models.PerfilSistema;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface PerfilSistemaService {

    List<PerfilSistema> listar();

    List<PerfilSistema> listarPorSistema(String idSistema);

    PerfilSistema incluir(PerfilSistema perfilSistema, BindingResult validacao);

    void excluir(Long id);

}
