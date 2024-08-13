package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ImpostoParcelado.
 */
@Entity
@Table(name = "imposto_parcelado")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ImpostoParcelado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "dias_atraso")
    private Integer diasAtraso;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "parcelaImpostoAPagars", "impostoParcelados", "imposto", "empresa" }, allowSetters = true)
    private ParcelamentoImposto parcelamentoImposto;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "impostoParcelados", "imposto" }, allowSetters = true)
    private ImpostoAPagarEmpresa impostoAPagarEmpresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ImpostoParcelado id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDiasAtraso() {
        return this.diasAtraso;
    }

    public ImpostoParcelado diasAtraso(Integer diasAtraso) {
        this.setDiasAtraso(diasAtraso);
        return this;
    }

    public void setDiasAtraso(Integer diasAtraso) {
        this.diasAtraso = diasAtraso;
    }

    public ParcelamentoImposto getParcelamentoImposto() {
        return this.parcelamentoImposto;
    }

    public void setParcelamentoImposto(ParcelamentoImposto parcelamentoImposto) {
        this.parcelamentoImposto = parcelamentoImposto;
    }

    public ImpostoParcelado parcelamentoImposto(ParcelamentoImposto parcelamentoImposto) {
        this.setParcelamentoImposto(parcelamentoImposto);
        return this;
    }

    public ImpostoAPagarEmpresa getImpostoAPagarEmpresa() {
        return this.impostoAPagarEmpresa;
    }

    public void setImpostoAPagarEmpresa(ImpostoAPagarEmpresa impostoAPagarEmpresa) {
        this.impostoAPagarEmpresa = impostoAPagarEmpresa;
    }

    public ImpostoParcelado impostoAPagarEmpresa(ImpostoAPagarEmpresa impostoAPagarEmpresa) {
        this.setImpostoAPagarEmpresa(impostoAPagarEmpresa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImpostoParcelado)) {
            return false;
        }
        return getId() != null && getId().equals(((ImpostoParcelado) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImpostoParcelado{" +
            "id=" + getId() +
            ", diasAtraso=" + getDiasAtraso() +
            "}";
    }
}
