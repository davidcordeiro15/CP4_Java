package org.example.ui;

import org.example.domain.Usuario;
import org.example.service.UsuarioService;
import org.example.util.EmailValidator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Optional;

public class CadastroUI extends JFrame {
    private JTextField nomeField;
    private JTextField emailField;
    private UsuarioService usuarioService;

    public CadastroUI() {
        usuarioService = new UsuarioService();
        inicializarUI();
    }

    private void inicializarUI() {
        setTitle("Sistema - Cadastro de Usuário");
        setSize(450, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titulo = new JLabel("Cadastro de Usuário", SwingConstants.CENTER);
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
        JButton cadastrarBtn = new JButton("Cadastrar");
        JButton voltarBtn = new JButton("Voltar para Login");

        // Estilizar botões
        cadastrarBtn.setBackground(new Color(70, 130, 180));
        cadastrarBtn.setForeground(Color.WHITE);
        cadastrarBtn.setFocusPainted(false);

        cadastrarBtn.addActionListener(this::cadastrarUsuario);
        voltarBtn.addActionListener(e -> voltarParaLogin());

        buttonPanel.add(cadastrarBtn);
        buttonPanel.add(voltarBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        setupValidacaoTempoReal();
    }

    private void setupValidacaoTempoReal() {
        emailField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { validarEmailVisual(); }
            @Override
            public void removeUpdate(DocumentEvent e) { validarEmailVisual(); }
            @Override
            public void changedUpdate(DocumentEvent e) { validarEmailVisual(); }

            private void validarEmailVisual() {
                String email = emailField.getText().trim();
                if (!email.isEmpty()) {
                    if (EmailValidator.isValidEmail(email)) {
                        emailField.setForeground(Color.BLACK);
                        emailField.setToolTipText("Email válido");
                    } else {
                        emailField.setForeground(Color.RED);
                        emailField.setToolTipText("Formato de email inválido. Exemplo: usuario@exemplo.com");
                    }
                } else {
                    emailField.setForeground(Color.BLACK);
                    emailField.setToolTipText("Informe o email");
                }
            }
        });
    }

    private void cadastrarUsuario(ActionEvent e) {
        String nome = nomeField.getText().trim();
        String email = emailField.getText().trim();

        // Validação dos campos
        if (nome.isEmpty()) {
            mostrarErro("Por favor, informe o nome!", "Campo Obrigatório");
            nomeField.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            mostrarErro("Por favor, informe o email!", "Campo Obrigatório");
            emailField.requestFocus();
            return;
        }

        if (!EmailValidator.isValidEmail(email)) {
            mostrarErro("Por favor, informe um email válido!\nExemplo: usuario@exemplo.com", "Email Inválido");
            emailField.requestFocus();
            emailField.selectAll();
            return;
        }

        Usuario user = new Usuario();
        user.setNome(nome);
        user.setEmail(email);

        try {
            Usuario usuarioCriado = usuarioService.adicionarUsuario(user);

            if (usuarioCriado != null) {
                mostrarSucesso("Usuário cadastrado com sucesso!\nID: " + usuarioCriado.getId());

                int opcao = JOptionPane.showConfirmDialog(this,
                        "Deseja voltar para a tela de login?",
                        "Cadastro Concluído",
                        JOptionPane.YES_NO_OPTION);

                if (opcao == JOptionPane.YES_OPTION) {
                    voltarParaLogin();
                } else {
                    limparCampos();
                }
            } else {
                mostrarErro("Erro ao cadastrar usuário. Email já está em uso.", "Erro no Cadastro");
            }
        } catch (SQLException ex) {
            mostrarErro("Erro de conexão com o banco de dados: " + ex.getMessage(), "Erro");
        }
    }

    private void voltarParaLogin() {
        dispose();
        new LoginUI().setVisible(true);
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