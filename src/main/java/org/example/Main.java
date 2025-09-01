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
        /*String sql = "ALTER TABLE estoque MODIFY usuarioRetirada VARCHAR2(100); ALTER TABLE estoque MODIFY usuarioEntrada VARCHAR2(100); ";

        QueryExecutor.executarSelect(sql);*/


    }
}
