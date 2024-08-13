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
 * A Funcionalidade.
 */
@Entity
@Table(name = "funcionalidade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Funcionalidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "ativa")
    private Boolean ativa;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionalidade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionalidade", "grupoAcessoPadrao", "permisao" }, allowSetters = true)
    private Set<FuncionalidadeGrupoAcessoPadrao> funcionalidadeGrupoAcessoPadraos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionalidade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionalidade", "grupoAcessoEmpresa", "permisao" }, allowSetters = true)
    private Set<FuncionalidadeGrupoAcessoEmpresa> funcionalidadeGrupoAcessoEmpresas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "funcionalidades", "sistema" }, allowSetters = true)
    private Modulo modulo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Funcionalidade id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Funcionalidade nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAtiva() {
        return this.ativa;
    }

    public Funcionalidade ativa(Boolean ativa) {
        this.setAtiva(ativa);
        return this;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public Set<FuncionalidadeGrupoAcessoPadrao> getFuncionalidadeGrupoAcessoPadraos() {
        return this.funcionalidadeGrupoAcessoPadraos;
    }

    public void setFuncionalidadeGrupoAcessoPadraos(Set<FuncionalidadeGrupoAcessoPadrao> funcionalidadeGrupoAcessoPadraos) {
        if (this.funcionalidadeGrupoAcessoPadraos != null) {
            this.funcionalidadeGrupoAcessoPadraos.forEach(i -> i.setFuncionalidade(null));
        }
        if (funcionalidadeGrupoAcessoPadraos != null) {
            funcionalidadeGrupoAcessoPadraos.forEach(i -> i.setFuncionalidade(this));
        }
        this.funcionalidadeGrupoAcessoPadraos = funcionalidadeGrupoAcessoPadraos;
    }

    public Funcionalidade funcionalidadeGrupoAcessoPadraos(Set<FuncionalidadeGrupoAcessoPadrao> funcionalidadeGrupoAcessoPadraos) {
        this.setFuncionalidadeGrupoAcessoPadraos(funcionalidadeGrupoAcessoPadraos);
        return this;
    }

    public Funcionalidade addFuncionalidadeGrupoAcessoPadrao(FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao) {
        this.funcionalidadeGrupoAcessoPadraos.add(funcionalidadeGrupoAcessoPadrao);
        funcionalidadeGrupoAcessoPadrao.setFuncionalidade(this);
        return this;
    }

    public Funcionalidade removeFuncionalidadeGrupoAcessoPadrao(FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao) {
        this.funcionalidadeGrupoAcessoPadraos.remove(funcionalidadeGrupoAcessoPadrao);
        funcionalidadeGrupoAcessoPadrao.setFuncionalidade(null);
        return this;
    }

    public Set<FuncionalidadeGrupoAcessoEmpresa> getFuncionalidadeGrupoAcessoEmpresas() {
        return this.funcionalidadeGrupoAcessoEmpresas;
    }

    public void setFuncionalidadeGrupoAcessoEmpresas(Set<FuncionalidadeGrupoAcessoEmpresa> funcionalidadeGrupoAcessoEmpresas) {
        if (this.funcionalidadeGrupoAcessoEmpresas != null) {
            this.funcionalidadeGrupoAcessoEmpresas.forEach(i -> i.setFuncionalidade(null));
        }
        if (funcionalidadeGrupoAcessoEmpresas != null) {
            funcionalidadeGrupoAcessoEmpresas.forEach(i -> i.setFuncionalidade(this));
        }
        this.funcionalidadeGrupoAcessoEmpresas = funcionalidadeGrupoAcessoEmpresas;
    }

    public Funcionalidade funcionalidadeGrupoAcessoEmpresas(Set<FuncionalidadeGrupoAcessoEmpresa> funcionalidadeGrupoAcessoEmpresas) {
        this.setFuncionalidadeGrupoAcessoEmpresas(funcionalidadeGrupoAcessoEmpresas);
        return this;
    }

    public Funcionalidade addFuncionalidadeGrupoAcessoEmpresa(FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresa) {
        this.funcionalidadeGrupoAcessoEmpresas.add(funcionalidadeGrupoAcessoEmpresa);
        funcionalidadeGrupoAcessoEmpresa.setFuncionalidade(this);
        return this;
    }

    public Funcionalidade removeFuncionalidadeGrupoAcessoEmpresa(FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresa) {
        this.funcionalidadeGrupoAcessoEmpresas.remove(funcionalidadeGrupoAcessoEmpresa);
        funcionalidadeGrupoAcessoEmpresa.setFuncionalidade(null);
        return this;
    }

    public Modulo getModulo() {
        return this.modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public Funcionalidade modulo(Modulo modulo) {
        this.setModulo(modulo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Funcionalidade)) {
            return false;
        }
        return getId() != null && getId().equals(((Funcionalidade) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Funcionalidade{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", ativa='" + getAtiva() + "'" +
            "}";
    }
}
