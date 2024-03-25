package br.com.gestao_contribuintes.gestaocontribuintes.Entity;

public class Relacionamento {
    
    private TipoRelacionamento tipo;
    private Contribuintes contribuinte;
    private Contribuintes dependente;
    
    public TipoRelacionamento getTipo() {
        return tipo;
    }
    public void setTipo(TipoRelacionamento tipo) {
        this.tipo = tipo;
    }
    public Contribuintes getContribuinte() {
        return contribuinte;
    }
    public void setContribuinte(Contribuintes contribuinte) {
        this.contribuinte = contribuinte;
    }
    public Contribuintes getDependente() {
        return dependente;
    }
    public void setDependente(Contribuintes dependente) {
        this.dependente = dependente;
    }
    
}