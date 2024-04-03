package br.com.gestao_contribuintes.gestaocontribuintes.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Dependentes")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cpf")
public class Dependentes {

    @Id
    private String CPF;
    private String nomeCivil;

    @ManyToMany(mappedBy = "dependentes")
    private List<Contribuintes> contribuintes;

    @ManyToOne
    private Contribuintes responsavel;

    private TipoRelacionamento tipoRelacionamento;

    public List<Contribuintes> getContribuintes() {
        return contribuintes;
    }

    public void setContribuintes(List<Contribuintes> contribuintes) {
        this.contribuintes = contribuintes;
    }

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