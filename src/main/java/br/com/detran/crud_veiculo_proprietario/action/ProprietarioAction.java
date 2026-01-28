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
        } else if ("buscar".equals(action)) {
            return buscar(mapping, request);
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
        int pagina = 1;
        int registrosPorPagina = 15;
        
        String paginaParam = request.getParameter("pagina");
        if (paginaParam != null && !paginaParam.trim().isEmpty()) {
            try {
                pagina = Integer.parseInt(paginaParam);
                if (pagina < 1) pagina = 1;
            } catch (NumberFormatException e) {
                pagina = 1;
            }
        }
        
        List<Proprietario> proprietarios = proprietarioDAO.buscarTodosPaginado(pagina, registrosPorPagina);
        int totalRegistros = proprietarioDAO.contar();
        int totalPaginas = (int) Math.ceil((double) totalRegistros / registrosPorPagina);
        
        request.setAttribute("proprietarios", proprietarios);
        request.setAttribute("paginaAtual", pagina);
        request.setAttribute("totalPaginas", totalPaginas);
        request.setAttribute("totalRegistros", totalRegistros);
        request.setAttribute("registrosPorPagina", registrosPorPagina);
        
        return mapping.findForward("listar");
    }

    private ActionForward buscar(ActionMapping mapping, HttpServletRequest request) {
        String nomeBusca = request.getParameter("nomeBusca");
        int pagina = 1;
        int registrosPorPagina = 15;
        
        String paginaParam = request.getParameter("pagina");
        if (paginaParam != null && !paginaParam.trim().isEmpty()) {
            try {
                pagina = Integer.parseInt(paginaParam);
                if (pagina < 1) pagina = 1;
            } catch (NumberFormatException e) {
                pagina = 1;
            }
        }
        
        List<Proprietario> proprietarios;
        int totalRegistros;
        
        if (nomeBusca != null && !nomeBusca.trim().isEmpty()) {
            proprietarios = proprietarioDAO.buscarPorNomePaginado(nomeBusca.trim().toUpperCase(), pagina, registrosPorPagina);
            totalRegistros = proprietarioDAO.contarPorNome(nomeBusca.trim().toUpperCase());
        } else {
            proprietarios = proprietarioDAO.buscarTodosPaginado(pagina, registrosPorPagina);
            totalRegistros = proprietarioDAO.contar();
        }
        
        int totalPaginas = (int) Math.ceil((double) totalRegistros / registrosPorPagina);
        
        request.setAttribute("proprietarios", proprietarios);
        request.setAttribute("paginaAtual", pagina);
        request.setAttribute("totalPaginas", totalPaginas);
        request.setAttribute("totalRegistros", totalRegistros);
        request.setAttribute("registrosPorPagina", registrosPorPagina);
        
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

        // ✅ validação manual (porque no struts-config está validate="false")
        ActionErrors errors = proprietarioForm.validate(mapping, request);
        if (errors != null && !errors.isEmpty()) {
            saveErrors(request, errors);

            // mantém os dados preenchidos
            Proprietario p = new Proprietario();
            String idStr = proprietarioForm.getId();

            if (idStr != null && !idStr.trim().isEmpty()) {
                int id = Integer.parseInt(idStr);
                p.setId(id);
                request.setAttribute("veiculos", veiculoDAO.buscarPorProprietario(id));
            }

            p.setCpfCnpj(proprietarioForm.getCpfCnpj());
            p.setNome(proprietarioForm.getNome());
            p.setEndereco(proprietarioForm.getEndereco());

            request.setAttribute("proprietario", p);
            return mapping.findForward("form");
        }

        Proprietario proprietario = new Proprietario();
        proprietario.setCpfCnpj(proprietarioForm.getCpfCnpj());
        proprietario.setNome(proprietarioForm.getNome());
        proprietario.setEndereco(proprietarioForm.getEndereco());

        String idStr = proprietarioForm.getId();

        // =========================
        // ✅ EDITAR (fica na página)
        // =========================
        if (idStr != null && !idStr.trim().isEmpty()) {
            int id = Integer.parseInt(idStr);
            proprietario.setId(id);

            boolean ok = proprietarioDAO.atualizar(proprietario);

            if (!ok) {
                ActionErrors dbErr = new ActionErrors();
                dbErr.add("cpfCnpj", new ActionMessage("message.error"));
                saveErrors(request, dbErr);

                // recarrega página do proprietário com veículos
                request.setAttribute("proprietario", proprietario);
                request.setAttribute("veiculos", veiculoDAO.buscarPorProprietario(id));
                return mapping.findForward("form");
            }

            // ✅ busca no banco para mostrar atualizado certinho
            Proprietario atualizado = proprietarioDAO.buscarPorId(id);
            List<Veiculo> veiculos = veiculoDAO.buscarPorProprietario(id);

            request.setAttribute("mensagem", "Proprietário atualizado com sucesso!");
            request.setAttribute("proprietario", atualizado);
            request.setAttribute("veiculos", veiculos);

            // ✅ agora permanece na página do proprietário
            return mapping.findForward("form");
        }

        // =========================
        // ✅ CRIAR (continua indo pra lista)
        // =========================
        boolean ok = proprietarioDAO.inserir(proprietario);

        if (!ok) {
            ActionErrors dbErr = new ActionErrors();
            dbErr.add("cpfCnpj", new ActionMessage("message.error"));
            saveErrors(request, dbErr);

            request.setAttribute("proprietario", proprietario);
            return mapping.findForward("form");
        }

        request.setAttribute("mensagem", "Proprietário cadastrado com sucesso!");
        return listar(mapping, request);
    }

    private ActionForward deletar(ActionMapping mapping, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        proprietarioDAO.deletar(id);
        request.setAttribute("mensagem", "Proprietário removido com sucesso!");
        return listar(mapping, request);
    }
}
