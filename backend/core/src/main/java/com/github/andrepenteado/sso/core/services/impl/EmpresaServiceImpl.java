package com.github.andrepenteado.sso.core.services.impl;

import com.github.andrepenteado.sso.core.domain.entities.Empresa;
import com.github.andrepenteado.sso.core.domain.repositories.EmpresaRepository;
import com.github.andrepenteado.sso.core.services.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository repository;

    @Override
    public List<Empresa> listar() {
        return repository.findAll(Sort.by("razaoSocial"));
    }

}
