package com.github.andrepenteado.apsso.backend.repositories;

import com.github.andrepenteado.apsso.backend.models.Sistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SistemaRepository extends JpaRepository<Sistema, String> {
}
