package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DescontoPeriodoPagamento.
 */
@Entity
@Table(name = "desconto_periodo_pagamento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DescontoPeriodoPagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "percentual")
    private Double percentual;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "assinaturaEmpresas", "descontoPeriodoPagamentos" }, allowSetters = true)
    private PeriodoPagamento periodoPagamento;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "descontoPeriodoPagamentos", "adicionalRamos", "adicionalTributacaos", "adicionalEnquadramentos" },
        allowSetters = true
    )
    private PlanoAssinaturaContabil planoAssinaturaContabil;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DescontoPeriodoPagamento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPercentual() {
        return this.percentual;
    }

    public DescontoPeriodoPagamento percentual(Double percentual) {
        this.setPercentual(percentual);
        return this;
    }

    public void setPercentual(Double percentual) {
        this.percentual = percentual;
    }

    public PeriodoPagamento getPeriodoPagamento() {
        return this.periodoPagamento;
    }

    public void setPeriodoPagamento(PeriodoPagamento periodoPagamento) {
        this.periodoPagamento = periodoPagamento;
    }

    public DescontoPeriodoPagamento periodoPagamento(PeriodoPagamento periodoPagamento) {
        this.setPeriodoPagamento(periodoPagamento);
        return this;
    }

    public PlanoAssinaturaContabil getPlanoAssinaturaContabil() {
        return this.planoAssinaturaContabil;
    }

    public void setPlanoAssinaturaContabil(PlanoAssinaturaContabil planoAssinaturaContabil) {
        this.planoAssinaturaContabil = planoAssinaturaContabil;
    }

    public DescontoPeriodoPagamento planoAssinaturaContabil(PlanoAssinaturaContabil planoAssinaturaContabil) {
        this.setPlanoAssinaturaContabil(planoAssinaturaContabil);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DescontoPeriodoPagamento)) {
            return false;
        }
        return getId() != null && getId().equals(((DescontoPeriodoPagamento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DescontoPeriodoPagamento{" +
            "id=" + getId() +
            ", percentual=" + getPercentual() +
            "}";
    }
}
