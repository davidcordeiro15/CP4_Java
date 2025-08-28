package org.example.dao;

import org.example.config.DatabaseConnectionFactory;
import org.example.domain.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    // 🔹 Criar novo usuário
    public void salvar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, email) VALUES (?, ?)";
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.executeUpdate();
        }
    }

    // 🔹 Listar todos os usuários
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, nome, email FROM usuarios";
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                usuarios.add(u);
            }
        }
        return usuarios;
    }

    // 🔹 Deletar usuário pelo nome e email
    public boolean deletar(String nome, String email) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE nome = ? AND email = ?";
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);
            int rows = stmt.executeUpdate();
            return rows > 0; // true se algum usuário foi deletado
        }
    }

    // 🔹 Modificar usuário (atualizar nome e email)
    public boolean modificar(String nomeAtual, String emailAtual, String novoNome, String novoEmail) throws SQLException {
        String sql = "UPDATE usuarios SET nome = ?, email = ? WHERE nome = ? AND email = ?";
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setString(2, novoEmail);
            stmt.setString(3, nomeAtual);
            stmt.setString(4, emailAtual);
            int rows = stmt.executeUpdate();
            return rows > 0; // true se algum usuário foi atualizado
        }
    }
    public boolean buscar(String nome, String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE nome = ? AND email = ?";
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // retorna true se achou algum usuário
                }
            }
        }
        return false;
    }
}
