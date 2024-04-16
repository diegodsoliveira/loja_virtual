package com.diego.lojavirtual.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "acesso")
@SequenceGenerator(name = "seq_acesso", sequenceName = "seq_acesso", initialValue = 1, allocationSize = 1)
public class Acesso implements GrantedAuthority {

    private static final long serialVersionUID = -7882289777501217510L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_acesso")
    private Long id;
    @Column(nullable = false)
    private String descricao; // ROLE_ADMIN...

    public Acesso(Acesso usuario) {
        this.id = usuario.getId();
        this.descricao = usuario.getDescricao();
    }

    public Acesso() {

    }

    @Override
    public String getAuthority() {
        return this.descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Acesso)) return false;
        Acesso acesso = (Acesso) o;
        return getId().equals(acesso.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
