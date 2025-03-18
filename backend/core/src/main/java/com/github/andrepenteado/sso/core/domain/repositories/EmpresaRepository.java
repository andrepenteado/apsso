package com.github.andrepenteado.sso.core.domain.repositories;

import br.unesp.fc.andrepenteado.core.upload.Upload;
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

    @Query("SELECT u FROM Upload u WHERE u.uuid = (SELECT e.logotipo FROM Empresa e WHERE e.urlLogin = :urlLogin)")
    Upload buscarLogotipoEmpresaPorUrlLogin(@Param("urlLogin") String urlLogin);

}
