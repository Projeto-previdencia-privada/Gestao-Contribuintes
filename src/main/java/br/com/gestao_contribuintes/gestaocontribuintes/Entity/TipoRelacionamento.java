package br.com.gestao_contribuintes.gestaocontribuintes.Entity;

public enum TipoRelacionamento {
    PAI("pai"),
    MAE("mae"),
    FILHO("filho");

    private final String descricao;

    TipoRelacionamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}