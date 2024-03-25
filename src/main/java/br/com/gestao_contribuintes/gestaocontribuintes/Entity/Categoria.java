package br.com.gestao_contribuintes.gestaocontribuintes.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name= "Categoria")
public class Categoria {
    
    @Id
    private String descricao;

    @OneToOne
    @JoinColumn(name = "CPF_contribuinte")
    private Contribuintes contribuinte;

    //Getters e Setters
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Contribuintes getContribuinte() {
        return contribuinte;
    }

    public void setContribuinte(Contribuintes contribuinte) {
        this.contribuinte = contribuinte;
    }
}
