package br.com.gestao_contribuintes.gestaocontribuintes.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "contribuintes")
public class Contribuintes {

    @Id
    @Column(name = "CPF")
    private String CPF;

    private String nomeCivil;
    private String nomeSocial;
    private String endereco;
    private String email;
    private BigDecimal salario;
    private String categoria;
    private String telefone;

    // Adicionado para representar a data de início de contribuição
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate inicioContribuicao;

    // Adicionado para representar os dependentes
    @OneToMany(mappedBy = "responsavel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Dependentes> dependentes;

    private String tipoRelacionamento;
    private String cpfPai;
    private String cpfMae;
    private String cpfAvô;
    private String cpfAvó;

    // Getters e Setters

    public String getTipoRelacionamento() {
        return tipoRelacionamento;
    }

    public void setTipoRelacionamento(String tipoRelacionamento) {
        this.tipoRelacionamento = tipoRelacionamento;
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

    public String getCpfAvô() {
        return cpfAvô;
    }

    public void setCpfAvô(String cpfAvô) {
        this.cpfAvô = cpfAvô;
    }

    public String getCpfAvó() {
        return cpfAvó;
    }

    public void setCpfAvó(String cpfAvó) {
        this.cpfAvó = cpfAvó;
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

    public List<Dependentes> getDependentes() {
        return dependentes;
    }

    public void setDependentes(List<Dependentes> dependentes) {
        this.dependentes = dependentes;
    }

}
