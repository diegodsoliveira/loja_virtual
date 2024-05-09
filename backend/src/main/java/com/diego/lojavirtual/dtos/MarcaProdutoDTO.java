package com.diego.lojavirtual.dtos;

import com.diego.lojavirtual.model.MarcaProduto;
import com.diego.lojavirtual.model.Pessoa;
import com.diego.lojavirtual.model.PessoaJuridica;

import java.io.Serializable;
import java.util.Objects;

public class MarcaProdutoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nomeDesc;
    private PessoaJuridica empresa;

    @Override
    public String toString() {
        return "MarcaProdutoDTO{" +
                "nomeDesc='" + nomeDesc + '\'' +
                ", empresa=" + empresa +
                '}';
    }

    public MarcaProdutoDTO(MarcaProduto marcaProduto) {
        this.nomeDesc = marcaProduto.getNomeDesc();
        this.empresa = marcaProduto.getEmpresa();
    }

    public MarcaProdutoDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarcaProdutoDTO that = (MarcaProdutoDTO) o;
        return Objects.equals(nomeDesc, that.nomeDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nomeDesc);
    }

    public String getNomeDesc() {
        return nomeDesc;
    }

    public void setNomeDesc(String nomeDesc) {
        this.nomeDesc = nomeDesc;
    }

    public PessoaJuridica getEmpresa() {
        return empresa;
    }

    public void setEmpresa(PessoaJuridica empresa) {
        this.empresa = empresa;
    }
}
