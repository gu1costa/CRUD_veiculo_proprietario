package br.com.detran.crud_veiculo_proprietario.action;

import br.com.detran.crud_veiculo_proprietario.dao.ProprietarioDAO;
import br.com.detran.crud_veiculo_proprietario.dao.VeiculoDAO;
import br.com.detran.crud_veiculo_proprietario.form.ProprietarioForm;
import br.com.detran.crud_veiculo_proprietario.model.Proprietario;
import br.com.detran.crud_veiculo_proprietario.model.Veiculo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ProprietarioAction extends Action {

    private ProprietarioDAO proprietarioDAO = new ProprietarioDAO();
    private VeiculoDAO veiculoDAO = new VeiculoDAO();

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
        List<Proprietario> proprietarios = proprietarioDAO.buscarTodos();
        request.setAttribute("proprietarios", proprietarios);
        return mapping.findForward("listar");
    }

    private ActionForward novo(ActionMapping mapping, HttpServletRequest request) {
        return mapping.findForward("form");
    }

    private ActionForward editar(ActionMapping mapping, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Proprietario proprietario = proprietarioDAO.buscarPorId(id);
        List<Veiculo> veiculos = veiculoDAO.buscarPorProprietario(id);

        request.setAttribute("proprietario", proprietario);
        request.setAttribute("veiculos", veiculos);

        return mapping.findForward("form");
    }

    private ActionForward salvar(ActionMapping mapping, ActionForm form, HttpServletRequest request) {
        ProprietarioForm proprietarioForm = (ProprietarioForm) form;

        Proprietario proprietario = new Proprietario();
        proprietario.setCpfCnpj(proprietarioForm.getCpfCnpj());
        proprietario.setNome(proprietarioForm.getNome());
        proprietario.setEndereco(proprietarioForm.getEndereco());

        String idStr = proprietarioForm.getId();
        if (idStr != null && !idStr.isEmpty()) {
            proprietario.setId(Integer.parseInt(idStr));
            proprietarioDAO.atualizar(proprietario);
            request.setAttribute("mensagem", "Proprietário atualizado com sucesso!");
        } else {
            proprietarioDAO.inserir(proprietario);
            request.setAttribute("mensagem", "Proprietário cadastrado com sucesso!");
        }

        return listar(mapping, request);
    }

    private ActionForward deletar(ActionMapping mapping, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        proprietarioDAO.deletar(id);
        request.setAttribute("mensagem", "Proprietário removido com sucesso!");
        return listar(mapping, request);
    }
}