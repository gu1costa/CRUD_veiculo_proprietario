package br.com.detran.crud_veiculo_proprietario.form;

import br.com.detran.crud_veiculo_proprietario.util.CpfCnpjValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

public class ProprietarioForm extends ActionForm {

    private String id;
    private String cpfCnpj;
    private String nome;
    private String endereco;

    public ProprietarioForm() {
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        String doc = onlyDigits(cpfCnpj);
        this.cpfCnpj = doc;

        if (doc.isEmpty()) {
            errors.add("cpfCnpj", new ActionMessage("error.cpfCnpj.required"));
        } else if (!CpfCnpjValidator.isValidCpfOrCnpj(doc)) {
            errors.add("cpfCnpj", new ActionMessage("error.cpfCnpj.invalid"));
        }

        if (nome == null || nome.trim().isEmpty()) {
            errors.add("nome", new ActionMessage("error.nome.required"));
        } else if (nome.length() > 100) {
            errors.add("nome", new ActionMessage("error.nome.maxlength"));
        }

        if (endereco == null || endereco.trim().isEmpty()) {
            errors.add("endereco", new ActionMessage("error.endereco.required"));
        }

        return errors;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.id = null;
        this.cpfCnpj = null;
        this.nome = null;
        this.endereco = null;
    }

    private String onlyDigits(String s) {
        if (s == null) return "";
        return s.replaceAll("\\D", "");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
