package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AreaContabilContador.
 */
@Entity
@Table(name = "area_contabil_contador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AreaContabilContador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "percentual_experiencia")
    private Double percentualExperiencia;

    @Column(name = "descricao_experiencia")
    private String descricaoExperiencia;

    @Column(name = "ativo")
    private Boolean ativo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "pessoa",
            "usuarioContador",
            "areaContabilAssinaturaEmpresas",
            "feedBackContadorParaUsuarios",
            "ordemServicos",
            "areaContabilContadors",
            "contadorResponsavelOrdemServicos",
            "contadorResponsavelTarefaRecorrentes",
            "departamentoEmpresas",
            "departamentoContadors",
            "termoAdesaoContadors",
            "avaliacaoContadors",
            "tarefaEmpresas",
            "perfilContador",
        },
        allowSetters = true
    )
    private Contador contador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "areaContabilAssinaturaEmpresas", "servicoContabils", "areaContabilContadors" }, allowSetters = true)
    private AreaContabil areaContabil;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AreaContabilContador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPercentualExperiencia() {
        return this.percentualExperiencia;
    }

    public AreaContabilContador percentualExperiencia(Double percentualExperiencia) {
        this.setPercentualExperiencia(percentualExperiencia);
        return this;
    }

    public void setPercentualExperiencia(Double percentualExperiencia) {
        this.percentualExperiencia = percentualExperiencia;
    }

    public String getDescricaoExperiencia() {
        return this.descricaoExperiencia;
    }

    public AreaContabilContador descricaoExperiencia(String descricaoExperiencia) {
        this.setDescricaoExperiencia(descricaoExperiencia);
        return this;
    }

    public void setDescricaoExperiencia(String descricaoExperiencia) {
        this.descricaoExperiencia = descricaoExperiencia;
    }

    public Boolean getAtivo() {
        return this.ativo;
    }

    public AreaContabilContador ativo(Boolean ativo) {
        this.setAtivo(ativo);
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Contador getContador() {
        return this.contador;
    }

    public void setContador(Contador contador) {
        this.contador = contador;
    }

    public AreaContabilContador contador(Contador contador) {
        this.setContador(contador);
        return this;
    }

    public AreaContabil getAreaContabil() {
        return this.areaContabil;
    }

    public void setAreaContabil(AreaContabil areaContabil) {
        this.areaContabil = areaContabil;
    }

    public AreaContabilContador areaContabil(AreaContabil areaContabil) {
        this.setAreaContabil(areaContabil);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AreaContabilContador)) {
            return false;
        }
        return getId() != null && getId().equals(((AreaContabilContador) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaContabilContador{" +
            "id=" + getId() +
            ", percentualExperiencia=" + getPercentualExperiencia() +
            ", descricaoExperiencia='" + getDescricaoExperiencia() + "'" +
            ", ativo='" + getAtivo() + "'" +
            "}";
    }
}
