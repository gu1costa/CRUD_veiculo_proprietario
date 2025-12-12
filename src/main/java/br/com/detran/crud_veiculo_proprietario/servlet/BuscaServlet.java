package br.com.detran.crud_veiculo_proprietario.servlet;

import br.com.detran.crud_veiculo_proprietario.dao.VeiculoDAO;
import br.com.detran.crud_veiculo_proprietario.model.Veiculo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BuscaServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tipoBusca = request.getParameter("tipo");
        String valorBusca = request.getParameter("valor");

        if (valorBusca != null && !valorBusca.trim().isEmpty()) {
            VeiculoDAO dao = new VeiculoDAO();

            if ("placa".equals(tipoBusca)) {
                Veiculo veiculo = dao.buscarPorPlaca(valorBusca.trim());
                request.setAttribute("veiculo", veiculo);
                request.setAttribute("tipoBusca", "placa");
            } else if ("cpf".equals(tipoBusca)) {
                List<Veiculo> veiculos = dao.buscarPorCpfCnpj(valorBusca.trim());
                request.setAttribute("veiculos", veiculos);
                request.setAttribute("tipoBusca", "cpf");
            }

            request.setAttribute("valorBusca", valorBusca);
        }

        request.getRequestDispatcher("busca.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}