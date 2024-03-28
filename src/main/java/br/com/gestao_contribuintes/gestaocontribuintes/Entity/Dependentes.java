package br.com.gestao_contribuintes.gestaocontribuintes.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
//import jakarta.persistence.Enumerated;
//import jakarta.persistence.EnumType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Dependentes")
public class Dependentes {

    @Id
    private String CPF;
    private String nomeCivil;

    // @Enumerated(EnumType.STRING)
    private TipoRelacionamento tipoRelacionamento;

    @ManyToOne
    @JsonBackReference
    private Contribuintes responsavel;

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String cPF) {
        CPF = cPF;
    }

    public String getnomeCivil() {
        return nomeCivil;
    }

    public void setnomeCivil(String nomeCivil) {
        this.nomeCivil = nomeCivil;
    }

    public TipoRelacionamento getTipoRelacionamento() {
        return tipoRelacionamento;
    }

    public void setTipoRelacionamento(TipoRelacionamento tipoRelacionamento) {
        this.tipoRelacionamento = tipoRelacionamento;
    }

    public Contribuintes getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Contribuintes responsavel) {
        this.responsavel = responsavel;
    }
}