package org.example.ui;

import org.example.domain.Usuario;
import org.example.service.UsuarioService;
import org.example.util.EmailValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Optional;

public class LoginUI extends JFrame {
    private JTextField nomeField;
    private JTextField emailField;
    private UsuarioService usuarioService;

    public LoginUI() {
        usuarioService = new UsuarioService();
        inicializarUI();
    }

    private void inicializarUI() {
        setTitle("Sistema - Login");
        setSize(450, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titulo = new JLabel("Login do Sistema", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titulo, BorderLayout.NORTH);

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        formPanel.add(new JLabel("Nome:*"));
        nomeField = new JTextField();
        formPanel.add(nomeField);

        formPanel.add(new JLabel("Email:*"));
        emailField = new JTextField();
        formPanel.add(emailField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton loginBtn = new JButton("Login");
        JButton cadastroBtn = new JButton("Cadastrar Novo Usuário");

        // Estilizar botões
        loginBtn.setBackground(new Color(70, 130, 180));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);

        loginBtn.addActionListener(this::loginUsuario);
        cadastroBtn.addActionListener(e -> irParaCadastro());

        buttonPanel.add(loginBtn);
        buttonPanel.add(cadastroBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private void loginUsuario(ActionEvent e) {
        String nome = nomeField.getText().trim();
        String email = emailField.getText().trim();

        // Validação do email
        if (!EmailValidator.isValidEmail(email)) {
            mostrarErro("Por favor, insira um email válido!", "Email Inválido");
            return;
        }

        // Validação do nome
        if (nome.isEmpty()) {
            mostrarErro("Por favor, insira seu nome!", "Nome Obrigatório");
            return;
        }

        try {
            Usuario usuario = usuarioService.autenticarUsuario(nome, email);

            if (!usuario.getNome().isEmpty()) {
                mostrarSucesso("Login realizado com sucesso!\nBem-vindo, " + usuario.getNome() + "!");
                abrirEstoque(usuario);
            } else {
                int opcao = JOptionPane.showConfirmDialog(this,
                        "Usuário não encontrado. Deseja criar uma nova conta?",
                        "Login Falhou",
                        JOptionPane.YES_NO_OPTION);

                if (opcao == JOptionPane.YES_OPTION) {
                    irParaCadastro();
                }
            }
        } catch (SQLException ex) {
            mostrarErro("Erro ao conectar com o banco de dados: " + ex.getMessage(), "Erro de Conexão");
        }
    }

    private void abrirEstoque(Usuario usuario) {
        dispose();
        new EstoqueUI(usuario).setVisible(true);
    }

    private void irParaCadastro() {
        dispose();
        new CadastroUI().setVisible(true);
    }

    private void limparCampos() {
        nomeField.setText("");
        emailField.setText("");
        nomeField.requestFocus();
    }

    private void mostrarErro(String mensagem, String titulo) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarSucesso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
}