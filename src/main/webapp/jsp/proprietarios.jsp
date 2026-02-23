<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.com.detran.crud_veiculo_proprietario.model.Proprietario" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gerenciar Proprietários - DETRAN</title>
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

        .btn-primary {
            background: #0056a6;
            color: white;
            border: 1px solid #0056a6;
        }

        .btn-primary:hover {
            background: #004494;
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

        .footer {
            background: #f8f9fa;
            border-top: 1px solid #e0e0e0;
            padding: 20px;
            text-align: center;
            color: #6b7280;
            margin-top: auto;
        }

        .print-btn {
            background: #ffffff;
            color: #0056a6;
            padding: 8px 15px;
            text-decoration: none;
            font-weight: 600;
            font-size: 14px;
            border-radius: 2px;
            transition: background 0.2s;
        }

        .print-btn:hover {
            background: #e5e7eb;
        }

        /* Estilos modernos de paginação */
        .pagination-container {
            background: white;
            border: 1px solid #e5e7eb;
            border-radius: 12px;
            padding: 20px;
            margin-top: 24px;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        }

        .pagination-info {
            text-align: center;
            color: #6b7280;
            font-size: 14px;
            margin-bottom: 16px;
            font-weight: 500;
        }

        .pagination {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 8px;
            flex-wrap: wrap;
        }

        .pagination-btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            min-width: 40px;
            height: 40px;
            padding: 0 12px;
            border: 1px solid #d1d5db;
            background: white;
            color: #374151;
            text-decoration: none;
            font-size: 16px;
            font-weight: 500;
            border-radius: 8px;
            transition: all 0.2s ease;
            cursor: pointer;
        }

        .pagination-btn:hover {
            background: #f8fafc;
            border-color: #0056a6;
            color: #0056a6;
            transform: translateY(-1px);
            box-shadow: 0 2px 4px rgba(0, 86, 166, 0.1);
        }

        .pagination-btn.active {
            background: #0056a6;
            border-color: #0056a6;
            color: white;
            box-shadow: 0 2px 4px rgba(0, 86, 166, 0.2);
        }

        .pagination-btn.disabled {
            opacity: 0.5;
            cursor: not-allowed;
            background: #f9fafb;
            color: #9ca3af;
        }

        .pagination-btn.disabled:hover {
            background: #f9fafb;
            border-color: #d1d5db;
            color: #9ca3af;
            transform: none;
            box-shadow: none;
        }

        .pagination-ellipsis {
            color: #6b7280;
            padding: 0 4px;
            font-weight: 600;
        }
    </style>
</head>
<body>
<div class="header">
    <div class="header-content">
        <div class="logo">
            <h1>Gerenciamento de Proprietários</h1>
        </div>
        <div class="header-actions">
            <a href="index.jsp" class="back-btn">← Voltar</a>

            <a href="${pageContext.request.contextPath}/gerarRelatorio.do?tipo=proprietarios&nomeBusca=${param.nomeBusca}"
               class="print-btn"
               target="_blank">
                Imprimir Relatório
            </a>


            <a href="proprietario.do?action=novo" class="new-btn">+ Novo Proprietário</a>
        </div>
    </div>
</div>

