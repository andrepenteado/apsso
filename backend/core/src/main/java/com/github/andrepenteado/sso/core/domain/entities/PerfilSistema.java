package com.github.andrepenteado.sso.core.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Data
@ToString(of = "descricao")
public class PerfilSistema implements Serializable {

    @Id
    @NotNull(message = "Perfil do sistema é um campo obrigatório")
    private String authority;

    @ManyToOne
    @JoinColumn(name = "fk_sistema")
    @NotNull(message = "Sistema é um campo obrigatório")
    private Sistema sistema;

    @NotNull(message = "Descrição do perfil é um campo obrigatório")
    private String descricao;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PerfilSistema that = (PerfilSistema) o;
        return authority.equals(that.authority) && sistema.equals(that.sistema);
    }

    @Override
    public int hashCode() {
        int result = authority.hashCode();
        result = 31 * result + sistema.hashCode();
        return result;
    }

}
