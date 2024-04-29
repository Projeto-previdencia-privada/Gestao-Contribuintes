package br.com.gestao_contribuintes.gestaocontribuintes.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "contribuintes")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cpf")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contribuintes {

    @Id
    @Column(name = "CPF")
    @NotBlank(message = "O campo CPF não pode estar vazio")
    @Size(min = 11, max = 11)
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "contribuintes_dependentes", joinColumns = @JoinColumn(name = "cpf_contribuinte"), inverseJoinColumns = @JoinColumn(name = "cpf_dependente"))
    private List<Dependentes> dependentes;
    
    
    // Getters e Setters
    public String getCpfPai3() {
        return cpfPai3;
    }

    public void setCpfPai3(String cpfPai3) {
        this.cpfPai3 = cpfPai3;
    }

    public String getCpfMae3() {
        return cpfMae3;
    }

    public void setCpfMae3(String cpfMae3) {
        this.cpfMae3 = cpfMae3;
    }
    
    public String getCpfPai2() {
        return cpfPai2;
    }

    public void setCpfPai2(String cpfPai2) {
        this.cpfPai2 = cpfPai2;
    }

    public String getCpfMae2() {
        return cpfMae2;
    }

    public void setCpfMae2(String cpfMae2) {
        this.cpfMae2 = cpfMae2;
    }

    public String getCpfConjuge() {
        return cpfConjuge;
    }

    public void setCpfConjuge(String cpfConjuge) {
        this.cpfConjuge = cpfConjuge;
    }

    @JsonIgnore
    public List<Dependentes> getDependentes() {
        return dependentes;
    }

    @JsonIgnore
    public void setDependentes(List<Dependentes> dependentes) {
        this.dependentes = dependentes;
    }

    public String getCpfPai() {
        return cpfPai;
    }

    public void setCpfPai(String cpfPai) {
        this.cpfPai = cpfPai;
    }

    public String getCpfMae() {
        return cpfMae;
    }

    public void setCpfMae(String cpfMae) {
        this.cpfMae = cpfMae;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
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

}
