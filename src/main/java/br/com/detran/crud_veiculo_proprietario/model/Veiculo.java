package br.com.detran.crud_veiculo_proprietario.model;

public class Veiculo {
    private Integer id;
    private String placa;
    private String renavam;
    private Integer idProp;

    private String proprietarioCpfCnpj;
    private String proprietarioNome;
    private String proprietarioEndereco;

    public Veiculo() {
    }

    public Veiculo(String placa, String renavam, Integer idProp) {
        this.placa = placa;
        this.renavam = renavam;
        this.idProp = idProp;
    }

    public Veiculo(Integer id, String placa, String renavam, Integer idProp) {
        this.id = id;
        this.placa = placa;
        this.renavam = renavam;
        this.idProp = idProp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getIdProp() {
        return idProp;
    }

    public void setIdProp(Integer idProp) {
        this.idProp = idProp;
    }

    public String getProprietarioCpfCnpj() {
        return proprietarioCpfCnpj;
    }

    public void setProprietarioCpfCnpj(String proprietarioCpfCnpj) {
        this.proprietarioCpfCnpj = proprietarioCpfCnpj;
    }

    public String getProprietarioNome() {
        return proprietarioNome;
    }

    public void setProprietarioNome(String proprietarioNome) {
        this.proprietarioNome = proprietarioNome;
    }

    public String getProprietarioEndereco() {
        return proprietarioEndereco;
    }

    public void setProprietarioEndereco(String proprietarioEndereco) {
        this.proprietarioEndereco = proprietarioEndereco;
    }

    @Override
    public String toString() {
        return "Veiculo{" +
                "id=" + id +
                ", placa='" + placa + '\'' +
                ", renavam='" + renavam + '\'' +
                ", idProp=" + idProp +
                ", proprietarioNome='" + proprietarioNome + '\'' +
                '}';
    }
}