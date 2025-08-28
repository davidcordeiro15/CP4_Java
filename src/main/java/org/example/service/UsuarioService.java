package org.example.service;

import org.example.dao.UsuarioDao;
import org.example.domain.Usuario;

import java.sql.SQLException;
import java.util.List;

public class UsuarioService {

    private UsuarioDao usuarioDAO = new UsuarioDao();

    // 🔹 Adicionar usuário
    public void adicionarUsuario(Usuario usuario) throws SQLException {
        usuarioDAO.salvar(usuario);
    }

    // 🔹 Listar todos os usuários
    public List<Usuario> listarUsuarios() throws SQLException {
        return usuarioDAO.listarTodos();
    }

    // 🔹 Atualizar usuário
    public boolean atualizarUsuario(String nomeAtual, String emailAtual, Usuario novoUsuario) throws SQLException {
        return usuarioDAO.modificar(
                nomeAtual,
                emailAtual,
                novoUsuario.getNome(),
                novoUsuario.getEmail()
        );
    }

    // 🔹 Deletar usuário
    public boolean deletarUsuario(String nome, String email) throws SQLException {
        return usuarioDAO.deletar(nome, email);
    }
}
