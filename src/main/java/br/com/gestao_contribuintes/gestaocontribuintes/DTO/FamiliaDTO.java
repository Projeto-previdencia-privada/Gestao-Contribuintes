package br.com.gestao_contribuintes.gestaocontribuintes.DTO;

import java.util.List;

public class FamiliaDTO {
    private String nomeCivilPrincipal;
    private String cpfPrincipal;
    private String conjugeNomeCivil;
    private String conjugeCPF;
    private String nomeCivilPai;
    private String cpfPai;
    private String nomeCivilMae;
    private String cpfMae;
    private String nomeCivilAvoPaterno;
    private String cpfAvoPaterno;
    private String nomeCivilAvóPaterno; // Renomeado
    private String cpfAvóPaterno; // Renomeado
    private String nomeCivilAvoMaterno;
    private String cpfAvoMaterno;
    private String nomeCivilAvóMaterno; // Renomeado
    private String cpfAvóMaterno; // Renomeado
    private List<DependentesDTO> dependentes;

    public String getNomeCivilAvoMaterno() {
        return nomeCivilAvoMaterno;
    }

    public void setNomeCivilAvoMaterno(String nomeCivilAvoMaterno) {
        this.nomeCivilAvoMaterno = nomeCivilAvoMaterno;
    }

    public String getCpfAvoMaterno() {
        return cpfAvoMaterno;
    }

    public void setCpfAvoMaterno(String cpfAvoMaterno) {
        this.cpfAvoMaterno = cpfAvoMaterno;
    }

    public String getNomeCivilAvóMaterno() {
        return nomeCivilAvóMaterno;
    }

    public void setNomeCivilAvóMaterno(String nomeCivilAvóMaterno) {
        this.nomeCivilAvóMaterno = nomeCivilAvóMaterno;
    }

    public String getCpfAvóMaterno() {
        return cpfAvóMaterno;
    }

    public void setCpfAvóMaterno(String cpfAvóMaterno) {
        this.cpfAvóMaterno = cpfAvóMaterno;
    }

    public String getNomeCivilPrincipal() {
        return nomeCivilPrincipal;
    }

    public void setNomeCivilPrincipal(String nomeCivilPrincipal) {
        this.nomeCivilPrincipal = nomeCivilPrincipal;
    }

    public String getCpfPrincipal() {
        return cpfPrincipal;
    }

    public void setCpfPrincipal(String cpfPrincipal) {
        this.cpfPrincipal = cpfPrincipal;
    }

    public String getConjugeNomeCivil() {
        return conjugeNomeCivil;
    }

    public void setConjugeNomeCivil(String conjugeNomeCivil) {
        this.conjugeNomeCivil = conjugeNomeCivil;
    }

    public String getConjugeCPF() {
        return conjugeCPF;
    }

    public void setConjugeCPF(String conjugeCPF) {
        this.conjugeCPF = conjugeCPF;
    }

    public String getNomeCivilPai() {
        return nomeCivilPai;
    }

    public void setNomeCivilPai(String nomeCivilPai) {
        this.nomeCivilPai = nomeCivilPai;
    }

    public String getCpfPai() {
        return cpfPai;
    }

    public void setCpfPai(String cpfPai) {
        this.cpfPai = cpfPai;
    }

    public String getNomeCivilMae() {
        return nomeCivilMae;
    }

    public void setNomeCivilMae(String nomeCivilMae) {
        this.nomeCivilMae = nomeCivilMae;
    }

    public String getCpfMae() {
        return cpfMae;
    }

    public void setCpfMae(String cpfMae) {
        this.cpfMae = cpfMae;
    }

    public String getNomeCivilAvoPaterno() {
        return nomeCivilAvoPaterno;
    }

    public void setNomeCivilAvoPaterno(String nomeCivilAvoPaterno) {
        this.nomeCivilAvoPaterno = nomeCivilAvoPaterno;
    }

    public String getCpfAvoPaterno() {
        return cpfAvoPaterno;
    }

    public void setCpfAvoPaterno(String cpfAvoPaterno) {
        this.cpfAvoPaterno = cpfAvoPaterno;
    }

    public String getNomeCivilAvóPaterno() {
        return nomeCivilAvóPaterno;
    }

    public void setNomeCivilAvóPaterno(String nomeCivilAvóPaterno) {
        this.nomeCivilAvóPaterno = nomeCivilAvóPaterno;
    }

    public String getCpfAvóPaterno() {
        return cpfAvóPaterno;
    }

    public void setCpfAvóPaterno(String cpfAvóPaterno) {
        this.cpfAvóPaterno = cpfAvóPaterno;
    }

    public List<DependentesDTO> getDependentes() {
        return dependentes;
    }

    public void setDependentes(List<DependentesDTO> dependentes) {
        this.dependentes = dependentes;
    }
}