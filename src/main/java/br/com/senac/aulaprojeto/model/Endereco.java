package br.com.senac.aulaprojeto.model;

public class Endereco {
    private int id;
    private String cidade;
    private String estado;
    private String bairro;
    private String rua;
    private String cep;
    private Integer codCliente;

    public Endereco() {
    }
    public Endereco(int id, String cidade, String estado, String bairro, String rua, String cep, Integer codCliente) {
        this.id = id;
        this.cidade = cidade;
        this.estado = estado;
        this.bairro = bairro;
        this.rua = rua;
        this.cep = cep;
        this.codCliente = codCliente;
    }

    public Endereco(int id, String cep, String bairro, String numero, String cidade, String estado) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(Integer codCliente) {
        this.codCliente = codCliente;
    }
}
