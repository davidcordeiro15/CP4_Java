package org.example;

import org.example.domain.Item;
import org.example.domain.Usuario;
import org.example.service.ItemService;
import org.example.service.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

public class ItemServiceTest {

    private ItemService itemService;
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        itemService = new ItemService();
        usuarioService = new UsuarioService();
    }

    @Test
    public void testAtualizarItem() throws SQLException {
        // Setup: Criar um usuário primeiro
        Usuario usuarioEntrada = new Usuario();
        usuarioEntrada.setNome("Teste João");
        usuarioEntrada.setEmail("teste.joao@email.com");
        usuarioService.adicionarUsuario(usuarioEntrada);

        // Setup: Criar um item para testar
        Item itemOriginal = new Item();
        itemOriginal.setNome("Item Teste Original");
        itemOriginal.setDescricao("Descrição do item teste");
        itemOriginal.setQuantidade(50);
        itemOriginal.setCategoria("Teste");
        itemOriginal.setLocalizacao("Prateleira T1");
        itemOriginal.setDataEntrada(new Date(System.currentTimeMillis()));
        itemOriginal.setUsuarioEntradaEmail(usuarioEntrada.getEmail());

        // Adiciona o item ao banco
        boolean itemAdicionado = itemService.adicionarItem(itemOriginal);
        Assertions.assertTrue(itemAdicionado, "O item deveria ter sido adicionado");

        // Busca o item recém-criado para obter seu ID
        List<Item> itens = itemService.buscarItensPorNome("Item Teste Original");
        Assertions.assertFalse(itens.isEmpty(), "Deve encontrar o item criado");

        Item itemParaAtualizar = itens.get(0);
        int itemId = itemParaAtualizar.getId();

        // Cria os dados atualizados
        Item itemAtualizado = new Item();
        itemAtualizado.setNome("Item Teste Atualizado");
        itemAtualizado.setDescricao("Descrição atualizada");
        itemAtualizado.setQuantidade(itemParaAtualizar.getQuantidade() + 10);
        itemAtualizado.setCategoria("Teste Atualizado");
        itemAtualizado.setLocalizacao("Prateleira T2");
        itemAtualizado.setDataEntrada(new Date(System.currentTimeMillis()));
        itemAtualizado.setUsuarioEntradaEmail(usuarioEntrada.getEmail());

        // Executa a atualização
        boolean atualizacaoSucesso = itemService.atualizarItem(itemId, itemAtualizado);
        Assertions.assertTrue(atualizacaoSucesso, "O item deveria ter sido atualizado com sucesso");

        // Verifica se a atualização foi persistida
        Item itemAtualizadoDoBanco = itemService.buscarItemPorId(itemId);
        Assertions.assertNotNull(itemAtualizadoDoBanco, "Deve encontrar o item atualizado");
        Assertions.assertEquals("Item Teste Atualizado", itemAtualizadoDoBanco.getNome());
        Assertions.assertEquals(60, itemAtualizadoDoBanco.getQuantidade());
        Assertions.assertEquals("Descrição atualizada", itemAtualizadoDoBanco.getDescricao());
    }

    @Test
    public void testAtualizarItemInexistente() throws SQLException {
        Item itemInexistente = new Item();
        itemInexistente.setNome("Item Inexistente");

        boolean resultado = itemService.atualizarItem(9999, itemInexistente);
        Assertions.assertFalse(resultado, "Não deveria atualizar item inexistente");
    }

    @Test
    public void testAdicionarItemComUsuarioValido() throws SQLException {
        // Setup: Criar usuário
        Usuario usuario = new Usuario();
        usuario.setNome("Usuario Teste");
        usuario.setEmail("usuario.teste@email.com");
        usuarioService.adicionarUsuario(usuario);

        // Criar item
        Item item = new Item();
        item.setNome("Novo Item de Teste");
        item.setDescricao("Item para testar adição");
        item.setQuantidade(100);
        item.setCategoria("Teste");
        item.setLocalizacao("Local Teste");
        item.setDataEntrada(new Date(System.currentTimeMillis()));
        item.setUsuarioEntradaEmail(usuario.getNome());

        boolean resultado = itemService.adicionarItem(item);
        Assertions.assertTrue(resultado, "Deveria adicionar item com usuário válido");

        // Verificar se foi realmente adicionado
        List<Item> itensEncontrados = itemService.buscarItensPorNome("Novo Item de Teste");
        Assertions.assertFalse(itensEncontrados.isEmpty(), "Deve encontrar o item adicionado");
        Assertions.assertEquals(usuario.getId(), itensEncontrados.get(0).getUsuarioEntradaEmail());
    }
}