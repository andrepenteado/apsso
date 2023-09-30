package com.github.andrepenteado.apsso.services;

import org.springframework.validation.BindingResult;

public class Util {

    public static String validateModel(BindingResult validacao) {
        String result = null;
        if (validacao.hasErrors()) {
            final StringBuilder errosFinal = new StringBuilder();
            validacao.getFieldErrors().forEach(msg -> {
                errosFinal.append(msg.getDefaultMessage());
            });
            result = errosFinal.toString();
        }
        return result;
    }

}
