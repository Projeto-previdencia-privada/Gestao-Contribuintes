package br.com.gestao_contribuintes.gestaocontribuintes.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name= "Dependentes")
public class Dependentes {

    @Id
    private String CPF;
    private String nome;

    @ManyToOne
    @JsonBackReference
    private Contribuintes responsavel;

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String cPF) {
        CPF = cPF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Contribuintes getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Contribuintes responsavel) {
        this.responsavel = responsavel;
    }

    
}

