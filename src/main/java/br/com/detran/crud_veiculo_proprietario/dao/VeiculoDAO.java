package br.com.detran.crud_veiculo_proprietario.dao;

import br.com.detran.crud_veiculo_proprietario.model.Veiculo;
import br.com.detran.crud_veiculo_proprietario.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {

    public boolean inserir(Veiculo veiculo) {
        String sql = "INSERT INTO cadastro.veiculo (placa, renavam, id_prop) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, veiculo.getPlaca().toUpperCase());
            stmt.setString(2, veiculo.getRenavam());
            stmt.setInt(3, veiculo.getIdProp());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    veiculo.setId(rs.getInt(1));
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.err.println("Erro ao inserir veículo: " + e.getMessage());
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

    public List<Veiculo> buscarTodos() {
        String sql = "SELECT v.id, v.placa, v.renavam, v.id_prop, " +
                "p.cpf_cnpj, p.nome, p.endereco " +
                "FROM cadastro.veiculo v " +
                "INNER JOIN cadastro.proprietario p ON v.id_prop = p.id " +
                "ORDER BY v.placa";

        List<Veiculo> lista = new ArrayList<Veiculo>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Veiculo v = new Veiculo();
                v.setId(rs.getInt("id"));
                v.setPlaca(rs.getString("placa"));
                v.setRenavam(rs.getString("renavam"));
                v.setIdProp(rs.getInt("id_prop"));
                v.setProprietarioCpfCnpj(rs.getString("cpf_cnpj"));
                v.setProprietarioNome(rs.getString("nome"));
                v.setProprietarioEndereco(rs.getString("endereco"));
                lista.add(v);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar veículos: " + e.getMessage());
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

    public Veiculo buscarPorPlaca(String placa) {
        String sql = "SELECT * FROM funcoes.buscar_veiculo_por_placa(?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Veiculo veiculo = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, placa);
            rs = stmt.executeQuery();

            if (rs.next()) {
                veiculo = new Veiculo();
                veiculo.setId(rs.getInt("veiculo_id"));
                veiculo.setPlaca(rs.getString("veiculo_placa"));
                veiculo.setRenavam(rs.getString("veiculo_renavam"));
                veiculo.setIdProp(rs.getInt("proprietario_id"));
                veiculo.setProprietarioCpfCnpj(rs.getString("proprietario_cpf_cnpj"));
                veiculo.setProprietarioNome(rs.getString("proprietario_nome"));
                veiculo.setProprietarioEndereco(rs.getString("proprietario_endereco"));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar por placa: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return veiculo;
    }

    public List<Veiculo> buscarPorCpfCnpj(String cpfCnpj) {
        String sql = "SELECT * FROM funcoes.buscar_veiculo_por_cpf_cnpj(?)";
        List<Veiculo> lista = new ArrayList<Veiculo>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpfCnpj);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Veiculo v = new Veiculo();
                v.setId(rs.getInt("veiculo_id"));
                v.setPlaca(rs.getString("veiculo_placa"));
                v.setRenavam(rs.getString("veiculo_renavam"));
                v.setIdProp(rs.getInt("proprietario_id"));
                v.setProprietarioCpfCnpj(rs.getString("proprietario_cpf_cnpj"));
                v.setProprietarioNome(rs.getString("proprietario_nome"));
                v.setProprietarioEndereco(rs.getString("proprietario_endereco"));
                lista.add(v);
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

        return lista;
    }

    public List<Veiculo> buscarPorProprietario(Integer idProp) {
        String sql = "SELECT v.id, v.placa, v.renavam, v.id_prop, " +
                "p.cpf_cnpj, p.nome, p.endereco " +
                "FROM cadastro.veiculo v " +
                "INNER JOIN cadastro.proprietario p ON v.id_prop = p.id " +
                "WHERE v.id_prop = ? ORDER BY v.placa";

        List<Veiculo> lista = new ArrayList<Veiculo>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idProp);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Veiculo v = new Veiculo();
                v.setId(rs.getInt("id"));
                v.setPlaca(rs.getString("placa"));
                v.setRenavam(rs.getString("renavam"));
                v.setIdProp(rs.getInt("id_prop"));
                v.setProprietarioCpfCnpj(rs.getString("cpf_cnpj"));
                v.setProprietarioNome(rs.getString("nome"));
                v.setProprietarioEndereco(rs.getString("endereco"));
                lista.add(v);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar veículos por proprietário: " + e.getMessage());
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

    public boolean atualizar(Veiculo veiculo) {
        String sql = "UPDATE cadastro.veiculo SET placa = ?, renavam = ?, id_prop = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, veiculo.getPlaca().toUpperCase());
            stmt.setString(2, veiculo.getRenavam());
            stmt.setInt(3, veiculo.getIdProp());
            stmt.setInt(4, veiculo.getId());

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
        String sql = "DELETE FROM cadastro.veiculo WHERE id = ?";
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