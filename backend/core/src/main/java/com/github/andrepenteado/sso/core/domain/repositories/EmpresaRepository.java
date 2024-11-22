package com.github.andrepenteado.sso.core.domain.repositories;

import com.github.andrepenteado.sso.core.domain.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    List<Empresa> findAllByOrderByRazaoSocialAsc();

    Empresa findByCnpj(Long cnpj);

}
