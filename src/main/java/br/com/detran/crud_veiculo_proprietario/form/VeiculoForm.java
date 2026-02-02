package br.com.detran.crud_veiculo_proprietario.form;

import br.com.detran.crud_veiculo_proprietario.dao.VeiculoDAO;
import br.com.detran.crud_veiculo_proprietario.model.Veiculo;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

public class VeiculoForm extends ActionForm {

    private String id;
    private String placa;
    private String renavam;
    private String idProp;
    private String origem;

    public VeiculoForm() {
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        String placaNorm = onlyAlphaNumUpper(placa);
        this.placa = placaNorm;

        if (placaNorm.isEmpty()) {
            errors.add("placa", new ActionMessage("error.placa.required"));
        } else if (!isPlacaValida(placaNorm)) {
            errors.add("placa", new ActionMessage("error.placa.invalid"));
        }

        String renavamNorm = onlyDigits(renavam);
        this.renavam = renavamNorm;

        if (renavamNorm.isEmpty()) {
            errors.add("renavam", new ActionMessage("error.renavam.required"));
        } else if (!renavamNorm.matches("^\\d{11}$")) {
            errors.add("renavam", new ActionMessage("error.renavam.invalid"));
        }

        String idPropNorm = onlyDigits(idProp);
        this.idProp = idPropNorm;

        if (idPropNorm.isEmpty()) {
            errors.add("idProp", new ActionMessage("error.proprietario.required"));
        }

        // Validar duplicidade de placa e renavam
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        
        if (!placaNorm.isEmpty()) {
            Veiculo veiculoPorPlaca = veiculoDAO.buscarPorPlaca(placaNorm);
            if (veiculoPorPlaca != null) {
                // Se está editando, permitir apenas se for o mesmo registro
                if (id != null && !id.trim().isEmpty()) {
                    try {
                        int idAtual = Integer.parseInt(id);
                        if (veiculoPorPlaca.getId() != idAtual) {
                            errors.add("placa", new ActionMessage("error.placa.duplicate"));
                        }
                    } catch (NumberFormatException e) {
                        errors.add("placa", new ActionMessage("error.placa.duplicate"));
                    }
                } else {
                    // Novo cadastro, qualquer duplicidade é inválida
                    errors.add("placa", new ActionMessage("error.placa.duplicate"));
                }
            }
        }
        
        if (!renavamNorm.isEmpty()) {
            Veiculo veiculoPorRenavam = veiculoDAO.buscarPorRenavam(renavamNorm);
            if (veiculoPorRenavam != null) {
                // Se está editando, permitir apenas se for o mesmo registro
                if (id != null && !id.trim().isEmpty()) {
                    try {
                        int idAtual = Integer.parseInt(id);
                        if (veiculoPorRenavam.getId() != idAtual) {
                            errors.add("renavam", new ActionMessage("error.renavam.duplicate"));
                        }
                    } catch (NumberFormatException e) {
                        errors.add("renavam", new ActionMessage("error.renavam.duplicate"));
                    }
                } else {
                    // Novo cadastro, qualquer duplicidade é inválida
                    errors.add("renavam", new ActionMessage("error.renavam.duplicate"));
                }
            }
        }

        return errors;
    }

    private boolean isPlacaValida(String p) {
        // Antiga: ABC1234
        // Mercosul: ABC1D23
        return p.matches("^[A-Z]{3}\\d{4}$") || p.matches("^[A-Z]{3}\\d[A-Z]\\d{2}$");
    }

    private String onlyDigits(String s) {
        if (s == null) return "";
        return s.replaceAll("\\D", "");
    }

    private String onlyAlphaNumUpper(String s) {
        if (s == null) return "";
        return s.toUpperCase().replaceAll("[^A-Z0-9]", "");
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.id = null;
        this.placa = null;
        this.renavam = null;
        this.idProp = null;
        this.origem = null;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getRenavam() { return renavam; }
    public void setRenavam(String renavam) { this.renavam = renavam; }

    public String getIdProp() { return idProp; }
    public void setIdProp(String idProp) { this.idProp = idProp; }

    public String getOrigem() { return origem; }
    public void setOrigem(String origem) { this.origem = origem; }
}
