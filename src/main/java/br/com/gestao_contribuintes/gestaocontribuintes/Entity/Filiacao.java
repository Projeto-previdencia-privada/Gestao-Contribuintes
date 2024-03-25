package br.com.gestao_contribuintes.gestaocontribuintes.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name= "Filiacao")
public class Filiacao {
    
    @Id
    private String CPF;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CPF_responsavel")
    private Contribuintes contribuinte;
    
    //Getters e Setters
    public Contribuintes getContribuinte() {
        return contribuinte;
    }
    public void setContribuinte(Contribuintes contribuinte) {
        this.contribuinte = contribuinte;
    }
    
    public String getCPF() {
        return CPF;
    }
    public void setCPF(String cPF) {
        CPF = cPF;
    }
}


