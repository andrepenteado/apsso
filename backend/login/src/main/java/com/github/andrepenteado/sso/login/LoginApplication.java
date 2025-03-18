package com.github.andrepenteado.sso.login;

import br.unesp.fc.andrepenteado.core.upload.Upload;
import br.unesp.fc.andrepenteado.core.upload.UploadRepository;
import br.unesp.fc.andrepenteado.core.upload.UploadResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
    scanBasePackages = {
        "com.github.andrepenteado.sso"
    },
    scanBasePackageClasses = {
        UploadResource.class,
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
public class LoginApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class, args);
    }

}
