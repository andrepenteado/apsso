package com.github.andrepenteado.sso.equipe;

import br.unesp.fc.andrepenteado.core.upload.Upload;
import br.unesp.fc.andrepenteado.core.upload.UploadRepository;
import br.unesp.fc.andrepenteado.core.upload.UploadResource;
import br.unesp.fc.andrepenteado.core.web.config.CorsConfig;
import br.unesp.fc.andrepenteado.core.web.config.SecurityConfig;
import br.unesp.fc.andrepenteado.core.web.services.UserLoginOAuth2Service;
import com.github.andrepenteado.sso.equipe.login.EquipeUserLoginResource;
import com.github.andrepenteado.sso.equipe.login.EquipeUserLoginService;
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
        UserLoginOAuth2Service.class,
        CorsConfig.class,
        UploadResource.class,
        EquipeUserLoginService.class,
        EquipeUserLoginResource.class
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
public class EquipeApplication {

    public static final String PERFIL_GESTOR = "ROLE_com.github.andrepenteado.sso.equipe_GESTOR";

    public static void main(String[] args) {
        SpringApplication.run(EquipeApplication.class, args);
    }

}
