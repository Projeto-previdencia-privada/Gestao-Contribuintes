package br.com.gestao_contribuintes.gestaocontribuintes.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Contribuintes;

public class ContribuintesInfo {

    private String CPF;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate inicioContribuicao;
    private BigDecimal salario;
    private String categoria;
    private Boolean status;
    
    
    public ContribuintesInfo(Contribuintes contribuinte) {
        this.CPF = contribuinte.getCPF();
        this.salario = contribuinte.getSalario();
        this.inicioContribuicao = contribuinte.getInicioContribuicao();
        this.categoria = contribuinte.getCategoria();
        this.status = contribuinte.getStatus();
    }

    // Getters e Setters
    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public BigDecimal getSalario() {
        return salario;
    }
    
    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }
    
    public LocalDate getInicioContribuicao() {
        return inicioContribuicao;
    }
    
    public void setInicioContribuicao(LocalDate inicioContribuicao) {
        this.inicioContribuicao = inicioContribuicao;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public Boolean getStatus() {
        return status;
    }
    
    public void setStatus(Boolean status) {
        this.status = status;
    }
}