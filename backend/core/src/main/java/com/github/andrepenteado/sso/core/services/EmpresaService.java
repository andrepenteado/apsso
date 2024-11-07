package com.github.andrepenteado.sso.core.services;

import com.github.andrepenteado.sso.core.domain.entities.Empresa;

import java.util.List;

public interface EmpresaService {

    List<Empresa> listar();

    Empresa buscarPorUrlSso(String urlSso);

}
