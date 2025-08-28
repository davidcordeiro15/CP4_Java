package org.example;

import org.example.domain.Usuario;
import org.example.service.UsuarioService;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioServiceTest {

    static UsuarioService usuarioService;

    @BeforeAll
    public static void setup() {
        usuarioService = new UsuarioService();
    }

    @Test
    @Order(1)
    public void testCriarUsuarios() throws SQLException {
        Usuario u1 = new Usuario();
        u1.setNome("Teste João");
        u1.setEmail("teste.joao@email.com");
        usuarioService.adicionarUsuario(u1);

        Usuario u2 = new Usuario();
        u2.setNome("Teste Maria");
        u2.setEmail("teste.maria@email.com");
        usuarioService.adicionarUsuario(u2);

        List<Usuario> lista = usuarioService.listarUsuarios();
        Assertions.assertTrue(lista.stream().anyMatch(u -> u.getNome().equals("Teste João")));
        Assertions.assertTrue(lista.stream().anyMatch(u -> u.getNome().equals("Teste Maria")));
    }

    @Test
    @Order(2)
    public void testAtualizarUsuario() throws SQLException {
        Usuario novoU = new Usuario();
        novoU.setNome("Maria Atualizada");
        novoU.setEmail("maria.atualizada@email.com");

        boolean atualizado = usuarioService.atualizarUsuario("Teste Maria", "teste.maria@email.com", novoU);
        Assertions.assertTrue(atualizado);

        List<Usuario> lista = usuarioService.listarUsuarios();
        Assertions.assertTrue(lista.stream().anyMatch(u -> u.getNome().equals("Maria Atualizada")));
    }

    @Test
    @Order(3)
    public void testDeletarUsuario() throws SQLException {
        boolean deletado = usuarioService.deletarUsuario("Teste João", "teste.joao@email.com");
        Assertions.assertTrue(deletado);

        List<Usuario> lista = usuarioService.listarUsuarios();
        Assertions.assertFalse(lista.stream().anyMatch(u -> u.getNome().equals("Teste João")));
    }
}
