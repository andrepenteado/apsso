package com.github.andrepenteado.sso.core.domain.repositories;

import com.github.andrepenteado.sso.core.domain.entities.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

    List<Cargo> findAllByOrderByNomeAsc();

    List<Cargo> findByEmpresa_Id(Long idEmpresa);

}
