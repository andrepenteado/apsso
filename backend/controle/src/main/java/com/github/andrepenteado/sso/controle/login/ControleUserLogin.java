package com.github.andrepenteado.sso.controle.login;

import br.unesp.fc.andrepenteado.core.web.dto.UserLogin;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

@Getter @Setter
public class ControleUserLogin extends UserLogin {

    private String fotoBase64;

    public ControleUserLogin(DefaultOidcUser userLogin) {
        super(userLogin);
    }

}
