package br.com.detran.crud_veiculo_proprietario.dao;

import br.com.detran.crud_veiculo_proprietario.model.Veiculo;
import br.com.detran.crud_veiculo_proprietario.util.SqlMapClientUtil;
import com.ibatis.sqlmap.client.SqlMapClient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {

    private SqlMapClient sqlMapClient;

    public VeiculoDAO() {
        this.sqlMapClient = SqlMapClientUtil.getSqlMapClient();
    }

    public boolean inserir(Veiculo veiculo) {
        try {
            sqlMapClient.insert("Veiculo.inserirVeiculo", veiculo);
            System.out.println("✓ Veículo inserido! ID: " + veiculo.getId());
            return true;
        } catch (SQLException e) {
            System.err.println("✗ Erro ao inserir veículo: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizar(Veiculo veiculo) {
        try {
            int rows = sqlMapClient.update("Veiculo.atualizarVeiculo", veiculo);
            System.out.println("✓ Veículo atualizado!");
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("✗ Erro ao atualizar veículo: " + e.getMessage());
            return false;
        }
    }

    public boolean deletar(Integer id) {
        try {
            int rows = sqlMapClient.delete("Veiculo.deletarVeiculo", id);
            System.out.println("✓ Veículo deletado!");
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("✗ Erro ao deletar veículo: " + e.getMessage());
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Veiculo> buscarTodos() {
        try {
            return sqlMapClient.queryForList("Veiculo.buscarTodos");
        } catch (SQLException e) {
            System.err.println("✗ Erro ao buscar veículos: " + e.getMessage());
            return new ArrayList<Veiculo>();
        }
    }

    public Veiculo buscarPorId(Integer id) {
        try {
            return (Veiculo) sqlMapClient.queryForObject("Veiculo.buscarPorId", id);
        } catch (SQLException e) {
            System.err.println("✗ Erro ao buscar veículo por ID: " + e.getMessage());
            return null;
        }
    }

    public Veiculo buscarPorPlaca(String placa) {
        try {
            return (Veiculo) sqlMapClient.queryForObject("Veiculo.buscarPorPlaca", placa);
        } catch (SQLException e) {
            System.err.println("✗ Erro ao buscar veículo por placa: " + e.getMessage());
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Veiculo> buscarPorCpfCnpj(String cpfCnpj) {
        try {
            return sqlMapClient.queryForList("Veiculo.buscarPorCpfCnpj", cpfCnpj);
        } catch (SQLException e) {
            System.err.println("✗ Erro ao buscar veículos por CPF/CNPJ: " + e.getMessage());
            return new ArrayList<Veiculo>();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Veiculo> buscarPorProprietario(Integer idProp) {
        try {
            return sqlMapClient.queryForList("Veiculo.buscarPorProprietario", idProp);
        } catch (SQLException e) {
            System.err.println("✗ Erro ao buscar veículos por proprietário: " + e.getMessage());
            return new ArrayList<Veiculo>();
        }
    }

    public int contar() {
        try {
            Integer count = (Integer) sqlMapClient.queryForObject("Veiculo.contarVeiculos");
            return count != null ? count : 0;
        } catch (SQLException e) {
            System.err.println("✗ Erro ao contar veículos: " + e.getMessage());
            return 0;
        }
    }
}