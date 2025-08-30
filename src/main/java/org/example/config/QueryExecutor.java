package org.example.config;




import java.sql.*;

public class QueryExecutor {

    // Executa INSERT, UPDATE, DELETE, ALTER TABLE
    public static void executarUpdate(String sql) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {

            int linhasAfetadas = stmt.executeUpdate(sql);
            System.out.println("✅ Query executada com sucesso! Linhas afetadas: " + linhasAfetadas);

        } catch (SQLException e) {
            System.err.println("❌ Erro ao executar query: " + e.getMessage());
        }
    }

    // Executa SELECT e mostra os resultados
    public static void executarSelect(String sql) {
        try (Connection conn = DatabaseConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int colunas = metaData.getColumnCount();

            System.out.println("📊 Resultados da consulta:");
            while (rs.next()) {
                for (int i = 1; i <= colunas; i++) {
                    System.out.print(metaData.getColumnName(i) + ": " + rs.getString(i) + " | ");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao executar SELECT: " + e.getMessage());
        }
    }
}