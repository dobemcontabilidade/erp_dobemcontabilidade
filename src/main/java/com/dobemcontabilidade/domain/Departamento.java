package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Departamento.
 */
@Entity
@Table(name = "departamento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Departamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "departamento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "departamento", "empresa", "contador" }, allowSetters = true)
    private Set<DepartamentoEmpresa> departamentoEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "departamento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "departamento", "perfilContador" }, allowSetters = true)
    private Set<PerfilContadorDepartamento> perfilContadorDepartamentos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "departamento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "departamento", "contador" }, allowSetters = true)
    private Set<DepartamentoContador> departamentoContadors = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "departamento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionario", "departamento" }, allowSetters = true)
    private Set<DepartamentoFuncionario> departamentoFuncionarios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Departamento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Departamento nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Departamento descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<DepartamentoEmpresa> getDepartamentoEmpresas() {
        return this.departamentoEmpresas;
    }

    public void setDepartamentoEmpresas(Set<DepartamentoEmpresa> departamentoEmpresas) {
        if (this.departamentoEmpresas != null) {
            this.departamentoEmpresas.forEach(i -> i.setDepartamento(null));
        }
        if (departamentoEmpresas != null) {
            departamentoEmpresas.forEach(i -> i.setDepartamento(this));
        }
        this.departamentoEmpresas = departamentoEmpresas;
    }

    public Departamento departamentoEmpresas(Set<DepartamentoEmpresa> departamentoEmpresas) {
        this.setDepartamentoEmpresas(departamentoEmpresas);
        return this;
    }

    public Departamento addDepartamentoEmpresa(DepartamentoEmpresa departamentoEmpresa) {
        this.departamentoEmpresas.add(departamentoEmpresa);
        departamentoEmpresa.setDepartamento(this);
        return this;
    }

    public Departamento removeDepartamentoEmpresa(DepartamentoEmpresa departamentoEmpresa) {
        this.departamentoEmpresas.remove(departamentoEmpresa);
        departamentoEmpresa.setDepartamento(null);
        return this;
    }

    public Set<PerfilContadorDepartamento> getPerfilContadorDepartamentos() {
        return this.perfilContadorDepartamentos;
    }

    public void setPerfilContadorDepartamentos(Set<PerfilContadorDepartamento> perfilContadorDepartamentos) {
        if (this.perfilContadorDepartamentos != null) {
            this.perfilContadorDepartamentos.forEach(i -> i.setDepartamento(null));
        }
        if (perfilContadorDepartamentos != null) {
            perfilContadorDepartamentos.forEach(i -> i.setDepartamento(this));
        }
        this.perfilContadorDepartamentos = perfilContadorDepartamentos;
    }

    public Departamento perfilContadorDepartamentos(Set<PerfilContadorDepartamento> perfilContadorDepartamentos) {
        this.setPerfilContadorDepartamentos(perfilContadorDepartamentos);
        return this;
    }

    public Departamento addPerfilContadorDepartamento(PerfilContadorDepartamento perfilContadorDepartamento) {
        this.perfilContadorDepartamentos.add(perfilContadorDepartamento);
        perfilContadorDepartamento.setDepartamento(this);
        return this;
    }

    public Departamento removePerfilContadorDepartamento(PerfilContadorDepartamento perfilContadorDepartamento) {
        this.perfilContadorDepartamentos.remove(perfilContadorDepartamento);
        perfilContadorDepartamento.setDepartamento(null);
        return this;
    }

    public Set<DepartamentoContador> getDepartamentoContadors() {
        return this.departamentoContadors;
    }

    public void setDepartamentoContadors(Set<DepartamentoContador> departamentoContadors) {
        if (this.departamentoContadors != null) {
            this.departamentoContadors.forEach(i -> i.setDepartamento(null));
        }
        if (departamentoContadors != null) {
            departamentoContadors.forEach(i -> i.setDepartamento(this));
        }
        this.departamentoContadors = departamentoContadors;
    }

    public Departamento departamentoContadors(Set<DepartamentoContador> departamentoContadors) {
        this.setDepartamentoContadors(departamentoContadors);
        return this;
    }

    public Departamento addDepartamentoContador(DepartamentoContador departamentoContador) {
        this.departamentoContadors.add(departamentoContador);
        departamentoContador.setDepartamento(this);
        return this;
    }

    public Departamento removeDepartamentoContador(DepartamentoContador departamentoContador) {
        this.departamentoContadors.remove(departamentoContador);
        departamentoContador.setDepartamento(null);
        return this;
    }

    public Set<DepartamentoFuncionario> getDepartamentoFuncionarios() {
        return this.departamentoFuncionarios;
    }

    public void setDepartamentoFuncionarios(Set<DepartamentoFuncionario> departamentoFuncionarios) {
        if (this.departamentoFuncionarios != null) {
            this.departamentoFuncionarios.forEach(i -> i.setDepartamento(null));
        }
        if (departamentoFuncionarios != null) {
            departamentoFuncionarios.forEach(i -> i.setDepartamento(this));
        }
        this.departamentoFuncionarios = departamentoFuncionarios;
    }

    public Departamento departamentoFuncionarios(Set<DepartamentoFuncionario> departamentoFuncionarios) {
        this.setDepartamentoFuncionarios(departamentoFuncionarios);
        return this;
    }

    public Departamento addDepartamentoFuncionario(DepartamentoFuncionario departamentoFuncionario) {
        this.departamentoFuncionarios.add(departamentoFuncionario);
        departamentoFuncionario.setDepartamento(this);
        return this;
    }

    public Departamento removeDepartamentoFuncionario(DepartamentoFuncionario departamentoFuncionario) {
        this.departamentoFuncionarios.remove(departamentoFuncionario);
        departamentoFuncionario.setDepartamento(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Departamento)) {
            return false;
        }
        return getId() != null && getId().equals(((Departamento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Departamento{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
