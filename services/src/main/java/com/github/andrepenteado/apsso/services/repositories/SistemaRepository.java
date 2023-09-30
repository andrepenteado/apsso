package com.github.andrepenteado.apsso.services.repositories;

import com.github.andrepenteado.apsso.services.models.Sistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SistemaRepository extends JpaRepository<Sistema, String> {
}
