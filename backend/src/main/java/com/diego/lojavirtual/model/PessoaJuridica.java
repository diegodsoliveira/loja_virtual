package com.diego.lojavirtual.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "pessoa_juridica")
@PrimaryKeyJoinColumn(name = "id")
public class PessoaJuridica extends Pessoa {
    private static final long serialVersionUID = 1471400271051147236L;

    @Column(nullable = false)
    private String cnpj;
    @Column(nullable = false)
    private String inscricaoEstadual;
    private String inscricaoMunicipall;
    @Column(nullable = false)
    private String nomeFantasia;
    @Column(nullable = false)
    private String razaoSocial;
    private String categoria;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getInscricaoMunicipall() {
        return inscricaoMunicipall;
    }

    public void setInscricaoMunicipall(String inscricaoMunicipall) {
        this.inscricaoMunicipall = inscricaoMunicipall;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
