package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TermoDeAdesao.
 */
@Entity
@Table(name = "termo_de_adesao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TermoDeAdesao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "termoDeAdesao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contador", "termoDeAdesao" }, allowSetters = true)
    private Set<TermoAdesaoContador> termoAdesaoContadors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TermoDeAdesao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public TermoDeAdesao titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public TermoDeAdesao descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<TermoAdesaoContador> getTermoAdesaoContadors() {
        return this.termoAdesaoContadors;
    }

    public void setTermoAdesaoContadors(Set<TermoAdesaoContador> termoAdesaoContadors) {
        if (this.termoAdesaoContadors != null) {
            this.termoAdesaoContadors.forEach(i -> i.setTermoDeAdesao(null));
        }
        if (termoAdesaoContadors != null) {
            termoAdesaoContadors.forEach(i -> i.setTermoDeAdesao(this));
        }
        this.termoAdesaoContadors = termoAdesaoContadors;
    }

    public TermoDeAdesao termoAdesaoContadors(Set<TermoAdesaoContador> termoAdesaoContadors) {
        this.setTermoAdesaoContadors(termoAdesaoContadors);
        return this;
    }

    public TermoDeAdesao addTermoAdesaoContador(TermoAdesaoContador termoAdesaoContador) {
        this.termoAdesaoContadors.add(termoAdesaoContador);
        termoAdesaoContador.setTermoDeAdesao(this);
        return this;
    }

    public TermoDeAdesao removeTermoAdesaoContador(TermoAdesaoContador termoAdesaoContador) {
        this.termoAdesaoContadors.remove(termoAdesaoContador);
        termoAdesaoContador.setTermoDeAdesao(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TermoDeAdesao)) {
            return false;
        }
        return getId() != null && getId().equals(((TermoDeAdesao) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TermoDeAdesao{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
