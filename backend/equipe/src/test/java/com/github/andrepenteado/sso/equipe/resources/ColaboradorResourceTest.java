package com.github.andrepenteado.sso.equipe.resources;

import br.unesp.fc.andrepenteado.core.web.dto.UserLogin;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.andrepenteado.sso.core.domain.entities.Cargo;
import com.github.andrepenteado.sso.core.domain.entities.Colaborador;
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
 * Testes do resource {@link ColaboradorResource}
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class
})
@DatabaseSetup("/datasets/colaborador.xml")
@Transactional
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ColaboradorResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String NOME = "Colaborador de testes";

    private final Long CPF = 12345678901L;

    private Empresa getEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setId(10L);
        empresa.setDataCadastro(LocalDateTime.now());
        empresa.setRazaoSocial("Empresa testes");
        empresa.setCnpj(123123123000112L);
        empresa.setTelefone("123123123");
        return empresa;
    }

    private UnidadeAdministrativa getUnidadeAdministrativa() {
        UnidadeAdministrativa unidadeAdministrativa = new UnidadeAdministrativa();
        unidadeAdministrativa.setId(10L);
        unidadeAdministrativa.setNome(NOME);
        unidadeAdministrativa.setEmpresa(getEmpresa());
        unidadeAdministrativa.setTipo(TipoUnidadeAdministrativa.DIRETORIA);
        return unidadeAdministrativa;
    }

    private Cargo getCargo() {
        Cargo cargo = new Cargo();
        cargo.setId(10L);
        cargo.setNome(NOME);
        cargo.setEmpresa(getEmpresa());
        return cargo;
    }

    private Colaborador getColaborador(Long id) {
        Colaborador colaborador = new Colaborador();
        if (id != null)
            colaborador.setId(id);
        colaborador.setDataCadastro(LocalDateTime.now());
        colaborador.setUsuarioCadastro("admin");
        colaborador.setNome(NOME);
        colaborador.setCpf(CPF);
        colaborador.setTelefone("123123123");
        colaborador.setUnidadeAdministrativa(getUnidadeAdministrativa());
        colaborador.setCargo(getCargo());
        return colaborador;
    }

    private String getJsonColaborador(Long id) throws Exception {
        return objectMapper.writeValueAsString(getColaborador(id));
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
    @DisplayName("Listar todos colaboradores")
    void testListar() throws Exception {
        String json = mockMvc.perform(get("/colaboradores")
                .with(authentication(getToken()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        List<Colaborador> lista = objectMapper.readValue(json, new TypeReference<List<Colaborador>>() {});
        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("Buscar colaborador por ID")
    void testBuscar() throws Exception {
        mockMvc.perform(get("/colaboradores/100")
                .with(authentication(getToken()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        mockMvc.perform(get("/colaboradores/999")
                .with(authentication(getToken()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Incluir novo colaborador")
    void testIncluir() throws Exception {
        String json = mockMvc.perform(post("/colaboradores")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonColaborador(null)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        Colaborador colaboradorNovo = objectMapper.readValue(json, Colaborador.class);
        assertEquals(NOME, colaboradorNovo.getNome());
        assertNotNull(colaboradorNovo.getId());

        // Sem dados obrigatórios
        mockMvc.perform(post("/colaboradores")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Colaborador())))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(ex -> assertTrue(Objects.requireNonNull(ex.getResolvedException()).getMessage().contains("é um campo obrigatório")));

        // CPF duplicado
        mockMvc.perform(post("/colaboradores")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonColaborador(-1L)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(ex -> assertTrue(Objects.requireNonNull(ex.getResolvedException()).getMessage().contains("já se encontra cadastrado")));
    }

    @Test
    @DisplayName("Alterar colaborador existente")
    void testAlterar() throws Exception {
        String json = mockMvc.perform(put("/colaboradores/100")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonColaborador(100L)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        Colaborador colaboradorAlterado = objectMapper.readValue(json, Colaborador.class);
        assertEquals(NOME, colaboradorAlterado.getNome());
        assertEquals(100, colaboradorAlterado.getId());

        mockMvc.perform(put("/colaboradores/999")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonColaborador(999L)))
            .andExpect(status().isNotFound())
            .andExpect(ex -> assertTrue(Objects.requireNonNull(ex.getResolvedException()).getMessage().contains("não encontrado")));

        mockMvc.perform(put("/colaboradores/100")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonColaborador(300L)))
            .andExpect(status().isConflict())
            .andExpect(ex -> assertTrue(Objects.requireNonNull(ex.getResolvedException()).getMessage().contains("porém enviado dados do colaborador")));

        mockMvc.perform(put("/colaboradores/100")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Colaborador())))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(ex -> assertTrue(Objects.requireNonNull(ex.getResolvedException()).getMessage().contains("é um campo obrigatório")));
    }

    @Test
    @DisplayName("Excluir colaborador existente")
    void testExcluir() throws Exception {
        mockMvc.perform(delete("/colaboradores/200")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

}
