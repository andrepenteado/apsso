package com.github.andrepenteado.sso.core.services;

import com.github.andrepenteado.sso.core.domain.entities.Usuario;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioService {

    List<Usuario> listar();

    Optional<Usuario> buscar(String username);

    Optional<Usuario> buscarPorEmail(String email);

    Usuario incluir(Usuario usuario, BindingResult validacao);

    Usuario alterar(Usuario usuario, String username, BindingResult validacao);

    void excluir(String username);

    void alterarSenha(String username, String senha);

    void atualizarFoto(String username, UUID uuidFoto);
}
