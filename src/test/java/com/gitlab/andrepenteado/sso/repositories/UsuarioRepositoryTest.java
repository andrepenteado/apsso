package com.gitlab.andrepenteado.sso.repositories;

/*
 * Autor: André Penteado
 * Data: 06/02/19 - 23:46
 */

import com.gitlab.andrepenteado.sso.repositories.specs.UsuarioSpecification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void pesquisarPorLogin() {
        assertFalse(usuarioRepository.findAll(UsuarioSpecification.igualLogin("admin")).isEmpty());
        assertTrue(usuarioRepository.findAll(UsuarioSpecification.igualLogin("não-existe")).isEmpty());
    }

    @Test
    public void pesquisarPorNome() {
        assertFalse(usuarioRepository.findAll(UsuarioSpecification.contemNome("inistr")).isEmpty());
        assertTrue(usuarioRepository.findAll(UsuarioSpecification.contemNome("Não existe")).isEmpty());
    }
}
