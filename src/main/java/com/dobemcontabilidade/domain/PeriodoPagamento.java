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

    @Column(name = "id_plan_gnet")
    private String idPlanGnet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "periodoPagamento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "periodoPagamento",
            "planoContaAzul",
            "planoContabil",
            "ramo",
            "tributacao",
            "descontoPlanoContabil",
            "descontoPlanoContaAzul",
            "assinaturaEmpresa",
        },
        allowSetters = true
    )
    private Set<CalculoPlanoAssinatura> calculoPlanoAssinaturas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "periodoPagamento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "calculoPlanoAssinaturas", "pagamentos", "periodoPagamento", "formaDePagamento", "planoContaAzul", "planoContabil", "empresa",
        },
        allowSetters = true
    )
    private Set<AssinaturaEmpresa> assinaturaEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "periodoPagamento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "calculoPlanoAssinaturas", "planoContaAzul", "periodoPagamento" }, allowSetters = true)
    private Set<DescontoPlanoContaAzul> descontoPlanoContaAzuls = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "periodoPagamento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "calculoPlanoAssinaturas", "periodoPagamento", "planoContabil" }, allowSetters = true)
    private Set<DescontoPlanoContabil> descontoPlanoContabils = new HashSet<>();

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

    public String getIdPlanGnet() {
        return this.idPlanGnet;
    }

    public PeriodoPagamento idPlanGnet(String idPlanGnet) {
        this.setIdPlanGnet(idPlanGnet);
        return this;
    }

    public void setIdPlanGnet(String idPlanGnet) {
        this.idPlanGnet = idPlanGnet;
    }

    public Set<CalculoPlanoAssinatura> getCalculoPlanoAssinaturas() {
        return this.calculoPlanoAssinaturas;
    }

    public void setCalculoPlanoAssinaturas(Set<CalculoPlanoAssinatura> calculoPlanoAssinaturas) {
        if (this.calculoPlanoAssinaturas != null) {
            this.calculoPlanoAssinaturas.forEach(i -> i.setPeriodoPagamento(null));
        }
        if (calculoPlanoAssinaturas != null) {
            calculoPlanoAssinaturas.forEach(i -> i.setPeriodoPagamento(this));
        }
        this.calculoPlanoAssinaturas = calculoPlanoAssinaturas;
    }

    public PeriodoPagamento calculoPlanoAssinaturas(Set<CalculoPlanoAssinatura> calculoPlanoAssinaturas) {
        this.setCalculoPlanoAssinaturas(calculoPlanoAssinaturas);
        return this;
    }

    public PeriodoPagamento addCalculoPlanoAssinatura(CalculoPlanoAssinatura calculoPlanoAssinatura) {
        this.calculoPlanoAssinaturas.add(calculoPlanoAssinatura);
        calculoPlanoAssinatura.setPeriodoPagamento(this);
        return this;
    }

    public PeriodoPagamento removeCalculoPlanoAssinatura(CalculoPlanoAssinatura calculoPlanoAssinatura) {
        this.calculoPlanoAssinaturas.remove(calculoPlanoAssinatura);
        calculoPlanoAssinatura.setPeriodoPagamento(null);
        return this;
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

    public Set<DescontoPlanoContaAzul> getDescontoPlanoContaAzuls() {
        return this.descontoPlanoContaAzuls;
    }

    public void setDescontoPlanoContaAzuls(Set<DescontoPlanoContaAzul> descontoPlanoContaAzuls) {
        if (this.descontoPlanoContaAzuls != null) {
            this.descontoPlanoContaAzuls.forEach(i -> i.setPeriodoPagamento(null));
        }
        if (descontoPlanoContaAzuls != null) {
            descontoPlanoContaAzuls.forEach(i -> i.setPeriodoPagamento(this));
        }
        this.descontoPlanoContaAzuls = descontoPlanoContaAzuls;
    }

    public PeriodoPagamento descontoPlanoContaAzuls(Set<DescontoPlanoContaAzul> descontoPlanoContaAzuls) {
        this.setDescontoPlanoContaAzuls(descontoPlanoContaAzuls);
        return this;
    }

    public PeriodoPagamento addDescontoPlanoContaAzul(DescontoPlanoContaAzul descontoPlanoContaAzul) {
        this.descontoPlanoContaAzuls.add(descontoPlanoContaAzul);
        descontoPlanoContaAzul.setPeriodoPagamento(this);
        return this;
    }

    public PeriodoPagamento removeDescontoPlanoContaAzul(DescontoPlanoContaAzul descontoPlanoContaAzul) {
        this.descontoPlanoContaAzuls.remove(descontoPlanoContaAzul);
        descontoPlanoContaAzul.setPeriodoPagamento(null);
        return this;
    }

    public Set<DescontoPlanoContabil> getDescontoPlanoContabils() {
        return this.descontoPlanoContabils;
    }

    public void setDescontoPlanoContabils(Set<DescontoPlanoContabil> descontoPlanoContabils) {
        if (this.descontoPlanoContabils != null) {
            this.descontoPlanoContabils.forEach(i -> i.setPeriodoPagamento(null));
        }
        if (descontoPlanoContabils != null) {
            descontoPlanoContabils.forEach(i -> i.setPeriodoPagamento(this));
        }
        this.descontoPlanoContabils = descontoPlanoContabils;
    }

    public PeriodoPagamento descontoPlanoContabils(Set<DescontoPlanoContabil> descontoPlanoContabils) {
        this.setDescontoPlanoContabils(descontoPlanoContabils);
        return this;
    }

    public PeriodoPagamento addDescontoPlanoContabil(DescontoPlanoContabil descontoPlanoContabil) {
        this.descontoPlanoContabils.add(descontoPlanoContabil);
        descontoPlanoContabil.setPeriodoPagamento(this);
        return this;
    }

    public PeriodoPagamento removeDescontoPlanoContabil(DescontoPlanoContabil descontoPlanoContabil) {
        this.descontoPlanoContabils.remove(descontoPlanoContabil);
        descontoPlanoContabil.setPeriodoPagamento(null);
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
            ", idPlanGnet='" + getIdPlanGnet() + "'" +
            "}";
    }
}
