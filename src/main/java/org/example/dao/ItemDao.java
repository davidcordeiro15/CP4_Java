package org.example.dao;

import org.example.config.DatabaseConnectionFactory;
import org.example.domain.Item;
import org.example.domain.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;




public class ItemDao {

    private static final String INSERT_SQL =
            "INSERT INTO estoque (nome, descricao, categoria, quantidade, localizacao, data_entrada, usuarioEntrada) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_SQL =
                "\n" +
                        "SELECT \n" +
                        "    e.id, \n" +
                        "    e.nome, \n" +
                        "    e.descricao, \n" +
                        "    e.categoria, \n" +
                        "    e.quantidade, \n" +
                        "    e.localizacao,\n" +
                        "    e.data_entrada, \n" +
                        "    e.data_retirada, \n" +
                        "    e.usuarioEntrada, \n" +
                        "    e.usuarioRetirada, \n" +
                        "    u1.nome AS usuarioEntradaNome, \n" +
                        "    u2.nome AS usuarioRetiradaNome \n" +
                        "FROM estoque e \n" +
                        "LEFT JOIN usuarios u1 ON e.usuarioEntrada = u1.id  -- ✅ JOIN pelo ID\n" +
                        "LEFT JOIN usuarios u2 ON e.usuarioRetirada = u2.id  -- ✅ JOIN pelo ID\n" +
                        "ORDER BY e.data_entrada DESC";


    private static final String SELECT_BY_ID_SQL =
            "SELECT e.id, e.nome, e.descricao, e.categoria, e.quantidade, e.localizacao, " +
                    "e.data_entrada, e.data_retirada, e.usuarioEntrada, e.usuarioRetirada, " +
                    "u1.nome AS usuarioEntradaNome, u2.nome AS usuarioRetiradaNome " +
                    "FROM estoque e " +
                    "LEFT JOIN usuarios u1 ON e.usuarioEntrada = u1.id " +
                    "LEFT JOIN usuarios u2 ON e.usuarioRetirada = u2.id " +
                    "WHERE e.id = ?";

    private static final String UPDATE_SQL =
            "UPDATE estoque SET nome = ?, descricao = ?, categoria = ?, quantidade = ?, " +
                    "localizacao = ?, usuarioEntrada = ? WHERE id = ?";



    private static final String DELETE_SQL =
            "DELETE FROM estoque WHERE id = ?";

    private static final String SELECT_BY_NOME_SQL =
            "SELECT e.id, e.nome, e.descricao, e.categoria, e.quantidade, e.localizacao, " +
                    "e.data_entrada, e.data_retirada, e.usuarioEntrada, e.usuarioRetirada, " +
                    "u1.nome AS usuarioEntradaNome, u2.nome AS usuarioRetiradaNome " +
                    "FROM estoque e " +
                    "LEFT JOIN usuarios u1 ON e.usuarioEntrada = u1.email " +
                    "LEFT JOIN usuarios u2 ON e.usuarioRetirada = u2.email" +
                    "WHERE LOWER(e.nome) LIKE LOWER(?) ORDER BY e.nome";

    // Criar (INSERT) - Retorna o ID do item criado
    public int salvar(Item item) throws SQLException {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, new String[]{"id"})) {

            stmt.setString(1, item.getNome());
            stmt.setString(2, item.getDescricao());
            stmt.setString(3, item.getCategoria());
            stmt.setInt(4, item.getQuantidade());
            stmt.setString(5, item.getLocalizacao());
            stmt.setTimestamp(6, new Timestamp(item.getDataEntrada().getTime()));
            stmt.setInt(7, item.getUsuarioEntradaId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar item, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao criar item, nenhum ID obtido.");
                }
            }
        }
    }

    // Listar todos os itens
    public List<Item> listarTodos() throws SQLException {
        List<Item> itens = new ArrayList<>();

        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                itens.add(mapearResultSetParaItem(rs));
            }
        }

        return itens;
    }

    // Buscar item por I
    public Item buscarPorId(int id) throws SQLException {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetParaItem(rs);
                }
                throw null;
            }
        }
    }



    // Atualizar item
    public boolean modificar(int id, Item item) throws SQLException {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, item.getNome());
            stmt.setString(2, item.getDescricao());
            stmt.setString(3, item.getCategoria());
            stmt.setInt(4, item.getQuantidade());
            stmt.setString(5, item.getLocalizacao());
            stmt.setInt(6, item.getUsuarioEntradaId());
            stmt.setInt(7, id);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }


    // Deletar item
    public boolean deletar(int idItem) throws SQLException {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setInt(1, idItem);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    // Método auxiliar para mapear ResultSet para objeto Item
    private Item mapearResultSetParaItem(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setId(rs.getInt("id"));
        item.setNome(rs.getString("nome"));
        item.setDescricao(rs.getString("descricao"));
        item.setCategoria(rs.getString("categoria"));
        item.setQuantidade(rs.getInt("quantidade"));
        item.setLocalizacao(rs.getString("localizacao"));
        item.setDataEntrada(rs.getDate("data_entrada"));
        item.setDataRetirada(rs.getDate("data_retirada"));
        item.setUsuarioEntradaId(rs.getInt("usuarioEntrada"));
        item.setUsuarioRetiradaId(rs.getInt("usuarioRetirada"));

        // Mapear objetos Usuario se existirem
        String usuarioEntradaNome = rs.getString("usuarioEntradaNome");
        if (usuarioEntradaNome != null) {
            Usuario usuarioEntrada = new Usuario();
            usuarioEntrada.setId(rs.getInt("usuarioEntrada"));
            usuarioEntrada.setNome(usuarioEntradaNome);
            item.setUsuarioEntrada(usuarioEntrada);
        }

        String usuarioRetiradaNome = rs.getString("usuarioRetiradaNome");
        if (usuarioRetiradaNome != null) {
            Usuario usuarioRetirada = new Usuario();
            usuarioRetirada.setId(rs.getInt("usuarioRetirada"));
            usuarioRetirada.setNome(usuarioRetiradaNome);
            item.setUsuarioRetirada(usuarioRetirada);
        }

        return item;
    }

    // Método para atualizar apenas a quantidade
    public boolean atualizarQuantidade(int idItem, int idUsuario, int novaQuantidade) throws SQLException {
        String sql = "UPDATE estoque SET quantidade = ?, data_retirada = ?, usuarioRetirada = ? WHERE id = ?";

        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, novaQuantidade);
            stmt.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
            stmt.setInt(3, idUsuario);
            stmt.setInt(4, idItem);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }


    // Método para buscar itens por categoria
    public List<Item> buscarPorCategoria(String categoria) throws SQLException {
        List<Item> itens = new ArrayList<>();
        String sql = "SELECT e.*, u1.nome AS usuarioEntradaNome, u2.nome AS usuarioRetiradaNome " +
                "FROM estoque e " +
                "LEFT JOIN usuarios u1 ON e.usuarioEntrada = u1.id " +
                "LEFT JOIN usuarios u2 ON e.usuarioRetirada = u2.id " +
                "WHERE LOWER(e.categoria) = LOWER(?) ORDER BY e.nome";

        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.toLowerCase());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    itens.add(mapearResultSetParaItem(rs));
                }
            }
        }

        return itens;
    }
}