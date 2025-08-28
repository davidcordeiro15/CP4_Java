package org.example;

import org.example.domain.Item;
import org.example.service.ItemService;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemServiceTest {

    static ItemService itemService;

    @BeforeAll
    public static void setup() {
        itemService = new ItemService();
    }

    @Test
    @Order(1)
    public void testCriarItens() throws SQLException {
        Item i1 = new Item();
        i1.setNome("Soro Fisiológico");
        i1.setQuantidade(50);
        i1.setDataEntrada(new Date(System.currentTimeMillis()));
        i1.setUsuarioRetirada("Maria Atualizada");
        itemService.adicionarItem(i1);

        Item i2 = new Item();
        i2.setNome("Luvas Cirúrgicas");
        i2.setQuantidade(100);
        i2.setDataEntrada(new Date(System.currentTimeMillis()));
        i2.setUsuarioRetirada("Maria Atualizada");
        itemService.adicionarItem(i2);

        List<Item> lista = itemService.listarItens();
        Assertions.assertTrue(lista.stream().anyMatch(i -> i.getNome().equals("Soro Fisiológico")));
        Assertions.assertTrue(lista.stream().anyMatch(i -> i.getNome().equals("Luvas Cirúrgicas")));
    }

    @Test
    @Order(2)
    public void testAtualizarItem() throws SQLException {
        List<Item> lista = itemService.listarItens();
        Item primeiro = lista.get(0); // pegando o primeiro item para atualizar

        Item novo = new Item();
        novo.setNome("Soro Salino");
        novo.setQuantidade(60);
        novo.setDataEntrada(new Date(System.currentTimeMillis()));
        novo.setUsuarioRetirada("Maria Atualizada");

        boolean atualizado = itemService.atualizarItem(primeiro.getId(), novo);
        Assertions.assertTrue(atualizado);

        List<Item> listaAtualizada = itemService.listarItens();
        Assertions.assertTrue(listaAtualizada.stream().anyMatch(i -> i.getNome().equals("Soro Salino")));
    }

    @Test
    @Order(3)
    public void testDeletarItem() throws SQLException {
        List<Item> lista = itemService.listarItens();
        Item primeiro = lista.get(0);

        boolean deletado = itemService.deletarItem(primeiro.getId());
        Assertions.assertTrue(deletado);

        List<Item> listaAtualizada = itemService.listarItens();
        Assertions.assertFalse(listaAtualizada.stream().anyMatch(i -> i.getId() == primeiro.getId()));
    }
}
