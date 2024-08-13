package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GrupoAcessoPadrao.
 */
@Entity
@Table(name = "grupo_acesso_padrao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GrupoAcessoPadrao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "grupoAcessoPadrao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionalidade", "grupoAcessoPadrao", "permisao" }, allowSetters = true)
    private Set<FuncionalidadeGrupoAcessoPadrao> funcionalidadeGrupoAcessoPadraos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "grupoAcessoPadrao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "usuarioContador", "grupoAcessoPadrao" }, allowSetters = true)
    private Set<GrupoAcessoUsuarioContador> grupoAcessoUsuarioContadors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GrupoAcessoPadrao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public GrupoAcessoPadrao nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<FuncionalidadeGrupoAcessoPadrao> getFuncionalidadeGrupoAcessoPadraos() {
        return this.funcionalidadeGrupoAcessoPadraos;
    }

    public void setFuncionalidadeGrupoAcessoPadraos(Set<FuncionalidadeGrupoAcessoPadrao> funcionalidadeGrupoAcessoPadraos) {
        if (this.funcionalidadeGrupoAcessoPadraos != null) {
            this.funcionalidadeGrupoAcessoPadraos.forEach(i -> i.setGrupoAcessoPadrao(null));
        }
        if (funcionalidadeGrupoAcessoPadraos != null) {
            funcionalidadeGrupoAcessoPadraos.forEach(i -> i.setGrupoAcessoPadrao(this));
        }
        this.funcionalidadeGrupoAcessoPadraos = funcionalidadeGrupoAcessoPadraos;
    }

    public GrupoAcessoPadrao funcionalidadeGrupoAcessoPadraos(Set<FuncionalidadeGrupoAcessoPadrao> funcionalidadeGrupoAcessoPadraos) {
        this.setFuncionalidadeGrupoAcessoPadraos(funcionalidadeGrupoAcessoPadraos);
        return this;
    }

    public GrupoAcessoPadrao addFuncionalidadeGrupoAcessoPadrao(FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao) {
        this.funcionalidadeGrupoAcessoPadraos.add(funcionalidadeGrupoAcessoPadrao);
        funcionalidadeGrupoAcessoPadrao.setGrupoAcessoPadrao(this);
        return this;
    }

    public GrupoAcessoPadrao removeFuncionalidadeGrupoAcessoPadrao(FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao) {
        this.funcionalidadeGrupoAcessoPadraos.remove(funcionalidadeGrupoAcessoPadrao);
        funcionalidadeGrupoAcessoPadrao.setGrupoAcessoPadrao(null);
        return this;
    }

    public Set<GrupoAcessoUsuarioContador> getGrupoAcessoUsuarioContadors() {
        return this.grupoAcessoUsuarioContadors;
    }

    public void setGrupoAcessoUsuarioContadors(Set<GrupoAcessoUsuarioContador> grupoAcessoUsuarioContadors) {
        if (this.grupoAcessoUsuarioContadors != null) {
            this.grupoAcessoUsuarioContadors.forEach(i -> i.setGrupoAcessoPadrao(null));
        }
        if (grupoAcessoUsuarioContadors != null) {
            grupoAcessoUsuarioContadors.forEach(i -> i.setGrupoAcessoPadrao(this));
        }
        this.grupoAcessoUsuarioContadors = grupoAcessoUsuarioContadors;
    }

    public GrupoAcessoPadrao grupoAcessoUsuarioContadors(Set<GrupoAcessoUsuarioContador> grupoAcessoUsuarioContadors) {
        this.setGrupoAcessoUsuarioContadors(grupoAcessoUsuarioContadors);
        return this;
    }

    public GrupoAcessoPadrao addGrupoAcessoUsuarioContador(GrupoAcessoUsuarioContador grupoAcessoUsuarioContador) {
        this.grupoAcessoUsuarioContadors.add(grupoAcessoUsuarioContador);
        grupoAcessoUsuarioContador.setGrupoAcessoPadrao(this);
        return this;
    }

    public GrupoAcessoPadrao removeGrupoAcessoUsuarioContador(GrupoAcessoUsuarioContador grupoAcessoUsuarioContador) {
        this.grupoAcessoUsuarioContadors.remove(grupoAcessoUsuarioContador);
        grupoAcessoUsuarioContador.setGrupoAcessoPadrao(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrupoAcessoPadrao)) {
            return false;
        }
        return getId() != null && getId().equals(((GrupoAcessoPadrao) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GrupoAcessoPadrao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
