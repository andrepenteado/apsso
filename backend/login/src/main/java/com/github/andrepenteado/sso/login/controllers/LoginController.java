package com.github.andrepenteado.sso.login.controllers;

import br.unesp.fc.andrepenteado.core.upload.Upload;
import com.github.andrepenteado.sso.core.domain.repositories.EmpresaRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final EmpresaRepository empresaRepository;

    @RequestMapping("/login")
    public String customLogin(Model model, HttpServletRequest request) {
        String url = request.getRequestURL().toString().replaceFirst(request.getRequestURI() + "$", "");

        log.info("Acessando login URL: {}", url);;

        Upload logotipo = empresaRepository.buscarLogotipoEmpresaPorUrlLogin(url);
        if (logotipo != null)
            model.addAttribute("logotipo", logotipo);

        return "login";
    }

    @RequestMapping("/logout")
    public RedirectView logout(@RequestParam("redirectUrl") String redirectUrl, HttpServletRequest request) throws IOException {
        request.getSession().invalidate();
        new SecurityContextLogoutHandler().logout(request, null, null);
        return new RedirectView(redirectUrl);
    }

}
