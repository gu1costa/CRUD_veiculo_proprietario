<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.com.detran.crud_veiculo_proprietario.model.Proprietario" %>
<%@ page import="br.com.detran.crud_veiculo_proprietario.model.Veiculo" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Proprietário - DETRAN</title>
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

        .back-btn {
            color: white;
            text-decoration: none;
            font-weight: 500;
            display: inline-flex;
            align-items: center;
            gap: 5px;
        }

        .back-btn:hover {
            text-decoration: underline;
        }

        .main-content {
            max-width: 1200px;
            margin: 40px auto;
            padding: 0 20px;
            flex: 1;
        }

        .page-title {
            color: #0056a6;
            margin-bottom: 30px;
            font-size: 28px;
            border-bottom: 2px solid #e0e0e0;
            padding-bottom: 10px;
        }

        .card {
            background: white;
            border: 1px solid #d1d5db;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            padding: 30px;
            margin-bottom: 25px;
        }

        .card-title {
            color: #1f2937;
            font-size: 20px;
            font-weight: 600;
            margin-bottom: 25px;
            padding-bottom: 15px;
            border-bottom: 1px solid #e5e7eb;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            color: #374151;
            font-weight: 500;
            margin-bottom: 8px;
        }

        .form-control {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e2e8f0;
            border-radius: 8px;
            font-size: 1em;
            transition: border-color 0.2s;
        }

        .form-control:focus {
            outline: none;
            border-color: #0056a6;
        }

        .btn {
            padding: 12px 30px;
            background: #0056a6;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 1em;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.2s ease;
            text-decoration: none;
            display: inline-block;
        }

        .btn:hover {
            background: #004494;
        }

        .btn-secondary {
            background: #718096;
        }

        .btn-secondary:hover {
            background: #5a6777;
        }

        .btn-danger {
            background: #e53e3e;
        }

        .btn-danger:hover {
            background: #c53030;
        }

        .btn-small {
            padding: 8px 15px;
            font-size: 0.9em;
        }

        .button-group {
            display: flex;
            gap: 15px;
            margin-top: 25px;
        }

        .alert {
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }

        .alert-success {
            background: #d1fae5;
            color: #065f46;
            border: 1px solid #10b981;
        }

        .vehicle-section {
            margin-top: 40px;
        }

        .vehicle-list {
            margin-top: 20px;
        }

        .vehicle-card {
            background: #f7fafc;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 15px;
            border-left: 5px solid #0056a6;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .vehicle-info {
            flex: 1;
        }

        .vehicle-info h3 {
            color: #2d3748;
            margin-bottom: 10px;
            font-size: 18px;
        }

        .vehicle-details {
            color: #718096;
        }

        .add-vehicle-form {
            background: #edf2f7;
            padding: 20px;
            border-radius: 10px;
            margin-top: 20px;
        }

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
        }

        .empty-state {
            text-align: center;
            padding: 30px;
            color: #718096;
        }

        .footer {
            background: #f8f9fa;
            border-top: 1px solid #e0e0e0;
            padding: 20px;
            text-align: center;
            color: #6b7280;
            margin-top: auto;
        }

        .required {
            color: #e53e3e;
        }
    </style>
</head>
<body>
<div class="header">
    <div class="header-content">
        <div class="logo">
            <%
                Proprietario proprietario = (Proprietario) request.getAttribute("proprietario");
                boolean isEdicao = proprietario != null;
            %>
            <h1><%= isEdicao ? "Editar Proprietário" : "Novo Proprietário" %></h1>
        </div>
        <a href="proprietario.do?action=listar" class="back-btn">← Voltar para Lista</a>
    </div>
</div>

