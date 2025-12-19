package br.com.detran.crud_veiculo_proprietario.action;

import java.io.InputStream;
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
import br.com.detran.crud_veiculo_proprietario.util.DatabaseConnection;

public class RelatorioAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();

            // 1. Pega o parâmetro 'tipo' da URL (ex: ?tipo=proprietarios)
            String tipo = request.getParameter("tipo");

            String arquivoJasper = "relatorio-veiculos.jasper";
            String nomeDownload = "relatorio-veiculos.pdf";

            // 2. Se for proprietários, muda o arquivo e o nome do PDF de saída
            if ("proprietarios".equals(tipo)) {
                arquivoJasper = "relatorio-proprietarios.jasper";
                nomeDownload = "relatorio-proprietarios.pdf";
            }

            // 3. Carrega via Stream (mais seguro que getRealPath para arquivos dentro de classes)
            InputStream reportStream = this.getClass().getResourceAsStream("/relatorios/" + arquivoJasper);

            if (reportStream == null) {
                throw new RuntimeException("Arquivo não encontrado no classpath: /relatorios/" + arquivoJasper);
            }

            Map<String, Object> parameters = new HashMap<String, Object>();

            // 4. Geração do PDF
            byte[] pdfBytes = JasperRunManager.runReportToPdf(reportStream, parameters, conn);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=" + nomeDownload);
            response.setContentLength(pdfBytes.length);

            ServletOutputStream out = response.getOutputStream();
            out.write(pdfBytes);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null && !conn.isClosed()) conn.close();
        }

        return null;
    }
}