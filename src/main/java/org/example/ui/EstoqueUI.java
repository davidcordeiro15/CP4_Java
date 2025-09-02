package org.example.ui;

import org.example.domain.Item;
import org.example.domain.Usuario;
import org.example.service.ItemService;
import org.example.service.UsuarioService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EstoqueUI extends JFrame {
    private JTable tabelaEstoque;
    private DefaultTableModel modelo;
    private ItemService itemService;
    private UsuarioService usuarioService;

    public EstoqueUI(Usuario usuario) {
        itemService = new ItemService();
        usuarioService = new UsuarioService();

        setTitle("Sistema de Gerenciamento de Estoque");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título
        JLabel titulo = new JLabel("Controle de Estoque", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titulo, BorderLayout.NORTH);

        // Modelo da tabela com mais colunas
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela não editável
            }
        };

        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        modelo.addColumn("Descrição");
        modelo.addColumn("Categoria");
        modelo.addColumn("Quantidade");
        modelo.addColumn("Localização");
        modelo.addColumn("Data Entrada");
        modelo.addColumn("Usuário Entrada");
        modelo.addColumn("Data Retirada");
        modelo.addColumn("Usuário Retirada");

        tabelaEstoque = new JTable(modelo);
        tabelaEstoque.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaEstoque.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tabelaEstoque);
        scrollPane.setPreferredSize(new Dimension(1100, 400));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Painel com botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnAdicionar = new JButton("Adicionar Item");
        JButton btnEditar = new JButton("Editar Item");
        JButton btnRetirar = new JButton("Retirar Item");
        JButton btnDeletar = new JButton("Deletar Item");
        JButton btnAtualizar = new JButton("Atualizar Tabela");

        // Estilizar botões
        Color buttonColor = new Color(70, 130, 180);
        Color buttonTextColor = Color.WHITE;
        for (JButton button : new JButton[]{btnAdicionar, btnEditar, btnRetirar, btnDeletar, btnAtualizar}) {
            button.setBackground(buttonColor);
            button.setForeground(buttonTextColor);
            button.setFocusPainted(false);
        }

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnRetirar);
        painelBotoes.add(btnDeletar);
        painelBotoes.add(btnAtualizar);

        mainPanel.add(painelBotoes, BorderLayout.SOUTH);

        add(mainPanel);

        // Carrega os dados do banco
        carregarDados();

        // Eventos
        btnAdicionar.addActionListener(e -> adicionarItem());
        btnEditar.addActionListener(e -> editarItem());
        btnRetirar.addActionListener(e -> retirarItem());
        btnDeletar.addActionListener(e -> deletarItem());
        btnAtualizar.addActionListener(e -> carregarDados());

        // Duplo clique para editar
        tabelaEstoque.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    editarItem();
                }
            }
        });
    }

    private void carregarDados() {
        modelo.setRowCount(0); // limpa tabela
        try {
            List<Item> itens = itemService.listarItens();
            for (Item item : itens) {
                modelo.addRow(new Object[]{
                        item.getId(),
                        item.getNome(),
                        item.getDescricao(),
                        item.getCategoria(),
                        item.getQuantidade(),
                        item.getLocalizacao(),
                        item.getDataEntrada(),
                        item.getUsuarioEntrada() != null ? item.getUsuarioEntrada().getNome() : "N/A",
                        item.getDataRetirada(),
                        item.getUsuarioRetirada() != null ? item.getUsuarioRetirada().getNome() : "N/A"
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar estoque: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarItem() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

        JTextField nomeField = new JTextField();
        JTextField descricaoField = new JTextField();
        JTextField categoriaField = new JTextField();
        JTextField quantidadeField = new JTextField();
        JTextField localizacaoField = new JTextField();
        JTextField usuarioIdField = new JTextField();

        panel.add(new JLabel("Nome:*"));
        panel.add(nomeField);
        panel.add(new JLabel("Descrição:"));
        panel.add(descricaoField);
        panel.add(new JLabel("Categoria:"));
        panel.add(categoriaField);
        panel.add(new JLabel("Quantidade:*"));
        panel.add(quantidadeField);
        panel.add(new JLabel("Localização:"));
        panel.add(localizacaoField);
        panel.add(new JLabel("Email Usuário Entrada:*"));
        panel.add(usuarioIdField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Adicionar Novo Item",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                // Validações
                if (nomeField.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("Nome é obrigatório");
                }

                int quantidade = Integer.parseInt(quantidadeField.getText());
                if (quantidade < 0) {
                    throw new IllegalArgumentException("Quantidade não pode ser negativa");
                }

                String usuarioId = usuarioIdField.getText();

                // Verificar se usuário existe
                Usuario usuario = usuarioService.buscarUsuarioPorEmail(usuarioId);
                if (usuario.getNome().isEmpty()) {
                    throw new IllegalArgumentException("Usuário com ID " + usuarioId + " não encontrado");
                }

                Item item = new Item();
                item.setNome(nomeField.getText().trim());
                item.setDescricao(descricaoField.getText().trim());
                item.setCategoria(categoriaField.getText().trim());
                item.setQuantidade(quantidade);
                item.setLocalizacao(localizacaoField.getText().trim());
                item.setUsuarioEntradaEmail(usuarioId);

                boolean sucesso = itemService.adicionarItem(item);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Item adicionado com sucesso!");
                    carregarDados();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao adicionar item.");
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, insira valores numéricos válidos para quantidade e email do usuário.",
                        "Erro de Formato",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "Erro de banco de dados: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarItem() {
        int linha = tabelaEstoque.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um item para editar!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (Integer) modelo.getValueAt(linha, 0);

        try {
            Item itemOptional = itemService.buscarItemPorId(id);
            if (itemOptional.getNome().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Item não encontrado!");
                return;
            }

            Item item = itemOptional;
            JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

            JTextField nomeField = new JTextField(item.getNome());
            JTextField descricaoField = new JTextField(item.getDescricao());
            JTextField categoriaField = new JTextField(item.getCategoria());
            JTextField quantidadeField = new JTextField(String.valueOf(item.getQuantidade()));
            JTextField localizacaoField = new JTextField(item.getLocalizacao());
            JTextField usuarioEmailField = new JTextField(String.valueOf(item.getUsuarioEntradaEmail()));

            panel.add(new JLabel("Nome:*"));
            panel.add(nomeField);
            panel.add(new JLabel("Descrição:"));
            panel.add(descricaoField);
            panel.add(new JLabel("Categoria:"));
            panel.add(categoriaField);
            panel.add(new JLabel("Quantidade:*"));
            panel.add(quantidadeField);
            panel.add(new JLabel("Localização:"));
            panel.add(localizacaoField);
            panel.add(new JLabel("Email Usuário Entrada:*"));
            panel.add(usuarioEmailField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Editar Item - ID: " + id,
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                // Validações
                if (nomeField.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("Nome é obrigatório");
                }

                int quantidade = Integer.parseInt(quantidadeField.getText());
                if (quantidade < 0) {
                    throw new IllegalArgumentException("Quantidade não pode ser negativa");
                }

                String usuarioEmail = usuarioEmailField.getText();

                // Verificar se usuário existe
               Usuario usuario = usuarioService.buscarUsuarioPorEmail(usuarioEmail);
                if (usuario.getNome().isEmpty()) {
                    throw new IllegalArgumentException("Usuário com Email " + usuarioEmail + " não encontrado");
                }

                Item itemAtualizado = new Item();
                itemAtualizado.setNome(nomeField.getText().trim());
                itemAtualizado.setDescricao(descricaoField.getText().trim());
                itemAtualizado.setCategoria(categoriaField.getText().trim());
                itemAtualizado.setQuantidade(quantidade);
                itemAtualizado.setLocalizacao(localizacaoField.getText().trim());
                itemAtualizado.setUsuarioEntradaEmail(usuarioEmail);

                boolean sucesso = itemService.atualizarItem(item.getId(), itemAtualizado);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Item atualizado com sucesso!");
                    carregarDados();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar item.");
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro de banco de dados: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void retirarItem() {
        int linha = tabelaEstoque.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um item para retirar!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (Integer) modelo.getValueAt(linha, 0);
        String nomeItem = (String) modelo.getValueAt(linha, 1);
        int quantidadeAtual = (Integer) modelo.getValueAt(linha, 4);

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField quantidadeField = new JTextField();
        JTextField usuarioEmailField = new JTextField();

        panel.add(new JLabel("Quantidade a retirar:"));
        panel.add(quantidadeField);
        panel.add(new JLabel("ID Usuário que retirou:*"));
        panel.add(usuarioEmailField);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Retirar Item: " + nomeItem,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int quantidadeRetirar = Integer.parseInt(quantidadeField.getText());
                if (quantidadeRetirar <= 0 || quantidadeRetirar > quantidadeAtual) {
                    throw new IllegalArgumentException("Quantidade inválida para retirada");
                }

                String usuarioEmail = usuarioEmailField.getText();

                // Verificar se usuário existe
                Usuario usuario = usuarioService.buscarUsuarioPorEmail(usuarioEmail);
                if (usuario.getNome().isEmpty()) {
                    throw new IllegalArgumentException("Usuário com Email " + usuarioEmail + " não encontrado");
                }

                boolean sucesso = itemService.retirarItem(id, usuarioEmail, quantidadeRetirar);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this,
                            "Retirada de " + quantidadeRetirar + " unidades registrada com sucesso!");
                    carregarDados();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao registrar retirada.");
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, insira valores numéricos válidos.",
                        "Erro de Formato",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "Erro de banco de dados: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deletarItem() {
        int linha = tabelaEstoque.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um item para deletar!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (Integer) modelo.getValueAt(linha, 0);
        String nomeItem = (String) modelo.getValueAt(linha, 1);

        // Solicitar o ID do usuário que está realizando a exclusão
        String usuarioEmailStr = JOptionPane.showInputDialog(this,
                "Informe o ID do usuário que está realizando a exclusão:",
                "Confirmação de Usuário",
                JOptionPane.QUESTION_MESSAGE);

        if (usuarioEmailStr == null || usuarioEmailStr.trim().isEmpty()) {
            return; // Usuário cancelou a operação
        }

        try {
            String usuarioEmail = usuarioEmailStr;

            // Verificar se o usuário existe
            Usuario usuario = usuarioService.buscarUsuarioPorEmail(usuarioEmail);
            if (usuario.getNome().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Usuário com ID " + usuarioEmail + " não encontrado!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja deletar o item '" + nomeItem + "'?\n" +
                            "Usuário responsável: " + usuario.getNome(),
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean sucesso = itemService.deletarItem(id, usuarioEmail);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Item deletado com sucesso!");
                    carregarDados();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao deletar item.");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, insira um ID de usuário válido.",
                    "Erro de Formato",
                    JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro de banco de dados: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método auxiliar para buscar usuário (você precisa implementar no UsuarioService)
    private Usuario buscarUsuarioPorEmail(String email) {
        try {
            return usuarioService.buscarUsuarioPorEmail(email);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao buscar usuário: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}