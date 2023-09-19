package com.github.andrepenteado.apsso.backend.services.impl;

import com.github.andrepenteado.apsso.backend.ApssoBackendApplication;
import com.github.andrepenteado.apsso.backend.models.Usuario;
import com.github.andrepenteado.apsso.backend.repositories.UsuarioRepository;
import com.github.andrepenteado.apsso.backend.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> buscar(String username) {
        return usuarioRepository.findById(username);
    }

    @Override
    public Usuario incluir(Usuario usuario, BindingResult validacao) {
        String erros = ApssoBackendApplication.validateModel(validacao);

        if (erros != null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, erros);

        if (buscar(usuario.getUsername()).isPresent())
            throw new ResponseStatusException(HttpStatus.FOUND, String.format("Usuário %s já está cadastrado", usuario.getUsername()));

        if (usuario.getPassword() != null)
            usuario.setPassword("{bcrypt}" + new BCryptPasswordEncoder().encode(usuario.getPassword()));

        usuario.setDataCadastro(LocalDateTime.now());
        usuario.setEnabled(true);

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario alterar(Usuario usuario, String username, BindingResult validacao) {
        String erros = ApssoBackendApplication.validateModel(validacao);

        if (erros != null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, erros);

        Usuario usuarioAlterar = buscar(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Usuário %s não encontrado", username)));

        BeanUtils.copyProperties(usuario, usuarioAlterar);

        if (!Objects.equals(usuarioAlterar.getUsername(), username))
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Solicitado alterar usuário %s, porém enviado dados do usuário %s", username, usuarioAlterar.getUsername()));

        if (usuario.getPassword() != null)
            usuarioAlterar.setPassword("{bcrypt}" + new BCryptPasswordEncoder().encode(usuario.getPassword()));

        usuarioAlterar.setDataUltimaModificacao(LocalDateTime.now());
        usuarioAlterar.setEnabled(true);

        return usuarioRepository.save(usuarioAlterar);
    }

    @Override
    public void excluir(String username) {
        try {
            usuarioRepository.deleteById(username);
        }
        catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
