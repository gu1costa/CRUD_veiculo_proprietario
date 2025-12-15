<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.com.detran.crud_veiculo_proprietario.model.Veiculo" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gerenciar Veículos - DETRAN</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background: #f0f2f5;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .header {
            background: #0056a6;
            color: white;
            padding: 20px 40px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            border-bottom: 3px solid #003366;
        }

        .header-content {
            max-width: 1200px;
            margin: 0 auto;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo h1 {
            font-size: 24px;
            font-weight: 600;
        }

        .header-actions {
            display: flex;
            gap: 15px;
            align-items: center;
        }

        .back-btn, .new-btn {
            color: white;
            text-decoration: none;
            font-weight: 500;
            padding: 8px 16px;
            transition: background 0.2s;
        }

        .back-btn:hover {
            background: rgba(255,255,255,0.1);
        }

        .new-btn {
            background: white;
            color: #0056a6;
        }

        .new-btn:hover {
            background: #f0f0f0;
        }

        .main-content {
            max-width: 1200px;
            margin: 30px auto;
            padding: 0 20px;
            flex: 1;
        }

        .page-title {
            color: #0056a6;
            margin-bottom: 20px;
            font-size: 24px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .btn {
            padding: 8px 20px;
            background: #0056a6;
            color: white;
            border: none;
            font-weight: 500;
            cursor: pointer;
            transition: background 0.2s;
            text-decoration: none;
            display: inline-block;
        }

        .btn:hover {
            background: #004494;
        }

        .btn-outline {
            background: white;
            color: #0056a6;
            border: 1px solid #0056a6;
        }

        .btn-outline:hover {
            background: #f0f7ff;
        }

        .btn-danger {
            background: #dc2626;
        }

        .btn-danger:hover {
            background: #b91c1c;
        }

        .btn-sm {
            padding: 5px 12px;
            font-size: 14px;
        }

        .table-container {
            background: white;
            border: 1px solid #d1d5db;
            overflow: hidden;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        thead {
            background: #f9fafb;
            border-bottom: 2px solid #e5e7eb;
        }

        th {
            padding: 15px;
            text-align: left;
            color: #374151;
            font-weight: 600;
            font-size: 14px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        td {
            padding: 15px;
            border-bottom: 1px solid #e5e7eb;
            color: #1f2937;
        }

        tr:hover {
            background: #f9fafb;
        }

        .actions {
            display: flex;
            gap: 8px;
        }

        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid transparent;
        }

        .alert-success {
            background: #d1fae5;
            border-color: #10b981;
            color: #065f46;
        }

        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: #6b7280;
        }

        .empty-state h3 {
            margin-bottom: 10px;
            color: #374151;
        }

        .proprietario-badge {
            display: inline-block;
            background: #e0f2fe;
            color: #0369a1;
            padding: 4px 10px;
            border-radius: 4px;
            font-size: 13px;
            font-weight: 500;
        }

        .footer {
            background: #f8f9fa;
            border-top: 1px solid #e0e0e0;
            padding: 20px;
            text-align: center;
            color: #6b7280;
            margin-top: auto;
        }
    </style>
</head>
<body>
<div class="header">
    <div class="header-content">
        <div class="logo">
            <h1>Gerenciamento de Veículos</h1>
        </div>
        <div class="header-actions">
            <a href="../index.jsp" class="back-btn">← Voltar</a>
            <a href="veiculo?action=novo" class="new-btn">+ Novo Veículo</a>
        </div>
    </div>
</div>

<div class="main-content">
    <div class="page-title">
        <h2>Veículos Cadastrados</h2>
        <a href="veiculo?action=novo" class="btn">+ Adicionar Veículo</a>
    </div>

    <%
        String msg = request.getParameter("msg");
        if ("criado".equals(msg)) {
    %>
    <div class="alert alert-success">Veículo cadastrado com sucesso!</div>
    <%
    } else if ("atualizado".equals(msg)) {
    %>
    <div class="alert alert-success">Veículo atualizado com sucesso!</div>
    <%
    } else if ("deletado".equals(msg)) {
    %>
    <div class="alert alert-success">Veículo removido com sucesso!</div>
    <% } %>

    <%
        List<Veiculo> veiculos = (List<Veiculo>) request.getAttribute("veiculos");
        if (veiculos != null && !veiculos.isEmpty()) {
    %>
    <div class="table-container">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Placa</th>
                <th>RENAVAM</th>
                <th>Proprietário</th>
                <th>CPF/CNPJ</th>
                <th>Ações</th>
            </tr>
            </thead>
            <tbody>
            <% for (Veiculo v : veiculos) { %>
            <tr>
                <td><%= v.getId() %></td>
                <td><strong><%= v.getPlaca() %></strong></td>
                <td><%= v.getRenavam() %></td>
                <td>
                    <span class="proprietario-badge"><%= v.getProprietarioNome() %></span>
                </td>
                <td><%= v.getProprietarioCpfCnpj() %></td>
                <td>
                    <div class="actions">
                        <a href="veiculo?action=editar&id=<%= v.getId() %>" class="btn btn-sm btn-outline">
                            Editar
                        </a>
                        <a href="veiculo?action=deletar&id=<%= v.getId() %>&origem=veiculo"
                           class="btn btn-sm btn-danger"
                           onclick="return confirm('Deseja realmente remover este veículo?')">
                            Remover
                        </a>
                    </div>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
    <%
    } else {
    %>
    <div class="empty-state">
        <h3>Nenhum veículo cadastrado</h3>
        <p>Cadastre o primeiro veículo para começar</p>
        <a href="veiculo?action=novo" class="btn" style="margin-top: 20px;">Cadastrar Veículo</a>
    </div>
    <% } %>
</div>

<div class="footer">
    <p>Sistema DETRAN - Gerenciamento de Veículos | Total: <%= veiculos != null ? veiculos.size() : 0 %> registros</p>
</div>
</body>
</html>