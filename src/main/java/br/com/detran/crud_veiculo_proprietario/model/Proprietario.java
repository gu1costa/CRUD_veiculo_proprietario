package br.com.detran.crud_veiculo_proprietario.model;

public class Proprietario {
    private Integer id;
    private String cpfCnpj;
    private String nome;
    private String endereco;

    public Proprietario() {
    }

    public Proprietario(String cpfCnpj, String nome, String endereco) {
        this.cpfCnpj = cpfCnpj;
        this.nome = nome;
        this.endereco = endereco;
    }

    public Proprietario(Integer id, String cpfCnpj, String nome, String endereco) {
        this.id = id;
        this.cpfCnpj = cpfCnpj;
        this.nome = nome;
        this.endereco = endereco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @Override
    public String toString() {
        return "Proprietario{" +
                "id=" + id +
                ", cpfCnpj='" + cpfCnpj + '\'' +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                '}';
    }
}