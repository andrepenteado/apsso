package com.github.andrepenteado.sso.core.services;

import com.github.andrepenteado.sso.core.domain.entities.AmbienteSistema;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

public interface AmbienteSistemaService {

    List<AmbienteSistema> listar();

    List<AmbienteSistema> pesquisarPorSistema(Long idSistema);

    List<AmbienteSistema> pesquisarPorUsuario(String username);

    Optional<AmbienteSistema> buscar(String id);

    AmbienteSistema incluirOuAlterar(AmbienteSistema ambienteSistema, BindingResult validacao);

    void excluir(String id);

}
