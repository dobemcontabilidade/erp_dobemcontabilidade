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
 * A CriterioAvaliacaoAtor.
 */
@Entity
@Table(name = "criterio_avaliacao_ator")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CriterioAvaliacaoAtor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "ativo")
    private Boolean ativo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "criterioAvaliacaoAtor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "usuarioEmpresa", "usuarioContador", "criterioAvaliacaoAtor", "ordemServico" }, allowSetters = true)
    private Set<FeedBackUsuarioParaContador> feedBackUsuarioParaContadors = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "criterioAvaliacaoAtor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "criterioAvaliacaoAtor", "usuarioEmpresa", "contador", "ordemServico" }, allowSetters = true)
    private Set<FeedBackContadorParaUsuario> feedBackContadorParaUsuarios = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "criterioAvaliacaoAtors" }, allowSetters = true)
    private CriterioAvaliacao criterioAvaliacao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "criterioAvaliacaoAtors" }, allowSetters = true)
    private AtorAvaliado atorAvaliado;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CriterioAvaliacaoAtor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public CriterioAvaliacaoAtor descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getAtivo() {
        return this.ativo;
    }

    public CriterioAvaliacaoAtor ativo(Boolean ativo) {
        this.setAtivo(ativo);
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Set<FeedBackUsuarioParaContador> getFeedBackUsuarioParaContadors() {
        return this.feedBackUsuarioParaContadors;
    }

    public void setFeedBackUsuarioParaContadors(Set<FeedBackUsuarioParaContador> feedBackUsuarioParaContadors) {
        if (this.feedBackUsuarioParaContadors != null) {
            this.feedBackUsuarioParaContadors.forEach(i -> i.setCriterioAvaliacaoAtor(null));
        }
        if (feedBackUsuarioParaContadors != null) {
            feedBackUsuarioParaContadors.forEach(i -> i.setCriterioAvaliacaoAtor(this));
        }
        this.feedBackUsuarioParaContadors = feedBackUsuarioParaContadors;
    }

    public CriterioAvaliacaoAtor feedBackUsuarioParaContadors(Set<FeedBackUsuarioParaContador> feedBackUsuarioParaContadors) {
        this.setFeedBackUsuarioParaContadors(feedBackUsuarioParaContadors);
        return this;
    }

    public CriterioAvaliacaoAtor addFeedBackUsuarioParaContador(FeedBackUsuarioParaContador feedBackUsuarioParaContador) {
        this.feedBackUsuarioParaContadors.add(feedBackUsuarioParaContador);
        feedBackUsuarioParaContador.setCriterioAvaliacaoAtor(this);
        return this;
    }

    public CriterioAvaliacaoAtor removeFeedBackUsuarioParaContador(FeedBackUsuarioParaContador feedBackUsuarioParaContador) {
        this.feedBackUsuarioParaContadors.remove(feedBackUsuarioParaContador);
        feedBackUsuarioParaContador.setCriterioAvaliacaoAtor(null);
        return this;
    }

    public Set<FeedBackContadorParaUsuario> getFeedBackContadorParaUsuarios() {
        return this.feedBackContadorParaUsuarios;
    }

    public void setFeedBackContadorParaUsuarios(Set<FeedBackContadorParaUsuario> feedBackContadorParaUsuarios) {
        if (this.feedBackContadorParaUsuarios != null) {
            this.feedBackContadorParaUsuarios.forEach(i -> i.setCriterioAvaliacaoAtor(null));
        }
        if (feedBackContadorParaUsuarios != null) {
            feedBackContadorParaUsuarios.forEach(i -> i.setCriterioAvaliacaoAtor(this));
        }
        this.feedBackContadorParaUsuarios = feedBackContadorParaUsuarios;
    }

    public CriterioAvaliacaoAtor feedBackContadorParaUsuarios(Set<FeedBackContadorParaUsuario> feedBackContadorParaUsuarios) {
        this.setFeedBackContadorParaUsuarios(feedBackContadorParaUsuarios);
        return this;
    }

    public CriterioAvaliacaoAtor addFeedBackContadorParaUsuario(FeedBackContadorParaUsuario feedBackContadorParaUsuario) {
        this.feedBackContadorParaUsuarios.add(feedBackContadorParaUsuario);
        feedBackContadorParaUsuario.setCriterioAvaliacaoAtor(this);
        return this;
    }

    public CriterioAvaliacaoAtor removeFeedBackContadorParaUsuario(FeedBackContadorParaUsuario feedBackContadorParaUsuario) {
        this.feedBackContadorParaUsuarios.remove(feedBackContadorParaUsuario);
        feedBackContadorParaUsuario.setCriterioAvaliacaoAtor(null);
        return this;
    }

    public CriterioAvaliacao getCriterioAvaliacao() {
        return this.criterioAvaliacao;
    }

    public void setCriterioAvaliacao(CriterioAvaliacao criterioAvaliacao) {
        this.criterioAvaliacao = criterioAvaliacao;
    }

    public CriterioAvaliacaoAtor criterioAvaliacao(CriterioAvaliacao criterioAvaliacao) {
        this.setCriterioAvaliacao(criterioAvaliacao);
        return this;
    }

    public AtorAvaliado getAtorAvaliado() {
        return this.atorAvaliado;
    }

    public void setAtorAvaliado(AtorAvaliado atorAvaliado) {
        this.atorAvaliado = atorAvaliado;
    }

    public CriterioAvaliacaoAtor atorAvaliado(AtorAvaliado atorAvaliado) {
        this.setAtorAvaliado(atorAvaliado);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CriterioAvaliacaoAtor)) {
            return false;
        }
        return getId() != null && getId().equals(((CriterioAvaliacaoAtor) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CriterioAvaliacaoAtor{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", ativo='" + getAtivo() + "'" +
            "}";
    }
}
