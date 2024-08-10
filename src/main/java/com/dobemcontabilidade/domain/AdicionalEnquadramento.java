package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdicionalEnquadramento.
 */
@Entity
@Table(name = "adicional_enquadramento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdicionalEnquadramento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "valor")
    private Double valor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "empresas", "adicionalEnquadramentos" }, allowSetters = true)
    private Enquadramento enquadramento;

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

    public AdicionalEnquadramento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValor() {
        return this.valor;
    }

    public AdicionalEnquadramento valor(Double valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Enquadramento getEnquadramento() {
        return this.enquadramento;
    }

    public void setEnquadramento(Enquadramento enquadramento) {
        this.enquadramento = enquadramento;
    }

    public AdicionalEnquadramento enquadramento(Enquadramento enquadramento) {
        this.setEnquadramento(enquadramento);
        return this;
    }

    public PlanoContabil getPlanoContabil() {
        return this.planoContabil;
    }

    public void setPlanoContabil(PlanoContabil planoContabil) {
        this.planoContabil = planoContabil;
    }

    public AdicionalEnquadramento planoContabil(PlanoContabil planoContabil) {
        this.setPlanoContabil(planoContabil);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdicionalEnquadramento)) {
            return false;
        }
        return getId() != null && getId().equals(((AdicionalEnquadramento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdicionalEnquadramento{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            "}";
    }
}
