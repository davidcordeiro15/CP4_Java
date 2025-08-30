package org.example.ui;

import org.example.domain.Item;
import org.example.service.ItemService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class EstoqueUI extends JFrame {
    private JTable tabelaEstoque;
    private DefaultTableModel modelo;
    private ItemService itemService;

    public EstoqueUI() {
        itemService = new ItemService();

        setTitle("Sistema - Estoque");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Modelo da tabela
        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nome do Item");
        modelo.addColumn("Quantidade");
        modelo.addColumn("Data Entrada");
        modelo.addColumn("Usuário Entrada");
        modelo.addColumn("Data Retirada");
        modelo.addColumn("Usuário Retirada");

        tabelaEstoque = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabelaEstoque);

        // Painel com botões
        JPanel painelBotoes = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar Produto");
        JButton btnAtualizar = new JButton("Atualizar Produto");
        JButton btnRetirar = new JButton("Retirar Produto");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnRetirar);

        add(scrollPane, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        // Carrega os dados do banco
        carregarDados();

        // Eventos
        btnAdicionar.addActionListener(e -> adicionarProduto());
        btnAtualizar.addActionListener(e -> atualizarProduto());
        btnRetirar.addActionListener(e -> retirarProduto());
    }

    private void carregarDados() {
        modelo.setRowCount(0); // limpa tabela
        try {
            List<Item> itens = itemService.listarItens();
            for (Item i : itens) {
                modelo.addRow(new Object[]{
                        i.getId(),
                        i.getNome(),
                        i.getQuantidade(),
                        i.getDataEntrada(),
                        i.getNomeUsuarioEntrada(),
                        i.getDataRetirada(),
                        i.getNomeUsuarioEntrada()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar estoque: " + e.getMessage());
        }
    }

    private void adicionarProduto() {
        String nome = JOptionPane.showInputDialog(this, "Nome do produto:");
        if (nome == null || nome.trim().isEmpty()) return;

        String qtdStr = JOptionPane.showInputDialog(this, "Quantidade:");
        if (qtdStr == null || qtdStr.trim().isEmpty()) return;

        String usuarioEntrada = JOptionPane.showInputDialog(this, "Usuário responsável pela entrada:");
        if (usuarioEntrada == null || usuarioEntrada.trim().isEmpty()) usuarioEntrada = "Sistema";

        try {
            int quantidade = Integer.parseInt(qtdStr);
            Item item = new Item();
            item.setNome(nome);
            item.setQuantidade(quantidade);
            item.setDataEntrada(new java.sql.Date(System.currentTimeMillis()));
            item.setNomeUsuarioEntrada(usuarioEntrada);

            itemService.adicionarItem(item);
            JOptionPane.showMessageDialog(this, "Produto adicionado com sucesso!");
            carregarDados();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar produto: " + ex.getMessage());
        }
    }

    private void atualizarProduto() {
        int linha = tabelaEstoque.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para atualizar!");
            return;
        }

        int id = (int) modelo.getValueAt(linha, 0);
        String nomeAtual = (String) modelo.getValueAt(linha, 1);
        int qtdAtual = (int) modelo.getValueAt(linha, 2);
        String usuarioEntradaAtual = (String) modelo.getValueAt(linha, 4);

        String novoNome = JOptionPane.showInputDialog(this, "Novo nome:", nomeAtual);
        if (novoNome == null || novoNome.trim().isEmpty()) return;

        String novaQtdStr = JOptionPane.showInputDialog(this, "Nova quantidade:", qtdAtual);
        if (novaQtdStr == null || novaQtdStr.trim().isEmpty()) return;

        String novoUsuario = JOptionPane.showInputDialog(this, "Usuário responsável pela entrada:", usuarioEntradaAtual);
        if (novoUsuario == null || novoUsuario.trim().isEmpty()) novoUsuario = usuarioEntradaAtual;

        try {
            int novaQtd = Integer.parseInt(novaQtdStr);

            Item novoItem = new Item();
            novoItem.setNome(novoNome);
            novoItem.setQuantidade(novaQtd);
            novoItem.setDataEntrada(new java.sql.Date(System.currentTimeMillis()));
            novoItem.setNomeUsuarioEntrada(novoUsuario);

            boolean atualizado = itemService.atualizarItem(id, novoItem);
            if (atualizado) {
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
                carregarDados();
            } else {
                JOptionPane.showMessageDialog(this, "Erro: produto não encontrado!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar produto: " + ex.getMessage());
        }
    }

    private void retirarProduto() {
        int linha = tabelaEstoque.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para retirar!");
            return;
        }

        int id = (int) modelo.getValueAt(linha, 0);
        String nomeProduto = (String) modelo.getValueAt(linha, 1);
        int qtdAtual = (int) modelo.getValueAt(linha, 2);

        String qtdRetiradaStr = JOptionPane.showInputDialog(this, "Quantidade a retirar de " + nomeProduto + ":");
        if (qtdRetiradaStr == null || qtdRetiradaStr.trim().isEmpty()) return;

        try {
            int qtdRetirada = Integer.parseInt(qtdRetiradaStr);
            if (qtdRetirada <= 0 || qtdRetirada > qtdAtual) {
                JOptionPane.showMessageDialog(this, "Quantidade inválida para retirada!");
                return;
            }

            String usuarioRetirada = JOptionPane.showInputDialog(this, "Usuário que retirou:");
            if (usuarioRetirada == null || usuarioRetirada.trim().isEmpty()) usuarioRetirada = "Desconhecido";

            Item itemAtualizado = new Item();
            itemAtualizado.setNome(nomeProduto);
            itemAtualizado.setQuantidade(qtdAtual - qtdRetirada);
            itemAtualizado.setDataEntrada(new java.sql.Date(System.currentTimeMillis()));
            itemAtualizado.setNomeUsuarioEntrada((String) modelo.getValueAt(linha, 4));
            itemAtualizado.setDataRetirada(new java.sql.Date(System.currentTimeMillis()));
            itemAtualizado.setNomeUsuarioRetirada(usuarioRetirada);

            boolean atualizado = itemService.deletarItem(id, usuarioRetirada);
            if (atualizado) {
                JOptionPane.showMessageDialog(this, "Retirada registrada com sucesso!");
                carregarDados();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao registrar retirada!");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor inválido!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao retirar produto: " + ex.getMessage());
        }
    }
}
