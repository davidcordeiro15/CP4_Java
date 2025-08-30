package org.example.service;

import org.example.dao.ItemDao;
import org.example.domain.Item;
import org.example.domain.Usuario;

import java.sql.SQLException;
import java.util.List;

public class ItemService {
    private ItemDao itemDAO = new ItemDao();

    public void adicionarItem(Item item) throws SQLException {
        itemDAO.salvar(item);
    }

    public List<Item> listarItens() throws SQLException {
        return itemDAO.listarTodos();
    }

    public boolean atualizarItem(int id, Item novoItem) throws SQLException {
        return itemDAO.modificar(id, novoItem);
    }

    public boolean deletarItem(int id, String nomeUsuarioRetirada) throws SQLException {
        return itemDAO.retirarItem(id, nomeUsuarioRetirada);
    }
}
