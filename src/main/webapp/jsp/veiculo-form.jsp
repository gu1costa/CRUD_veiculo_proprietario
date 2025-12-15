<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.com.detran.crud_veiculo_proprietario.model.Veiculo" %>
<%@ page import="br.com.detran.crud_veiculo_proprietario.model.Proprietario" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cadastro de Veículo - DETRAN</title>
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

        .back-btn {
            color: white;
            text-decoration: none;
            font-weight: 500;
            padding: 8px 16px;
            transition: background 0.2s;
        }

        .back-btn:hover {
            background: rgba(255,255,255,0.1);
        }

        .main-content {
            max-width: 900px;
            margin: 30px auto;
            padding: 0 20px;
            flex: 1;
        }

        .card {
            background: white;
            border: 1px solid #d1d5db;
            margin-bottom: 30px;
        }

        .card-header {
            background: #f9fafb;
            padding: 20px;
            border-bottom: 1px solid #e5e7eb;
        }

        .card-header h2 {
            color: #0056a6;
            font-size: 20px;
            font-weight: 600;
        }

        .card-body {
            padding: 25px;
        }

        .form-row {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 20px;
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
            padding: 10px 15px;
            border: 1px solid #d1d5db;
            font-size: 16px;
            transition: border 0.2s;
        }

        .form-control:focus {
            outline: none;
            border-color: #0056a6;
        }

        select.form-control {
            cursor: pointer;
        }

        .btn {
            padding: 10px 25px;
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

        .form-actions {
            display: flex;
            gap: 15px;
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #e5e7eb;
        }

        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid transparent;
        }

        .alert-info {
            background: #dbeafe;
            border-color: #3b82f6;
            color: #1e40af;
        }

        .alert-warning {
            background: #fef3c7;
            border-color: #fbbf24;
            color: #92400e;
        }

        .proprietario-info {
            background: #f0f7ff;
            padding: 15px;
            border-radius: 8px;
            border: 1px solid #bfdbfe;
            margin-top: 10px;
        }

        .proprietario-info strong {
            color: #0056a6;
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
            <h1>Cadastro de Veículo</h1>
        </div>
        <div class="header-actions">
            <a href="veiculo?action=listar" class="back-btn">← Voltar para Lista</a>
        </div>
    </div>
</div>

<div class="main-content">
    <%
        Veiculo veiculo = (Veiculo) request.getAttribute("veiculo");
        List<Proprietario> proprietarios = (List<Proprietario>) request.getAttribute("proprietarios");
        boolean isEdicao = veiculo != null;
    %>

    <% if (proprietarios == null || proprietarios.isEmpty()) { %>
    <div class="alert alert-warning">
        <strong>Atenção!</strong> Não há proprietários cadastrados.
        É necessário <a href="proprietario?action=novo">cadastrar um proprietário</a> antes de adicionar veículos.
    </div>
    <% } else { %>

    <div class="card">
        <div class="card-header">
            <h2><%= isEdicao ? "Editar Veículo" : "Novo Veículo" %></h2>
        </div>
        <div class="card-body">
            <form method="post" action="veiculo">
                <input type="hidden" name="origem" value="veiculo">

                <% if (isEdicao) { %>
                <input type="hidden" name="id" value="<%= veiculo.getId() %>">
                <% } %>

                <div class="form-row">
                    <div class="form-group">
                        <label for="placa">Placa * (sem formatação)</label>
                        <input type="text" id="placa" name="placa" class="form-control"
                               value="<%= isEdicao ? veiculo.getPlaca() : "" %>"
                               maxlength="7" placeholder="ABC1234" required
                               pattern="[A-Za-z]{3}[0-9]{4}"
                               title="Digite 3 letras seguidas de 4 números. Ex: ABC1234"
                               style="text-transform: uppercase;">
                    </div>

                    <div class="form-group">
                        <label for="renavam">RENAVAM * (somente números)</label>
                        <input type="text" id="renavam" name="renavam" class="form-control"
                               value="<%= isEdicao ? veiculo.getRenavam() : "" %>"
                               maxlength="11" placeholder="00000000000" required
                               pattern="\d{11}"
                               title="Digite 11 dígitos numéricos">
                    </div>
                </div>

                <div class="form-group">
                    <label for="idProp">Proprietário *</label>
                    <select id="idProp" name="idProp" class="form-control" required>
                        <option value="">Selecione o proprietário</option>
                        <% for (Proprietario p : proprietarios) { %>
                        <option value="<%= p.getId() %>"
                                <%= (isEdicao && veiculo.getIdProp().equals(p.getId())) ? "selected" : "" %>>
                            <%= p.getNome() %> - CPF/CNPJ: <%= p.getCpfCnpj() %>
                        </option>
                        <% } %>
                    </select>
                </div>

                <% if (isEdicao) { %>
                <div class="proprietario-info">
                    <strong>Proprietário Atual:</strong><br>
                    <strong>Nome:</strong> <%= veiculo.getProprietarioNome() %><br>
                    <strong>CPF/CNPJ:</strong> <%= veiculo.getProprietarioCpfCnpj() %><br>
                    <strong>Endereço:</strong> <%= veiculo.getProprietarioEndereco() %>
                </div>
                <% } %>

                <div class="form-actions">
                    <button type="submit" class="btn">
                        <%= isEdicao ? "Salvar Alterações" : "Cadastrar Veículo" %>
                    </button>
                    <a href="veiculo?action=listar" class="btn btn-outline">Cancelar</a>
                </div>
            </form>
        </div>
    </div>

    <% } %>
</div>

<div class="footer">
    <p>Sistema DETRAN - Cadastro de Veículos</p>
</div>

<script>
    // Campo RENAVAM: permite apenas números
    document.getElementById('renavam')?.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');
        e.target.value = value;
    });

    // Campo Placa: converte para maiúsculas e remove caracteres especiais
    document.getElementById('placa')?.addEventListener('input', function(e) {
        let value = e.target.value.toUpperCase().replace(/[^A-Z0-9]/g, '');
        e.target.value = value;
    });
</script>
</body>
</html>