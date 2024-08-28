package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdicionalTributacao.
 */
@Entity
@Table(name = "adicional_tributacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdicionalTributacao implements Serializable {

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
    @JsonIgnoreProperties(value = { "empresas", "adicionalTributacaos" }, allowSetters = true)
    private Tributacao tributacao;

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

    public AdicionalTributacao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValor() {
        return this.valor;
    }

    public AdicionalTributacao valor(Double valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Tributacao getTributacao() {
        return this.tributacao;
    }

    public void setTributacao(Tributacao tributacao) {
        this.tributacao = tributacao;
    }

    public AdicionalTributacao tributacao(Tributacao tributacao) {
        this.setTributacao(tributacao);
        return this;
    }

    public PlanoAssinaturaContabil getPlanoAssinaturaContabil() {
        return this.planoAssinaturaContabil;
    }

    public void setPlanoAssinaturaContabil(PlanoAssinaturaContabil planoAssinaturaContabil) {
        this.planoAssinaturaContabil = planoAssinaturaContabil;
    }

    public AdicionalTributacao planoAssinaturaContabil(PlanoAssinaturaContabil planoAssinaturaContabil) {
        this.setPlanoAssinaturaContabil(planoAssinaturaContabil);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdicionalTributacao)) {
            return false;
        }
        return getId() != null && getId().equals(((AdicionalTributacao) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdicionalTributacao{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            "}";
    }
}
