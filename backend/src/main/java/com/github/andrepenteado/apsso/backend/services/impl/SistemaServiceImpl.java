package com.github.andrepenteado.apsso.backend.services.impl;

import com.github.andrepenteado.apsso.backend.ApssoBackendApplication;
import com.github.andrepenteado.apsso.backend.models.Sistema;
import com.github.andrepenteado.apsso.backend.repositories.SistemaRepository;
import com.github.andrepenteado.apsso.backend.services.SistemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
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
        String erros = ApssoBackendApplication.validateModel(validacao);
        if (erros != null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, erros);

        Sistema sistemaAlterar = buscar(sistema.getId()).orElse(novoSistema());

        sistemaAlterar.setDataUltimaModificacao(LocalDateTime.now());
        sistemaAlterar.setId(sistema.getId());
        sistemaAlterar.setDescricao(sistema.getDescricao());
        sistemaAlterar.setClientId(sistema.getClientId());
        sistemaAlterar.setRedirectUris(String.format("%s/login/oauth2/code/%s-oidc,%s/authorized",
                sistema.getUrlEntrada(), sistema.getUrlEntrada(), sistema.getId().toLowerCase()));
        sistemaAlterar.setUrlEntrada(sistema.getUrlEntrada());

        if (sistema.getClientSecret() != null && !sistema.getClientSecret().startsWith("{bcrypt}"))
            sistemaAlterar.setClientSecret("{bcrypt}" + new BCryptPasswordEncoder().encode(sistema.getClientSecret()));
        else
            sistemaAlterar.setClientSecret(sistema.getClientSecret());

        return sistemaRepository.save(sistemaAlterar);
    }

    private Sistema novoSistema() {
        Sistema sistema = new Sistema();
        sistema.setDataCadastro(LocalDateTime.now());
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
