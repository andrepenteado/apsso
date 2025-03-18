package com.github.andrepenteado.sso.controle.login;

import br.unesp.fc.andrepenteado.core.upload.UploadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Primary
@Service
@RequiredArgsConstructor
@Slf4j
public class ControleUserLoginService extends OidcUserService {

    private final UploadRepository uploadRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Carregando informações do usuario");
        ControleUserLogin userLogin = new ControleUserLogin((DefaultOidcUser) super.loadUser(userRequest));
        if (userLogin.getUuidFoto() != null && !userLogin.getUuidFoto().isEmpty()) {
            uploadRepository
                .findById(UUID.fromString(userLogin.getUuidFoto()))
                .ifPresent(foto -> userLogin.setFotoBase64(foto.getBase64()));
        }
        return userLogin;
    }
}
