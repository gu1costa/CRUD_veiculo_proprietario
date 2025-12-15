package br.com.detran.crud_veiculo_proprietario.form;

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

        if (placa == null || placa.trim().isEmpty()) {
            errors.add("placa", new ActionMessage("error.placa.required"));
        } else if (placa.length() != 7) {
            errors.add("placa", new ActionMessage("error.placa.invalid"));
        }

        if (renavam == null || renavam.trim().isEmpty()) {
            errors.add("renavam", new ActionMessage("error.renavam.required"));
        } else if (renavam.length() != 11) {
            errors.add("renavam", new ActionMessage("error.renavam.invalid"));
        }

        if (idProp == null || idProp.trim().isEmpty()) {
            errors.add("idProp", new ActionMessage("error.proprietario.required"));
        }

        return errors;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.id = null;
        this.placa = null;
        this.renavam = null;
        this.idProp = null;
        this.origem = null;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getRenavam() {
        return renavam;
    }

    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }

    public String getIdProp() {
        return idProp;
    }

    public void setIdProp(String idProp) {
        this.idProp = idProp;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }
}