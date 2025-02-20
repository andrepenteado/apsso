package com.github.andrepenteado.sso.core.services.impl;

import br.unesp.fc.andrepenteado.core.common.CoreUtil;
import com.github.andrepenteado.sso.core.domain.entities.AmbienteSistema;
import com.github.andrepenteado.sso.core.domain.repositories.AmbienteSistemaRepository;
import com.github.andrepenteado.sso.core.services.AmbienteSistemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AmbienteSistemaServiceImpl implements AmbienteSistemaService {

    private final AmbienteSistemaRepository ambienteSistemaRepository;

    @Override
    public List<AmbienteSistema> listar() {
        return ambienteSistemaRepository.findByOrderBySistema();
    }

    @Override
    public List<AmbienteSistema> filtrarPorSistema(Long idSistema) {
        return ambienteSistemaRepository.findBySistemaIdOrderById(idSistema);
    }

    @Override
    public Optional<AmbienteSistema> buscar(String id) {
        return ambienteSistemaRepository.findById(id);
    }

    @Override
    public AmbienteSistema incluirOuAlterar(AmbienteSistema ambienteSistema, BindingResult validacao) {
        String erros = CoreUtil.validateModel(validacao);

        if (erros != null)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, erros);

        AmbienteSistema ambienteSistemaAlterar;
        String senhaPlain = "********** (inalterada)";

        if (ambienteSistema.getId() == null) {
            ambienteSistemaAlterar = novoAmbiente();
            ambienteSistemaAlterar.setId(UUID.randomUUID().toString());

            senhaPlain = UUID.randomUUID().toString();
            ambienteSistemaAlterar.setClientSecret("{bcrypt}" + new BCryptPasswordEncoder().encode(senhaPlain));

            // Gera uma string do tipo PRODUCAO-H8623bh
            ambienteSistemaAlterar.setClientId(ambienteSistema.getTipo().toString()
                .concat(new Random().ints(6, 33, 127)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString()
                )
            );
        }
        else {
            ambienteSistemaAlterar = buscar(ambienteSistema.getId()).orElseThrow();
        }

        ambienteSistemaAlterar.setDescricao(ambienteSistema.getDescricao());
        ambienteSistemaAlterar.setTipo(ambienteSistema.getTipo());
        ambienteSistemaAlterar.setUrlAcesso(ambienteSistema.getUrlAcesso());
        ambienteSistemaAlterar.setUrlLogin(ambienteSistema.getUrlLogin());
        ambienteSistemaAlterar.setRedirectUris(ambienteSistema.getRedirectUris());
        ambienteSistemaAlterar.setPostLogoutRedirectUris(ambienteSistema.getPostLogoutRedirectUris());
        ambienteSistemaAlterar.setSistema(ambienteSistema.getSistema());

        AmbienteSistema result = ambienteSistemaRepository.save(ambienteSistemaAlterar);
        result.setClientSecretPlain(senhaPlain);

        return result;
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
