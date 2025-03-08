package com.github.andrepenteado.sso.core.services.impl;

import br.unesp.fc.andrepenteado.core.common.CoreUtil;
import com.github.andrepenteado.sso.core.domain.entities.Colaborador;
import com.github.andrepenteado.sso.core.domain.repositories.ColaboradorRepository;
import com.github.andrepenteado.sso.core.services.ColaboradorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ColaboradorServiceImpl implements ColaboradorService {

    private final ColaboradorRepository repository;

    @Override
    public List<Colaborador> listar() {
        return repository.findAllByOrderByNomeAsc();
    }

    @Override
    public Optional<Colaborador> buscar(Long id) {
        return repository.findById(id);
    }

    @Override
    public Colaborador incluir(Colaborador colaborador, BindingResult validacao) {
        String erros = CoreUtil.validateModel(validacao);
        if (erros != null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, erros);

        if (repository.buscarPorCpfEmpresa(colaborador.getCpf(), colaborador.getCargo().getEmpresa().getId()).isPresent())
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("CPF %d já se encontra cadastrado", colaborador.getCpf()));

        return repository.save(colaborador);
    }

    @Override
    public Colaborador alterar(Colaborador colaborador, Long id, BindingResult validacao) {
        String erros = CoreUtil.validateModel(validacao);
        if (erros != null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, erros);

        Colaborador colaboradorAlterar = buscar(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Colaborador de ID #%d não encontrado", id)));

        BeanUtils.copyProperties(colaborador, colaboradorAlterar);

        if (!Objects.equals(colaboradorAlterar.getId(), id))
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Solicitado alterar colaborador #ID %d, porém enviado dados do colaborador #%d", id, colaboradorAlterar.getId()));

        return repository.save(colaborador);
    }

    @Override
    public void excluir(Long id) {
        try {
            repository.deleteById(id);
        }
        catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        }
    }
}
