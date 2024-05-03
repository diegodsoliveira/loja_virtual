package com.diego.lojavirtual.dtos;

import com.diego.lojavirtual.model.CategoriaProduto;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class CategoriaProdutoDTO implements Serializable {

    public static final Long serialVersionUID = 1L;

    private Long id;

    @NotEmpty(message = "O campo nome é requerido")
    @Length(min = 3, max = 100, message = "O campo NOME deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotEmpty(message = "O campo ID_EMPRESA é requerido")
    private Long idEmpresa;

    public CategoriaProdutoDTO() {
    }

    public CategoriaProdutoDTO(CategoriaProduto newObj) {
        this.id = newObj.getId();
        this.nome = newObj.getNomeDesc();
        this.idEmpresa = newObj.getEmpresa().getId();

    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

