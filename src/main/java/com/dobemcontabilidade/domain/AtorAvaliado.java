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
 * A AtorAvaliado.
 */
@Entity
@Table(name = "ator_avaliado")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AtorAvaliado implements Serializable {

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

    @Column(name = "ativo")
    private Boolean ativo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "atorAvaliado")
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

    public AtorAvaliado id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public AtorAvaliado nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public AtorAvaliado descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getAtivo() {
        return this.ativo;
    }

    public AtorAvaliado ativo(Boolean ativo) {
        this.setAtivo(ativo);
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Set<CriterioAvaliacaoAtor> getCriterioAvaliacaoAtors() {
        return this.criterioAvaliacaoAtors;
    }

    public void setCriterioAvaliacaoAtors(Set<CriterioAvaliacaoAtor> criterioAvaliacaoAtors) {
        if (this.criterioAvaliacaoAtors != null) {
            this.criterioAvaliacaoAtors.forEach(i -> i.setAtorAvaliado(null));
        }
        if (criterioAvaliacaoAtors != null) {
            criterioAvaliacaoAtors.forEach(i -> i.setAtorAvaliado(this));
        }
        this.criterioAvaliacaoAtors = criterioAvaliacaoAtors;
    }

    public AtorAvaliado criterioAvaliacaoAtors(Set<CriterioAvaliacaoAtor> criterioAvaliacaoAtors) {
        this.setCriterioAvaliacaoAtors(criterioAvaliacaoAtors);
        return this;
    }

    public AtorAvaliado addCriterioAvaliacaoAtor(CriterioAvaliacaoAtor criterioAvaliacaoAtor) {
        this.criterioAvaliacaoAtors.add(criterioAvaliacaoAtor);
        criterioAvaliacaoAtor.setAtorAvaliado(this);
        return this;
    }

    public AtorAvaliado removeCriterioAvaliacaoAtor(CriterioAvaliacaoAtor criterioAvaliacaoAtor) {
        this.criterioAvaliacaoAtors.remove(criterioAvaliacaoAtor);
        criterioAvaliacaoAtor.setAtorAvaliado(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AtorAvaliado)) {
            return false;
        }
        return getId() != null && getId().equals(((AtorAvaliado) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AtorAvaliado{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", ativo='" + getAtivo() + "'" +
            "}";
    }
}
