package br.com.gestao_contribuintes.gestaocontribuintes.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contribuintes")
public class Contribuintes {

    @Id
    @Column(name = "CPF")
    private String CPF;

    private String Nome_civil;
    private String Nome_social;
    private String Endereco;
    private String Email;
    private BigDecimal Salario;
    private String Categoria;
    private String Telefone;

    @OneToOne(mappedBy = "contribuinte")
    private Categoria categoria;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate inicio_contribuicao;

    @OneToMany(mappedBy = "responsavel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Dependentes> dependentes;

    // Getters e Setters

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String cPF) {
        CPF = cPF;
    }

    public String getNome_civil() {
        return Nome_civil;
    }

    public void setNome_civil(String nome_civil) {
        Nome_civil = nome_civil;
    }

    public String getNome_social() {
        return Nome_social;
    }

    public void setNome_social(String nome_social) {
        Nome_social = nome_social;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public BigDecimal getSalario() {
        return Salario;
    }

    public void setSalario(BigDecimal salario) {
        Salario = salario;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public LocalDate getInicio_contribuicao() {
        return inicio_contribuicao;
    }

    public void setInicio_contribuicao(LocalDate inicio_contribuicao) {
        this.inicio_contribuicao = inicio_contribuicao;
    }

    public List<Dependentes> getDependentes() {
        return dependentes;
    }

    public void setDependentes(List<Dependentes> dependentes) {
        this.dependentes = dependentes;
    }

}