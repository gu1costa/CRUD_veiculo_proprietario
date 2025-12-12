package br.com.detran.crud_veiculo_proprietario.dao;

import br.com.detran.crud_veiculo_proprietario.model.Proprietario;
import br.com.detran.crud_veiculo_proprietario.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioDAO {

    public boolean inserir(Proprietario proprietario) {
        String sql = "INSERT INTO cadastro.proprietario (cpf_cnpj, nome, endereco) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, proprietario.getCpfCnpj());
            stmt.setString(2, proprietario.getNome());
            stmt.setString(3, proprietario.getEndereco());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    proprietario.setId(rs.getInt(1));
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.err.println("Erro ao inserir: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Proprietario> buscarTodos() {
        String sql = "SELECT id, cpf_cnpj, nome, endereco FROM cadastro.proprietario ORDER BY nome";
        List<Proprietario> lista = new ArrayList<Proprietario>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Proprietario p = new Proprietario();
                p.setId(rs.getInt("id"));
                p.setCpfCnpj(rs.getString("cpf_cnpj"));
                p.setNome(rs.getString("nome"));
                p.setEndereco(rs.getString("endereco"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return lista;
    }

    public Proprietario buscarPorId(Integer id) {
        String sql = "SELECT id, cpf_cnpj, nome, endereco FROM cadastro.proprietario WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Proprietario proprietario = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                proprietario = new Proprietario();
                proprietario.setId(rs.getInt("id"));
                proprietario.setCpfCnpj(rs.getString("cpf_cnpj"));
                proprietario.setNome(rs.getString("nome"));
                proprietario.setEndereco(rs.getString("endereco"));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar por ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return proprietario;
    }

    public Proprietario buscarPorCpfCnpj(String cpfCnpj) {
        String sql = "SELECT id, cpf_cnpj, nome, endereco FROM cadastro.proprietario WHERE cpf_cnpj = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Proprietario proprietario = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpfCnpj);
            rs = stmt.executeQuery();

            if (rs.next()) {
                proprietario = new Proprietario();
                proprietario.setId(rs.getInt("id"));
                proprietario.setCpfCnpj(rs.getString("cpf_cnpj"));
                proprietario.setNome(rs.getString("nome"));
                proprietario.setEndereco(rs.getString("endereco"));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar por CPF/CNPJ: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return proprietario;
    }

    public boolean atualizar(Proprietario proprietario) {
        String sql = "UPDATE cadastro.proprietario SET cpf_cnpj = ?, nome = ?, endereco = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, proprietario.getCpfCnpj());
            stmt.setString(2, proprietario.getNome());
            stmt.setString(3, proprietario.getEndereco());
            stmt.setInt(4, proprietario.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean deletar(Integer id) {
        String sql = "DELETE FROM cadastro.proprietario WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao deletar: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}