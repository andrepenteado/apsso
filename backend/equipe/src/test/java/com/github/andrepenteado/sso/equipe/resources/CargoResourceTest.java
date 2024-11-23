package com.github.andrepenteado.sso.equipe.resources;

import br.unesp.fc.andrepenteado.core.web.dto.UserLogin;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.andrepenteado.sso.core.domain.entities.Cargo;
import com.github.andrepenteado.sso.core.domain.entities.Empresa;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testes do resource {@link CargoResource}
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class
})
@DatabaseSetup("/datasets/cargo.xml")
@Transactional
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class CargoResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String NOME = "Cargo de testes";

    private Empresa getEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setId(10L);
        empresa.setDataCadastro(LocalDateTime.now());
        empresa.setRazaoSocial("Empresa testes");
        empresa.setCnpj(123123123000112L);
        empresa.setTelefone("123123123");
        return empresa;
    }

    private Cargo getCargo(Long id) {
        Cargo cargo = new Cargo();
        if (id != null)
            cargo.setId(id);
        cargo.setNome(NOME);
        cargo.setEmpresa(getEmpresa());
        return cargo;
    }

    private String getJsonCargo(Long id) throws Exception {
        return objectMapper.writeValueAsString(getCargo(id));
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
    @DisplayName("Listar todos cargos")
    void testListar() throws Exception {
        String json = mockMvc.perform(get("/cargos")
                .with(authentication(getToken()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        List<Cargo> lista = objectMapper.readValue(json, new TypeReference<List<Cargo>>() {});
        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("Buscar cargo por ID")
    void testBuscar() throws Exception {
        mockMvc.perform(get("/cargos/100")
                .with(authentication(getToken()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        mockMvc.perform(get("/cargos/999")
                .with(authentication(getToken()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Incluir novo cargo")
    void testIncluir() throws Exception {
        String json = mockMvc.perform(post("/cargos")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonCargo(null)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        Cargo cargoNovo = objectMapper.readValue(json, Cargo.class);
        assertEquals(NOME, cargoNovo.getNome());
        assertNotNull(cargoNovo.getId());

        // Sem dados obrigatórios
        mockMvc.perform(post("/cargos")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Cargo())))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(ex -> assertTrue(Objects.requireNonNull(ex.getResolvedException()).getMessage().contains("é um campo obrigatório")));
    }

    @Test
    @DisplayName("Alterar cargo existente")
    void testAlterar() throws Exception {
        String json = mockMvc.perform(put("/cargos/100")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonCargo(100L)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        Cargo cargoAlterado = objectMapper.readValue(json, Cargo.class);
        assertEquals(NOME, cargoAlterado.getNome());
        assertEquals(100, cargoAlterado.getId());

        mockMvc.perform(put("/cargos/999")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonCargo(100L)))
            .andExpect(status().isNotFound())
            .andExpect(ex -> assertTrue(Objects.requireNonNull(ex.getResolvedException()).getMessage().contains("não encontrado")));

        mockMvc.perform(put("/cargos/100")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonCargo(300L)))
            .andExpect(status().isConflict())
            .andExpect(ex -> assertTrue(Objects.requireNonNull(ex.getResolvedException()).getMessage().contains("porém enviado dados do cargo")));

        mockMvc.perform(put("/cargos/100")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Cargo())))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(ex -> assertTrue(Objects.requireNonNull(ex.getResolvedException()).getMessage().contains("é um campo obrigatório")));
    }

    @Test
    @DisplayName("Excluir cargo existente")
    void testExcluir() throws Exception {
        mockMvc.perform(delete("/cargos/200")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
