package com.diego.lojavirtual.enums;

public enum TipoAcesso {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    private TipoAcesso(String descAcesso) {
        this.descricao = descAcesso;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
