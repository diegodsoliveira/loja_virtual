package com.diego.lojavirtual.dtos;

import com.diego.lojavirtual.model.Pessoa;
import com.diego.lojavirtual.model.Produto;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ProdutoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String tipoUnidade;
    private String nome;
    private String descricao;
    private Boolean ativo = Boolean.TRUE;

    // associar nota item produto

    private Double peso;
    private Double largura;
    private Double altura;
    private Double profundidade;
    private BigDecimal valorVenda = BigDecimal.ZERO;
    private Integer qtdEstoque = 0;
    private Integer qtdAlertaEstoque = 0;
    private String linkVideoProduto;
    private Boolean alertaQtdEstoque = Boolean.FALSE;
    private Integer qtdClique = 0;
    private Pessoa empresa;

    public ProdutoDto(Produto produto) {
        this.id = produto.getId();
        this.tipoUnidade = produto.getTipoUnidade();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.ativo = produto.getAtivo();
        this.peso = produto.getPeso();
        this.largura = produto.getLargura();
        this.altura = produto.getAltura();
        this.profundidade = produto.getProfundidade();
        this.valorVenda = produto.getValorVenda();
        this.qtdEstoque = produto.getQtdEstoque();
        this.qtdAlertaEstoque = produto.getQtdAlertaEstoque();
        this.linkVideoProduto = produto.getLinkVideoProduto();
        this.alertaQtdEstoque = produto.getAlertaQtdEstoque();
        this.qtdClique = produto.getQtdClique();
        this.empresa = produto.getEmpresa();
    }

    public ProdutoDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoUnidade() {
        return tipoUnidade;
    }

    public void setTipoUnidade(String tipoUnidade) {
        this.tipoUnidade = tipoUnidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getLargura() {
        return largura;
    }

    public void setLargura(Double largura) {
        this.largura = largura;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getProfundidade() {
        return profundidade;
    }

    public void setProfundidade(Double profundidade) {
        this.profundidade = profundidade;
    }

    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }

    public Integer getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(Integer qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public Integer getQtdAlertaEstoque() {
        return qtdAlertaEstoque;
    }

    public void setQtdAlertaEstoque(Integer qtdAlertaEstoque) {
        this.qtdAlertaEstoque = qtdAlertaEstoque;
    }

    public String getLinkVideoProduto() {
        return linkVideoProduto;
    }

    public void setLinkVideoProduto(String linkVideoProduto) {
        this.linkVideoProduto = linkVideoProduto;
    }

    public Boolean getAlertaQtdEstoque() {
        return alertaQtdEstoque;
    }

    public void setAlertaQtdEstoque(Boolean alertaQtdEstoque) {
        this.alertaQtdEstoque = alertaQtdEstoque;
    }

    public Integer getQtdClique() {
        return qtdClique;
    }

    public void setQtdClique(Integer qtdClique) {
        this.qtdClique = qtdClique;
    }

    public Pessoa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Pessoa empresa) {
        this.empresa = empresa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoDto that = (ProdutoDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProdutoDto{" +
                "id=" + id +
                ", tipoUnidade='" + tipoUnidade + '\'' +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", ativo=" + ativo +
                ", peso=" + peso +
                ", largura=" + largura +
                ", altura=" + altura +
                ", profundidade=" + profundidade +
                ", valorVenda=" + valorVenda +
                ", qtdEstoque=" + qtdEstoque +
                ", qtdAlertaEstoque=" + qtdAlertaEstoque +
                ", linkVideoProduto='" + linkVideoProduto + '\'' +
                ", alertaQtdEstoque=" + alertaQtdEstoque +
                ", qtdClique=" + qtdClique +
                ", empresa=" + empresa +
                '}';
    }
}
