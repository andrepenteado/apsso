package com.github.andrepenteado.sso.services.impl;

import com.github.andrepenteado.core.common.CoreUtil;
import com.github.andrepenteado.sso.services.SistemaService;
import com.github.andrepenteado.sso.services.UsuarioService;
import com.github.andrepenteado.sso.services.entities.Sistema;
import com.github.andrepenteado.sso.services.repositories.SistemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SistemaServiceImpl implements SistemaService {

    private final SistemaRepository sistemaRepository;

    private final UsuarioService usuarioService;

    @Override
    public List<Sistema> listar() {
        return sistemaRepository.findAll();
    }

    @Override
    public Optional<Sistema> buscar(String id) {
        return sistemaRepository.findById(id);
    }

    @Override
    public Sistema incluirOuAlterar(Sistema sistema, BindingResult validacao) {
        String erros = CoreUtil.validateModel(validacao);
        if (erros != null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, erros);

        Sistema sistemaAlterar = buscar(sistema.getId()).orElse(novoSistema());

        sistemaAlterar.setDataUltimaAtualizacao(LocalDateTime.now());
        sistemaAlterar.setUsuarioUltimaAtualizacao(usuarioService.buscar(SecurityContextHolder.getContext().getAuthentication().getName()).get().getNome());
        sistemaAlterar.setId(sistema.getId());
        sistemaAlterar.setDescricao(sistema.getDescricao());
        sistemaAlterar.setClientId(sistema.getClientId());
        sistemaAlterar.setIcone(sistema.getIcone());
        sistemaAlterar.setUrlEntrada(sistema.getUrlEntrada());

        StringBuilder redirectUris = new StringBuilder();
        StringBuilder logoutUris = new StringBuilder();
        for (String url: sistema.getUrlEntrada().split(";")) {
            redirectUris.append(String.format("%s/authorized,", url));
            logoutUris.append(String.format("%s/logout,", url));
        }
        sistemaAlterar.setRedirectUris(redirectUris.toString());
        sistemaAlterar.setPostLogoutRedirectUris(logoutUris.toString());

        if (sistema.getClientSecret() != null && !sistema.getClientSecret().startsWith("{bcrypt}"))
            sistemaAlterar.setClientSecret("{bcrypt}" + new BCryptPasswordEncoder().encode(sistema.getClientSecret()));

        return sistemaRepository.save(sistemaAlterar);
    }

    private Sistema novoSistema() {
        Sistema sistema = new Sistema();
        sistema.setDataCadastro(LocalDateTime.now());
        sistema.setUsuarioCadastro(usuarioService.buscar(SecurityContextHolder.getContext().getAuthentication().getName()).get().getNome());
        sistema.setClientAuthenticationMethods("client_secret_basic");
        sistema.setAuthorizationGrantTypes("refresh_token,client_credentials,authorization_code");
        sistema.setScopes("openid");
        sistema.setClientSettings("{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":false}");
        sistema.setTokenSettings("{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":false,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",900.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",86400.000000000],\"settings.token.authorization-code-time-to-live\":[\"java.time.Duration\",300.000000000],\"settings.token.device-code-time-to-live\":[\"java.time.Duration\",300.000000000]}");
        return sistema;
    }

    @Override
    public void excluir(String id) {
        try {
            sistemaRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