<div class="main-content">
    <h2 class="page-title">Proprietários Cadastrados</h2>

    <!-- Campo de Busca -->
    <div class="search-container" style="background: white; border: 1px solid #d1d5db; padding: 20px; margin-bottom: 20px; border-radius: 8px;">
        <form method="get" action="proprietario.do" style="display: flex; gap: 10px; align-items: center;">
            <input type="hidden" name="action" value="buscar">
            <div style="flex: 1;">
                <input type="text" 
                       name="nomeBusca" 
                       class="form-control" 
                       placeholder="Digite o nome do proprietário para buscar..."
                       value="<%= request.getParameter("nomeBusca") != null ? request.getParameter("nomeBusca") : "" %>"
                       style="width: 100%; padding: 10px; border: 1px solid #d1d5db; border-radius: 4px;">
            </div>
            <button type="submit" class="btn" style="min-width: 120px; height: 40px; font-size: 14px;">Buscar</button>
            <a href="proprietario.do?action=listar" class="btn btn-outline" style="min-width: 120px; height: 40px; text-align: center; text-decoration: none; display: inline-block; line-height: 24px; font-size: 14px;">Limpar</a>
        </form>
    </div>

    <%
        String mensagem = (String) request.getAttribute("mensagem");
        if (mensagem != null && !mensagem.trim().isEmpty()) {
    %>
    <div class="alert alert-success"><%= mensagem %></div>
    <%
        } else {
            String msg = request.getParameter("msg");
            if ("criado".equals(msg)) {
    %>
    <div class="alert alert-success">Proprietário cadastrado com sucesso!</div>
    <%
            } else if ("atualizado".equals(msg)) {
    %>
    <div class="alert alert-success">Proprietário atualizado com sucesso!</div>
    <%
            } else if ("deletado".equals(msg)) {
    %>
    <div class="alert alert-success">Proprietário removido com sucesso!</div>
    <%
            }
        }
    %>

    <%
        String nomeBusca = request.getParameter("nomeBusca");
        List<Proprietario> proprietarios = (List<Proprietario>) request.getAttribute("proprietarios");
        if (proprietarios != null && !proprietarios.isEmpty()) {
    %>
    <% if (nomeBusca != null && !nomeBusca.trim().isEmpty()) { %>
    <div style="background: #f0f7ff; border: 1px solid #bfdbfe; padding: 10px; margin-bottom: 15px; border-radius: 4px; color: #0056a6;">
        <strong>Resultados da busca:</strong> <%= proprietarios.size() %> proprietário(s) encontrado(s) para "<%= nomeBusca %>"
        <% 
            Integer totalRegistros = (Integer) request.getAttribute("totalRegistros");
            if (totalRegistros != null && totalRegistros > proprietarios.size()) {
        %>
        (mostrando <%= proprietarios.size() %> de <%= totalRegistros %> registros)
        <% } %>
    </div>
    <% } %>
    <div class="table-container">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>CPF/CNPJ</th>
                <th>Nome</th>
                <th>Endereço</th>
                <th>Ações</th>
            </tr>
            </thead>
            <tbody>
            <% for (Proprietario p : proprietarios) { %>
            <tr>
                <td><%= p.getId() %></td>
                <td><%= p.getCpfCnpj() %></td>
                <td><%= p.getNome() %></td>
                <td><%= p.getEndereco() %></td>
                <td>
                    <div class="actions">
                        <a href="proprietario.do?action=editar&id=<%= p.getId() %>" class="btn btn-sm btn-outline">
                            Editar
                        </a>
                        <a href="veiculo.do?action=novoForm&idProp=<%= p.getId() %>" class="btn btn-sm btn-primary">
                            Adicionar Veículo
                        </a>
                        <a href="proprietario.do?action=deletar&id=<%= p.getId() %>"
                           class="btn btn-sm btn-danger"
                           onclick="return confirm('ATENÇÃO: Esta ação irá remover o proprietário e todos os veículos associados. Deseja continuar?')">
                            Remover
                        </a>
                    </div>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
    
    <!-- Paginação -->
    <%
        Integer paginaAtual = (Integer) request.getAttribute("paginaAtual");
        Integer totalPaginas = (Integer) request.getAttribute("totalPaginas");
        Integer totalRegistros = (Integer) request.getAttribute("totalRegistros");
        Integer registrosPorPagina = (Integer) request.getAttribute("registrosPorPagina");
        
        if (paginaAtual != null && totalPaginas != null && totalPaginas > 1) {
    %>
    <div class="pagination-container">
        <div class="pagination-info">
            Mostrando <%= (paginaAtual - 1) * registrosPorPagina + 1 %> a <%= Math.min(paginaAtual * registrosPorPagina, totalRegistros) %> de <%= totalRegistros %> proprietários
        </div>
        
        <div class="pagination">
            <% if (paginaAtual > 1) { %>
                <a href="?action=<%= nomeBusca != null ? "buscar" : "listar" %>&pagina=1<%= nomeBusca != null ? "&nomeBusca=" + nomeBusca : "" %>" 
                   class="pagination-btn">
                    «
                </a>
                <a href="?action=<%= nomeBusca != null ? "buscar" : "listar" %>&pagina=<%= paginaAtual - 1 %><%= nomeBusca != null ? "&nomeBusca=" + nomeBusca : "" %>" 
                   class="pagination-btn">
                    ‹
                </a>
            <% } else { %>
                <span class="pagination-btn disabled">
                    «
                </span>
                <span class="pagination-btn disabled">
                    ‹
                </span>
            <% } %>
            
            <% 
                int inicio = Math.max(1, paginaAtual - 2);
                int fim = Math.min(totalPaginas, paginaAtual + 2);
                
                // Adiciona elipsis se necessário no início
                if (inicio > 1) {
            %>
                <a href="?action=<%= nomeBusca != null ? "buscar" : "listar" %>&pagina=1<%= nomeBusca != null ? "&nomeBusca=" + nomeBusca : "" %>" 
                   class="pagination-btn">1</a>
                <% if (inicio > 2) { %>
                    <span class="pagination-ellipsis">...</span>
                <% } %>
            <% } %>
            
            <% 
                for (int i = inicio; i <= fim; i++) {
                    if (i == paginaAtual) {
            %>
                <span class="pagination-btn active"><%= i %></span>
            <%      } else { %>
                <a href="?action=<%= nomeBusca != null ? "buscar" : "listar" %>&pagina=<%= i %><%= nomeBusca != null ? "&nomeBusca=" + nomeBusca : "" %>" 
                   class="pagination-btn"><%= i %></a>
            <%      }
                } %>
            
            <% 
                // Adiciona elipsis se necessário no fim
                if (fim < totalPaginas) {
                    if (fim < totalPaginas - 1) {
            %>
                        <span class="pagination-ellipsis">...</span>
                    <% } %>
                    <a href="?action=<%= nomeBusca != null ? "buscar" : "listar" %>&pagina=<%= totalPaginas %><%= nomeBusca != null ? "&nomeBusca=" + nomeBusca : "" %>" 
                       class="pagination-btn"><%= totalPaginas %></a>
            <% } %>
            
            <% if (paginaAtual < totalPaginas) { %>
                <a href="?action=<%= nomeBusca != null ? "buscar" : "listar" %>&pagina=<%= paginaAtual + 1 %><%= nomeBusca != null ? "&nomeBusca=" + nomeBusca : "" %>" 
                   class="pagination-btn">
                    ›
                </a>
                <a href="?action=<%= nomeBusca != null ? "buscar" : "listar" %>&pagina=<%= totalPaginas %><%= nomeBusca != null ? "&nomeBusca=" + nomeBusca : "" %>" 
                   class="pagination-btn">
                    »
                </a>
            <% } else { %>
                <span class="pagination-btn disabled">
                    ›
                </span>
                <span class="pagination-btn disabled">
                    »
                </span>
            <% } %>
        </div>
    </div>
    <% } %>
    <%
    } else {
        if (nomeBusca != null && !nomeBusca.trim().isEmpty()) {
    %>
    <div class="empty-state">
        <h3>Nenhum proprietário encontrado</h3>
        <p>Não foi encontrado nenhum proprietário com o nome "<%= nomeBusca %>"</p>
        <a href="proprietario.do?action=listar" class="btn" style="margin-top: 20px;">Ver Todos os Proprietários</a>
    </div>
    <%
        } else {
    %>
    <div class="empty-state">
        <h3>Nenhum proprietário cadastrado</h3>
        <p>Cadastre o primeiro proprietário para começar</p>
        <a href="proprietario.do?action=novo" class="btn" style="margin-top: 20px;">Cadastrar Proprietário</a>
    </div>
    <%
        }
    }
    %>
</div>

<div class="footer">
    <p>Sistema DETRAN - Gerenciamento de Proprietários | Total: <%= proprietarios != null ? proprietarios.size() : 0 %> registros</p>
</div>
</body>
</html>