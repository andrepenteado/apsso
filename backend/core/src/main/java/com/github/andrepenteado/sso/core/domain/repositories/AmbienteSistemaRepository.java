package com.github.andrepenteado.sso.core.domain.repositories;

import com.github.andrepenteado.sso.core.domain.entities.AmbienteSistema;
import com.github.andrepenteado.sso.core.domain.entities.Sistema;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmbienteSistemaRepository extends JpaRepository<AmbienteSistema, String> {

    List<AmbienteSistema> findByOrderBySistema();

    List<AmbienteSistema> findBySistemaIdOrderById(Long idSistema);

}
