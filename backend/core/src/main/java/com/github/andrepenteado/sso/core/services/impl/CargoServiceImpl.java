package com.github.andrepenteado.sso.core.services.impl;

import br.unesp.fc.andrepenteado.core.common.CoreUtil;
import com.github.andrepenteado.sso.core.domain.entities.Cargo;
import com.github.andrepenteado.sso.core.domain.repositories.CargoRepository;
import com.github.andrepenteado.sso.core.services.CargoService;
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
public class CargoServiceImpl implements CargoService {

    private final CargoRepository repository;

    @Override
    public List<Cargo> listar() {
        return repository.findAllByOrderByNomeAsc();
    }

    @Override
    public List<Cargo> listarPorEmpresa(Long idEmpresa) {
        return repository.findByEmpresa_Id(idEmpresa);
    }

    @Override
    public Optional<Cargo> buscar(Long id) {
        return repository.findById(id);
    }

    @Override
    public Cargo incluir(Cargo cargo, BindingResult validacao) {
        String erros = CoreUtil.validateModel(validacao);
        if (erros != null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, erros);
        return repository.save(cargo);
    }

    @Override
    public Cargo alterar(Cargo cargo, Long id, BindingResult validacao) {
        String erros = CoreUtil.validateModel(validacao);
        if (erros != null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, erros);

        Cargo cargoAlterar = buscar(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Cargo de ID #%d não encontrado", id)));

        BeanUtils.copyProperties(cargo, cargoAlterar);

        if (!Objects.equals(cargoAlterar.getId(), id))
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Solicitado alterar cargo ID %d, porém enviado dados do cargo %d", id, cargoAlterar.getId()));

        return repository.save(cargo);
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
