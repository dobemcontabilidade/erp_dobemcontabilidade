package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PeriodoPagamento.
 */
@Entity
@Table(name = "periodo_pagamento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PeriodoPagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "periodo")
    private String periodo;

    @Column(name = "numero_dias")
    private Integer numeroDias;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "periodoPagamento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "termoContratoAssinaturaEmpresas", "periodoPagamento", "formaDePagamento", "empresa" },
        allowSetters = true
    )
    private Set<AssinaturaEmpresa> assinaturaEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "periodoPagamento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "periodoPagamento", "planoAssinaturaContabil" }, allowSetters = true)
    private Set<DescontoPeriodoPagamento> descontoPeriodoPagamentos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PeriodoPagamento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriodo() {
        return this.periodo;
    }

    public PeriodoPagamento periodo(String periodo) {
        this.setPeriodo(periodo);
        return this;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Integer getNumeroDias() {
        return this.numeroDias;
    }

    public PeriodoPagamento numeroDias(Integer numeroDias) {
        this.setNumeroDias(numeroDias);
        return this;
    }

    public void setNumeroDias(Integer numeroDias) {
        this.numeroDias = numeroDias;
    }

    public Set<AssinaturaEmpresa> getAssinaturaEmpresas() {
        return this.assinaturaEmpresas;
    }

    public void setAssinaturaEmpresas(Set<AssinaturaEmpresa> assinaturaEmpresas) {
        if (this.assinaturaEmpresas != null) {
            this.assinaturaEmpresas.forEach(i -> i.setPeriodoPagamento(null));
        }
        if (assinaturaEmpresas != null) {
            assinaturaEmpresas.forEach(i -> i.setPeriodoPagamento(this));
        }
        this.assinaturaEmpresas = assinaturaEmpresas;
    }

    public PeriodoPagamento assinaturaEmpresas(Set<AssinaturaEmpresa> assinaturaEmpresas) {
        this.setAssinaturaEmpresas(assinaturaEmpresas);
        return this;
    }

    public PeriodoPagamento addAssinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.assinaturaEmpresas.add(assinaturaEmpresa);
        assinaturaEmpresa.setPeriodoPagamento(this);
        return this;
    }

    public PeriodoPagamento removeAssinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.assinaturaEmpresas.remove(assinaturaEmpresa);
        assinaturaEmpresa.setPeriodoPagamento(null);
        return this;
    }

    public Set<DescontoPeriodoPagamento> getDescontoPeriodoPagamentos() {
        return this.descontoPeriodoPagamentos;
    }

    public void setDescontoPeriodoPagamentos(Set<DescontoPeriodoPagamento> descontoPeriodoPagamentos) {
        if (this.descontoPeriodoPagamentos != null) {
            this.descontoPeriodoPagamentos.forEach(i -> i.setPeriodoPagamento(null));
        }
        if (descontoPeriodoPagamentos != null) {
            descontoPeriodoPagamentos.forEach(i -> i.setPeriodoPagamento(this));
        }
        this.descontoPeriodoPagamentos = descontoPeriodoPagamentos;
    }

    public PeriodoPagamento descontoPeriodoPagamentos(Set<DescontoPeriodoPagamento> descontoPeriodoPagamentos) {
        this.setDescontoPeriodoPagamentos(descontoPeriodoPagamentos);
        return this;
    }

    public PeriodoPagamento addDescontoPeriodoPagamento(DescontoPeriodoPagamento descontoPeriodoPagamento) {
        this.descontoPeriodoPagamentos.add(descontoPeriodoPagamento);
        descontoPeriodoPagamento.setPeriodoPagamento(this);
        return this;
    }

    public PeriodoPagamento removeDescontoPeriodoPagamento(DescontoPeriodoPagamento descontoPeriodoPagamento) {
        this.descontoPeriodoPagamentos.remove(descontoPeriodoPagamento);
        descontoPeriodoPagamento.setPeriodoPagamento(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PeriodoPagamento)) {
            return false;
        }
        return getId() != null && getId().equals(((PeriodoPagamento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PeriodoPagamento{" +
            "id=" + getId() +
            ", periodo='" + getPeriodo() + "'" +
            ", numeroDias=" + getNumeroDias() +
            "}";
    }
}
