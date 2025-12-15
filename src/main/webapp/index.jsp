<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sistema DETRAN - Busca de Veículos</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
        }

        .header {
            background: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
            text-align: center;
            margin-bottom: 30px;
        }

        h1 {
            color: #2d3748;
            font-size: 2.5em;
            margin-bottom: 10px;
        }

        .subtitle {
            color: #718096;
            font-size: 1.1em;
        }

        .menu-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }

        .menu-card {
            background: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
            text-align: center;
            transition: transform 0.3s ease;
        }

        .menu-card:hover {
            transform: translateY(-5px);
        }

        .menu-card h2 {
            color: #2d3748;
            margin-bottom: 15px;
            font-size: 1.5em;
        }

        .menu-card p {
            color: #718096;
            margin-bottom: 20px;
        }

        .btn {
            display: inline-block;
            padding: 12px 30px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .btn:hover {
            transform: scale(1.05);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }

        .icon {
            font-size: 3em;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>🚗 Sistema DETRAN</h1>
        <p class="subtitle">Sistema de Consulta e Gerenciamento de Veículos</p>
    </div>

    <div class="menu-grid">
        <div class="menu-card">
            <div class="icon">🔍</div>
            <h2>Buscar Veículos</h2>
            <p>Consulte veículos por placa ou CPF/CNPJ do proprietário</p>
            <a href="busca" class="btn">Acessar Busca</a>
        </div>

        <div class="menu-card">
            <div class="icon">👥</div>
            <h2>Gerenciar Proprietários</h2>
            <p>Cadastre, edite e gerencie proprietários e seus veículos</p>
            <a href="proprietario.do?action=listar" class="btn">Gerenciar</a>
        </div>
    </div>
</div>
</body>
</html>