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
@ToString(of = "razaoSocial")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dataCadastro;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dataUltimaAtualizacao;

    private String usuarioCadastro;

    private String usuarioUltimaAtualizacao;

    @NotNull(message = "Razão social é um campo obrigatório")
    private String razaoSocial;

    @NotNull(message = "CNPJ é um campo obrigatório")
    private Long cnpj;

    private String telefone;

    private String nomeFantasia;

    private String email;

    private Long cep;

    private String logradouro;

    private String complemento;

    private Long numeroLogradouro;

    private String bairro;

    private String cidade;

    private String estado;

    private String urlSso;

    @ManyToOne
    @JoinColumn(name = "fk_empresa_matriz")
    private Empresa matriz;

    @Column(name = "fk_upload")
    private UUID logotipo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Empresa empresa = (Empresa) o;
        return id.equals(empresa.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
