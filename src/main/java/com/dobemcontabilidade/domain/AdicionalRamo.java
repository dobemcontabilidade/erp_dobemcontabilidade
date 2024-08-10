package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdicionalRamo.
 */
@Entity
@Table(name = "adicional_ramo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdicionalRamo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "valor", nullable = false)
    private Double valor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "calculoPlanoAssinaturas", "empresas", "adicionalRamos", "valorBaseRamos" }, allowSetters = true)
    private Ramo ramo;

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

    public AdicionalRamo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValor() {
        return this.valor;
    }

    public AdicionalRamo valor(Double valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Ramo getRamo() {
        return this.ramo;
    }

    public void setRamo(Ramo ramo) {
        this.ramo = ramo;
    }

    public AdicionalRamo ramo(Ramo ramo) {
        this.setRamo(ramo);
        return this;
    }

    public PlanoContabil getPlanoContabil() {
        return this.planoContabil;
    }

    public void setPlanoContabil(PlanoContabil planoContabil) {
        this.planoContabil = planoContabil;
    }

    public AdicionalRamo planoContabil(PlanoContabil planoContabil) {
        this.setPlanoContabil(planoContabil);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdicionalRamo)) {
            return false;
        }
        return getId() != null && getId().equals(((AdicionalRamo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdicionalRamo{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            "}";
    }
}
