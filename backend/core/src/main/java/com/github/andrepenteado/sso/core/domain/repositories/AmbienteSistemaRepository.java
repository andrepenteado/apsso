package com.github.andrepenteado.sso.core.domain.repositories;

import com.github.andrepenteado.sso.core.domain.entities.AmbienteSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmbienteSistemaRepository extends JpaRepository<AmbienteSistema, String> {

    List<AmbienteSistema> findBySistemaIdOrderById(Long idSistema);

}
