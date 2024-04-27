package com.diego.lojavirtual.model;

import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "pessoa_juridica")
@PrimaryKeyJoinColumn(name = "id")
public class PessoaJuridica extends Pessoa {
    private static final long serialVersionUID = 1471400271051147236L;

    @NotBlank(message = "CNPJ é obrigatório.")
    @NotNull(message = "CNPJ é obrigatório.")
    @CNPJ(message = "CNPJ inválido")
    @Column(nullable = false)
    private String cnpj;

    @NotBlank(message = "Inscrição estadual é obrigatória.")
    @NotNull(message = "Inscricão estadual é obrigatória.")
    @Column(nullable = false)
    private String inscricaoEstadual;

    @NotBlank(message = "Inscrição municipal é obrigatória.")
    @NotNull(message = "Inscrição municipal é obrigatória.")
    private String inscricaoMunicipall;
    @Column(nullable = false)

    @NotBlank(message = "Nome fantasia é obrigatório.")
    @NotNull(message = "Nome fantasia é obrigatório.")
    private String nomeFantasia;

    @NotBlank(message = "Razão social é obrigatório.")
    @NotNull(message = "Razão social é obrigatório.")
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
