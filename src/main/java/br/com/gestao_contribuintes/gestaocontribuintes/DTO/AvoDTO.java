package br.com.gestao_contribuintes.gestaocontribuintes.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvoDTO {
    private String nomeCivilAvoPaterno;
    private String cpfAvoPaterno;
    private String nomeCivilAvoMaterno;
    private String cpfAvoMaterno;
    private String nomeCivilAvoPaterno2;
    private String cpfAvoPaterno2;
    private String nomeCivilAvoMaterno2;
    private String cpfAvoMaterno2;

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

    public String getNomeCivilAvoPaterno2() {
        return nomeCivilAvoPaterno2;
    }

    public void setNomeCivilAvoPaterno2(String nomeCivilAvoPaterno2) {
        this.nomeCivilAvoPaterno2 = nomeCivilAvoPaterno2;
    }

    public String getCpfAvoPaterno2() {
        return cpfAvoPaterno2;
    }

    public void setCpfAvoPaterno2(String cpfAvoPaterno2) {
        this.cpfAvoPaterno2 = cpfAvoPaterno2;
    }

    // Getters e setters para avôs maternos
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

    public String getNomeCivilAvoMaterno2() {
        return nomeCivilAvoMaterno2;
    }

    public void setNomeCivilAvoMaterno2(String nomeCivilAvoMaterno2) {
        this.nomeCivilAvoMaterno2 = nomeCivilAvoMaterno2;
    }

    public String getCpfAvoMaterno2() {
        return cpfAvoMaterno2;
    }

    public void setCpfAvoMaterno2(String cpfAvoMaterno2) {
        this.cpfAvoMaterno2 = cpfAvoMaterno2;
    }
}
