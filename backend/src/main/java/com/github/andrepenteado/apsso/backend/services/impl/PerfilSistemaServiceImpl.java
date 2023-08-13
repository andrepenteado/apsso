package com.github.andrepenteado.apsso.backend.services.impl;

import com.github.andrepenteado.apsso.backend.ApssoBackendApplication;
import com.github.andrepenteado.apsso.backend.models.PerfilSistema;
import com.github.andrepenteado.apsso.backend.repositories.PerfilSistemaRepository;
import com.github.andrepenteado.apsso.backend.services.PerfilSistemaService;
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
    public List<PerfilSistema> listarPorSistema(String idSistema) {
        return repository.findBySistemaIdOrderByAuthority(idSistema);
    }

    @Override
    public PerfilSistema incluir(PerfilSistema perfilSistema, BindingResult validacao) {
        String erros = ApssoBackendApplication.validateModel(validacao);
        if (erros != null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, erros);
        return repository.save(perfilSistema);
    }

    @Override
    public void excluir(Long id) {
        try {
            repository.deleteById(id);
        }
        catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
