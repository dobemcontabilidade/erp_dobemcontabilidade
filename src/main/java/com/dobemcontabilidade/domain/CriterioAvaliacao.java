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
 * A CriterioAvaliacao.
 */
@Entity
@Table(name = "criterio_avaliacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CriterioAvaliacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "criterioAvaliacao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "feedBackUsuarioParaContadors", "feedBackContadorParaUsuarios", "criterioAvaliacao", "atorAvaliado" },
        allowSetters = true
    )
    private Set<CriterioAvaliacaoAtor> criterioAvaliacaoAtors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CriterioAvaliacao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public CriterioAvaliacao nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public CriterioAvaliacao descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<CriterioAvaliacaoAtor> getCriterioAvaliacaoAtors() {
        return this.criterioAvaliacaoAtors;
    }

    public void setCriterioAvaliacaoAtors(Set<CriterioAvaliacaoAtor> criterioAvaliacaoAtors) {
        if (this.criterioAvaliacaoAtors != null) {
            this.criterioAvaliacaoAtors.forEach(i -> i.setCriterioAvaliacao(null));
        }
        if (criterioAvaliacaoAtors != null) {
            criterioAvaliacaoAtors.forEach(i -> i.setCriterioAvaliacao(this));
        }
        this.criterioAvaliacaoAtors = criterioAvaliacaoAtors;
    }

    public CriterioAvaliacao criterioAvaliacaoAtors(Set<CriterioAvaliacaoAtor> criterioAvaliacaoAtors) {
        this.setCriterioAvaliacaoAtors(criterioAvaliacaoAtors);
        return this;
    }

    public CriterioAvaliacao addCriterioAvaliacaoAtor(CriterioAvaliacaoAtor criterioAvaliacaoAtor) {
        this.criterioAvaliacaoAtors.add(criterioAvaliacaoAtor);
        criterioAvaliacaoAtor.setCriterioAvaliacao(this);
        return this;
    }

    public CriterioAvaliacao removeCriterioAvaliacaoAtor(CriterioAvaliacaoAtor criterioAvaliacaoAtor) {
        this.criterioAvaliacaoAtors.remove(criterioAvaliacaoAtor);
        criterioAvaliacaoAtor.setCriterioAvaliacao(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CriterioAvaliacao)) {
            return false;
        }
        return getId() != null && getId().equals(((CriterioAvaliacao) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CriterioAvaliacao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
