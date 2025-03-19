package com.github.andrepenteado.sso.core.services;

import br.unesp.fc.andrepenteado.core.upload.Upload;
import com.github.andrepenteado.sso.core.domain.entities.Empresa;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

public interface EmpresaService {

    List<Empresa> listar();

    List<Empresa> listarPorCpfColaborador(Long cpf);

    Optional<Empresa> buscar(Long id);

    Empresa incluir(Empresa empresa, BindingResult validacao);

    Empresa alterar(Empresa empresa, Long id, BindingResult validacao);

    void excluir(Long id);

    Upload buscarLogotipoEmpresaPorUrlLogin(String urlLogin);

}
