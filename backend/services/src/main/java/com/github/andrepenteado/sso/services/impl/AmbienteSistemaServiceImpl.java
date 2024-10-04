package com.github.andrepenteado.sso.services.impl;

import br.unesp.fc.andrepenteado.core.common.CoreUtil;
import com.github.andrepenteado.sso.services.AmbienteSistemaService;
import com.github.andrepenteado.sso.services.entities.AmbienteSistema;
import com.github.andrepenteado.sso.services.repositories.AmbienteSistemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AmbienteSistemaServiceImpl implements AmbienteSistemaService {

    private final AmbienteSistemaRepository ambienteSistemaRepository;

    @Override
    public List<AmbienteSistema> listarPorSistema(Long idSistema) {
        return ambienteSistemaRepository.findBySistemaIdOrderById(idSistema);
    }

    @Override
    public Optional<AmbienteSistema> buscar(String id) {
        return ambienteSistemaRepository.findById(id);
    }

    @Override
    public AmbienteSistema incluir(AmbienteSistema ambienteSistema, BindingResult validacao) {
        String erros = CoreUtil.validateModel(validacao);
        if (erros != null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, erros);

        AmbienteSistema ambienteSistemaAlterar = novoAmbiente();

        ambienteSistemaAlterar.setId(ambienteSistema.getId());
        ambienteSistemaAlterar.setDescricao(ambienteSistema.getDescricao());
        ambienteSistemaAlterar.setClientId(ambienteSistema.getClientId());
        ambienteSistemaAlterar.setUrlEntrada(ambienteSistema.getUrlEntrada());
        ambienteSistemaAlterar.setRedirectUris(ambienteSistema.getRedirectUris());
        ambienteSistemaAlterar.setPostLogoutRedirectUris(ambienteSistema.getPostLogoutRedirectUris());
        ambienteSistemaAlterar.setClientSecret("{bcrypt}" + new BCryptPasswordEncoder().encode(ambienteSistema.getClientSecret()));

        return ambienteSistemaRepository.save(ambienteSistemaAlterar);
    }

    private AmbienteSistema novoAmbiente() {
        AmbienteSistema ambienteSistema = new AmbienteSistema();
        ambienteSistema.setClientAuthenticationMethods("client_secret_basic");
        ambienteSistema.setAuthorizationGrantTypes("refresh_token,client_credentials,authorization_code");
        ambienteSistema.setScopes("openid");
        ambienteSistema.setClientSettings("{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":false}");
        ambienteSistema.setTokenSettings("{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":false,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",900.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",86400.000000000],\"settings.token.authorization-code-time-to-live\":[\"java.time.Duration\",300.000000000],\"settings.token.device-code-time-to-live\":[\"java.time.Duration\",300.000000000]}");
        return ambienteSistema;
    }

    @Override
    public void excluir(String id) {
        try {
            ambienteSistemaRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
