package com.diego.lojavirtual.enums;

public enum TipoPessoa {

    FISICA("FÍSICA"),
    JURIDICA("JURÍDICA");

    private String descricao;

    private TipoPessoa(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
