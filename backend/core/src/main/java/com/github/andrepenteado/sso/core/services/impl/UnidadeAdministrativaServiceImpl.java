package com.github.andrepenteado.sso.core.services.impl;

import br.unesp.fc.andrepenteado.core.common.CoreUtil;
import com.github.andrepenteado.sso.core.domain.entities.UnidadeAdministrativa;
import com.github.andrepenteado.sso.core.domain.repositories.UnidadeAdministrativaRepository;
import com.github.andrepenteado.sso.core.services.UnidadeAdministrativaService;
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
public class UnidadeAdministrativaServiceImpl implements UnidadeAdministrativaService {

    private final UnidadeAdministrativaRepository repository;

    @Override
    public List<UnidadeAdministrativa> listar() {
        return repository.findAllByOrderByNomeAsc();
    }

    @Override
    public List<UnidadeAdministrativa> listarPorEmpresa(Long idEmpresa) {
        return repository.findByEmpresa_Id(idEmpresa);
    }

    @Override
    public Optional<UnidadeAdministrativa> buscar(Long id) {
        return repository.findById(id);
    }

    @Override
    public UnidadeAdministrativa incluir(UnidadeAdministrativa unidadeAdministrativa, BindingResult validacao) {
        String erros = CoreUtil.validateModel(validacao);
        if (erros != null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, erros);
        return repository.save(unidadeAdministrativa);
    }

    @Override
    public UnidadeAdministrativa alterar(UnidadeAdministrativa unidadeAdministrativa, Long id, BindingResult validacao) {
        String erros = CoreUtil.validateModel(validacao);
        if (erros != null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, erros);

        UnidadeAdministrativa unidadeAdministrativaAlterar = buscar(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unidade administrativa de ID #%d não encontrado", id)));

        BeanUtils.copyProperties(unidadeAdministrativa, unidadeAdministrativaAlterar);

        if (!Objects.equals(unidadeAdministrativaAlterar.getId(), id))
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                String.format("Solicitado alterar unidade administrativa ID #%d, porém enviado dados da unidade administrativa #%d",
                id, unidadeAdministrativaAlterar.getId()));

        return repository.save(unidadeAdministrativa);
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