<div class="main-content">
    <h2 class="page-title"><%= isEdicao ? "Atualizar Dados do Proprietário" : "Cadastrar Novo Proprietário" %></h2>

    <div class="card">
        <h3 class="card-title">Informações do Proprietário</h3>

        <form method="post" action="proprietario">
            <% if (isEdicao) { %>
            <input type="hidden" name="id" value="<%= proprietario.getId() %>">
            <% } %>

            <div class="form-group">
                <label>CPF/CNPJ <span class="required">*</span></label>
                <input type="text"
                       name="cpfCnpj"
                       class="form-control"
                       value="<%= isEdicao ? proprietario.getCpfCnpj() : "" %>"
                       placeholder="Digite apenas números (11 dígitos para CPF ou 14 para CNPJ)"
                       maxlength="14"
                       required>
            </div>

            <div class="form-group">
                <label>Nome Completo <span class="required">*</span></label>
                <input type="text"
                       name="nome"
                       class="form-control"
                       value="<%= isEdicao ? proprietario.getNome() : "" %>"
                       placeholder="Digite o nome completo do proprietário"
                       maxlength="100"
                       required>
            </div>

            <div class="form-group">
                <label>Endereço Completo <span class="required">*</span></label>
                <input type="text"
                       name="endereco"
                       class="form-control"
                       value="<%= isEdicao ? proprietario.getEndereco() : "" %>"
                       placeholder="Rua, número, bairro, cidade - UF"
                       required>
            </div>

            <div class="button-group">
                <button type="submit" class="btn">
                    <%= isEdicao ? "💾 Salvar Alterações" : "➕ Cadastrar Proprietário" %>
                </button>
                <a href="proprietario.do?action=listar" class="btn btn-secondary">Cancelar</a>
            </div>
        </form>
    </div>

    <% if (isEdicao) {
        String msg = request.getParameter("msg");
        List<Veiculo> veiculos = (List<Veiculo>) request.getAttribute("veiculos");
    %>

    <div class="card vehicle-section">
        <h3 class="card-title">Veículos Cadastrados</h3>

        <% if ("veiculo_adicionado".equals(msg)) { %>
        <div class="alert alert-success">✅ Veículo adicionado com sucesso!</div>
        <% } else if ("veiculo_deletado".equals(msg)) { %>
        <div class="alert alert-success">✅ Veículo removido com sucesso!</div>
        <% } %>

        <% if (veiculos != null && !veiculos.isEmpty()) { %>
        <div class="vehicle-list">
            <% for (Veiculo v : veiculos) { %>
            <div class="vehicle-card">
                <div class="vehicle-info">
                    <h3>🚙 <%= v.getPlaca() %></h3>
                    <div class="vehicle-details">
                        <strong>RENAVAM:</strong> <%= v.getRenavam() %>
                    </div>
                </div>
                <a href="veiculo.do?action=deletar&id=<%= v.getId() %>&idProp=<%= proprietario.getId() %>"
                   class="btn btn-small btn-danger"
                   onclick="return confirm('Tem certeza que deseja remover este veículo?')">
                    🗑️ Remover
                </a>
            </div>
            <% } %>
        </div>
        <% } else { %>
        <div class="empty-state">
            <p>📭 Nenhum veículo cadastrado para este proprietário</p>
        </div>
        <% } %>

        <div class="add-vehicle-form">
            <h3 style="margin-bottom: 20px; color: #2d3748;">➕ Adicionar Novo Veículo</h3>
            <form method="post" action="veiculo">
                <input type="hidden" name="idProp" value="<%= proprietario.getId() %>">

                <div class="form-row">
                    <div class="form-group">
                        <label>Placa <span class="required">*</span></label>
                        <input type="text"
                               name="placa"
                               class="form-control"
                               maxlength="7"
                               placeholder="Ex: ABC1234"
                               style="text-transform: uppercase;"
                               required>
                    </div>

                    <div class="form-group">
                        <label>RENAVAM <span class="required">*</span></label>
                        <input type="text"
                               name="renavam"
                               class="form-control"
                               maxlength="11"
                               placeholder="Ex: 12345678901"
                               required>
                    </div>
                </div>

                <button type="submit" class="btn">➕ Adicionar Veículo</button>
            </form>
        </div>
    </div>

    <% } %>
</div>

<div class="footer">
    <p>Sistema DETRAN - Versão 1.0 | © 2025 Departamento Estadual de Trânsito</p>
</div>

<script>
    // Formata CPF/CNPJ para aceitar apenas números
    document.querySelector('input[name="cpfCnpj"]')?.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');
        e.target.value = value;
    });

    // Formata placa para maiúsculas
    document.querySelector('input[name="placa"]')?.addEventListener('input', function(e) {
        let value = e.target.value.toUpperCase().replace(/[^A-Z0-9]/g, '');
        e.target.value = value;
    });

    // Formata RENAVAM para aceitar apenas números
    document.querySelector('input[name="renavam"]')?.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');
        e.target.value = value;
    });
</script>
</body>
</html>