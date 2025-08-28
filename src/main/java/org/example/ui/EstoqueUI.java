package org.example.ui;



import org.example.config.DatabaseConnectionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class EstoqueUI extends JFrame {
    private JTable tabelaEstoque;

    public EstoqueUI() {
        setTitle("Sistema - Estoque");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Modelo da tabela
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nome do Item");
        modelo.addColumn("Quantidade");

        tabelaEstoque = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabelaEstoque);

        add(scrollPane, BorderLayout.CENTER);

        // Carrega os dados do banco
        carregarDados(modelo);
    }

    private void carregarDados(DefaultTableModel modelo) {
        try (Connection conn = DatabaseConnectionFactory.getConnection()) {
            String sql = "SELECT id, nome, quantidade FROM estoque";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("quantidade")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar estoque: " + e.getMessage());
        }
    }
}
