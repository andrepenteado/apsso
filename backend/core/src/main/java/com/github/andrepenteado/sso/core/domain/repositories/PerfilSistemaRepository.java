package com.github.andrepenteado.sso.core.domain.repositories;

import com.github.andrepenteado.sso.core.domain.entities.PerfilSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfilSistemaRepository extends JpaRepository<PerfilSistema, String> {

    List<PerfilSistema> findByOrderBySistemaId();

    List<PerfilSistema> findBySistemaIdOrderByAuthority(Long idSistema);

    PerfilSistema findByAuthority(String authority);

}
