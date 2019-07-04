package com.gitlab.andrepenteado.portal;

/*
 * Autor: André Penteado
 * Data: 03/01/2018
 */

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class MDCLogFilter implements Filter {

    public void destroy() {}

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) {
        try {
            HttpServletRequest httpReq = (HttpServletRequest) req;
            SecurityContextImpl sci = null;
            if (httpReq.getSession(false) != null) {
                sci = (SecurityContextImpl) (httpReq).getSession(false)
                        .getAttribute("SPRING_SECURITY_CONTEXT");
            }
            User userLogin = null;
            if (sci != null) {
                userLogin = (User)sci.getAuthentication().getPrincipal();
            }
            if (userLogin != null) {
                MDC.put("USER", userLogin.getUsername());
                MDC.put("IP", req.getRemoteAddr());
            }
            chain.doFilter(req, resp);
        } catch (Exception ex) {
            log.error("Erro preenchendo usuário no MDC", ex);
        } finally {
            MDC.clear();
        }
    }

    public void init(FilterConfig config) {}

}
