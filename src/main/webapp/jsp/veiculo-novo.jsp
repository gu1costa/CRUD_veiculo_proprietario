<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String idProp = request.getParameter("idProp");
    String mensagem = (String) request.getAttribute("mensagem");
    boolean hasErrors = request.getAttribute("org.apache.struts.action.ERROR") != null;
    
    // Manter valores digitados quando há erro
    String placaValue = (String) request.getAttribute("placaTemp");
    String renavamValue = (String) request.getAttribute("renavamTemp");
    if (placaValue == null) placaValue = "";
    if (renavamValue == null) renavamValue = "";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Adicionar Veículo - DETRAN</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Arial, sans-serif; background: #f0f2f5; min-height: 100vh; display: flex; flex-direction: column; }
        .header { background: #0056a6; color: white; padding: 20px 40px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); border-bottom: 3px solid #003366; }
        .header-content { max-width: 1200px; margin: 0 auto; display: flex; justify-content: space-between; align-items: center; }
        .logo h1 { font-size: 24px; font-weight: 600; }
        .back-btn { color: white; text-decoration: none; font-weight: 500; display: inline-flex; align-items: center; gap: 5px; }
        .back-btn:hover { text-decoration: underline; }
        .main-content { max-width: 1200px; margin: 40px auto; padding: 0 20px; flex: 1; }
        .page-title { color: #0056a6; margin-bottom: 30px; font-size: 28px; border-bottom: 2px solid #e0e0e0; padding-bottom: 10px; }
        .card { background: white; border: 1px solid #d1d5db; box-shadow: 0 2px 4px rgba(0,0,0,0.05); padding: 30px; margin-bottom: 25px; }
        .card-title { color: #1f2937; font-size: 20px; font-weight: 600; margin-bottom: 25px; padding-bottom: 15px; border-bottom: 1px solid #e5e7eb; }
        .form-group { margin-bottom: 20px; }
        .form-group label { display: block; color: #374151; font-weight: 500; margin-bottom: 8px; }
        .form-control { width: 100%; padding: 12px 15px; border: 2px solid #e2e8f0; border-radius: 8px; font-size: 1em; transition: border-color 0.2s; }
        .form-control:focus { outline: none; border-color: #0056a6; }
        .btn { padding: 12px 30px; background: #0056a6; color: white; border: none; border-radius: 8px; font-size: 1em; font-weight: 600; cursor: pointer; transition: all 0.2s ease; text-decoration: none; display: inline-block; }
        .btn:hover { background: #004494; }
        .btn-secondary { background: #718096; }
        .btn-secondary:hover { background: #5a6777; }
        .button-group { display: flex; gap: 15px; margin-top: 25px; }
        .alert { padding: 15px; border-radius: 8px; margin-bottom: 20px; }
        .alert-success { background: #d1fae5; color: #065f46; border: 1px solid #10b981; }
        .alert-error { background: #fee2e2; color: #991b1b; border: 1px solid #ef4444; }
        .alert-error ul { margin-left: 18px; }
        .required { color: #e53e3e; }
        .form-row { display: grid; grid-template-columns: 1fr 1fr; gap: 15px; }
    </style>
</head>
<body>
    <div class="header">
        <div class="header-content">
            <div class="logo">
                <h1>Adicionar Veículo</h1>
            </div>
            <a href="${pageContext.request.contextPath}/proprietario.do?action=listar" class="back-btn">← Voltar para Lista</a>
        </div>
    </div>

    <div class="main-content">
        <h2 class="page-title">Cadastrar Novo Veículo</h2>

        <% if (mensagem != null && !mensagem.trim().isEmpty()) { %>
        <div class="alert alert-success">
            ✅ <%= mensagem %>
        </div>
        <% } %>

        <div class="card">
            <h3 class="card-title">Informações do Veículo</h3>

            <% if (hasErrors) { %>
            <div class="alert alert-error">
                <html:errors property="placa"/>
                <html:errors property="renavam"/>
                <html:errors property="message"/>
            </div>
            <% } %>

            <form method="post" action="${pageContext.request.contextPath}/veiculo.do">
                <input type="hidden" name="action" value="salvar">
                <input type="hidden" name="idProp" value="<%= idProp %>">
                <input type="hidden" name="origem" value="novoForm">

                <div class="form-row">
                    <div class="form-group">
                        <label>Placa <span class="required">*</span></label>
                        <input type="text"
                               name="placa"
                               class="form-control"
                               value="<%= placaValue %>"
                               placeholder="Digite a placa (ex: ABC1234)"
                               maxlength="7"
                               required>
                    </div>

                    <div class="form-group">
                        <label>Renavam <span class="required">*</span></label>
                        <input type="text"
                               name="renavam"
                               class="form-control"
                               value="<%= renavamValue %>"
                               placeholder="Digite o Renavam"
                               maxlength="11"
                               required>
                    </div>
                </div>

                <div class="button-group">
                    <button type="submit" class="btn">
                        ➕ Cadastrar Veículo
                    </button>
                    <a href="${pageContext.request.contextPath}/proprietario.do?action=listar" class="btn btn-secondary">Cancelar</a>
                </div>
            </form>
        </div>
    </div>

    <script>
        document.querySelector('input[name="placa"]')?.addEventListener('input', function(e) {
            e.target.value = e.target.value.toUpperCase().replace(/[^A-Z0-9]/g, '');
        });

        document.querySelector('input[name="renavam"]')?.addEventListener('input', function(e) {
            e.target.value = e.target.value.replace(/[^0-9]/g, '');
        });
    </script>
</body>
</html>
