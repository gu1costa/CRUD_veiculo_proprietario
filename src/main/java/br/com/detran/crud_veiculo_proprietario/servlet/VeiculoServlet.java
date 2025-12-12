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

public class VeiculoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("deletar".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            int idProp = Integer.parseInt(request.getParameter("idProp"));

            VeiculoDAO dao = new VeiculoDAO();
            dao.deletar(id);

            response.sendRedirect("proprietario?action=editar&id=" + idProp + "&msg=veiculo_deletado");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String placa = request.getParameter("placa");
        String renavam = request.getParameter("renavam");
        int idProp = Integer.parseInt(request.getParameter("idProp"));

        VeiculoDAO dao = new VeiculoDAO();
        Veiculo veiculo = new Veiculo();

        veiculo.setPlaca(placa);
        veiculo.setRenavam(renavam);
        veiculo.setIdProp(idProp);

        dao.inserir(veiculo);

        response.sendRedirect("proprietario?action=editar&id=" + idProp + "&msg=veiculo_adicionado");
    }
}