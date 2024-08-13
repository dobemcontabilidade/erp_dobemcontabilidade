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
 * A Banco.
 */
@Entity
@Table(name = "banco")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Banco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Size(max = 20)
    @Column(name = "codigo", length = 20, nullable = false)
    private String codigo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "banco")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa", "banco" }, allowSetters = true)
    private Set<BancoPessoa> bancoPessoas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Banco id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Banco nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public Banco codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Set<BancoPessoa> getBancoPessoas() {
        return this.bancoPessoas;
    }

    public void setBancoPessoas(Set<BancoPessoa> bancoPessoas) {
        if (this.bancoPessoas != null) {
            this.bancoPessoas.forEach(i -> i.setBanco(null));
        }
        if (bancoPessoas != null) {
            bancoPessoas.forEach(i -> i.setBanco(this));
        }
        this.bancoPessoas = bancoPessoas;
    }

    public Banco bancoPessoas(Set<BancoPessoa> bancoPessoas) {
        this.setBancoPessoas(bancoPessoas);
        return this;
    }

    public Banco addBancoPessoa(BancoPessoa bancoPessoa) {
        this.bancoPessoas.add(bancoPessoa);
        bancoPessoa.setBanco(this);
        return this;
    }

    public Banco removeBancoPessoa(BancoPessoa bancoPessoa) {
        this.bancoPessoas.remove(bancoPessoa);
        bancoPessoa.setBanco(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Banco)) {
            return false;
        }
        return getId() != null && getId().equals(((Banco) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Banco{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", codigo='" + getCodigo() + "'" +
            "}";
    }
}
