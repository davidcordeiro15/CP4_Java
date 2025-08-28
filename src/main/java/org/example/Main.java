package org.example;

import org.example.config.DatabaseConnectionFactory;
import org.example.domain.Usuario;
import org.example.domain.Item;
import org.example.service.UsuarioService;
import org.example.service.ItemService;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Caso queira criar a tabela e inserir valores iniciais nela descomente a linha abaixo e rode o código

        // setupService.setupDatabase();

        UsuarioService usuarioService = new UsuarioService();
        ItemService itemService = new ItemService();

        try {
            System.out.println("===== SISTEMA DE TESTE =====");

            // ====================== USUÁRIOS ======================
            System.out.println("\n--- Criando usuários ---");
            Usuario u1 = new Usuario();
            u1.setNome("Carlos Silva");
            u1.setEmail("carlos@email.com");
            usuarioService.adicionarUsuario(u1);
            System.out.println("Usuário Carlos Silva criado com sucesso!");

            Usuario u2 = new Usuario();
            u2.setNome("Ana Souza");
            u2.setEmail("ana@email.com");
            usuarioService.adicionarUsuario(u2);
            System.out.println("Usuário Ana Souza criado com sucesso!");

            System.out.println("\n--- Listando usuários ---");
            listarUsuarios(usuarioService.listarUsuarios());

            // Atualizar usuário
            Usuario novoU = new Usuario();
            novoU.setNome("Ana Oliveira");
            novoU.setEmail("ana.oliveira@email.com");
            usuarioService.atualizarUsuario("Ana Souza", "ana@email.com", novoU);
            System.out.println("Usuário Ana Souza atualizado para Ana Oliveira!");

            System.out.println("\n--- Listando usuários após atualização ---");
            listarUsuarios(usuarioService.listarUsuarios());

            // Deletar usuário
            usuarioService.deletarUsuario("Carlos Silva", "carlos@email.com");
            System.out.println("Usuário Carlos Silva deletado!");

            System.out.println("\n--- Listando usuários após exclusão ---");
            listarUsuarios(usuarioService.listarUsuarios());

            // ====================== ITENS ======================
            System.out.println("\n--- Criando itens ---");
            Item i1 = new Item();
            i1.setNome("Seringa 10ml");
            i1.setQuantidade(100);
            i1.setDataEntrada(new Date(System.currentTimeMillis()));
            i1.setUsuarioRetirada("Ana Oliveira");
            itemService.adicionarItem(i1);
            System.out.println("Item Seringa 10ml criado com sucesso!");

            Item i2 = new Item();
            i2.setNome("Luvas Cirúrgicas");
            i2.setQuantidade(200);
            i2.setDataEntrada(new Date(System.currentTimeMillis()));
            i2.setUsuarioRetirada("Ana Oliveira");
            itemService.adicionarItem(i2);
            System.out.println("Item Luvas Cirúrgicas criado com sucesso!");

            System.out.println("\n--- Listando itens ---");
            listarItens(itemService.listarItens());

            // Atualizar item
            Item novoI = new Item();
            novoI.setNome("Luvas Hospitalares");
            novoI.setQuantidade(150);
            novoI.setDataEntrada(new Date(System.currentTimeMillis()));
            novoI.setUsuarioRetirada("Ana Oliveira");

            List<Item> itens = itemService.listarItens();
            int idAtualizar = itens.get(1).getId(); // atualizando o segundo item
            itemService.atualizarItem(idAtualizar, novoI);
            System.out.println("Item Luvas Cirúrgicas atualizado para Luvas Hospitalares!");

            System.out.println("\n--- Listando itens após atualização ---");
            listarItens(itemService.listarItens());

            // Deletar item
            int idDeletar = itens.get(0).getId(); // deletando o primeiro item
            itemService.deletarItem(idDeletar);
            System.out.println("Item Seringa 10ml deletado!");

            System.out.println("\n--- Listando itens após exclusão ---");
            listarItens(itemService.listarItens());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listarUsuarios(List<Usuario> usuarios) {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
        } else {
            for (Usuario u : usuarios) {
                System.out.println("Nome: " + u.getNome() + " | Email: " + u.getEmail());
            }
        }
    }

    private static void listarItens(List<Item> itens) {
        if (itens.isEmpty()) {
            System.out.println("Nenhum item encontrado.");
        } else {
            for (Item i : itens) {
                System.out.println("ID: " + i.getId() + " | Nome: " + i.getNome() +
                        " | Quantidade: " + i.getQuantidade() +
                        " | Entrada: " + i.getDataEntrada() +
                        " | Usuário Retirada: " + i.getUsuarioRetirada());
            }
        }
    }
}
