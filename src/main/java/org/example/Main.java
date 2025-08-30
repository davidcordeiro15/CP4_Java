package org.example;

import org.example.config.DatabaseConnectionFactory;
import org.example.config.QueryExecutor;
import org.example.domain.Usuario;
import org.example.domain.Item;
import org.example.service.UsuarioService;
import org.example.service.ItemService;
import org.example.ui.LoginUI;

import javax.swing.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) {



        SwingUtilities.invokeLater(() -> {
            LoginUI tela = new LoginUI();
            tela.setVisible(true);
        });

        // Caso queira criar a tabela e inserir valores iniciais nela descomente a linha abaixo e rode o código
        //Exemplo: listar produtos
        /*String sql = "SELECT e.id, e.nome, e.quantidade, e.data_entrada, e.data_retirada, " +
                "u1.nome AS usuarioEntrada, " +
                "u2.nome AS usuarioRetirada " +
                "FROM estoque e " +
                "LEFT JOIN usuarios u1 ON e.usuarioEntrada = u1.id " +
                "LEFT JOIN usuarios u2 ON e.usuarioRetirada = u2.id";

        QueryExecutor.executarSelect(sql);*/


    }
}
