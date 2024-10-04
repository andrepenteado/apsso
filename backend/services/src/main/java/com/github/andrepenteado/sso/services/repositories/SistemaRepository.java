package com.github.andrepenteado.sso.services.repositories;

import com.github.andrepenteado.sso.services.entities.Sistema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SistemaRepository extends JpaRepository<Sistema, Long> {
}
