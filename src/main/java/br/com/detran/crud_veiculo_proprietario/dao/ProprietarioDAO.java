package br.com.detran.crud_veiculo_proprietario.dao;

import br.com.detran.crud_veiculo_proprietario.model.Proprietario;
import br.com.detran.crud_veiculo_proprietario.util.SqlMapClientUtil;
import com.ibatis.sqlmap.client.SqlMapClient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioDAO {

    private SqlMapClient sqlMapClient;

    public ProprietarioDAO() {
        this.sqlMapClient = SqlMapClientUtil.getSqlMapClient();
    }

    public boolean inserir(Proprietario proprietario) {
        try {
            sqlMapClient.insert("Proprietario.inserirProprietario", proprietario);
            System.out.println("✓ Proprietário inserido! ID: " + proprietario.getId());
            return true;
        } catch (SQLException e) {
            System.err.println("✗ Erro ao inserir proprietário: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizar(Proprietario proprietario) {
        try {
            int rows = sqlMapClient.update("Proprietario.atualizarProprietario", proprietario);
            System.out.println("✓ Proprietário atualizado!");
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("✗ Erro ao atualizar proprietário: " + e.getMessage());
            return false;
        }
    }

    public boolean deletar(Integer id) {
        try {
            int rows = sqlMapClient.delete("Proprietario.deletarProprietario", id);
            System.out.println("✓ Proprietário deletado!");
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("✗ Erro ao deletar proprietário: " + e.getMessage());
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Proprietario> buscarTodos() {
        try {
            return sqlMapClient.queryForList("Proprietario.buscarTodos");
        } catch (SQLException e) {
            System.err.println("✗ Erro ao buscar proprietários: " + e.getMessage());
            return new ArrayList<Proprietario>();
        }
    }

    public Proprietario buscarPorId(Integer id) {
        try {
            return (Proprietario) sqlMapClient.queryForObject("Proprietario.buscarPorId", id);
        } catch (SQLException e) {
            System.err.println("✗ Erro ao buscar proprietário por ID: " + e.getMessage());
            return null;
        }
    }

    public Proprietario buscarPorCpfCnpj(String cpfCnpj) {
        try {
            return (Proprietario) sqlMapClient.queryForObject("Proprietario.buscarPorCpfCnpj", cpfCnpj);
        } catch (SQLException e) {
            System.err.println("✗ Erro ao buscar proprietário por CPF/CNPJ: " + e.getMessage());
            return null;
        }
    }

    public int contar() {
        try {
            Integer count = (Integer) sqlMapClient.queryForObject("Proprietario.contarProprietarios");
            return count != null ? count : 0;
        } catch (SQLException e) {
            System.err.println("✗ Erro ao contar proprietários: " + e.getMessage());
            return 0;
        }
    }
}