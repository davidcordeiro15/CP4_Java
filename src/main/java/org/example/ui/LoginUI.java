package org.example.ui;

import org.example.service.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class LoginUI extends JFrame {
    private JTextField nomeField;
    private JTextField emailField;
    private UsuarioService usuarioService;

    public LoginUI() {
        usuarioService = new UsuarioService();

        setTitle("Sistema - Login");
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
        JButton loginBtn = new JButton("Login");
        JButton cadastroBtn = new JButton("Ir para Cadastro");

        loginBtn.addActionListener(this::loginUsuario);
        cadastroBtn.addActionListener(e -> {
            dispose(); // Fecha a tela de login
            new CadastroUI().setVisible(true); // Abre a tela de cadastro
        });

        buttonPanel.add(loginBtn);
        buttonPanel.add(cadastroBtn);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loginUsuario(ActionEvent e) {
        String nome = nomeField.getText();
        String email = emailField.getText();

        try {
            boolean encontrado = usuarioService.buscarUsuario(nome, email);
            if (encontrado) {
                JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
                dispose(); // Fecha a tela de login
                new EstoqueUI().setVisible(true); // Abre a tela do estoque
            } else {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }


    private void limparCampos() {
        nomeField.setText("");
        emailField.setText("");
    }
}
