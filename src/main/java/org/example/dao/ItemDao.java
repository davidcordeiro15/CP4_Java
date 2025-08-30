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


    public List<Item> listarTodos() {
        List<Item> itens = new ArrayList<>();

        String sql = "SELECT e.id, e.nome, e.quantidade, e.data_entrada, e.data_retirada, " +
                "u1.nome AS usuarioEntrada, " +
                "u2.nome AS usuarioRetirada " +
                "FROM estoque e " +
                "LEFT JOIN usuarios u1 ON e.usuarioEntrada = u1.id " +
                "LEFT JOIN usuarios u2 ON e.usuarioRetirada = u2.id";

        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setNome(rs.getString("nome"));
                item.setQuantidade(rs.getInt("quantidade"));
                item.setDataEntrada(rs.getDate("data_entrada"));
                item.setDataRetirada(rs.getDate("data_retirada"));
                item.setNomeUsuarioEntrada(rs.getString("usuarioEntrada"));
                item.setNomeUsuarioRetirada(rs.getString("usuarioRetirada"));

                itens.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itens;
    }


    // Atualizar (UPDATE)
    public boolean modificar(int id, Item novoItem) throws SQLException {
        String sql = "UPDATE estoque SET nome = ?, quantidade = ?, data_entrada = ?, usuarioRetirada = ? WHERE id = ?";
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
    public boolean retirarItem(int idItem, String nomeUsuario) throws SQLException {
        String sql = "UPDATE estoque " +
                "SET usuarioRetirada  = ?, data_retirada = SYSTIMESTAMP " +
                "WHERE id = ?";
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeUsuario);
            stmt.setInt(2, idItem);
            int rows = stmt.executeUpdate();
            return rows > 0;
        }
    }




}
