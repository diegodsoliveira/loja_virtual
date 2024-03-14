package com.diego.lojavirtual.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "cupom_desconto")
@SequenceGenerator(name = "seq_cupom_desconto", sequenceName = "seq_cupom_desconto", allocationSize = 1, initialValue = 1)
public class CupomDesconto implements Serializable {
    private static final long serialVersionUID = -6063297776735443845L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cupom_desconto")
    private Long id;

    @Column(nullable = false)
    private String codigoCupom;
    private BigDecimal valorRealDesconto;
    private BigDecimal valorPorcentagemDesconto;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date validadeCupom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoCupom() {
        return codigoCupom;
    }

    public void setCodigoCupom(String codigoCupom) {
        this.codigoCupom = codigoCupom;
    }

    public BigDecimal getValorRealDesconto() {
        return valorRealDesconto;
    }

    public void setValorRealDesconto(BigDecimal valorRealDesconto) {
        this.valorRealDesconto = valorRealDesconto;
    }

    public BigDecimal getValorPorcentagemDesconto() {
        return valorPorcentagemDesconto;
    }

    public void setValorPorcentagemDesconto(BigDecimal valorPorcentagemDesconto) {
        this.valorPorcentagemDesconto = valorPorcentagemDesconto;
    }

    public Date getValidadeCupom() {
        return validadeCupom;
    }

    public void setValidadeCupom(Date validadeCupom) {
        this.validadeCupom = validadeCupom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CupomDesconto)) return false;
        CupomDesconto that = (CupomDesconto) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
