package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FuncionalidadeGrupoAcessoPadrao.
 */
@Entity
@Table(name = "func_grup_acess_padrao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuncionalidadeGrupoAcessoPadrao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "autorizado")
    private Boolean autorizado;

    @Column(name = "data_expiracao")
    private Instant dataExpiracao;

    @Column(name = "data_atribuicao")
    private Instant dataAtribuicao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "funcionalidadeGrupoAcessoPadraos", "funcionalidadeGrupoAcessoEmpresas", "modulo" },
        allowSetters = true
    )
    private Funcionalidade funcionalidade;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "funcionalidadeGrupoAcessoPadraos", "grupoAcessoUsuarioContadors" }, allowSetters = true)
    private GrupoAcessoPadrao grupoAcessoPadrao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "funcionalidadeGrupoAcessoPadraos", "funcionalidadeGrupoAcessoEmpresas", "grupoAcessoEmpresaUsuarioContadors" },
        allowSetters = true
    )
    private Permisao permisao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FuncionalidadeGrupoAcessoPadrao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAutorizado() {
        return this.autorizado;
    }

    public FuncionalidadeGrupoAcessoPadrao autorizado(Boolean autorizado) {
        this.setAutorizado(autorizado);
        return this;
    }

    public void setAutorizado(Boolean autorizado) {
        this.autorizado = autorizado;
    }

    public Instant getDataExpiracao() {
        return this.dataExpiracao;
    }

    public FuncionalidadeGrupoAcessoPadrao dataExpiracao(Instant dataExpiracao) {
        this.setDataExpiracao(dataExpiracao);
        return this;
    }

    public void setDataExpiracao(Instant dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public Instant getDataAtribuicao() {
        return this.dataAtribuicao;
    }

    public FuncionalidadeGrupoAcessoPadrao dataAtribuicao(Instant dataAtribuicao) {
        this.setDataAtribuicao(dataAtribuicao);
        return this;
    }

    public void setDataAtribuicao(Instant dataAtribuicao) {
        this.dataAtribuicao = dataAtribuicao;
    }

    public Funcionalidade getFuncionalidade() {
        return this.funcionalidade;
    }

    public void setFuncionalidade(Funcionalidade funcionalidade) {
        this.funcionalidade = funcionalidade;
    }

    public FuncionalidadeGrupoAcessoPadrao funcionalidade(Funcionalidade funcionalidade) {
        this.setFuncionalidade(funcionalidade);
        return this;
    }

    public GrupoAcessoPadrao getGrupoAcessoPadrao() {
        return this.grupoAcessoPadrao;
    }

    public void setGrupoAcessoPadrao(GrupoAcessoPadrao grupoAcessoPadrao) {
        this.grupoAcessoPadrao = grupoAcessoPadrao;
    }

    public FuncionalidadeGrupoAcessoPadrao grupoAcessoPadrao(GrupoAcessoPadrao grupoAcessoPadrao) {
        this.setGrupoAcessoPadrao(grupoAcessoPadrao);
        return this;
    }

    public Permisao getPermisao() {
        return this.permisao;
    }

    public void setPermisao(Permisao permisao) {
        this.permisao = permisao;
    }

    public FuncionalidadeGrupoAcessoPadrao permisao(Permisao permisao) {
        this.setPermisao(permisao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FuncionalidadeGrupoAcessoPadrao)) {
            return false;
        }
        return getId() != null && getId().equals(((FuncionalidadeGrupoAcessoPadrao) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuncionalidadeGrupoAcessoPadrao{" +
            "id=" + getId() +
            ", autorizado='" + getAutorizado() + "'" +
            ", dataExpiracao='" + getDataExpiracao() + "'" +
            ", dataAtribuicao='" + getDataAtribuicao() + "'" +
            "}";
    }
}
