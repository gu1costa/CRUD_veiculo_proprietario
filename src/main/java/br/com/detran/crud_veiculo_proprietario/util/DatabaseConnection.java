package br.com.detran.crud_veiculo_proprietario.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/sistema_veiculo_proprietario";
    private static final String USER = "postgres";  // Seu usuário
    private static final String PASSWORD = "postgres";  // Sua senha
    private static final String DRIVER = "org.postgresql.Driver";

    static {
        try {
            Class.forName(DRIVER);
            System.out.println("✓ Driver PostgreSQL carregado com sucesso!");
        } catch (ClassNotFoundException e) {
            System.err.println("✗ Erro ao carregar o driver PostgreSQL: " + e.getMessage());
            throw new RuntimeException("Driver não encontrado", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✓ Conexão estabelecida com sucesso!");
            return conn;
        } catch (SQLException e) {
            System.err.println("✗ Erro ao conectar ao banco de dados: " + e.getMessage());
            throw e;
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("✓ Conexão fechada!");
            } catch (SQLException e) {
                System.err.println("✗ Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            boolean isValid = conn.isValid(5);
            closeConnection(conn);
            return isValid;
        } catch (SQLException e) {
            System.err.println("✗ Falha no teste de conexão: " + e.getMessage());
            return false;
        }
    }
}