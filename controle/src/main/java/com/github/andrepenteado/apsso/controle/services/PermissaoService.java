package com.github.andrepenteado.apsso.controle.services;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PermissaoService {

    public boolean isPermitido(Map<String, String> perfis) {
        return perfis.containsKey("ROLE_Controle_ARQUITETO");
    }

}
