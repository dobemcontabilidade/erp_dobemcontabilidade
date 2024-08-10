package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DescontoPlanoContabil.
 */
@Entity
@Table(name = "desconto_plano_contabil")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DescontoPlanoContabil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "percentual", nullable = false)
    private Double percentual;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "descontoPlanoContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "periodoPagamento", "planoContabil", "ramo", "tributacao", "descontoPlanoContabil", "assinaturaEmpresa" },
        allowSetters = true
    )
    private Set<CalculoPlanoAssinatura> calculoPlanoAssinaturas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "calculoPlanoAssinaturas", "assinaturaEmpresas", "descontoPlanoContabils" }, allowSetters = true)
    private PeriodoPagamento periodoPagamento;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "calculoPlanoAssinaturas",
            "assinaturaEmpresas",
            "descontoPlanoContabils",
            "adicionalRamos",
            "adicionalTributacaos",
            "termoContratoContabils",
            "adicionalEnquadramentos",
            "valorBaseRamos",
        },
        allowSetters = true
    )
    private PlanoContabil planoContabil;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DescontoPlanoContabil id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPercentual() {
        return this.percentual;
    }

    public DescontoPlanoContabil percentual(Double percentual) {
        this.setPercentual(percentual);
        return this;
    }

    public void setPercentual(Double percentual) {
        this.percentual = percentual;
    }

    public Set<CalculoPlanoAssinatura> getCalculoPlanoAssinaturas() {
        return this.calculoPlanoAssinaturas;
    }

    public void setCalculoPlanoAssinaturas(Set<CalculoPlanoAssinatura> calculoPlanoAssinaturas) {
        if (this.calculoPlanoAssinaturas != null) {
            this.calculoPlanoAssinaturas.forEach(i -> i.setDescontoPlanoContabil(null));
        }
        if (calculoPlanoAssinaturas != null) {
            calculoPlanoAssinaturas.forEach(i -> i.setDescontoPlanoContabil(this));
        }
        this.calculoPlanoAssinaturas = calculoPlanoAssinaturas;
    }

    public DescontoPlanoContabil calculoPlanoAssinaturas(Set<CalculoPlanoAssinatura> calculoPlanoAssinaturas) {
        this.setCalculoPlanoAssinaturas(calculoPlanoAssinaturas);
        return this;
    }

    public DescontoPlanoContabil addCalculoPlanoAssinatura(CalculoPlanoAssinatura calculoPlanoAssinatura) {
        this.calculoPlanoAssinaturas.add(calculoPlanoAssinatura);
        calculoPlanoAssinatura.setDescontoPlanoContabil(this);
        return this;
    }

    public DescontoPlanoContabil removeCalculoPlanoAssinatura(CalculoPlanoAssinatura calculoPlanoAssinatura) {
        this.calculoPlanoAssinaturas.remove(calculoPlanoAssinatura);
        calculoPlanoAssinatura.setDescontoPlanoContabil(null);
        return this;
    }

    public PeriodoPagamento getPeriodoPagamento() {
        return this.periodoPagamento;
    }

    public void setPeriodoPagamento(PeriodoPagamento periodoPagamento) {
        this.periodoPagamento = periodoPagamento;
    }

    public DescontoPlanoContabil periodoPagamento(PeriodoPagamento periodoPagamento) {
        this.setPeriodoPagamento(periodoPagamento);
        return this;
    }

    public PlanoContabil getPlanoContabil() {
        return this.planoContabil;
    }

    public void setPlanoContabil(PlanoContabil planoContabil) {
        this.planoContabil = planoContabil;
    }

    public DescontoPlanoContabil planoContabil(PlanoContabil planoContabil) {
        this.setPlanoContabil(planoContabil);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DescontoPlanoContabil)) {
            return false;
        }
        return getId() != null && getId().equals(((DescontoPlanoContabil) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DescontoPlanoContabil{" +
            "id=" + getId() +
            ", percentual=" + getPercentual() +
            "}";
    }
}
