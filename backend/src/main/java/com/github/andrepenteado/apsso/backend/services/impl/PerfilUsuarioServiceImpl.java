package com.github.andrepenteado.apsso.backend.services.impl;

import com.github.andrepenteado.apsso.backend.ApssoBackendApplication;
import com.github.andrepenteado.apsso.backend.models.PerfilUsuario;
import com.github.andrepenteado.apsso.backend.repositories.PerfilUsuarioRepository;
import com.github.andrepenteado.apsso.backend.services.PerfilUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerfilUsuarioServiceImpl implements PerfilUsuarioService {

    private final PerfilUsuarioRepository repository;

    @Override
    public List<PerfilUsuario> listarPorUsuario(String username) {
        return repository.findByUsernameOrderByAuthority(username);
    }

    @Override
    public PerfilUsuario incluir(PerfilUsuario perfilUsuario, BindingResult validacao) {
        String erros = ApssoBackendApplication.validateModel(validacao);
        if (erros != null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, erros);
        return repository.save(perfilUsuario);
    }

    @Override
    public void excluir(PerfilUsuario perfilUsuario) {
        repository.delete(perfilUsuario);
    }

}
