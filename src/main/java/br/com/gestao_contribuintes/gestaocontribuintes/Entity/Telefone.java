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
    private String cpf; 

    @ManyToOne
    @JoinColumn(name = "cpf_contribuinte")
    private Contribuintes contribuinte;

    private String Numero;
    private String Tipo;

    //Getters e Setters
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

    public Contribuintes getContribuinte() {
        return contribuinte;
    }
    public void setContribuinte(Contribuintes contribuinte) {
        this.contribuinte = contribuinte;
    }

}
