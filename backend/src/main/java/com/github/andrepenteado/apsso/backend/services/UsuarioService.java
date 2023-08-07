package com.github.andrepenteado.apsso.backend.services;

import com.github.andrepenteado.apsso.backend.models.Usuario;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> listar();

    Optional<Usuario> buscar(String username);

    Usuario incluir(Usuario usuario, BindingResult validacao);

    Usuario alterar(Usuario usuario, String username, BindingResult validacao);

    void excluir(String username);

}
