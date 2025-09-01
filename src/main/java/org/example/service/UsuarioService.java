package org.example.service;

import org.example.dao.UsuarioDao;
import org.example.domain.Usuario;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsuarioService {

    private UsuarioDao usuarioDAO = new UsuarioDao();

    // 🔹 Adicionar usuário com validação
    public Usuario adicionarUsuario(Usuario usuario) throws SQLException {
        // Verificar se email já existe


        try {
            int id = usuarioDAO.salvar(usuario);
            usuario.setId(id);
            return usuario;
        } catch (SQLException ex) {
            System.err.println("Erro ao adicionar usuário: " + ex.getMessage());
            throw ex;
        }
    }

    // 🔹 Listar todos os usuários
    public List<Usuario> listarUsuarios() throws SQLException {
        return usuarioDAO.listarTodos();
    }

    // 🔹 Buscar usuário por ID
    public Optional<Usuario> buscarUsuarioPorId(int id) throws SQLException {
        return usuarioDAO.buscarPorId(id);
    }

    // 🔹 Buscar usuário por email
    public Optional<Usuario> buscarUsuarioPorEmail(String email) throws SQLException {
        return usuarioDAO.buscarPorEmail(email);
    }

    // 🔹 Autenticar usuário
    public Optional<Usuario> autenticarUsuario(String nome, String email) throws SQLException {
        return usuarioDAO.buscarPorCredenciais(nome, email);
    }

    // 🔹 Verificar se usuário existe
    public boolean existeUsuario(String nome, String email) throws SQLException {
        return usuarioDAO.buscarPorCredenciais(nome, email).isPresent();
    }

    // 🔹 Atualizar usuário
    public boolean atualizarUsuario(Usuario usuario) throws SQLException {
        // Verificar se o novo email já pertence a outro usuário
        Optional<Usuario> usuarioComEmail = usuarioDAO.buscarPorEmail(usuario.getEmail());
        if (usuarioComEmail.isPresent() && usuarioComEmail.get().getId() != usuario.getId()) {
            return false; // Email já está em uso por outro usuário
        }

        return usuarioDAO.atualizar(usuario);
    }

    // 🔹 Deletar usuário por ID
    public boolean deletarUsuario(int id) throws SQLException {
        return usuarioDAO.deletar(id);
    }

    // 🔹 Verificar se email está disponível
    public boolean emailDisponivel(String email) throws SQLException {
        return !usuarioDAO.existePorEmail(email);
    }

    // 🔹 Validar dados do usuário
    public boolean validarUsuario(Usuario usuario) {
        return usuario.getNome() != null && !usuario.getNome().trim().isEmpty() &&
                usuario.getEmail() != null && !usuario.getEmail().trim().isEmpty();
    }
}