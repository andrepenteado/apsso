package com.github.andrepenteado.apsso.backend.services;

import com.github.andrepenteado.apsso.backend.models.PerfilUsuario;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface PerfilUsuarioService {

    List<PerfilUsuario> listarPorUsuario(String username);

    PerfilUsuario incluir(PerfilUsuario perfilUsuario, BindingResult validacao);

    void excluir(PerfilUsuario perfilUsuario);

}
