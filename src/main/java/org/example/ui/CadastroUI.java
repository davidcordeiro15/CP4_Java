package org.example.ui;

import org.example.domain.Usuario;
import org.example.service.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class CadastroUI extends JFrame {
    private JTextField nomeField;
    private JTextField emailField;
    private UsuarioService usuarioService;

    public CadastroUI() {
        usuarioService = new UsuarioService();

        setTitle("Sistema - Cadastro");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel de inputs
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        formPanel.add(nomeField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        add(formPanel, BorderLayout.CENTER);

        // Botões
        JPanel buttonPanel = new JPanel();
        JButton cadastrarBtn = new JButton("Cadastrar");
        JButton voltarBtn = new JButton("Voltar para Login");

        cadastrarBtn.addActionListener(this::cadastrarUsuario);
        voltarBtn.addActionListener(e -> {
            dispose(); // Fecha tela de cadastro
            new LoginUI().setVisible(true); // Volta pra tela de login
        });

        buttonPanel.add(cadastrarBtn);
        buttonPanel.add(voltarBtn);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void cadastrarUsuario(ActionEvent e) {
        String nome = nomeField.getText().trim();
        String email = emailField.getText().trim();

        Usuario user = new Usuario();
        user.setNome(nome);
        user.setEmail(email);

        try {
            boolean sucesso = usuarioService.adicionarUsuario(user);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar usuário.");
            }
            limparCampos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        emailField.setText("");
    }
}
