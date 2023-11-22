package com.github.andrepenteado.sso.services.repositories;

import com.github.andrepenteado.sso.services.entities.Sistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SistemaRepository extends JpaRepository<Sistema, String> {
}
