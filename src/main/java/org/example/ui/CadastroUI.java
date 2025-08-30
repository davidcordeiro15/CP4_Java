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
        setupValidacaoTempoReal();
    }
    private void setupValidacaoTempoReal() {
        // Validação em tempo real do email
        emailField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validarEmailVisual();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validarEmailVisual();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validarEmailVisual();
            }

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
            JOptionPane.showMessageDialog(this,
                    "Por favor, informe o nome!",
                    "Campo Obrigatório",
                    JOptionPane.WARNING_MESSAGE);
            nomeField.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, informe o email!",
                    "Campo Obrigatório",
                    JOptionPane.WARNING_MESSAGE);
            emailField.requestFocus();
            return;
        }

        // Validação do formato do email
        if (!EmailValidator.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, informe um email válido!\nExemplo: usuario@exemplo.com",
                    "Email Inválido",
                    JOptionPane.ERROR_MESSAGE);
            emailField.requestFocus();
            emailField.selectAll();
            return;
        }

        // Cria o usuário somente se todas as validações passarem
        Usuario user = new Usuario();
        user.setNome(nome);
        user.setEmail(email);

        try {
            boolean sucesso = usuarioService.adicionarUsuario(user);
            if (sucesso) {
                JOptionPane.showMessageDialog(this,
                        "Usuário cadastrado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);

                // Pergunta se deseja voltar para login
                int opcao = JOptionPane.showConfirmDialog(this,
                        "Deseja voltar para a tela de login?",
                        "Cadastro Concluído",
                        JOptionPane.YES_NO_OPTION);

                if (opcao == JOptionPane.YES_OPTION) {
                    dispose();
                    new LoginUI().setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Erro ao cadastrar usuário. Email já pode estar em uso.",
                        "Erro no Cadastro",
                        JOptionPane.ERROR_MESSAGE);
            }
            limparCampos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro de conexão com o banco de dados: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        emailField.setText("");
    }
}