package com.github.andrepenteado.sso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@ConfigurationProperties("apsso")
public class ApSsoProperties {

    @Getter @Setter @NotBlank
    private String uri;

    @Getter @Setter @NotNull
    private JksProperties jks;

    public static class JksProperties {
        @Getter @Setter @NotBlank
        private String keypass;

        @Getter @Setter @NotBlank
        private String storepass;

        @Getter @Setter @NotBlank
        private String alias;

        @Getter @Setter @NotBlank
        private String path;

    }

}
