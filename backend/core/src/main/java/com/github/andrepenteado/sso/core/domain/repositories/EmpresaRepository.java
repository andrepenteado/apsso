package com.github.andrepenteado.sso.core.domain.repositories;

import com.github.andrepenteado.sso.core.domain.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    List<Empresa> findAllByOrderByRazaoSocialAsc();

    @Query("SELECT c.cargo.empresa FROM Colaborador c WHERE c.cpf = :cpf")
    List<Empresa> listarPorCpfColaborador(@Param("cpf") Long cpf);

    Empresa findByCnpj(Long cnpj);

}
