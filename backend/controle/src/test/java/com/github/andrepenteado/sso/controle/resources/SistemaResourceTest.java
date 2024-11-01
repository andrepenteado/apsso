package com.github.andrepenteado.sso.controle.resources;

import br.unesp.fc.andrepenteado.core.web.dto.UserLogin;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.andrepenteado.sso.controle.ControleApplication;
import com.github.andrepenteado.sso.core.domain.entities.AmbienteSistema;
import com.github.andrepenteado.sso.core.domain.entities.Empresa;
import com.github.andrepenteado.sso.core.domain.entities.PerfilSistema;
import com.github.andrepenteado.sso.core.domain.entities.Sistema;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
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

    private final String DESCRICAO_SISTEMA = "Sistema de testes ABC";

    private final String DESCRICAO_PERFIL = "Perfil de sistema ABC";

    private final String DESCRICAO_AMBIENTE = "Ambiente de sistema ABC";

    private Sistema getSistema(Long id) {
        Empresa empresa = new Empresa();
        empresa.setId(10L);
        empresa.setRazaoSocial("Empresa testes");
        empresa.setCnpj(111111111L);
        empresa.setTelefone("5555555555");

        Sistema sistema = new Sistema();
        sistema.setId(id);
        sistema.setNome("Sistema ABC");
        sistema.setDescricao(DESCRICAO_SISTEMA);
        sistema.setEmpresa(empresa);
        return sistema;
    }

    private String getJsonSistema(Long id) throws Exception {
        return objectMapper.writeValueAsString(getSistema(id));
    }

    private PerfilSistema getPerfilSistema(Long id) {
        PerfilSistema perfilSistema = new PerfilSistema();
        perfilSistema.setAuthority("ROLE_Sistema1000_ABC");
        perfilSistema.setSistema(getSistema(100L));
        perfilSistema.setDescricao("Perfil de sistema ABC");
        return perfilSistema;
    }

    private String getJsonPerfilSistema(Long id) throws Exception {
        return objectMapper.writeValueAsString(getPerfilSistema(id));
    }

    private Map<String, String> getPerfil() {
        Map<String, String> perfil = new HashMap<>();
        perfil.put(ControleApplication.PERFIL_ARQUITETO, "Arquiteto do Sistema");
        return perfil;
    }

    private AmbienteSistema getAmbiente(String id) {
        AmbienteSistema ambienteSistema = new AmbienteSistema();
        ambienteSistema.setId(id);
        ambienteSistema.setDescricao(DESCRICAO_AMBIENTE);
        ambienteSistema.setUrlAcesso("http://localhost");
        ambienteSistema.setClientSecret("password");
        return ambienteSistema;
    }

    private String getJsonAmbiente(String id) throws Exception {
        return objectMapper.writeValueAsString(getAmbiente(id));
    }

    /*private Jwt getJwt() {
        Jwt.Builder jwtBuilder = Jwt.withTokenValue("token").header("alg", "none")
            .claim(JwtClaimNames.SUB, "user")
            .issuer("https://issuer-host/auth/realms/test")
            .claim("perfis", getPerfil());
        return jwtBuilder.build();
    }*/

    private OAuth2AuthenticationToken getToken() {
        OidcIdToken idToken = new OidcIdToken(
            "tokenValue",
            Instant.now(),
            Instant.now().plusSeconds(60),
            Map.of(
                "login", "arquiteto",
                "nome", "Arquiteto do Sistema",
                "perfis", Map.of(ControleApplication.PERFIL_ARQUITETO, "Arquiteto do Sistema"))
        );

        DefaultOidcUser oidcUser = new DefaultOidcUser(
            List.of(new SimpleGrantedAuthority(ControleApplication.PERFIL_ARQUITETO)),
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
    @DisplayName("Listar todos sistemas")
    void testListar() throws Exception {
        String json = mockMvc.perform(get("/sistemas")
                .with(authentication(getToken()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        List<Sistema> lista = objectMapper.readValue(json, new TypeReference<>() {});
        assertEquals(lista.size(), 3);
    }

    @Test
    @DisplayName("Buscar sistema por ID")
    void testBuscar() throws Exception {
        mockMvc.perform(get("/sistemas/100")
                .with(authentication(getToken()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        mockMvc.perform(get("/sistemas/999")
                .with(authentication(getToken()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Incluir ou alterar sistema")
    void testIncluirOuAlterar() throws Exception {
        String json = mockMvc.perform(post("/sistemas")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonSistema(2000L)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        Sistema sistemaNovo = objectMapper.readValue(json, Sistema.class);
        assertEquals(sistemaNovo.getDescricao(), DESCRICAO_SISTEMA);

        // Sem dados obrigatórios
        mockMvc.perform(post("/sistemas")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Sistema())))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(ex -> assertTrue(Objects.requireNonNull(ex.getResolvedException()).getMessage().contains("é um campo obrigatório")));
    }

    @Test
    @DisplayName("Excluir sistema existente")
    void testExcluir() throws Exception {
        mockMvc.perform(delete("/sistemas/200")
                .with(authentication(getToken()))
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
                .with(authentication(getToken()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        List<PerfilSistema> lista = objectMapper.readValue(json, new TypeReference<>() {});
        assertEquals(lista.size(), 4);
    }

    @Test
    @DisplayName("Incluir perfil de sistema")
    void testIncluirPerfil() throws Exception {
        String json = mockMvc.perform(post("/sistemas/perfil")
                .with(authentication(getToken()))
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
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PerfilSistema())))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(ex -> assertTrue(Objects.requireNonNull(ex.getResolvedException()).getMessage().contains("é um campo obrigatório")));
    }

    @Test
    @DisplayName("Excluir perfil de sistema existente")
    void testExcluirPerfil() throws Exception {
        mockMvc.perform(delete("/sistemas/perfil/ROLE_Sistema01_A")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        /*mockMvc.perform(delete("/sistemas/perfil/9999")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());*/
    }

    @Test
    @DisplayName("Listar ambientes por sistemas")
    void testListarAmbientes() throws Exception {
        String json = mockMvc.perform(get("/sistemas/100/ambientes")
                .with(authentication(getToken()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        List<AmbienteSistema> lista = objectMapper.readValue(json, new TypeReference<>() {});
        assertEquals(lista.size(), 2);
    }

    @Test
    @DisplayName("Incluir ambiente de sistema")
    void testIncluirAmbiente() throws Exception {
        String json = mockMvc.perform(post("/sistemas/ambiente")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getJsonAmbiente(UUID.randomUUID().toString())))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        AmbienteSistema ambienteSistemaNovo = objectMapper.readValue(json, AmbienteSistema.class);
        assertEquals(ambienteSistemaNovo.getDescricao(), DESCRICAO_AMBIENTE);

        // Sem dados obrigatórios
        mockMvc.perform(post("/sistemas/ambiente")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new AmbienteSistema())))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(ex -> assertTrue(Objects.requireNonNull(ex.getResolvedException()).getMessage().contains("é um campo obrigatório")));
    }

    @Test
    @DisplayName("Excluir ambiente de sistema existente")
    void testExcluirAmbiente() throws Exception {
        mockMvc.perform(delete("/sistemas/ambiente/Ambiente02")
                .with(authentication(getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        /*mockMvc.perform(delete("/sistemas/perfil/9999")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());*/
    }
}
