package br.com.gestao_contribuintes.gestaocontribuintes.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Email")
public class Email {

    @Id
    private String endereco_de_email;

    @ManyToOne
    @JoinColumn(name = "cpf_contribuinte")
    private Contribuintes contribuinte;

    // Getters e Setters
    public String getEndereco_de_email() {
        return endereco_de_email;
    }

    public void setEndereco_de_email(String endereco_de_email) {
        this.endereco_de_email = endereco_de_email;
    }

    public Contribuintes getContribuinte() {
        return contribuinte;
    }

    public void setContribuinte(Contribuintes contribuinte) {
        this.contribuinte = contribuinte;
    }

}
