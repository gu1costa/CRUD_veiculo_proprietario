<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.com.detran.crud_veiculo_proprietario.model.Proprietario" %>
<%@ page import="br.com.detran.crud_veiculo_proprietario.model.Veiculo" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

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

        .alert-error {
            background: #fee2e2;
            color: #991b1b;
            border: 1px solid #ef4444;
        }

        .alert-error ul {
            margin-left: 18px;
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

<%
    Proprietario proprietario = (Proprietario) request.getAttribute("proprietario");

    // ✅ CORREÇÃO: evita NullPointer quando proprietario.getId() é null (Integer)
    Integer proprietarioId = (proprietario != null ? proprietario.getId() : null);
    boolean isEdicao = (proprietarioId != null && proprietarioId.intValue() > 0);

    // pega erros do Struts (saveErrors)
    ActionErrors errs = (ActionErrors) request.getAttribute(Globals.ERROR_KEY);

    boolean hasPropErrors = false;
    boolean hasVeiculoErrors = false;

    if (errs != null && !errs.isEmpty()) {
        java.util.Iterator it;

        it = errs.get("cpfCnpj");
        if (it != null && it.hasNext()) hasPropErrors = true;

        it = errs.get("nome");
        if (it != null && it.hasNext()) hasPropErrors = true;

        it = errs.get("endereco");
        if (it != null && it.hasNext()) hasPropErrors = true;

        it = errs.get("placa");
        if (it != null && it.hasNext()) hasVeiculoErrors = true;

        it = errs.get("renavam");
        if (it != null && it.hasNext()) hasVeiculoErrors = true;

        it = errs.get("idProp");
        if (it != null && it.hasNext()) hasVeiculoErrors = true;
    }

    // valores temporários (para manter o que digitou quando falhar)
    String placaTemp = (String) request.getAttribute("placaTemp");
    String renavamTemp = (String) request.getAttribute("renavamTemp");
%>

<div class="header">
    <div class="header-content">
        <div class="logo">
            <h1><%= isEdicao ? "Editar Proprietário" : "Novo Proprietário" %></h1>
        </div>
        <a href="proprietario.do?action=listar" class="back-btn">← Voltar para Lista</a>
    </div>
</div>

<div class="main-content">
    <h2 class="page-title"><%= isEdicao ? "Atualizar Dados do Proprietário" : "Cadastrar Novo Proprietário" %></h2>

    <div class="card">
        <h3 class="card-title">Informações do Proprietário</h3>

        <%-- ✅ ERROS SOMENTE DO PROPRIETÁRIO (não duplica mais) --%>
        <% if (hasPropErrors) { %>
        <div class="alert alert-error">
            <html:errors property="cpfCnpj"/>
            <html:errors property="nome"/>
            <html:errors property="endereco"/>
        </div>
        <% } %>

        <form method="post" action="proprietario.do">
            <input type="hidden" name="action" value="salvar">
            <% if (isEdicao) { %>
            <input type="hidden" name="id" value="<%= proprietarioId %>">
            <% } %>

            <div class="form-group">
                <label>CPF/CNPJ <span class="required">*</span></label>
                <input type="text"
                       name="cpfCnpj"
                       class="form-control"
                       value="<%= proprietario != null ? proprietario.getCpfCnpj() : "" %>"
                       placeholder="Digite apenas números (11 dígitos para CPF ou 14 para CNPJ)"
                       maxlength="14"
                       required>
            </div>

            <div class="form-group">
                <label>Nome Completo <span class="required">*</span></label>
                <input type="text"
                       name="nome"
                       class="form-control"
                       value="<%= proprietario != null ? proprietario.getNome() : "" %>"
                       placeholder="Digite o nome completo do proprietário"
                       maxlength="100"
                       required>
            </div>

            <div class="form-group">
                <label>Endereço Completo <span class="required">*</span></label>
                <input type="text"
                       name="endereco"
                       class="form-control"
                       value="<%= proprietario != null ? proprietario.getEndereco() : "" %>"
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
        <% } else if ("veiculo_atualizado".equals(msg)) { %>
        <div class="alert alert-success">✅ Veículo atualizado com sucesso!</div>
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
                <div style="display:flex; gap:10px; align-items:center;">
                    <a href="${pageContext.request.contextPath}/veiculo.do?action=editar&id=<%= v.getId() %>&origem=proprietario&idProp=<%= proprietarioId %>"
                       class="btn btn-small btn-secondary">
                        ✏️ Editar
                    </a>

                    <a href="veiculo.do?action=deletar&id=<%= v.getId() %>&idProp=<%= proprietarioId %>&origem=proprietario"
                       class="btn btn-small btn-danger"
                       onclick="return confirm('Tem certeza que deseja remover este veículo?')">
                        🗑️ Remover
                    </a>
                </div>
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

            <%-- ✅ ERROS SOMENTE DO VEÍCULO (não duplica mais) --%>
            <% if (hasVeiculoErrors) { %>
            <div class="alert alert-error">
                <html:errors property="placa"/>
                <html:errors property="renavam"/>
                <html:errors property="idProp"/>
            </div>
            <% } %>

            <form method="post" action="${pageContext.request.contextPath}/veiculo.do">
                <input type="hidden" name="action" value="salvar">
                <input type="hidden" name="origem" value="proprietario">
                <input type="hidden" name="idProp" value="<%= proprietarioId %>">

                <div class="form-row">
                    <div class="form-group">
                        <label>Placa <span class="required">*</span></label>
                        <input type="text"
                               name="placa"
                               class="form-control"
                               maxlength="7"
                               minlength="7"
                               value="<%= placaTemp != null ? placaTemp : "" %>"
                               placeholder="Ex: ABC1D23"
                               style="text-transform: uppercase;"
                               required>
                    </div>

                    <div class="form-group">
                        <label>RENAVAM <span class="required">*</span></label>
                        <input type="text"
                               name="renavam"
                               class="form-control"
                               maxlength="11"
                               minlength="11"
                               value="<%= renavamTemp != null ? renavamTemp : "" %>"
                               placeholder="Ex: 12345678901"
                               required>
                    </div>
                </div>

                <button type="submit" class="btn">➕ Adicionar Veículo</button>
                <button type="button" class="btn btn-secondary" id="btnCancelarEdicaoVeiculo" style="display:none; margin-left:10px;">
                    Cancelar edição
                </button>
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

    // Formata placa para maiúsculas e alfanumérico
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
