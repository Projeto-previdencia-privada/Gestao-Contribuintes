package br.com.gestao_contribuintes.gestaocontribuintes.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Contribuintes;

public class CadastroContribuintesDTO {
   
    private String CPF;
   
    private String nomeCivil;
    
    private String nomeSocial;
   
    private String endereco;
    
    private String email;
    
    private BigDecimal salario;
  
    private String categoria;
    
    private String telefone;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate inicioContribuicao;
    
    private String cpfPai;
    
    private String cpfPai2;
    
    private String cpfPai3;
    
    private String cpfMae;
    
    private String cpfMae2;
    
    private String cpfMae3;
    
    private String cpfConjuge;
    
    public CadastroContribuintesDTO(Contribuintes contribuinte) {
        this.CPF = contribuinte.getCPF();
        this.nomeCivil = contribuinte.getNomeCivil();
        this.nomeSocial = contribuinte.getNomeSocial();
        this.endereco = contribuinte.getEndereco();
        this.email = contribuinte.getEmail();
        this.inicioContribuicao = contribuinte.getInicioContribuicao();
        this.salario = contribuinte.getSalario();
        this.categoria = contribuinte.getCategoria();
        this.telefone = contribuinte.getTelefone();
        this.cpfConjuge = contribuinte.getCpfConjuge();
        this.cpfPai = contribuinte.getCpfPai();
        this.cpfPai2 = contribuinte.getCpfPai2();
        this.cpfPai3 = contribuinte.getCpfPai3();
        this.cpfMae = contribuinte.getCpfMae();
        this.cpfMae2 = contribuinte.getCpfMae2();
        this.cpfMae3 = contribuinte.getCpfMae3();
    }
    

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String cPF) {
        CPF = cPF;
    }

    public String getNomeCivil() {
        return nomeCivil;
    }

    public void setNomeCivil(String nomeCivil) {
        this.nomeCivil = nomeCivil;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getInicioContribuicao() {
        return inicioContribuicao;
    }

    public void setInicioContribuicao(LocalDate inicioContribuicao) {
        this.inicioContribuicao = inicioContribuicao;
    }

    public String getCpfPai() {
        return cpfPai;
    }

    public void setCpfPai(String cpfPai) {
        this.cpfPai = cpfPai;
    }

    public String getCpfPai2() {
        return cpfPai2;
    }

    public void setCpfPai2(String cpfPai2) {
        this.cpfPai2 = cpfPai2;
    }

    public String getCpfPai3() {
        return cpfPai3;
    }

    public void setCpfPai3(String cpfPai3) {
        this.cpfPai3 = cpfPai3;
    }

    public String getCpfMae() {
        return cpfMae;
    }

    public void setCpfMae(String cpfMae) {
        this.cpfMae = cpfMae;
    }

    public String getCpfMae2() {
        return cpfMae2;
    }

    public void setCpfMae2(String cpfMae2) {
        this.cpfMae2 = cpfMae2;
    }

    public String getCpfMae3() {
        return cpfMae3;
    }

    public void setCpfMae3(String cpfMae3) {
        this.cpfMae3 = cpfMae3;
    }

    public String getCpfConjuge() {
        return cpfConjuge;
    }

    public void setCpfConjuge(String cpfConjuge) {
        this.cpfConjuge = cpfConjuge;
    }

    

}
