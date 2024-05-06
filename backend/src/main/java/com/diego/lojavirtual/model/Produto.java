package com.diego.lojavirtual.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "produto")
@SequenceGenerator(name = "seq_produto", sequenceName = "seq_produto", allocationSize = 1, initialValue = 1)
public class Produto implements Serializable {
    private static final long serialVersionUID = 1051718475554185282L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_produto")
    private Long id;

    @NotNull(message = "O campo TIPO UNIDADE é obrigatório.")
    @Column(nullable = false)
    private String tipoUnidade;

    @Column(nullable = false)
    @NotNull(message = "O campo NOME é obrigatório.")
    private String nome;

    @NotNull(message = "O campo DESCRIÇÃO é obrigatório.")
    @Column(columnDefinition = "text", length = 2000, nullable = false)
    private String descricao;

    @Column(nullable = false)
    @NotNull(message = "O campo ATIVO é obrigatório.")
    private Boolean ativo = Boolean.TRUE;


    // associar nota item produto


    @Column(nullable = false)
    @NotNull(message = "O campo PESO é obrigatório.")
    private Double peso;

    @Column(nullable = false)
    @NotNull(message = "O campo LARGURA é obrigatório.")
    private Double largura;

    @Column(nullable = false)
    @NotNull(message = "O campo ALTURA é obrigatório.")
    private Double altura;

    @Column(nullable = false)
    @NotNull(message = "O campo PROFUNDIDADE é obrigatório.")
    private Double profundidade;

    @Column(nullable = false)
    @NotNull(message = "O campo DESCRIÇÃO é obrigatório.")
    private BigDecimal valorVenda = BigDecimal.ZERO;

    @NotNull(message = "O campo QTDE ESTOQUE é obrigatório.")
    @Column(nullable = false)
    private Integer qtdEstoque = 0;
    private Integer qtdAlertaEstoque = 0;
    private String linkVideoProduto;
    private Boolean alertaQtdEstoque = Boolean.FALSE;
    private Integer qtdClique = 0;

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
    private PessoaJuridica empresa;

    public Pessoa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(PessoaJuridica empresa) {
        this.empresa = empresa;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;
        Produto produto = (Produto) o;
        return getId().equals(produto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
