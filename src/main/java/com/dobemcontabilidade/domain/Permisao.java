package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Permisao.
 */
@Entity
@Table(name = "permisao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Permisao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "label")
    private String label;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "permisao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionalidade", "grupoAcessoPadrao", "permisao" }, allowSetters = true)
    private Set<FuncionalidadeGrupoAcessoPadrao> funcionalidadeGrupoAcessoPadraos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "permisao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionalidade", "grupoAcessoEmpresa", "permisao" }, allowSetters = true)
    private Set<FuncionalidadeGrupoAcessoEmpresa> funcionalidadeGrupoAcessoEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "permisao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "usuarioContador", "permisao", "grupoAcessoEmpresa" }, allowSetters = true)
    private Set<GrupoAcessoEmpresaUsuarioContador> grupoAcessoEmpresaUsuarioContadors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Permisao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Permisao nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Permisao descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLabel() {
        return this.label;
    }

    public Permisao label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<FuncionalidadeGrupoAcessoPadrao> getFuncionalidadeGrupoAcessoPadraos() {
        return this.funcionalidadeGrupoAcessoPadraos;
    }

    public void setFuncionalidadeGrupoAcessoPadraos(Set<FuncionalidadeGrupoAcessoPadrao> funcionalidadeGrupoAcessoPadraos) {
        if (this.funcionalidadeGrupoAcessoPadraos != null) {
            this.funcionalidadeGrupoAcessoPadraos.forEach(i -> i.setPermisao(null));
        }
        if (funcionalidadeGrupoAcessoPadraos != null) {
            funcionalidadeGrupoAcessoPadraos.forEach(i -> i.setPermisao(this));
        }
        this.funcionalidadeGrupoAcessoPadraos = funcionalidadeGrupoAcessoPadraos;
    }

    public Permisao funcionalidadeGrupoAcessoPadraos(Set<FuncionalidadeGrupoAcessoPadrao> funcionalidadeGrupoAcessoPadraos) {
        this.setFuncionalidadeGrupoAcessoPadraos(funcionalidadeGrupoAcessoPadraos);
        return this;
    }

    public Permisao addFuncionalidadeGrupoAcessoPadrao(FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao) {
        this.funcionalidadeGrupoAcessoPadraos.add(funcionalidadeGrupoAcessoPadrao);
        funcionalidadeGrupoAcessoPadrao.setPermisao(this);
        return this;
    }

    public Permisao removeFuncionalidadeGrupoAcessoPadrao(FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao) {
        this.funcionalidadeGrupoAcessoPadraos.remove(funcionalidadeGrupoAcessoPadrao);
        funcionalidadeGrupoAcessoPadrao.setPermisao(null);
        return this;
    }

    public Set<FuncionalidadeGrupoAcessoEmpresa> getFuncionalidadeGrupoAcessoEmpresas() {
        return this.funcionalidadeGrupoAcessoEmpresas;
    }

    public void setFuncionalidadeGrupoAcessoEmpresas(Set<FuncionalidadeGrupoAcessoEmpresa> funcionalidadeGrupoAcessoEmpresas) {
        if (this.funcionalidadeGrupoAcessoEmpresas != null) {
            this.funcionalidadeGrupoAcessoEmpresas.forEach(i -> i.setPermisao(null));
        }
        if (funcionalidadeGrupoAcessoEmpresas != null) {
            funcionalidadeGrupoAcessoEmpresas.forEach(i -> i.setPermisao(this));
        }
        this.funcionalidadeGrupoAcessoEmpresas = funcionalidadeGrupoAcessoEmpresas;
    }

    public Permisao funcionalidadeGrupoAcessoEmpresas(Set<FuncionalidadeGrupoAcessoEmpresa> funcionalidadeGrupoAcessoEmpresas) {
        this.setFuncionalidadeGrupoAcessoEmpresas(funcionalidadeGrupoAcessoEmpresas);
        return this;
    }

    public Permisao addFuncionalidadeGrupoAcessoEmpresa(FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresa) {
        this.funcionalidadeGrupoAcessoEmpresas.add(funcionalidadeGrupoAcessoEmpresa);
        funcionalidadeGrupoAcessoEmpresa.setPermisao(this);
        return this;
    }

    public Permisao removeFuncionalidadeGrupoAcessoEmpresa(FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresa) {
        this.funcionalidadeGrupoAcessoEmpresas.remove(funcionalidadeGrupoAcessoEmpresa);
        funcionalidadeGrupoAcessoEmpresa.setPermisao(null);
        return this;
    }

    public Set<GrupoAcessoEmpresaUsuarioContador> getGrupoAcessoEmpresaUsuarioContadors() {
        return this.grupoAcessoEmpresaUsuarioContadors;
    }

    public void setGrupoAcessoEmpresaUsuarioContadors(Set<GrupoAcessoEmpresaUsuarioContador> grupoAcessoEmpresaUsuarioContadors) {
        if (this.grupoAcessoEmpresaUsuarioContadors != null) {
            this.grupoAcessoEmpresaUsuarioContadors.forEach(i -> i.setPermisao(null));
        }
        if (grupoAcessoEmpresaUsuarioContadors != null) {
            grupoAcessoEmpresaUsuarioContadors.forEach(i -> i.setPermisao(this));
        }
        this.grupoAcessoEmpresaUsuarioContadors = grupoAcessoEmpresaUsuarioContadors;
    }

    public Permisao grupoAcessoEmpresaUsuarioContadors(Set<GrupoAcessoEmpresaUsuarioContador> grupoAcessoEmpresaUsuarioContadors) {
        this.setGrupoAcessoEmpresaUsuarioContadors(grupoAcessoEmpresaUsuarioContadors);
        return this;
    }

    public Permisao addGrupoAcessoEmpresaUsuarioContador(GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador) {
        this.grupoAcessoEmpresaUsuarioContadors.add(grupoAcessoEmpresaUsuarioContador);
        grupoAcessoEmpresaUsuarioContador.setPermisao(this);
        return this;
    }

    public Permisao removeGrupoAcessoEmpresaUsuarioContador(GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador) {
        this.grupoAcessoEmpresaUsuarioContadors.remove(grupoAcessoEmpresaUsuarioContador);
        grupoAcessoEmpresaUsuarioContador.setPermisao(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Permisao)) {
            return false;
        }
        return getId() != null && getId().equals(((Permisao) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Permisao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
