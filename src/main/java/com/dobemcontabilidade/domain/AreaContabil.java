package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AreaContabil.
 */
@Entity
@Table(name = "area_contabil")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AreaContabil implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "areaContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "areaContabil", "perfilContador" }, allowSetters = true)
    private Set<PerfilContadorAreaContabil> perfilContadorAreaContabils = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "areaContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "areaContabil", "contador" }, allowSetters = true)
    private Set<AreaContabilContador> areaContabilContadors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AreaContabil id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public AreaContabil nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public AreaContabil descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<PerfilContadorAreaContabil> getPerfilContadorAreaContabils() {
        return this.perfilContadorAreaContabils;
    }

    public void setPerfilContadorAreaContabils(Set<PerfilContadorAreaContabil> perfilContadorAreaContabils) {
        if (this.perfilContadorAreaContabils != null) {
            this.perfilContadorAreaContabils.forEach(i -> i.setAreaContabil(null));
        }
        if (perfilContadorAreaContabils != null) {
            perfilContadorAreaContabils.forEach(i -> i.setAreaContabil(this));
        }
        this.perfilContadorAreaContabils = perfilContadorAreaContabils;
    }

    public AreaContabil perfilContadorAreaContabils(Set<PerfilContadorAreaContabil> perfilContadorAreaContabils) {
        this.setPerfilContadorAreaContabils(perfilContadorAreaContabils);
        return this;
    }

    public AreaContabil addPerfilContadorAreaContabil(PerfilContadorAreaContabil perfilContadorAreaContabil) {
        this.perfilContadorAreaContabils.add(perfilContadorAreaContabil);
        perfilContadorAreaContabil.setAreaContabil(this);
        return this;
    }

    public AreaContabil removePerfilContadorAreaContabil(PerfilContadorAreaContabil perfilContadorAreaContabil) {
        this.perfilContadorAreaContabils.remove(perfilContadorAreaContabil);
        perfilContadorAreaContabil.setAreaContabil(null);
        return this;
    }

    public Set<AreaContabilContador> getAreaContabilContadors() {
        return this.areaContabilContadors;
    }

    public void setAreaContabilContadors(Set<AreaContabilContador> areaContabilContadors) {
        if (this.areaContabilContadors != null) {
            this.areaContabilContadors.forEach(i -> i.setAreaContabil(null));
        }
        if (areaContabilContadors != null) {
            areaContabilContadors.forEach(i -> i.setAreaContabil(this));
        }
        this.areaContabilContadors = areaContabilContadors;
    }

    public AreaContabil areaContabilContadors(Set<AreaContabilContador> areaContabilContadors) {
        this.setAreaContabilContadors(areaContabilContadors);
        return this;
    }

    public AreaContabil addAreaContabilContador(AreaContabilContador areaContabilContador) {
        this.areaContabilContadors.add(areaContabilContador);
        areaContabilContador.setAreaContabil(this);
        return this;
    }

    public AreaContabil removeAreaContabilContador(AreaContabilContador areaContabilContador) {
        this.areaContabilContadors.remove(areaContabilContador);
        areaContabilContador.setAreaContabil(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AreaContabil)) {
            return false;
        }
        return getId() != null && getId().equals(((AreaContabil) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaContabil{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
