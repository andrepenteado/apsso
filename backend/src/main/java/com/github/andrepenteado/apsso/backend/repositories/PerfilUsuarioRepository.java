package com.github.andrepenteado.apsso.backend.repositories;

import com.github.andrepenteado.apsso.backend.models.PerfilUsuario;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfilUsuarioRepository extends JpaRepository<PerfilUsuario, String> {

    List<PerfilUsuario> findByUsernameOrderByAuthority(String username);

}
