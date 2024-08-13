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
 * A Escolaridade.
 */
@Entity
@Table(name = "escolaridade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Escolaridade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "escolaridade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa", "escolaridade" }, allowSetters = true)
    private Set<EscolaridadePessoa> escolaridadePessoas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Escolaridade id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Escolaridade nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Escolaridade descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<EscolaridadePessoa> getEscolaridadePessoas() {
        return this.escolaridadePessoas;
    }

    public void setEscolaridadePessoas(Set<EscolaridadePessoa> escolaridadePessoas) {
        if (this.escolaridadePessoas != null) {
            this.escolaridadePessoas.forEach(i -> i.setEscolaridade(null));
        }
        if (escolaridadePessoas != null) {
            escolaridadePessoas.forEach(i -> i.setEscolaridade(this));
        }
        this.escolaridadePessoas = escolaridadePessoas;
    }

    public Escolaridade escolaridadePessoas(Set<EscolaridadePessoa> escolaridadePessoas) {
        this.setEscolaridadePessoas(escolaridadePessoas);
        return this;
    }

    public Escolaridade addEscolaridadePessoa(EscolaridadePessoa escolaridadePessoa) {
        this.escolaridadePessoas.add(escolaridadePessoa);
        escolaridadePessoa.setEscolaridade(this);
        return this;
    }

    public Escolaridade removeEscolaridadePessoa(EscolaridadePessoa escolaridadePessoa) {
        this.escolaridadePessoas.remove(escolaridadePessoa);
        escolaridadePessoa.setEscolaridade(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Escolaridade)) {
            return false;
        }
        return getId() != null && getId().equals(((Escolaridade) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Escolaridade{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
