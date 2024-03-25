package br.com.gestao_contribuintes.gestaocontribuintes.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Endereco")
public class Endereco {
    
    @Id
    private String CPF; 

    private String Logradouro;
    private String Numero;
    private String Complemento;
    private String Bairro;
    private String Cidade;
    private String Estado;
    private String CEP;
    
    //Getters e Setters
    public String getCPF() {
        return CPF;
    }
    public void setCPF(String cpf) {
        this.CPF = cpf;
    }
    public String getLogradouro() {
        return Logradouro;
    }
    public void setLogradouro(String logradouro) {
        Logradouro = logradouro;
    }
    public String getNumero() {
        return Numero;
    }
    public void setNumero(String numero) {
        Numero = numero;
    }
    public String getComplemento() {
        return Complemento;
    }
    public void setComplemento(String complemento) {
        Complemento = complemento;
    }
    public String getBairro() {
        return Bairro;
    }
    public void setBairro(String bairro) {
        Bairro = bairro;
    }
    public String getCidade() {
        return Cidade;
    }
    public void setCidade(String cidade) {
        Cidade = cidade;
    }
    public String getEstado() {
        return Estado;
    }
    public void setEstado(String estado) {
        Estado = estado;
    }
    public String getCEP() {
        return CEP;
    }
    public void setCEP(String cEP) {
        CEP = cEP;
    }

}
