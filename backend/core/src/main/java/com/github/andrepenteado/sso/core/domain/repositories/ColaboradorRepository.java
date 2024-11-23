package com.github.andrepenteado.sso.core.domain.repositories;

import com.github.andrepenteado.sso.core.domain.entities.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

    List<Colaborador> findAllByOrderByNomeAsc();

    Colaborador findByCpf(Long cpf);

}
