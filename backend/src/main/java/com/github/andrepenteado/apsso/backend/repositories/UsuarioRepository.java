package com.github.andrepenteado.apsso.backend.repositories;

import com.github.andrepenteado.apsso.backend.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
}
