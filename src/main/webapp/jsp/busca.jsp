<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.com.detran.crud_veiculo_proprietario.model.Veiculo" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Consulta de Veículos - DETRAN</title>
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

        .main-content {
            max-width: 1000px;
            margin: 30px auto;
            padding: 0 20px;
            flex: 1;
        }

        .page-title {
            color: #0056a6;
            margin-bottom: 20px;
            font-size: 24px;
        }

        .search-container {
            background: white;
            border: 1px solid #d1d5db;
            padding: 25px;
            margin-bottom: 30px;
        }

        .tabs {
            display: flex;
            border-bottom: 1px solid #e5e7eb;
            margin-bottom: 25px;
        }

        .tab {
            padding: 12px 25px;
            background: none;
            border: none;
            cursor: pointer;
            font-weight: 500;
            color: #6b7280;
            border-bottom: 2px solid transparent;
            transition: all 0.2s;
        }

        .tab.active {
            color: #0056a6;
            border-bottom: 2px solid #0056a6;
        }

        .search-form {
            display: none;
        }

        .search-form.active {
            display: block;
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
            text-transform: uppercase;
        }

        .form-control:focus {
            outline: none;
            border-color: #0056a6;
        }

        .btn {
            padding: 10px 30px;
            background: #0056a6;
            color: white;
            border: none;
            font-weight: 500;
            cursor: pointer;
            transition: background 0.2s;
        }

        .btn:hover {
            background: #004494;
        }

        .results-container {
            background: white;
            border: 1px solid #d1d5db;
            padding: 25px;
        }

        .results-title {
            color: #374151;
            margin-bottom: 20px;
            font-size: 20px;
        }

        .vehicle-card {
            border: 1px solid #e5e7eb;
            padding: 20px;
            margin-bottom: 15px;
        }

        .vehicle-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
            padding-bottom: 10px;
            border-bottom: 1px solid #e5e7eb;
        }

        .vehicle-placa {
            color: #0056a6;
            font-size: 20px;
            font-weight: 600;
        }

        .vehicle-info-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 15px;
        }

        .info-item {
            padding: 10px;
            background: #f9fafb;
        }

        .info-label {
            color: #6b7280;
            font-size: 14px;
            margin-bottom: 5px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .info-value {
            color: #1f2937;
            font-weight: 500;
        }

        .no-results {
            text-align: center;
            padding: 40px;
            color: #6b7280;
        }

        .alert {
            padding: 15px;
            background: #fef3c7;
            border: 1px solid #fbbf24;
            margin-bottom: 20px;
            color: #92400e;
        }

        .alert.success {
            background: #d1fae5;
            border-color: #10b981;
            color: #065f46;
        }

        .alert.error {
            background: #fee2e2;
            border-color: #ef4444;
            color: #991b1b;
        }

        .footer {
            background: #f8f9fa;
            border-top: 1px solid #e0e0e0;
            padding: 20px;
            text-align: center;
            color: #6b7280;
            margin-top: auto;
        }

        .header-actions {
            display: flex;
            align-items: center;
            gap: 30px; /* Espaçamento considerável entre Voltar e Imprimir */
        }

        .btn-print-header {
            background: #ffffff;
            color: #0056a6;
            padding: 8px 15px;
            text-decoration: none;
            font-weight: 600;
            font-size: 14px;
            border-radius: 2px;
            transition: background 0.2s;
        }

        .btn-print-header:hover {
            background: #e5e7eb;
        }
    </style>
</head>
<body>
<div class="header">
    <div class="header-content">
        <div class="logo">
            <h1>Consulta de Veículos</h1>
        </div>

        <div class="header-actions">
            <a href="${pageContext.request.contextPath}/gerarRelatorio.do"
               class="btn-print-header"
               target="_blank">
                Imprimir Relatório
            </a>

            <a href="${pageContext.request.contextPath}/index.jsp" class="back-btn">← Voltar</a>
        </div>
    </div>
</div>

<div class="main-content">
    <h2 class="page-title">Consulta de Veículos por Placa ou CPF/CNPJ</h2>

    <div class="search-container">
        <div class="tabs">
            <button class="tab active" onclick="showTab('placa')">Placa</button>
            <button class="tab" onclick="showTab('cpf')">CPF/CNPJ</button>
        </div>

        <form method="get" action="busca.do" class="search-form active" id="form-placa">
            <input type="hidden" name="tipo" value="placa">
            <div class="form-group">
                <label for="placa">Número da Placa (sem formatação)</label>
                <input type="text" id="placa" name="valor" class="form-control"
                       placeholder="Ex: ABC1D23 (7 caracteres)" maxlength="7" required
                       title="Digite 3 letras seguidas de 4 números. Ex: ABC1234">
            </div>

            <button type="submit" class="btn">Consultar</button>
        </form>

        <form method="get" action="busca.do" class="search-form" id="form-cpf">
            <input type="hidden" name="tipo" value="cpf">
            <div class="form-group">
                <label for="cpfCnpj">CPF ou CNPJ (somente números)</label>
                <input type="text" id="cpfCnpj" name="valor" class="form-control"
                       placeholder="Ex: 12345678901 (11 dígitos) ou 12345678000190 (14 dígitos)"
                       maxlength="14" required
                       pattern="\d{11}|\d{14}"
                       title="Digite 11 dígitos para CPF ou 14 dígitos para CNPJ">
            </div>
            <button type="submit" class="btn">Consultar</button>
        </form>
    </div>

    <%
        String tipoBusca = (String) request.getAttribute("tipoBusca");

        if ("placa".equals(tipoBusca)) {
            Veiculo veiculo = (Veiculo) request.getAttribute("veiculo");
            if (veiculo != null) {
    %>
    <div class="results-container">
        <h3 class="results-title">Veículo Encontrado</h3>
        <div class="vehicle-card">
            <div class="vehicle-header">
                <div class="vehicle-placa"><%= veiculo.getPlaca() %></div>
                <div class="vehicle-status">Cadastrado</div>
            </div>
            <div class="vehicle-info-grid">
                <div class="info-item">
                    <div class="info-label">Placa</div>
                    <div class="info-value"><%= veiculo.getPlaca() %></div>
                </div>
                <div class="info-item">
                    <div class="info-label">RENAVAM</div>
                    <div class="info-value"><%= veiculo.getRenavam() %></div>
                </div>
                <div class="info-item">
                    <div class="info-label">Proprietário</div>
                    <div class="info-value"><%= veiculo.getProprietarioNome() %></div>
                </div>
                <div class="info-item">
                    <div class="info-label">CPF/CNPJ</div>
                    <div class="info-value"><%= veiculo.getProprietarioCpfCnpj() %></div>
                </div>
                <div class="info-item" style="grid-column: span 2;">
                    <div class="info-label">Endereço</div>
                    <div class="info-value"><%= veiculo.getProprietarioEndereco() %></div>
                </div>
            </div>
        </div>
    </div>
    <%
    } else if (tipoBusca != null) {
    %>
    <div class="results-container">
        <div class="alert error">
            Nenhum veículo encontrado com a placa informada.
        </div>
    </div>
    <%
        }
    } else if ("cpf".equals(tipoBusca)) {
        List<Veiculo> veiculos = (List<Veiculo>) request.getAttribute("veiculos");
        if (veiculos != null && !veiculos.isEmpty()) {
    %>
    <div class="results-container">
        <h3 class="results-title"><%= veiculos.size() %> Veículo(s) Encontrado(s)</h3>
        <% for (Veiculo v : veiculos) { %>
        <div class="vehicle-card">
            <div class="vehicle-header">
                <div class="vehicle-placa"><%= v.getPlaca() %></div>
                <div class="vehicle-status">Regular</div>
            </div>
            <div class="vehicle-info-grid">
                <div class="info-item">
                    <div class="info-label">Placa</div>
                    <div class="info-value"><%= v.getPlaca() %></div>
                </div>
                <div class="info-item">
                    <div class="info-label">RENAVAM</div>
                    <div class="info-value"><%= v.getRenavam() %></div>
                </div>
                <div class="info-item">
                    <div class="info-label">Proprietário</div>
                    <div class="info-value"><%= v.getProprietarioNome() %></div>
                </div>
                <div class="info-item">
                    <div class="info-label">CPF/CNPJ</div>
                    <div class="info-value"><%= v.getProprietarioCpfCnpj() %></div>
                </div>
                <div class="info-item" style="grid-column: span 2;">
                    <div class="info-label">Endereço</div>
                    <div class="info-value"><%= v.getProprietarioEndereco() %></div>
                </div>
            </div>
        </div>
        <% } %>
    </div>
    <%
    } else if (veiculos != null) {
    %>
    <div class="results-container">
        <div class="alert error">
            Nenhum veículo encontrado para o CPF/CNPJ informado.
        </div>
    </div>
    <%
            }
        }
    %>
</div>

<div class="footer">
    <p>Sistema DETRAN - Consulta de Veículos</p>
</div>

<script>
    function showTab(tipo) {
        var tabs = document.querySelectorAll('.tab');
        var forms = document.querySelectorAll('.search-form');

        tabs.forEach(function(tab) {
            tab.classList.remove('active');
        });

        forms.forEach(function(form) {
            form.classList.remove('active');
        });

        event.target.classList.add('active');
        document.getElementById('form-' + tipo).classList.add('active');
    }

    // Campo CPF/CNPJ: permite apenas números
    document.getElementById('cpfCnpj')?.addEventListener('input', function(e) {
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