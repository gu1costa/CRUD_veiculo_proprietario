package br.com.detran.crud_veiculo_proprietario.action;

import br.com.detran.crud_veiculo_proprietario.dao.ProprietarioDAO;
import br.com.detran.crud_veiculo_proprietario.dao.VeiculoDAO;
import br.com.detran.crud_veiculo_proprietario.form.ProprietarioForm;
import br.com.detran.crud_veiculo_proprietario.model.Proprietario;
import br.com.detran.crud_veiculo_proprietario.model.Veiculo;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

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

        // ✅ Validação manual (porque no struts-config está validate="false")
        ActionErrors errors = proprietarioForm.validate(mapping, request);
        if (errors != null && !errors.isEmpty()) {
            saveErrors(request, errors);

            Proprietario p = montarProprietarioDoForm(proprietarioForm);
            carregarVeiculosSeEdicao(request, p);

            request.setAttribute("proprietario", p);
            return mapping.findForward("form");
        }

        Proprietario proprietario = montarProprietarioDoForm(proprietarioForm);
        boolean isEdicao = proprietario.getId() != null && proprietario.getId() > 0;

        // ✅ Regra de negócio: CPF/CNPJ não pode repetir
        if (cpfCnpjJaExisteParaOutroRegistro(proprietario.getCpfCnpj(), proprietario.getId())) {
            ActionErrors dup = new ActionErrors();
            dup.add("cpfCnpj", new ActionMessage("error.cpfCnpj.duplicate"));
            saveErrors(request, dup);

            carregarVeiculosSeEdicao(request, proprietario);
            request.setAttribute("proprietario", proprietario);

            return mapping.findForward("form");
        }

        // ✅ Persistência (agora respeitando o retorno boolean do DAO)
        if (isEdicao) {
            boolean ok = proprietarioDAO.atualizar(proprietario);
            if (!ok) {
                ActionErrors dbErr = new ActionErrors();
                dbErr.add("cpfCnpj", new ActionMessage("message.error"));
                saveErrors(request, dbErr);

                carregarVeiculosSeEdicao(request, proprietario);
                request.setAttribute("proprietario", proprietario);
                return mapping.findForward("form");
            }

            request.setAttribute("mensagem", "Proprietário atualizado com sucesso!");
            return listar(mapping, request);
        } else {
            boolean ok = proprietarioDAO.inserir(proprietario);
            if (!ok) {
                // fallback caso tenha dado erro no insert (inclui UNIQUE se por corrida)
                // (o normal é cair na validação acima, mas isso cobre concorrência)
                ActionErrors dbErr = new ActionErrors();
                // se o CPF/CNPJ já existe agora, mostra mensagem certa
                if (cpfCnpjJaExisteParaOutroRegistro(proprietario.getCpfCnpj(), null)) {
                    dbErr.add("cpfCnpj", new ActionMessage("error.cpfCnpj.duplicate"));
                } else {
                    dbErr.add("cpfCnpj", new ActionMessage("message.error"));
                }
                saveErrors(request, dbErr);

                request.setAttribute("proprietario", proprietario);
                return mapping.findForward("form");
            }

            request.setAttribute("mensagem", "Proprietário cadastrado com sucesso!");
            return listar(mapping, request);
        }
    }

    private ActionForward deletar(ActionMapping mapping, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        proprietarioDAO.deletar(id);
        request.setAttribute("mensagem", "Proprietário removido com sucesso!");
        return listar(mapping, request);
    }

    // ------------------------
    // Helpers (organização)
    // ------------------------

    private Proprietario montarProprietarioDoForm(ProprietarioForm proprietarioForm) {
        Proprietario p = new Proprietario();

        String idStr = proprietarioForm.getId();
        if (idStr != null && !idStr.trim().isEmpty()) {
            p.setId(Integer.parseInt(idStr));
        }

        p.setCpfCnpj(proprietarioForm.getCpfCnpj());
        p.setNome(proprietarioForm.getNome());
        p.setEndereco(proprietarioForm.getEndereco());
        return p;
    }

    private void carregarVeiculosSeEdicao(HttpServletRequest request, Proprietario p) {
        if (p.getId() != null && p.getId() > 0) {
            List<Veiculo> veiculos = veiculoDAO.buscarPorProprietario(p.getId());
            request.setAttribute("veiculos", veiculos);
        }
    }

    /**
     * Retorna true se já existir um proprietário com o mesmo CPF/CNPJ,
     * considerando criação e edição.
     *
     * @param cpfCnpj CPF/CNPJ digitado
     * @param idAtual id do registro atual (null se for criação)
     */
    private boolean cpfCnpjJaExisteParaOutroRegistro(String cpfCnpj, Integer idAtual) {
        if (cpfCnpj == null || cpfCnpj.trim().isEmpty()) return false;

        Proprietario existente = proprietarioDAO.buscarPorCpfCnpj(cpfCnpj);

        if (existente == null) return false;

        // criação: se encontrou alguém, é duplicado
        if (idAtual == null) return true;

        // edição: é duplicado se o cpfCnpj pertence a outro id
        return existente.getId() != null && existente.getId().intValue() != idAtual.intValue();
    }
}
