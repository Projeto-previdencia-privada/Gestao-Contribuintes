package br.com.gestao_contribuintes.gestaocontribuintes.DTO;

import java.util.List;


public class FamiliaDTO {
    private String nomeCivilPrincipal;
    private String cpfPrincipal;
    private String conjugeNomeCivil;
    private String conjugeCPF;
    private List<DependentesDTO> dependentes;

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



