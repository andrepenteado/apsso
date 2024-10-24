package com.github.andrepenteado.sso.core.services.impl;

import br.unesp.fc.andrepenteado.core.common.CoreUtil;
import com.github.andrepenteado.sso.core.services.SistemaService;
import com.github.andrepenteado.sso.core.domain.entities.Sistema;
import com.github.andrepenteado.sso.core.domain.repositories.SistemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SistemaServiceImpl implements SistemaService {

    private final SistemaRepository repository;

    @Override
    public List<Sistema> listar() {
        return repository.findAll();
    }

    @Override
    public Optional<Sistema> buscar(Long id) {
        return repository.findById(id);
    }

    @Override
    public Sistema incluirOuAlterar(Sistema sistema, BindingResult validacao) {
        String erros = CoreUtil.validateModel(validacao);

        if (erros != null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, erros);

        if (Objects.isNull(sistema.getId()))
            sistema.setDataCadastro(LocalDateTime.now());
        else
            sistema.setDataUltimaAtualizacao(LocalDateTime.now());

        return repository.save(sistema);
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
