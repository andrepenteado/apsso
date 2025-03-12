package com.github.andrepenteado.sso.core.services.impl;

import br.unesp.fc.andrepenteado.core.common.CoreUtil;
import com.github.andrepenteado.sso.core.domain.entities.Empresa;
import com.github.andrepenteado.sso.core.domain.repositories.EmpresaRepository;
import com.github.andrepenteado.sso.core.services.EmpresaService;
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
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository repository;

    @Override
    public List<Empresa> listar() {
        return repository.findAllByOrderByRazaoSocialAsc();
    }

    @Override
    public List<Empresa> listarPorCpfColaborador(Long cpf) {
        return repository.listarPorCpfColaborador(cpf);
    }

    @Override
    public Optional<Empresa> buscar(Long id) {
        return repository.findById(id);
    }

    @Override
    public Empresa incluir(Empresa empresa, BindingResult validacao) {
        String erros = CoreUtil.validateModel(validacao);
        if (erros != null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, erros);

        if (Objects.nonNull(repository.findByCnpj(empresa.getCnpj())))
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("CNPJ %d já se encontra cadastrado", empresa.getCnpj()));

        return repository.save(empresa);
    }

    @Override
    public Empresa alterar(Empresa empresa, Long id, BindingResult validacao) {
        String erros = CoreUtil.validateModel(validacao);
        if (erros != null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, erros);

        Empresa empresaAlterar = buscar(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Empresa de ID %d não encontrada", id)));

        BeanUtils.copyProperties(empresa, empresaAlterar);

        if (!Objects.equals(empresaAlterar.getId(), id))
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Solicitado alterar empresa #ID %d, porém enviado dados da empresa #%d", id, empresaAlterar.getId()));

        return repository.save(empresa);
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
