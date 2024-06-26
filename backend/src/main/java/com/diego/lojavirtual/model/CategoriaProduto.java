package com.diego.lojavirtual.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "categoria_produto")
@SequenceGenerator(name = "seq_categoria_produto", sequenceName = "seq_categoria_produto", allocationSize = 1, initialValue = 1)
public class CategoriaProduto implements Serializable {

    private static final long serialVersionUID = -1416924528678848645L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_categoria_produto")
    private Long id;

    @Column(nullable = false)
    private String nomeDesc;

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(name = "empresa_id", nullable = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
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

    public String getNomeDesc() {
        return nomeDesc;
    }

    public void setNomeDesc(String nomeDesc) {
        this.nomeDesc = nomeDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoriaProduto)) return false;
        CategoriaProduto that = (CategoriaProduto) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
