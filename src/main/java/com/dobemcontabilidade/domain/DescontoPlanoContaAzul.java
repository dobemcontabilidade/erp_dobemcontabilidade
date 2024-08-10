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
 * A DescontoPlanoContaAzul.
 */
@Entity
@Table(name = "desconto_plano_conta_azul")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DescontoPlanoContaAzul implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "percentual")
    private Double percentual;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "descontoPlanoContaAzul")
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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "calculoPlanoAssinaturas", "assinaturaEmpresas", "descontoPlanoContaAzuls" }, allowSetters = true)
    private PlanoContaAzul planoContaAzul;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "calculoPlanoAssinaturas", "assinaturaEmpresas", "descontoPlanoContaAzuls", "descontoPlanoContabils" },
        allowSetters = true
    )
    private PeriodoPagamento periodoPagamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DescontoPlanoContaAzul id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPercentual() {
        return this.percentual;
    }

    public DescontoPlanoContaAzul percentual(Double percentual) {
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
            this.calculoPlanoAssinaturas.forEach(i -> i.setDescontoPlanoContaAzul(null));
        }
        if (calculoPlanoAssinaturas != null) {
            calculoPlanoAssinaturas.forEach(i -> i.setDescontoPlanoContaAzul(this));
        }
        this.calculoPlanoAssinaturas = calculoPlanoAssinaturas;
    }

    public DescontoPlanoContaAzul calculoPlanoAssinaturas(Set<CalculoPlanoAssinatura> calculoPlanoAssinaturas) {
        this.setCalculoPlanoAssinaturas(calculoPlanoAssinaturas);
        return this;
    }

    public DescontoPlanoContaAzul addCalculoPlanoAssinatura(CalculoPlanoAssinatura calculoPlanoAssinatura) {
        this.calculoPlanoAssinaturas.add(calculoPlanoAssinatura);
        calculoPlanoAssinatura.setDescontoPlanoContaAzul(this);
        return this;
    }

    public DescontoPlanoContaAzul removeCalculoPlanoAssinatura(CalculoPlanoAssinatura calculoPlanoAssinatura) {
        this.calculoPlanoAssinaturas.remove(calculoPlanoAssinatura);
        calculoPlanoAssinatura.setDescontoPlanoContaAzul(null);
        return this;
    }

    public PlanoContaAzul getPlanoContaAzul() {
        return this.planoContaAzul;
    }

    public void setPlanoContaAzul(PlanoContaAzul planoContaAzul) {
        this.planoContaAzul = planoContaAzul;
    }

    public DescontoPlanoContaAzul planoContaAzul(PlanoContaAzul planoContaAzul) {
        this.setPlanoContaAzul(planoContaAzul);
        return this;
    }

    public PeriodoPagamento getPeriodoPagamento() {
        return this.periodoPagamento;
    }

    public void setPeriodoPagamento(PeriodoPagamento periodoPagamento) {
        this.periodoPagamento = periodoPagamento;
    }

    public DescontoPlanoContaAzul periodoPagamento(PeriodoPagamento periodoPagamento) {
        this.setPeriodoPagamento(periodoPagamento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DescontoPlanoContaAzul)) {
            return false;
        }
        return getId() != null && getId().equals(((DescontoPlanoContaAzul) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DescontoPlanoContaAzul{" +
            "id=" + getId() +
            ", percentual=" + getPercentual() +
            "}";
    }
}
