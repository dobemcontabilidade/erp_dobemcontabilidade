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
 * A GrupoAcessoEmpresa.
 */
@Entity
@Table(name = "grupo_acesso_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GrupoAcessoEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "grupoAcessoEmpresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionalidade", "grupoAcessoEmpresa", "permisao" }, allowSetters = true)
    private Set<FuncionalidadeGrupoAcessoEmpresa> funcionalidadeGrupoAcessoEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "grupoAcessoEmpresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "grupoAcessoEmpresa", "usuarioEmpresa" }, allowSetters = true)
    private Set<GrupoAcessoUsuarioEmpresa> grupoAcessoUsuarioEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "grupoAcessoEmpresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "usuarioContador", "permisao", "grupoAcessoEmpresa" }, allowSetters = true)
    private Set<GrupoAcessoEmpresaUsuarioContador> grupoAcessoEmpresaUsuarioContadors = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "grupoAcessoEmpresas",
            "areaContabilAssinaturaEmpresas",
            "servicoContabilAssinaturaEmpresas",
            "gatewayAssinaturaEmpresas",
            "calculoPlanoAssinaturas",
            "pagamentos",
            "cobrancaEmpresas",
            "usuarioEmpresas",
            "periodoPagamento",
            "formaDePagamento",
            "planoContaAzul",
            "planoContabil",
            "empresa",
        },
        allowSetters = true
    )
    private AssinaturaEmpresa assinaturaEmpresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GrupoAcessoEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public GrupoAcessoEmpresa nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<FuncionalidadeGrupoAcessoEmpresa> getFuncionalidadeGrupoAcessoEmpresas() {
        return this.funcionalidadeGrupoAcessoEmpresas;
    }

    public void setFuncionalidadeGrupoAcessoEmpresas(Set<FuncionalidadeGrupoAcessoEmpresa> funcionalidadeGrupoAcessoEmpresas) {
        if (this.funcionalidadeGrupoAcessoEmpresas != null) {
            this.funcionalidadeGrupoAcessoEmpresas.forEach(i -> i.setGrupoAcessoEmpresa(null));
        }
        if (funcionalidadeGrupoAcessoEmpresas != null) {
            funcionalidadeGrupoAcessoEmpresas.forEach(i -> i.setGrupoAcessoEmpresa(this));
        }
        this.funcionalidadeGrupoAcessoEmpresas = funcionalidadeGrupoAcessoEmpresas;
    }

    public GrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresas(Set<FuncionalidadeGrupoAcessoEmpresa> funcionalidadeGrupoAcessoEmpresas) {
        this.setFuncionalidadeGrupoAcessoEmpresas(funcionalidadeGrupoAcessoEmpresas);
        return this;
    }

    public GrupoAcessoEmpresa addFuncionalidadeGrupoAcessoEmpresa(FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresa) {
        this.funcionalidadeGrupoAcessoEmpresas.add(funcionalidadeGrupoAcessoEmpresa);
        funcionalidadeGrupoAcessoEmpresa.setGrupoAcessoEmpresa(this);
        return this;
    }

    public GrupoAcessoEmpresa removeFuncionalidadeGrupoAcessoEmpresa(FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresa) {
        this.funcionalidadeGrupoAcessoEmpresas.remove(funcionalidadeGrupoAcessoEmpresa);
        funcionalidadeGrupoAcessoEmpresa.setGrupoAcessoEmpresa(null);
        return this;
    }

    public Set<GrupoAcessoUsuarioEmpresa> getGrupoAcessoUsuarioEmpresas() {
        return this.grupoAcessoUsuarioEmpresas;
    }

    public void setGrupoAcessoUsuarioEmpresas(Set<GrupoAcessoUsuarioEmpresa> grupoAcessoUsuarioEmpresas) {
        if (this.grupoAcessoUsuarioEmpresas != null) {
            this.grupoAcessoUsuarioEmpresas.forEach(i -> i.setGrupoAcessoEmpresa(null));
        }
        if (grupoAcessoUsuarioEmpresas != null) {
            grupoAcessoUsuarioEmpresas.forEach(i -> i.setGrupoAcessoEmpresa(this));
        }
        this.grupoAcessoUsuarioEmpresas = grupoAcessoUsuarioEmpresas;
    }

    public GrupoAcessoEmpresa grupoAcessoUsuarioEmpresas(Set<GrupoAcessoUsuarioEmpresa> grupoAcessoUsuarioEmpresas) {
        this.setGrupoAcessoUsuarioEmpresas(grupoAcessoUsuarioEmpresas);
        return this;
    }

    public GrupoAcessoEmpresa addGrupoAcessoUsuarioEmpresa(GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa) {
        this.grupoAcessoUsuarioEmpresas.add(grupoAcessoUsuarioEmpresa);
        grupoAcessoUsuarioEmpresa.setGrupoAcessoEmpresa(this);
        return this;
    }

    public GrupoAcessoEmpresa removeGrupoAcessoUsuarioEmpresa(GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa) {
        this.grupoAcessoUsuarioEmpresas.remove(grupoAcessoUsuarioEmpresa);
        grupoAcessoUsuarioEmpresa.setGrupoAcessoEmpresa(null);
        return this;
    }

    public Set<GrupoAcessoEmpresaUsuarioContador> getGrupoAcessoEmpresaUsuarioContadors() {
        return this.grupoAcessoEmpresaUsuarioContadors;
    }

    public void setGrupoAcessoEmpresaUsuarioContadors(Set<GrupoAcessoEmpresaUsuarioContador> grupoAcessoEmpresaUsuarioContadors) {
        if (this.grupoAcessoEmpresaUsuarioContadors != null) {
            this.grupoAcessoEmpresaUsuarioContadors.forEach(i -> i.setGrupoAcessoEmpresa(null));
        }
        if (grupoAcessoEmpresaUsuarioContadors != null) {
            grupoAcessoEmpresaUsuarioContadors.forEach(i -> i.setGrupoAcessoEmpresa(this));
        }
        this.grupoAcessoEmpresaUsuarioContadors = grupoAcessoEmpresaUsuarioContadors;
    }

    public GrupoAcessoEmpresa grupoAcessoEmpresaUsuarioContadors(
        Set<GrupoAcessoEmpresaUsuarioContador> grupoAcessoEmpresaUsuarioContadors
    ) {
        this.setGrupoAcessoEmpresaUsuarioContadors(grupoAcessoEmpresaUsuarioContadors);
        return this;
    }

    public GrupoAcessoEmpresa addGrupoAcessoEmpresaUsuarioContador(GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador) {
        this.grupoAcessoEmpresaUsuarioContadors.add(grupoAcessoEmpresaUsuarioContador);
        grupoAcessoEmpresaUsuarioContador.setGrupoAcessoEmpresa(this);
        return this;
    }

    public GrupoAcessoEmpresa removeGrupoAcessoEmpresaUsuarioContador(GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador) {
        this.grupoAcessoEmpresaUsuarioContadors.remove(grupoAcessoEmpresaUsuarioContador);
        grupoAcessoEmpresaUsuarioContador.setGrupoAcessoEmpresa(null);
        return this;
    }

    public AssinaturaEmpresa getAssinaturaEmpresa() {
        return this.assinaturaEmpresa;
    }

    public void setAssinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.assinaturaEmpresa = assinaturaEmpresa;
    }

    public GrupoAcessoEmpresa assinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.setAssinaturaEmpresa(assinaturaEmpresa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrupoAcessoEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((GrupoAcessoEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GrupoAcessoEmpresa{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
