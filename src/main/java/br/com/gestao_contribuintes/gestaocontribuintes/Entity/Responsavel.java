package br.com.gestao_contribuintes.gestaocontribuintes.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "responsavel")
public class Responsavel {

    @Id
    @Column(name = "CPF")
    private String CPF;

    private String nome;

    // Getters e Setters
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

}
