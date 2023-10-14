package com.github.andrepenteado.apsso.services.dto;

import java.util.Map;

public record UserLogin(
    String login,
    String nome,
    Map<String, String> perfis
) {
}
