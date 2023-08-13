package com.github.andrepenteado.apsso.backend.repositories;

import com.github.andrepenteado.apsso.backend.models.PerfilSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfilSistemaRepository extends JpaRepository<PerfilSistema, Long> {

    List<PerfilSistema> findByOrderBySistemaId();

    List<PerfilSistema> findBySistemaIdOrderByAuthority(String idSistema);

}
