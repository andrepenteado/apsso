package com.github.andrepenteado.sso.equipe.login;

import br.unesp.fc.andrepenteado.core.upload.Upload;
import br.unesp.fc.andrepenteado.core.upload.UploadRepository;
import com.github.andrepenteado.sso.core.services.UsuarioService;
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
public class EquipeUserLoginService extends OidcUserService {

    private final UsuarioService usuarioService;

    private final UploadRepository uploadRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Carregando informações do usuario");
        EquipeUserLogin userLogin = new EquipeUserLogin((DefaultOidcUser) super.loadUser(userRequest));
        userLogin.setUsuario(usuarioService.buscar(userLogin.getLogin()).orElse(null));
        Upload foto = uploadRepository.findById(UUID.fromString(userLogin.getUuidFoto())).orElse(null);
        if (foto != null)
            userLogin.setFotoBase64(foto.getBase64());
        return userLogin;
    }
}
