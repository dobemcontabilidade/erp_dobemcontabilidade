package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DepartamentoFuncionario.
 */
@Entity
@Table(name = "departamento_funcionario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DepartamentoFuncionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "cargo", nullable = false)
    private String cargo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "pessoa", "departamentoFuncionarios", "empresa" }, allowSetters = true)
    private Funcionario funcionario;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "departamentoEmpresas", "perfilContadorDepartamentos", "departamentoContadors", "departamentoFuncionarios" },
        allowSetters = true
    )
    private Departamento departamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DepartamentoFuncionario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCargo() {
        return this.cargo;
    }

    public DepartamentoFuncionario cargo(String cargo) {
        this.setCargo(cargo);
        return this;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Funcionario getFuncionario() {
        return this.funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public DepartamentoFuncionario funcionario(Funcionario funcionario) {
        this.setFuncionario(funcionario);
        return this;
    }

    public Departamento getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public DepartamentoFuncionario departamento(Departamento departamento) {
        this.setDepartamento(departamento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepartamentoFuncionario)) {
            return false;
        }
        return getId() != null && getId().equals(((DepartamentoFuncionario) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartamentoFuncionario{" +
            "id=" + getId() +
            ", cargo='" + getCargo() + "'" +
            "}";
    }
}
