package br.com.detran.crud_veiculo_proprietario.action;

import br.com.detran.crud_veiculo_proprietario.dao.ProprietarioDAO;
import br.com.detran.crud_veiculo_proprietario.dao.VeiculoDAO;
import br.com.detran.crud_veiculo_proprietario.form.VeiculoForm;
import br.com.detran.crud_veiculo_proprietario.model.Proprietario;
import br.com.detran.crud_veiculo_proprietario.model.Veiculo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class VeiculoAction extends Action {

    private final VeiculoDAO veiculoDAO = new VeiculoDAO();
    private final ProprietarioDAO proprietarioDAO = new ProprietarioDAO();

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) {

        String action = request.getParameter("action");
        if (action == null || "listar".equals(action)) {
            return listar(mapping, request);
        }

        switch (action) {
            case "novoForm":
                return novoForm(mapping, request);
            case "novo":
                return novo(mapping, request);
            case "editar":
                return editar(mapping, request);
            case "salvar":
                return salvar(mapping, form, request);
            case "deletar":
                return deletar(mapping, request);
            default:
                return listar(mapping, request);
        }
    }

    private ActionForward listar(ActionMapping mapping, HttpServletRequest request) {
        // Seu projeto não tem veiculos.jsp; então "listar" aqui vira abrir o formulário.
        List<Proprietario> proprietarios = proprietarioDAO.buscarTodos();
        request.setAttribute("proprietarios", proprietarios);
        return mapping.findForward("form");
    }

    private ActionForward novoForm(ActionMapping mapping, HttpServletRequest request) {
        String idProp = request.getParameter("idProp");
        if (idProp != null && !idProp.trim().isEmpty()) {
            Proprietario proprietario = proprietarioDAO.buscarPorId(Integer.parseInt(idProp));
            request.setAttribute("proprietario", proprietario);
        }
        return mapping.findForward("novoForm");
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
        request.setAttribute("proprietarios", proprietarios);
        request.setAttribute("veiculo", veiculo);

        return mapping.findForward("form");
    }

    private ActionForward salvar(ActionMapping mapping, ActionForm form, HttpServletRequest request) {
        VeiculoForm veiculoForm = (VeiculoForm) form;

        // ✅ VALIDAÇÃO MANUAL (porque no struts-config está validate="false")
        ActionErrors errors = veiculoForm.validate(mapping, request);
        if (errors != null && !errors.isEmpty()) {
            saveErrors(request, errors);
            return voltarParaTelaCorretaComContexto(mapping, request, veiculoForm);
        }

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(veiculoForm.getPlaca());
        veiculo.setRenavam(veiculoForm.getRenavam());
        veiculo.setIdProp(Integer.parseInt(veiculoForm.getIdProp()));

        String idStr = veiculoForm.getId();
        boolean isEdicao = idStr != null && !idStr.trim().isEmpty();
        if (isEdicao) {
            veiculo.setId(Integer.parseInt(idStr));
            veiculoDAO.atualizar(veiculo);
        } else {
            veiculoDAO.inserir(veiculo);
        }

        String origem = paramOrDefault(request.getParameter("origem"), veiculoForm.getOrigem(), "veiculo");
        if ("proprietario".equals(origem)) {
            String idProp = veiculoForm.getIdProp();
            return new ActionForward("proprietario.do?action=editar&id=" + idProp +
                    "&msg=" + (isEdicao ? "veiculo_atualizado" : "veiculo_adicionado"), true);
        } else if ("novoForm".equals(origem)) {
            // Veio da tela de adicionar veículo específica
            request.setAttribute("mensagem", "Veículo cadastrado com sucesso!");
            return mapping.findForward("novoForm");
        }

        return new ActionForward("veiculo.do?action=listar", true);
    }

    private ActionForward deletar(ActionMapping mapping, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        veiculoDAO.deletar(id);

        String origem = request.getParameter("origem");
        String idProp = request.getParameter("idProp");

        if ("proprietario".equals(origem) && idProp != null && !idProp.trim().isEmpty()) {
            return new ActionForward("proprietario.do?action=editar&id=" + idProp + "&msg=veiculo_deletado", true);
        }

        return new ActionForward("veiculo.do?action=listar", true);
    }

    private ActionForward voltarParaTelaCorretaComContexto(ActionMapping mapping, HttpServletRequest request, VeiculoForm veiculoForm) {
        String origem = paramOrDefault(request.getParameter("origem"), veiculoForm.getOrigem(), "veiculo");

        // guardar valores digitados pra repopular inputs
        request.setAttribute("placaTemp", veiculoForm.getPlaca());
        request.setAttribute("renavamTemp", veiculoForm.getRenavam());

        if ("proprietario".equals(origem)) {
            int idProp = Integer.parseInt(veiculoForm.getIdProp());
            Proprietario proprietario = proprietarioDAO.buscarPorId(idProp);
            request.setAttribute("proprietario", proprietario);
            request.setAttribute("veiculos", veiculoDAO.buscarPorProprietario(idProp));
            return mapping.findForward("proprietarioForm");
        } else if ("novoForm".equals(origem)) {
            // Veio da tela de adicionar veículo específica - manter na mesma tela
            String idProp = veiculoForm.getIdProp();
            if (idProp != null && !idProp.trim().isEmpty()) {
                Proprietario proprietario = proprietarioDAO.buscarPorId(Integer.parseInt(idProp));
                request.setAttribute("proprietario", proprietario);
            }
            return mapping.findForward("novoForm");
        }

        // tela veiculo-form.jsp
        request.setAttribute("proprietarios", proprietarioDAO.buscarTodos());

        // se for edição, reapresenta o mesmo veículo (sem marcar "novo" como edição)
        // (para edição, o JSP já usa request.getAttribute("veiculo"))
        if (veiculoForm.getId() != null && !veiculoForm.getId().trim().isEmpty()) {
            Veiculo v = new Veiculo();
            v.setId(Integer.parseInt(veiculoForm.getId()));
            v.setPlaca(veiculoForm.getPlaca());
            v.setRenavam(veiculoForm.getRenavam());
            v.setIdProp(Integer.parseInt(veiculoForm.getIdProp()));
            request.setAttribute("veiculo", v);
        }

        return mapping.findForward("form");
    }

    private String paramOrDefault(String p1, String p2, String def) {
        if (p1 != null && !p1.trim().isEmpty()) return p1.trim();
        if (p2 != null && !p2.trim().isEmpty()) return p2.trim();
        return def;
    }
}
