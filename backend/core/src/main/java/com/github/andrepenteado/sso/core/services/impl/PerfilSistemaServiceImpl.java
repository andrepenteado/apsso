package com.github.andrepenteado.sso.core.services.impl;

import br.unesp.fc.andrepenteado.core.common.CoreUtil;
import com.github.andrepenteado.sso.core.services.PerfilSistemaService;
import com.github.andrepenteado.sso.core.domain.entities.PerfilSistema;
import com.github.andrepenteado.sso.core.domain.repositories.PerfilSistemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerfilSistemaServiceImpl implements PerfilSistemaService {

    private final PerfilSistemaRepository repository;

    @Override
    public List<PerfilSistema> listar() {
        return repository.findByOrderBySistemaId();
    }

    @Override
    public List<PerfilSistema> listarPorSistema(Long idSistema) {
        return repository.findBySistemaIdOrderByAuthority(idSistema);
    }

    @Override
    public PerfilSistema incluir(PerfilSistema perfilSistema, BindingResult validacao) {
        String erros = CoreUtil.validateModel(validacao);
        if (erros != null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, erros);

        return repository.save(perfilSistema);
    }

    @Override
    public void excluir(String authority) {
        try {
            repository.deleteById(authority);
        }
        catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public PerfilSistema buscar(String authority) {
        return repository.findByAuthority(authority);
    }

}
