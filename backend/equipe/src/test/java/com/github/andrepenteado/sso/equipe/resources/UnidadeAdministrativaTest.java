package com.github.andrepenteado.sso.equipe.resources;

import br.unesp.fc.andrepenteado.core.web.dto.UserLogin;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.andrepenteado.sso.core.domain.entities.Empresa;
import com.github.andrepenteado.sso.core.domain.entities.UnidadeAdministrativa;
import com.github.andrepenteado.sso.core.domain.enums.TipoUnidadeAdministrativa;
import com.github.andrepenteado.sso.equipe.EquipeApplication;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testes do resource {@link UnidadeAdministrativaResource}
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class
})
@DatabaseSetup("/datasets/unidade-administrativa.xml")
@Transactional
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UnidadeAdministrativaTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String NOME = "Unidade administrativa de testes";

    private Empresa getEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setId(10L);
        empresa.setDataCadastro(LocalDateTime.now());
        empresa.setRazaoSocial("Empresa testes");
        empresa.setCnpj(123123123000112L);
        empresa.setTelefone("123123123");
        return empresa;
    }

    private UnidadeAdministrativa getUnidadeAdministrativa(Long id) {
        UnidadeAdministrativa unidadeAdministrativa = new UnidadeAdministrativa();
        if (id != null)
            unidadeAdministrativa.setId(id);
        unidadeAdministrativa.setNome(NOME);
        unidadeAdministrativa.setEmpresa(getEmpresa());
        unidadeAdministrativa.setTipo(TipoUnidadeAdministrativa.DIRETORIA);
        return unidadeAdministrativa;
    }

    private String getJsonUnidadeAdministrativa(Long id) throws Exception {
        return objectMapper.writeValueAsString(getUnidadeAdministrativa(id));
    }

    private OAuth2AuthenticationToken getToken() {
        OidcIdToken idToken = new OidcIdToken(
            "tokenValue",
            Instant.now(),
            Instant.now().plusSeconds(60),
            Map.of(
                "login", "arquiteto",
                "nome", "Arquiteto do Sistema",
                "perfis", Map.of(EquipeApplication.PERFIL_GESTOR, "Arquiteto do Sistema"))
        );

        DefaultOidcUser oidcUser = new DefaultOidcUser(
            List.of(new SimpleGrantedAuthority(EquipeApplication.PERFIL_GESTOR)),
            idToken,
            "login"
        );

        UserLogin userLogin = new UserLogin(oidcUser);

        return new OAuth2AuthenticationToken(
            userLogin,
            userLogin.getAuthorities(),
            "com.github.andrepenteado.sso.controle"
        );
    }

    @Test
    @DisplayName("Listar todas unidades administrativas")
    void testListar() throws Exception {
        String json = mockMvc.perform(get("/unidades-administrativas")
                .with(authentication(getToken()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        List<UnidadeAdministrativa> lista = objectMapper.readValue(json, new TypeReference<List<UnidadeAdministrativa>>() {});
        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("Buscar unidade administrativa por ID")
    void testBuscar() throws Exception {
        mockMvc.perform(get("/unidades-administrativas/100")
                .with(authentication(getToken()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        mockMvc.perform(get("/unidades-administrativas/999")
                .with(authentication(getToken()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Incluir nova unidade administrativa")
    void testIncluir() throws Exception {
        String json = mockMvc.perform(post("/unidades-administrativas")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonUnidadeAdministrativa(null)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        UnidadeAdministrativa unidadeAdministrativaNova = objectMapper.readValue(json, UnidadeAdministrativa.class);
        assertEquals(unidadeAdministrativaNova.getNome(), NOME);
        assertNotNull(unidadeAdministrativaNova.getId());

        // Sem dados obrigatórios
        mockMvc.perform(post("/unidades-administrativas")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UnidadeAdministrativa())))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(ex -> assertTrue(Objects.requireNonNull(ex.getResolvedException()).getMessage().contains("é um campo obrigatório")));
    }

    @Test
    @DisplayName("Alterar unidade administrativa existente")
    void testAlterar() throws Exception {
        String json = mockMvc.perform(put("/unidades-administrativas/100")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonUnidadeAdministrativa(100L)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        UnidadeAdministrativa unidadeAdministrativaAlterada = objectMapper.readValue(json, UnidadeAdministrativa.class);
        assertEquals(NOME, unidadeAdministrativaAlterada.getNome());
        assertEquals(100, unidadeAdministrativaAlterada.getId());

        mockMvc.perform(put("/unidades-administrativas/999")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonUnidadeAdministrativa(100L)))
            .andExpect(status().isNotFound())
            .andExpect(ex -> assertTrue(Objects.requireNonNull(ex.getResolvedException()).getMessage().contains("não encontrado")));

        mockMvc.perform(put("/unidades-administrativas/100")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonUnidadeAdministrativa(300L)))
            .andExpect(status().isConflict())
            .andExpect(ex -> assertTrue(Objects.requireNonNull(ex.getResolvedException()).getMessage().contains("porém enviado dados da unidade administrativa")));

        mockMvc.perform(put("/unidades-administrativas/100")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UnidadeAdministrativa())))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(ex -> assertTrue(Objects.requireNonNull(ex.getResolvedException()).getMessage().contains("é um campo obrigatório")));
    }

    @Test
    @DisplayName("Excluir unidade administrativa existente")
    void testExcluir() throws Exception {
        mockMvc.perform(delete("/unidades-administrativas/200")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

}
