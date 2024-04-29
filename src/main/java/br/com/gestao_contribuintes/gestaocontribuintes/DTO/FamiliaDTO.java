package br.com.gestao_contribuintes.gestaocontribuintes.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamiliaDTO {
    @Schema(description = "Nome do contribuinte", example = "Nome", required = true)
    private String nomeCivilPrincipal;
    private String cpfPrincipal;
    @Schema(description = "Nome do contribuinte", example = "Nome", required = true)
    private String conjugeNomeCivil;
    private String conjugeCPF;
    private List<PaisDTO> pais;
    private List<AvoDTO> avos;
    private List<DependentesDTO> dependentes;

    public List<PaisDTO> getPais() {
        return pais;
    }

    public void setPais(List<PaisDTO> pais) {
        this.pais = pais;
    }

    public List<AvoDTO> getAvos() {
        return avos;
    }

    public void setAvos(List<AvoDTO> avos) {
        this.avos = avos;
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

    public List<DependentesDTO> getDependentes() {
        return dependentes;
    }

    public void setDependentes(List<DependentesDTO> dependentes) {
        this.dependentes = dependentes;
    }
}