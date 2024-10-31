package com.github.andrepenteado.sso.core.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@ToString(of = "nome")
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

    @NotNull(message = "Código do sistema é um campo obrigatório")
    private String codigo;

    @NotNull(message = "Nome do sistema é um campo obrigatório")
    private String nome;

    private String descricao;

    @Column(name = "fk_upload")
    private UUID icone;

    @ManyToOne
    @JoinColumn(name = "fk_empresa")
    @NotNull(message = "Empresa é um campo obrigatório")
    private Empresa empresa;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sistema sistema = (Sistema) o;
        return id.equals(sistema.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
