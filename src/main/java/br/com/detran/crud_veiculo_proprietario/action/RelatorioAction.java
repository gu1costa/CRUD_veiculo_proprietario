package br.com.detran.crud_veiculo_proprietario.action;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperRunManager;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import br.com.detran.crud_veiculo_proprietario.util.DatabaseConnection; // Ajuste conforme seu utilitário

public class RelatorioAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {

        Connection conn = null;
        try {
            // 1. Obter conexão (ajuste conforme sua DatabaseConnection)
            conn = DatabaseConnection.getConnection();

            // 2. Caminho do arquivo .jasper dentro do target/classes
            String jasperPath = getServlet().getServletContext()
                    .getRealPath("/WEB-INF/classes/relatorios/relatorio-veiculos.jasper");

            // 3. Parâmetros (vazio se não houver filtros)
            Map<String, Object> parameters = new HashMap<String, Object>();

            // 4. Gerar os bytes do PDF
            byte[] pdfBytes = JasperRunManager.runReportToPdf(jasperPath, parameters, conn);

            // 5. Configurar resposta para download/exibição
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=relatorio-veiculos.pdf");
            response.setContentLength(pdfBytes.length);

            ServletOutputStream out = response.getOutputStream();
            out.write(pdfBytes);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) conn.close();
        }

        return null; // Retorna null pois o PDF já foi escrito na resposta
    }
}