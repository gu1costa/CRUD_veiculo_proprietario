package br.com.detran.crud_veiculo_proprietario.util;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.io.IOException;
import java.io.Reader;

public class SqlMapClientUtil {

    private static SqlMapClient sqlMapClient;

    static {
        try {
            Reader reader = com.ibatis.common.resources.Resources.getResourceAsReader("SqlMapConfig.xml");
            sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
            reader.close();
            System.out.println("✓ SqlMapClient inicializado com sucesso!");
        } catch (IOException e) {
            System.err.println("✗ Erro ao inicializar SqlMapClient: " + e.getMessage());
            throw new RuntimeException("Falha ao carregar SqlMapConfig.xml", e);
        }
    }

    public static SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }
}