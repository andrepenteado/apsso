package com.github.andrepenteado.sso.services.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class Sistema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime dataCadastro;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime dataUltimaAtualizacao;

    private String usuarioCadastro;

    private String usuarioUltimaAtualizacao;

    @NotNull(message = "Nome do sistema é um campo obrigatório")
    private String nome;

    private String descricao;

    @Column(name = "fk_upload")
    private UUID icone;

    @ManyToOne
    @JoinColumn(name = "fk_empresa")
    @NotNull(message = "Empresa é um campo obrigatório")
    private Empresa empresa;

}
