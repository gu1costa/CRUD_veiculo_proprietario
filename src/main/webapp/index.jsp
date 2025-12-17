<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sistema DETRAN - Gerenciamento de Veículos</title>
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

        .logo {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .logo h1 {
            font-size: 24px;
            font-weight: 600;
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

        .dashboard-cards {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
            gap: 25px;
            margin-bottom: 40px;
        }

        .card {
            background: white;
            border: 1px solid #d1d5db;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            padding: 30px;
            transition: transform 0.2s;
        }

        .card:hover {
            transform: translateY(-3px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            border-color: #0056a6;
        }

        .card-icon {
            font-size: 40px;
            margin-bottom: 20px;
            color: #0056a6;
        }

        .card-title {
            color: #1f2937;
            font-size: 20px;
            font-weight: 600;
            margin-bottom: 15px;
        }

        .card-description {
            color: #6b7280;
            line-height: 1.6;
            margin-bottom: 25px;
        }

        .btn {
            display: inline-block;
            padding: 10px 25px;
            background: #0056a6;
            color: white;
            text-decoration: none;
            font-weight: 500;
            border: none;
            cursor: pointer;
            transition: background 0.2s;
        }

        .btn:hover {
            background: #004494;
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
            <h1>Sistema DETRAN</h1>
        </div>
    </div>
</div>

<div class="main-content">
    <h2 class="page-title">Painel de Controle</h2>

    <div class="dashboard-cards">
        <div class="card">
            <div class="card-icon">🔍</div>
            <h3 class="card-title">Consultar Veículos</h3>
            <p class="card-description">
                Consulte veículos por placa, RENAVAM ou CPF/CNPJ do proprietário.
                Acesso rápido às informações cadastrais.
            </p>
            <a href="${pageContext.request.contextPath}/busca.do" class="btn">Acessar Consulta</a>
        </div>

        <div class="card">
            <div class="card-icon">👥</div>
            <h3 class="card-title">Gerenciar Proprietários</h3>
            <p class="card-description">
                Cadastro, edição e gerenciamento de proprietários e seus veículos.
                Controle completo de dados cadastrais.
            </p>
            <a href="${pageContext.request.contextPath}/proprietario.do?action=listar" class="btn">Gerenciar</a>
        </div>

        <div class="card">
            <div class="card-icon">🚙</div>
            <h3 class="card-title">Gerenciar Veículos</h3>
            <p class="card-description">
                Cadastro, edição e gerenciamento de veículos.
                Visualize e gerencie todos os veículos cadastrados.
            </p>
            <a href="${pageContext.request.contextPath}/veiculo.do?action=listar" class="btn">Gerenciar</a>
        </div>
    </div>
</div>

<div class="footer">
    <p>Sistema DETRAN - Versão 1.0 | © 2025 Departamento Estadual de Trânsito</p>
</div>
</body>
</html>