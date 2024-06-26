package com.github.andrepenteado.sso.controle.resources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.andrepenteado.sso.services.entities.PerfilSistema;
import com.github.andrepenteado.sso.services.entities.Sistema;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testes do resource {@link SistemaResource}
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class
})
@DatabaseSetup("/datasets/sistema.xml")
@Transactional
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class SistemaResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String ID_SISTEMA = "SistemaNovo";

    private final String DESCRICAO_PERFIL = "Perfil de sistema ABC";

    private Sistema getSistema(String id) {
        Sistema sistema = new Sistema();
        sistema.setId(id);
        sistema.setDescricao("Descrição do sistema " + id);
        sistema.setUrlEntrada("http://localhost");
        return sistema;
    }

    private String getJsonSistema(String id) throws Exception {
        return objectMapper.writeValueAsString(getSistema(id));
    }

    private PerfilSistema getPerfilSistema(Long id) {
        PerfilSistema perfilSistema = new PerfilSistema();
        perfilSistema.setAuthority("ROLE_Sistema01_ABC");
        perfilSistema.setSistema(getSistema("Sistema01"));
        perfilSistema.setDescricao("Perfil de sistema ABC");
        return perfilSistema;
    }

    private String getJsonPerfilSistema(Long id) throws Exception {
        return objectMapper.writeValueAsString(getPerfilSistema(id));
    }

    private Map<String, String> getPerfil() {
        Map<String, String> perfil = new HashMap<>();
        perfil.put("ROLE_com.github.andrepenteado.sso.controle_ARQUITETO", "Arquiteto do Sistema");
        return perfil;
    }

    private Jwt getJwt() {
        Jwt.Builder jwtBuilder = Jwt.withTokenValue("token").header("alg", "none")
            .claim(JwtClaimNames.SUB, "user")
            .issuer("https://issuer-host/auth/realms/test")
            .claim("perfis", getPerfil());
        return jwtBuilder.build();
    }

    @Test
    @DisplayName("Listar todos sistemas")
    void testListar() throws Exception {
        String json = mockMvc.perform(get("/sistemas")
                .with(jwt().jwt(getJwt()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        List<Sistema> lista = objectMapper.readValue(json, new TypeReference<List<Sistema>>() {});
        assertEquals(lista.size(), 3);
    }

    @Test
    @DisplayName("Buscar sistema por ID")
    void testBuscar() throws Exception {
        mockMvc.perform(get("/sistemas/Sistema01")
                .with(jwt().jwt(getJwt()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        mockMvc.perform(get("/sistemas/SistemaNaoExiste")
                .with(jwt().jwt(getJwt()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Incluir ou alterar sistema")
    void testIncluirOuAlterar() throws Exception {
        String json = mockMvc.perform(post("/sistemas")
                .with(jwt().jwt(getJwt()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonSistema(ID_SISTEMA)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        Sistema sistemaNovo = objectMapper.readValue(json, Sistema.class);
        assertEquals(sistemaNovo.getId(), ID_SISTEMA);

        // Sem dados obrigatórios
        mockMvc.perform(post("/sistemas")
                .with(jwt().jwt(getJwt()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Sistema())))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(ex -> assertTrue(ex.getResolvedException().getMessage().contains("é um campo obrigatório")));
    }

    @Test
    @DisplayName("Excluir sistema existente")
    void testExcluir() throws Exception {
        mockMvc.perform(delete("/sistemas/Sistema02")
                .with(jwt().jwt(getJwt()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        /*mockMvc.perform(delete("/sistemas/SistemaNaoExiste")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());*/
    }

    @Test
    @DisplayName("Listar perfis de todos sistemas")
    void testListarPerfis() throws Exception {
        String json = mockMvc.perform(get("/sistemas/perfis")
                .with(jwt().jwt(getJwt()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        List<PerfilSistema> lista = objectMapper.readValue(json, new TypeReference<List<PerfilSistema>>() {});
        assertEquals(lista.size(), 4);
    }

    @Test
    @DisplayName("Incluir perfil de sistema")
    void testIncluirPerfil() throws Exception {
        String json = mockMvc.perform(post("/sistemas/perfil")
                .with(jwt().jwt(getJwt()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonPerfilSistema(-1L)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        PerfilSistema perfilSistemaNovo = objectMapper.readValue(json, PerfilSistema.class);
        assertEquals(perfilSistemaNovo.getDescricao(), DESCRICAO_PERFIL);

        // Sem dados obrigatórios
        mockMvc.perform(post("/sistemas/perfil")
                .with(jwt().jwt(getJwt()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PerfilSistema())))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(ex -> assertTrue(ex.getResolvedException().getMessage().contains("é um campo obrigatório")));
    }

    @Test
    @DisplayName("Excluir perfil de sistema existente")
    void testExcluirPerfil() throws Exception {
        mockMvc.perform(delete("/sistemas/perfil/ROLE_Sistema01_A")
                .with(jwt().jwt(getJwt()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        /*mockMvc.perform(delete("/sistemas/perfil/9999")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());*/
    }
}
