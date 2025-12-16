package br.com.detran.crud_veiculo_proprietario.action;

import br.com.detran.crud_veiculo_proprietario.dao.ProprietarioDAO;
import br.com.detran.crud_veiculo_proprietario.dao.VeiculoDAO;
import br.com.detran.crud_veiculo_proprietario.form.VeiculoForm;
import br.com.detran.crud_veiculo_proprietario.model.Proprietario;
import br.com.detran.crud_veiculo_proprietario.model.Veiculo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class VeiculoAction extends Action {

    private VeiculoDAO veiculoDAO = new VeiculoDAO();
    private ProprietarioDAO proprietarioDAO = new ProprietarioDAO();

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) {

        String action = request.getParameter("action");

        if ("listar".equals(action) || action == null) {
            return listar(mapping, request);
        } else if ("novo".equals(action)) {
            return novo(mapping, request);
        } else if ("editar".equals(action)) {
            return editar(mapping, request);
        } else if ("salvar".equals(action)) {
            return salvar(mapping, form, request);
        } else if ("deletar".equals(action)) {
            return deletar(mapping, request);
        }

        return mapping.findForward("listar");
    }

    private ActionForward listar(ActionMapping mapping, HttpServletRequest request) {
        List<Veiculo> veiculos = veiculoDAO.buscarTodos();
        request.setAttribute("veiculos", veiculos);
        return mapping.findForward("listar");
    }

    private ActionForward novo(ActionMapping mapping, HttpServletRequest request) {
        List<Proprietario> proprietarios = proprietarioDAO.buscarTodos();
        request.setAttribute("proprietarios", proprietarios);
        return mapping.findForward("form");
    }

    private ActionForward editar(ActionMapping mapping, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Veiculo veiculo = veiculoDAO.buscarPorId(id);
        List<Proprietario> proprietarios = proprietarioDAO.buscarTodos();

        request.setAttribute("veiculo", veiculo);
        request.setAttribute("proprietarios", proprietarios);

        return mapping.findForward("form");
    }

    private ActionForward salvar(ActionMapping mapping, ActionForm form, HttpServletRequest request) {
        VeiculoForm veiculoForm = (VeiculoForm) form;

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(veiculoForm.getPlaca().toUpperCase());
        veiculo.setRenavam(veiculoForm.getRenavam());
        veiculo.setIdProp(Integer.parseInt(veiculoForm.getIdProp()));

        String idStr = veiculoForm.getId();
        String origem = veiculoForm.getOrigem();

        if (idStr != null && !idStr.isEmpty()) {
            veiculo.setId(Integer.parseInt(idStr));
            veiculoDAO.atualizar(veiculo);
            request.setAttribute("mensagem", "Veículo atualizado com sucesso!");
        } else {
            veiculoDAO.inserir(veiculo);
            request.setAttribute("mensagem", "Veículo cadastrado com sucesso!");
        }

        if ("proprietario".equals(origem)) {
            return new ActionForward("/proprietario.do?action=editar&id=" + veiculo.getIdProp(), true);
        }

        return listar(mapping, request);
    }

    private ActionForward deletar(ActionMapping mapping, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        String origem = request.getParameter("origem");
        String idProp = request.getParameter("idProp");

        veiculoDAO.deletar(id);

        if ("proprietario".equals(origem) && idProp != null) {
            Proprietario proprietario = proprietarioDAO.buscarPorId(Integer.parseInt(idProp));
            request.setAttribute("proprietario", proprietario);

            List<Veiculo> veiculos = veiculoDAO.buscarPorProprietario(proprietario.getId());
            request.setAttribute("veiculos", veiculos);

            request.setAttribute("msg", "veiculo_deletado"); // msg usada no JSP
            return mapping.findForward("proprietarioForm"); // JSP do formulário
        }

        request.setAttribute("mensagem", "Veículo removido com sucesso!");
        return listar(mapping, request);
    }
}