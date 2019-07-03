package com.gitlab.andrepenteado.sso.repositories;

import com.gitlab.andrepenteado.sso.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {

    Usuario findUsuarioByLogin(String login);

}
