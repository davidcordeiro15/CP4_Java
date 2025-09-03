package org.example.service;

import org.example.dao.ItemDao;
import org.example.domain.Item;

import java.sql.SQLException;
import java.util.List;

public class ItemService {
    private ItemDao itemDAO = new ItemDao();

    public boolean adicionarItem(Item item) throws SQLException {
        return itemDAO.salvar(item) > 0;
    }

    public List<Item> listarItens() throws SQLException {
        return itemDAO.listarTodos();
    }

    public Item buscarItemPorId(int id) throws SQLException {
        return itemDAO.buscarPorId(id);
    }

    public boolean atualizarItem(int id, Item novoItem) throws SQLException {
        // Verifica se o item existe antes de atualizar
        Item itemExistente = itemDAO.buscarPorId(id);
        if (itemExistente == null) {
            return false;
        }
        return itemDAO.modificar(id, novoItem);
    }

    public boolean deletarItem(int id) throws SQLException {
        return itemDAO.deletar(id);
    }

    public List<Item> buscarItensPorNome(String nome) throws SQLException {
        return itemDAO.buscarPorNome(nome);
    }
    public boolean retirarItem(int idItem, int idUsuario, int quantidade) throws SQLException {
        // Implementação para retirar quantidade específica
        Item item = itemDAO.buscarPorId(idItem);
        if (item == null) {
            return false;
        }


        if (item.getQuantidade() < quantidade) {
            throw new IllegalArgumentException("Quantidade insuficiente em estoque");
        }

        // Atualiza a quantidade
        item.setQuantidade(item.getQuantidade() - quantidade);
        return itemDAO.atualizarQuantidade(idItem, idUsuario, item.getQuantidade());
    }
}