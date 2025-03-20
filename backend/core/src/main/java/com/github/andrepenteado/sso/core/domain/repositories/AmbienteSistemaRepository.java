package com.github.andrepenteado.sso.core.domain.repositories;

import com.github.andrepenteado.sso.core.domain.entities.AmbienteSistema;
import com.github.andrepenteado.sso.core.domain.entities.Sistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmbienteSistemaRepository extends JpaRepository<AmbienteSistema, String> {

    List<AmbienteSistema> findByOrderBySistema();

    List<AmbienteSistema> findBySistemaIdOrderById(Long idSistema);

    @Query("""
       SELECT a
       FROM AmbienteSistema a
       WHERE EXISTS (
          SELECT 1
          FROM Usuario u
          JOIN u.perfis p
          WHERE p.sistema = a.sistema
          AND u.username = :username
       )
    """)
    List<AmbienteSistema> pesquisarPorUsuario(@Param("username") String username);

}
