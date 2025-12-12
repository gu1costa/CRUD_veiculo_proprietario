package br.com.detran.crud_veiculo_proprietario.servlet;

import br.com.detran.crud_veiculo_proprietario.dao.ProprietarioDAO;
import br.com.detran.crud_veiculo_proprietario.dao.VeiculoDAO;
import br.com.detran.crud_veiculo_proprietario.model.Proprietario;
import br.com.detran.crud_veiculo_proprietario.model.Veiculo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProprietarioServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        ProprietarioDAO dao = new ProprietarioDAO();

        if ("listar".equals(action) || action == null) {
            List<Proprietario> proprietarios = dao.buscarTodos();
            request.setAttribute("proprietarios", proprietarios);
            request.getRequestDispatcher("proprietarios.jsp").forward(request, response);

        } else if ("novo".equals(action)) {
            request.getRequestDispatcher("proprietario-form.jsp").forward(request, response);

        } else if ("editar".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Proprietario proprietario = dao.buscarPorId(id);

            VeiculoDAO veiculoDAO = new VeiculoDAO();
            List<Veiculo> veiculos = veiculoDAO.buscarPorProprietario(id);

            request.setAttribute("proprietario", proprietario);
            request.setAttribute("veiculos", veiculos);
            request.getRequestDispatcher("proprietario-form.jsp").forward(request, response);

        } else if ("deletar".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.deletar(id);
            response.sendRedirect("proprietario?action=listar&msg=deletado");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idStr = request.getParameter("id");
        String cpfCnpj = request.getParameter("cpfCnpj");
        String nome = request.getParameter("nome");
        String endereco = request.getParameter("endereco");

        ProprietarioDAO dao = new ProprietarioDAO();
        Proprietario proprietario = new Proprietario();

        proprietario.setCpfCnpj(cpfCnpj);
        proprietario.setNome(nome);
        proprietario.setEndereco(endereco);

        if (idStr != null && !idStr.isEmpty()) {
            proprietario.setId(Integer.parseInt(idStr));
            dao.atualizar(proprietario);
            response.sendRedirect("proprietario?action=listar&msg=atualizado");
        } else {
            dao.inserir(proprietario);
            response.sendRedirect("proprietario?action=listar&msg=criado");
        }
    }
}