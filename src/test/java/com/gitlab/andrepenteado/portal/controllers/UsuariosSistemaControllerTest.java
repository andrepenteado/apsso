package com.gitlab.andrepenteado.portal.controllers;

import com.gitlab.andrepenteado.portal.entities.Usuario;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuariosSistemaControllerTest {

    @Autowired
    private MockMvc mvc;

    @WithMockUser("admin")
    @Test
    public void pesquisar() throws Exception {
        mvc.perform(get("/usuarios"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(view().name("usuarios/pesquisar"));
    }

    @WithMockUser("admin")
    @Test
    public void incluirUsuario() throws Exception {
        mvc.perform(get("/usuarios/incluir"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(view().name("usuarios/cadastro"));
    }

    @WithMockUser("admin")
    @Test
    public void editarUsuario() throws Exception {
        mvc.perform(get("/usuarios/editar/1"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(view().name("usuarios/cadastro"));
    }

    @WithMockUser("admin")
    @Test
    public void meusDados() throws Exception {
        mvc.perform(get("/usuarios/meusdados"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(view().name("usuarios/cadastro"));
    }

    @WithMockUser("admin")
    @Test
    public void gravar() throws Exception {
        Usuario usuario = new Usuario();
        mvc.perform(post("/usuarios/gravar")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .sessionAttr("usuario", usuario)
                        .param("txt_nova_senha", "")
                        .param("login", "teste")
                        .param("nome", "Usuário de teste"))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(view().name("usuarios/cadastro"));
    }

    @WithMockUser("admin")
    @Test
    public void excluirUsuario() throws Exception {
        mvc.perform(get("/usuarios/excluir/1"))
           .andDo(print())
           .andExpect(status().isFound())
           .andExpect(redirectedUrl("/usuarios"));
    }
}