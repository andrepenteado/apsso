package com.github.andrepenteado.sso.controle.resources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.andrepenteado.sso.services.entities.PerfilSistema;
import com.github.andrepenteado.sso.services.entities.Sistema;
import com.github.andrepenteado.sso.services.entities.Usuario;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testes do resource {@link UsuarioResource}
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class
})
@DatabaseSetup("/datasets/usuario.xml")
@Transactional
@ActiveProfiles("test")
class UsuarioResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String USERNAME = "usuarioTeste01";

    private final String NOME = "Usuario Teste";

    private Usuario getUsuario(String username) {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setNome(NOME);
        usuario.setEnabled(true);
        usuario.setDataCadastro(LocalDateTime.now());
        usuario.setPassword("{bcrypt}$2a$10$ihm/8JoWWmtRakCeI7eT2e5tyTFRVR2mVkvdukKK47SKTW1k210qy");
        usuario.setPerfis(new ArrayList<>());

        Sistema sistema = new Sistema();
        sistema.setId("Sistema01");
        sistema.setDescricao("Sistema Sistema01");

        PerfilSistema perfilSistema = new PerfilSistema();
        perfilSistema.setAuthority("ROLE_Sistema01_Perfil01");
        perfilSistema.setSistema(sistema);
        perfilSistema.setDescricao("Perfil Teste");

        usuario.getPerfis().add(perfilSistema);

        return usuario;
    }

    private String getJsonUsuario(String username) throws Exception {
        return objectMapper.writeValueAsString(getUsuario(username));
    }

    private Map<String, String> getPerfil() {
        Map<String, String> perfil = new HashMap<>();
        perfil.put("ROLE_Controle_ARQUITETO", "Arquiteto do Sistema");
        return perfil;
    }

    @Test
    @DisplayName("Listar todos usuários")
    void testListar() throws Exception {
        String json = mockMvc.perform(get("/usuarios")
                .with(oidcLogin()
                    .idToken(token -> token.claim("perfis", getPerfil())))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        List<Usuario> lista = objectMapper.readValue(json, new TypeReference<List<Usuario>>() {});
        assertEquals(lista.size(), 2);
    }

    @Test
    @DisplayName("Buscar usuário por username")
    void testBuscar() throws Exception {
        String json = mockMvc.perform(get("/usuarios/teste01")
                .with(oidcLogin()
                    .idToken(token -> token.claim("perfis", getPerfil())))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        Usuario usuario = objectMapper.readValue(json, new TypeReference<Usuario>() {});
        assertEquals(usuario.getPerfis().size(), 1);

        mockMvc.perform(get("/usuarios/usuarioNaoExiste")
                .with(oidcLogin()
                    .idToken(token -> token.claim("perfis", getPerfil())))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Incluir usuário")
    void testIncluir() throws Exception {
        String json = mockMvc.perform(post("/usuarios")
                .with(oidcLogin()
                    .idToken(token -> token.claim("perfis", getPerfil())))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonUsuario(USERNAME)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        Usuario usuarioNovo = objectMapper.readValue(json, Usuario.class);
        assertEquals(usuarioNovo.getNome(), NOME);

        // Sem dados obrigatórios
        mockMvc.perform(post("/usuarios")
                .with(oidcLogin()
                    .idToken(token -> token.claim("perfis", getPerfil())))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Usuario())))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(ex -> assertTrue(ex.getResolvedException().getMessage().contains("é um campo obrigatório")));
    }

    @Test
    @DisplayName("Alterar usuário")
    void testAlterar() throws Exception {
        String json = mockMvc.perform(put("/usuarios/teste01")
                .with(oidcLogin()
                    .idToken(token -> token.claim("perfis", getPerfil())))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonUsuario("teste01")))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        Usuario usuarioAlterado = objectMapper.readValue(json, Usuario.class);
        assertEquals(usuarioAlterado.getNome(), NOME);

        mockMvc.perform(put("/usuarios/naoExiste")
                .with(oidcLogin()
                    .idToken(token -> token.claim("perfis", getPerfil())))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonUsuario("naoExiste")))
            .andExpect(status().isNotFound())
            .andExpect(ex -> assertTrue(ex.getResolvedException().getMessage().contains("não encontrado")));

        mockMvc.perform(put("/usuarios/dadosIncompletos")
                .with(oidcLogin()
                    .idToken(token -> token.claim("perfis", getPerfil())))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Usuario())))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(ex -> assertTrue(ex.getResolvedException().getMessage().contains("é um campo obrigatório")));
    }

    @Test
    @DisplayName("Excluir usuário")
    void testExcluir() throws Exception {
        mockMvc.perform(delete("/usuarios/teste02")
                .with(oidcLogin()
                    .idToken(token -> token.claim("perfis", getPerfil())))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        /*mockMvc.perform(delete("/usuarios/naoExiste")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());*/
    }
}