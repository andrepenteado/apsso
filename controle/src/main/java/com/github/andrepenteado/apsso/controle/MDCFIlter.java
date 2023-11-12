package com.github.andrepenteado.apsso.controle;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class MDCFIlter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var principal = request.getUserPrincipal();
        var ip = request.getRemoteAddr();

        if (principal != null)
            MDC.put("USUARIO", principal.getName());
        else
            MDC.put("USUARIO", "anônimo");

        if (ip != null)
            MDC.put("IP", ip);
        else
            MDC.put("IP", "0.0.0.0");

        try {
            filterChain.doFilter(request, response);
        }
        finally {
            MDC.remove("USUARIO");
            MDC.remove("IP");
        }
    }

}
