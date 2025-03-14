package com.github.andrepenteado.sso.core.domain.entities;

import com.github.andrepenteado.sso.core.domain.enums.TipoToken;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@ToString(of = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;

    private UUID token;

    @Enumerated(EnumType.STRING)
    private TipoToken tipo;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataExpiracao;

    private Boolean utilizado;

}
