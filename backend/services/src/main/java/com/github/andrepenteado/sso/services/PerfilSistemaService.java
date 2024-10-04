package com.github.andrepenteado.sso.services;

import com.github.andrepenteado.sso.services.entities.PerfilSistema;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface PerfilSistemaService {

    List<PerfilSistema> listar();

    List<PerfilSistema> listarPorSistema(Long idSistema);

    PerfilSistema incluir(PerfilSistema perfilSistema, BindingResult validacao);

    void excluir(String authority);

    PerfilSistema buscar(String authority);

}
