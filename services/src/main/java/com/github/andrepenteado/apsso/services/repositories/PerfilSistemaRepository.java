package com.github.andrepenteado.apsso.services.repositories;

import com.github.andrepenteado.apsso.services.models.PerfilSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfilSistemaRepository extends JpaRepository<PerfilSistema, String> {

    List<PerfilSistema> findByOrderBySistemaId();

    List<PerfilSistema> findBySistemaIdOrderByAuthority(String idSistema);

    PerfilSistema findByAuthority(String authority);

}
