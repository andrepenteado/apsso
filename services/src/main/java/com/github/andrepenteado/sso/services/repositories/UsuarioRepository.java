package com.github.andrepenteado.sso.services.repositories;

import com.github.andrepenteado.sso.services.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
}
