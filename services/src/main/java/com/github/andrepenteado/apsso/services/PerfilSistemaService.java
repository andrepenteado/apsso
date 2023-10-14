package com.github.andrepenteado.apsso.services;

import com.github.andrepenteado.apsso.services.entities.PerfilSistema;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface PerfilSistemaService {

    List<PerfilSistema> listar();

    List<PerfilSistema> listarPorSistema(String idSistema);

    PerfilSistema incluir(PerfilSistema perfilSistema, BindingResult validacao);

    void excluir(String authority);

    PerfilSistema buscar(String authority);

}
