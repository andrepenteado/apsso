package com.github.andrepenteado.sso.core.services;

import com.github.andrepenteado.sso.core.domain.entities.UnidadeAdministrativa;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

public interface UnidadeAdministrativaService {

    List<com.github.andrepenteado.sso.core.domain.entities.UnidadeAdministrativa> listar();

    List<UnidadeAdministrativa> listarPorEmpresa(Long idEmpresa);

    Optional<UnidadeAdministrativa> buscar(Long id);

    UnidadeAdministrativa incluir(UnidadeAdministrativa unidadeAdministrativa, BindingResult validacao);

    UnidadeAdministrativa alterar(UnidadeAdministrativa unidadeAdministrativa, Long id, BindingResult validacao);

    void excluir(Long id);

}
