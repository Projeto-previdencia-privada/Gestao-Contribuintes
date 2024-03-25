package br.com.gestao_contribuintes.gestaocontribuintes.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name= "Telefone")
public class Telefone {
    
    @Id
    private String cpf; // Alteração do tipo de identificador para String (CPF)

    private String Numero;
    private String Tipo;
    
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getNumero() {
        return Numero;
    }
    public void setNumero(String numero) {
        Numero = numero;
    }
    public String getTipo() {
        return Tipo;
    }
    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    @ManyToOne
    @JoinColumn(name = "cpf_contribuinte")
    private Contribuintes contribuinte;

    public Contribuintes getContribuinte() {
        return contribuinte;
    }

    public void setContribuinte(Contribuintes contribuinte) {
        this.contribuinte = contribuinte;
    }

}
