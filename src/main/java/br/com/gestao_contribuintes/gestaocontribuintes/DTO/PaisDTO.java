package br.com.gestao_contribuintes.gestaocontribuintes.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaisDTO {

    private String nomeCivilPai;
    private String cpfPai;
    private String nomeCivilMae;
    private String cpfMae;
    private String nomeCivilPai2;
    private String cpfPai2;
    private String nomeCivilMae2;
    private String cpfMae2;
    private String nomeCivilPai3;
    private String cpfPai3;
    private String nomeCivilMae3;
    private String cpfMae3;

    public String getNomeCivilPai3() {
        return nomeCivilPai3;
    }

    public void setNomeCivilPai3(String nomeCivilPai3) {
        this.nomeCivilPai3 = nomeCivilPai3;
    }

    public String getCpfPai3() {
        return cpfPai3;
    }

    public void setCpfPai3(String cpfPai3) {
        this.cpfPai3 = cpfPai3;
    }

    public String getNomeCivilMae3() {
        return nomeCivilMae3;
    }

    public void setNomeCivilMae3(String nomeCivilMae3) {
        this.nomeCivilMae3 = nomeCivilMae3;
    }

    public String getCpfMae3() {
        return cpfMae3;
    }

    public void setCpfMae3(String cpfMae3) {
        this.cpfMae3 = cpfMae3;
    }

    public String getNomeCivilPai2() {
        return nomeCivilPai2;
    }

    public void setNomeCivilPai2(String nomeCivilPai2) {
        this.nomeCivilPai2 = nomeCivilPai2;
    }

    public String getCpfPai2() {
        return cpfPai2;
    }

    public void setCpfPai2(String cpfPai2) {
        this.cpfPai2 = cpfPai2;
    }

    public String getNomeCivilMae2() {
        return nomeCivilMae2;
    }

    public void setNomeCivilMae2(String nomeCivilMae2) {
        this.nomeCivilMae2 = nomeCivilMae2;
    }

    public String getCpfMae2() {
        return cpfMae2;
    }

    public void setCpfMae2(String cpfMae2) {
        this.cpfMae2 = cpfMae2;
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

}