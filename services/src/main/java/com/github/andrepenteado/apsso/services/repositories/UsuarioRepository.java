package com.github.andrepenteado.apsso.services.repositories;

import com.github.andrepenteado.apsso.services.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
}
