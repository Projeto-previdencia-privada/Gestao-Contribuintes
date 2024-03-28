package br.com.gestao_contribuintes.gestaocontribuintes.Entity;

public enum TipoRelacionamento {
    AVÔ("avô"),
    AVÓ("avó"),
    PAI("pai"),
    MAE("mae"),
    FILHO("filho"),
    NETO("neto");

    private final String descricao;

    TipoRelacionamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
