package com.github.andrepenteado.sso.core.domain.repositories;

import com.github.andrepenteado.sso.core.domain.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
}
