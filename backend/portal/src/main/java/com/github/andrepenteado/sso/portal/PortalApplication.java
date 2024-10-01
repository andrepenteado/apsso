package com.github.andrepenteado.sso.portal;

import br.unesp.fc.andrepenteado.core.upload.Upload;
import br.unesp.fc.andrepenteado.core.upload.UploadRepository;
import br.unesp.fc.andrepenteado.core.upload.UploadResource;
import br.unesp.fc.andrepenteado.core.web.config.CorsConfig;
import br.unesp.fc.andrepenteado.core.web.config.SecurityConfig;
import br.unesp.fc.andrepenteado.core.web.resources.AuthResource;
import br.unesp.fc.andrepenteado.core.web.services.UserLoginOAuth2Service;
import br.unesp.fc.andrepenteado.core.web.services.UserLoginOidcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
    scanBasePackages = {
        "com.github.andrepenteado.sso"
    },
    scanBasePackageClasses = {
        SecurityConfig.class,
        AuthResource.class,
        UserLoginOAuth2Service.class,
        UserLoginOidcService.class,
        CorsConfig.class,
        UploadResource.class
    }
)
@EntityScan(
    basePackages = {
        "com.github.andrepenteado.sso"
    },
    basePackageClasses = {
        Upload.class
    }
)
@EnableJpaRepositories(
    basePackages = {
        "com.github.andrepenteado.sso"
    },
    basePackageClasses = {
        UploadRepository.class
    }
)
public class PortalApplication {

    public static final String PERFIL_USUARIO = "ROLE_com.github.andrepenteado.sso.portal_USUARIO";

    public static void main(String[] args) {
        SpringApplication.run(PortalApplication.class, args);
    }

}
