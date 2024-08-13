package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Avaliacao.
 */
@Entity
@Table(name = "avaliacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Avaliacao implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "avaliacao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contador", "avaliacao" }, allowSetters = true)
    private Set<AvaliacaoContador> avaliacaoContadors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Avaliacao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Avaliacao nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Avaliacao descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<AvaliacaoContador> getAvaliacaoContadors() {
        return this.avaliacaoContadors;
    }

    public void setAvaliacaoContadors(Set<AvaliacaoContador> avaliacaoContadors) {
        if (this.avaliacaoContadors != null) {
            this.avaliacaoContadors.forEach(i -> i.setAvaliacao(null));
        }
        if (avaliacaoContadors != null) {
            avaliacaoContadors.forEach(i -> i.setAvaliacao(this));
        }
        this.avaliacaoContadors = avaliacaoContadors;
    }

    public Avaliacao avaliacaoContadors(Set<AvaliacaoContador> avaliacaoContadors) {
        this.setAvaliacaoContadors(avaliacaoContadors);
        return this;
    }

    public Avaliacao addAvaliacaoContador(AvaliacaoContador avaliacaoContador) {
        this.avaliacaoContadors.add(avaliacaoContador);
        avaliacaoContador.setAvaliacao(this);
        return this;
    }

    public Avaliacao removeAvaliacaoContador(AvaliacaoContador avaliacaoContador) {
        this.avaliacaoContadors.remove(avaliacaoContador);
        avaliacaoContador.setAvaliacao(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Avaliacao)) {
            return false;
        }
        return getId() != null && getId().equals(((Avaliacao) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Avaliacao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
