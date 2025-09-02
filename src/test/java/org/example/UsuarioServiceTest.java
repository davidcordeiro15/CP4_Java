package org.example;

import org.example.domain.Usuario;
import org.example.service.UsuarioService;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioServiceTest {

    static UsuarioService usuarioService;

    @BeforeAll
    public static void setup() {
        usuarioService = new UsuarioService();
    }
    @Test
    @Order(1)
    public void testCriarUsuario() throws SQLException {
        // Criar um usuário de teste
        Usuario usuarioTeste = new Usuario();
        usuarioTeste.setNome("a23");
        usuarioTeste.setEmail("a23@exemplo.com");

        // Tentar adicionar o usuário
        Usuario usuarioCriado = usuarioService.adicionarUsuario(usuarioTeste);

        // Verificar se o usuário foi criado com sucesso
        assertEquals(usuarioCriado, usuarioTeste, "Usuário deveria ter sido criado com sucesso");

        // Verificar se o ID foi gerado
        Assertions.assertTrue(usuarioCriado.getId() > 0, "Usuário deveria ter um ID válido");

        // Verificar se os dados foram persistidos corretamente
        assertEquals("Usuario Teste", usuarioCriado.getNome(), "Nome deveria ser igual");
        assertEquals("teste.usuario@exemplo.com", usuarioCriado.getEmail(), "Email deveria ser igual");

        // Verificar se o usuário aparece na listagem
        List<Usuario> listaUsuarios = usuarioService.listarUsuarios();
        Assertions.assertTrue(
                listaUsuarios.stream().anyMatch(u ->
                        u.getEmail().equals("teste.usuario@exemplo.com") &&
                                u.getNome().equals("Usuario Teste")),
                "Usuário criado deveria aparecer na listagem"
        );

        System.out.println("Usuário criado com ID: " + usuarioCriado.getId());
    }
    @Test
    @Order(2)
    public void testListarUsuarios() throws SQLException {
        List<Usuario> lista = usuarioService.listarUsuarios();
        Assertions.assertNotNull(lista, "A lista de usuários não deveria ser nula");
        Assertions.assertFalse(lista.isEmpty(), "A lista de usuários não deveria estar vazia");
    }

    /*@Test
    @Order(3)
    public void testDeletarUsuario() throws SQLException {
        // Primeiro buscar o usuário criado no teste 1
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        Usuario usuarioParaDeletar = usuarios.stream()
                .filter(u -> u.getEmail().equals("teste.usuario@exemplo.com"))
                .findFirst();

        Assertions.assertTrue(usuarioParaDeletar, "Usuário de teste deveria existir");

        // Deletar o usuário
        boolean deletado = usuarioService.deletarUsuario(usuarioParaDeletar.getEmail());
        Assertions.assertTrue(deletado, "Usuário deveria ter sido deletado");

        // Verificar que o usuário não existe mais
        List<Usuario> listaAposDelete = usuarioService.listarUsuarios();
        Assertions.assertFalse(
                listaAposDelete.stream().anyMatch(u -> u.getEmail().equals("teste.usuario@exemplo.com")),
                "Usuário deletado não deveria mais existir na lista"
        );
    }*/

    @Test
    @Order(4)
    public void testBuscarUsuarioPorId() throws SQLException {


        // Buscar o usuário pelo ID
        Usuario usuarioEncontrado = usuarioService.buscarUsuarioPorEmail("teste@gmail.com");

        Assertions.assertFalse(usuarioEncontrado.getNome().isEmpty(), "Deveria encontrar o usuário pelo ID");
        assertEquals("teste", usuarioEncontrado.getNome());
        assertEquals("teste@gmail.com", usuarioEncontrado.getEmail());

    }


}
