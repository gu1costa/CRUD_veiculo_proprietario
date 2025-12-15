package br.com.detran.crud_veiculo_proprietario.action;

import br.com.detran.crud_veiculo_proprietario.dao.VeiculoDAO;
import br.com.detran.crud_veiculo_proprietario.model.Veiculo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class BuscaAction extends Action {

    private VeiculoDAO veiculoDAO = new VeiculoDAO();

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) {

        String tipo = request.getParameter("tipo");
        String valor = request.getParameter("valor");

        if (valor != null && !valor.trim().isEmpty()) {
            if ("placa".equals(tipo)) {
                Veiculo veiculo = veiculoDAO.buscarPorPlaca(valor.trim());
                request.setAttribute("veiculo", veiculo);
                request.setAttribute("tipoBusca", "placa");
            } else if ("cpf".equals(tipo)) {
                List<Veiculo> veiculos = veiculoDAO.buscarPorCpfCnpj(valor.trim());
                request.setAttribute("veiculos", veiculos);
                request.setAttribute("tipoBusca", "cpf");
            }
            request.setAttribute("valorBusca", valor);
        }

        return mapping.findForward("busca");
    }
}