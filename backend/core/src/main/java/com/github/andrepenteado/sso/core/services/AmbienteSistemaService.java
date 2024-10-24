package com.github.andrepenteado.sso.core.services;

import com.github.andrepenteado.sso.core.domain.entities.AmbienteSistema;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

public interface AmbienteSistemaService {

    List<AmbienteSistema> listarPorSistema(Long idSistema);

    Optional<AmbienteSistema> buscar(String id);

    AmbienteSistema incluir(AmbienteSistema ambienteSistema, BindingResult validacao);

    void excluir(String id);

}
