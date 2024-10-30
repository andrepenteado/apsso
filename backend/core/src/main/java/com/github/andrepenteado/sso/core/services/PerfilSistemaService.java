package com.github.andrepenteado.sso.core.services;

import com.github.andrepenteado.sso.core.domain.entities.PerfilSistema;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface PerfilSistemaService {

    List<PerfilSistema> listar();

    List<PerfilSistema> filtrarPorSistema(Long idSistema);

    PerfilSistema incluir(PerfilSistema perfilSistema, BindingResult validacao);

    void excluir(String authority);

    PerfilSistema buscar(String authority);

}
