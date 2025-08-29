package org.example.dao;

import org.example.config.DatabaseConnectionFactory;
import org.example.domain.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDao {

    // Criar (INSERT)
    public void salvar(Item item) throws SQLException {
        String sql = "INSERT INTO estoque (nome, quantidade, data_entrada, usuario_retirada) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getNome());
            stmt.setInt(2, item.getQuantidade());
            stmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            stmt.setString(4, item.getUsuarioRetirada());
            stmt.executeUpdate();
        }
    }

    // Ler (SELECT todos)
    public List<Item> listarTodos() throws SQLException {
        List<Item> itens = new ArrayList<>();
        String sql = "SELECT id, nome, quantidade, data_entrada, usuario_retirada FROM estoque";
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Item i = new Item();
                i.setId(rs.getInt("id"));
                i.setNome(rs.getString("nome"));
                i.setQuantidade(rs.getInt("quantidade"));
                i.setDataEntrada(rs.getDate("data_entrada"));
                i.setUsuarioRetirada(rs.getString("usuario_retirada"));
                itens.add(i);
            }
        }
        return itens;
    }

    // Atualizar (UPDATE)
    public boolean modificar(int id, Item novoItem) throws SQLException {
        String sql = "UPDATE estoque SET nome = ?, quantidade = ?, data_entrada = ?, usuario_retirada = ? WHERE id = ?";
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoItem.getNome());
            stmt.setInt(2, novoItem.getQuantidade());
            stmt.setDate(3, novoItem.getDataEntrada());           // coluna correta
            stmt.setString(4, novoItem.getUsuarioRetirada());     // coluna correta
            stmt.setInt(5, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        }
    }

    // Deletar (DELETE)
    public boolean deletar(int id) throws SQLException {
        String sql = "DELETE FROM estoque WHERE id = ?";
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        }
    }
}
