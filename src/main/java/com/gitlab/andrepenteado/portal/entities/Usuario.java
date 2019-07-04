package com.gitlab.andrepenteado.portal.entities;

import com.gitlab.andrepenteado.portal.entities.enums.Perfil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
@EqualsAndHashCode(of = { "login" })
@ToString(of = { "nome" })
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "senha")
    private String senha;

    @NotBlank
    @Column(name = "nome")
    private String nome;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "expiracao")
    private LocalDate expiracao;

    @ElementCollection(targetClass = Perfil.class, fetch = FetchType.EAGER)
    @JoinTable(name = "perfil_usuario", joinColumns = @JoinColumn(name = "id_usuario"))
    @Column(name = "perfil")
    @Enumerated(EnumType.STRING)
    private List<Perfil> perfis;
}
