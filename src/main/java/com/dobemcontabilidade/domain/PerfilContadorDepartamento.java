package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PerfilContadorDepartamento.
 */
@Entity
@Table(name = "perfil_contador_departamento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerfilContadorDepartamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "quantidade_empresas")
    private Integer quantidadeEmpresas;

    @Column(name = "percentual_experiencia")
    private Double percentualExperiencia;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "departamentoEmpresas", "perfilContadorDepartamentos", "departamentoContadors", "departamentoFuncionarios" },
        allowSetters = true
    )
    private Departamento departamento;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "contadors", "perfilContadorDepartamentos" }, allowSetters = true)
    private PerfilContador perfilContador;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PerfilContadorDepartamento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidadeEmpresas() {
        return this.quantidadeEmpresas;
    }

    public PerfilContadorDepartamento quantidadeEmpresas(Integer quantidadeEmpresas) {
        this.setQuantidadeEmpresas(quantidadeEmpresas);
        return this;
    }

    public void setQuantidadeEmpresas(Integer quantidadeEmpresas) {
        this.quantidadeEmpresas = quantidadeEmpresas;
    }

    public Double getPercentualExperiencia() {
        return this.percentualExperiencia;
    }

    public PerfilContadorDepartamento percentualExperiencia(Double percentualExperiencia) {
        this.setPercentualExperiencia(percentualExperiencia);
        return this;
    }

    public void setPercentualExperiencia(Double percentualExperiencia) {
        this.percentualExperiencia = percentualExperiencia;
    }

    public Departamento getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public PerfilContadorDepartamento departamento(Departamento departamento) {
        this.setDepartamento(departamento);
        return this;
    }

    public PerfilContador getPerfilContador() {
        return this.perfilContador;
    }

    public void setPerfilContador(PerfilContador perfilContador) {
        this.perfilContador = perfilContador;
    }

    public PerfilContadorDepartamento perfilContador(PerfilContador perfilContador) {
        this.setPerfilContador(perfilContador);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerfilContadorDepartamento)) {
            return false;
        }
        return getId() != null && getId().equals(((PerfilContadorDepartamento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerfilContadorDepartamento{" +
            "id=" + getId() +
            ", quantidadeEmpresas=" + getQuantidadeEmpresas() +
            ", percentualExperiencia=" + getPercentualExperiencia() +
            "}";
    }
}
