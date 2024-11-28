package com.github.andrepenteado.sso.core.domain.repositories;

import com.github.andrepenteado.sso.core.domain.entities.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

    List<Colaborador> findAllByOrderByNomeAsc();

    @Query("FROM Colaborador c WHERE c.cpf = :cpf AND c.unidadeAdministrativa.empresa.id = :idEmpresa")
    Optional<Colaborador> buscarPorCpfEmpresa(@Param("cpf") Long cpf, @Param("idEmpresa") Long idEmpresa);

}
