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
        usuarioEntrada.setNome("a");
        usuarioEntrada.setEmail("a@a.com");


        // Setup: Criar um item para testar
        Item itemOriginal = new Item();
        itemOriginal.setNome("Item Teste Original");
        itemOriginal.setDescricao("Descrição do item teste");
        itemOriginal.setQuantidade(50);
        itemOriginal.setCategoria("Teste");
        itemOriginal.setLocalizacao("Prateleira T1");
        itemOriginal.setDataEntrada(new Date(System.currentTimeMillis()));
        itemOriginal.setUsuarioEntradaId(9);

        // Adiciona o item ao banco
        boolean itemAdicionado = itemService.adicionarItem(itemOriginal);
        Assertions.assertTrue(itemAdicionado, "O item deveria ter sido adicionado");

        // Busca o item recém-criado para obter seu ID



        // Cria os dados atualizados
        Item itemAtualizado = new Item();
        itemAtualizado.setNome("Item Teste Atualizado");
        itemAtualizado.setDescricao("Descrição atualizada");
        itemAtualizado.setQuantidade(itemOriginal.getQuantidade() + 10);
        itemAtualizado.setCategoria("Teste Atualizado");
        itemAtualizado.setLocalizacao("Prateleira T2");
        itemAtualizado.setDataEntrada(new Date(System.currentTimeMillis()));
        itemAtualizado.setUsuarioEntradaId(19);

        // Executa a atualização
        boolean atualizacaoSucesso = itemService.atualizarItem(43, itemAtualizado);
        Assertions.assertTrue(atualizacaoSucesso, "O item deveria ter sido atualizado com sucesso");

        // Verifica se a atualização foi persistida
        Item itemAtualizadoDoBanco = itemService.buscarItemPorId(43);
        Assertions.assertNotNull(itemAtualizadoDoBanco, "Deve encontrar o item atualizado");
        Assertions.assertEquals("Item Teste Atualizado", itemAtualizadoDoBanco.getNome());
        Assertions.assertEquals(60, itemAtualizadoDoBanco.getQuantidade());
        Assertions.assertEquals("Descrição atualizada", itemAtualizadoDoBanco.getDescricao());

    }



    @Test
    public void testAdicionarItemComUsuarioValido() throws SQLException {
        // Setup: Criar usuário
        Usuario usuario = new Usuario();
        usuario.setNome("a");
        usuario.setEmail("a@a.com");


        // Criar item
        Item item = new Item();
        item.setNome("Novo Item de Teste");
        item.setDescricao("Item para testar adição");
        item.setQuantidade(100);
        item.setCategoria("Teste");
        item.setLocalizacao("Local Teste");
        item.setDataEntrada(new Date(System.currentTimeMillis()));
        item.setUsuarioEntradaId(9);

        boolean resultado = itemService.adicionarItem(item);
        Assertions.assertTrue(resultado, "Deveria adicionar item com usuário válido");



    }
    @Test
    public void testRetirarItem() throws SQLException {
        // Criar item
        Item item = new Item();
        item.setNome("Novo Item de Teste");
        item.setDescricao("Item para testar adição");
        item.setQuantidade(100);
        item.setCategoria("Teste");
        item.setLocalizacao("Local Teste");
        item.setDataEntrada(new Date(System.currentTimeMillis()));
        item.setUsuarioEntradaId(9);

        boolean itemAdicionado = itemService.adicionarItem(item);
        Assertions.assertTrue(itemAdicionado, "Deveria adicionar retirar item com sucesso");
        boolean resultado = itemService.retirarItem(43,9,2);
        Assertions.assertTrue(resultado, "Deveria adicionar retirar item com sucesso");

        itemService.deletarItem(44);
    }


}