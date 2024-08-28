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
 * A Cidade.
 */
@Entity
@Table(name = "cidade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "contratacao")
    private Boolean contratacao;

    @Column(name = "abertura")
    private Boolean abertura;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cidade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa", "cidade" }, allowSetters = true)
    private Set<EnderecoPessoa> enderecoPessoas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cidade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cidade", "pessoaJuridica" }, allowSetters = true)
    private Set<EnderecoEmpresa> enderecoEmpresas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "cidades", "pais" }, allowSetters = true)
    private Estado estado;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cidade id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Cidade nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getContratacao() {
        return this.contratacao;
    }

    public Cidade contratacao(Boolean contratacao) {
        this.setContratacao(contratacao);
        return this;
    }

    public void setContratacao(Boolean contratacao) {
        this.contratacao = contratacao;
    }

    public Boolean getAbertura() {
        return this.abertura;
    }

    public Cidade abertura(Boolean abertura) {
        this.setAbertura(abertura);
        return this;
    }

    public void setAbertura(Boolean abertura) {
        this.abertura = abertura;
    }

    public Set<EnderecoPessoa> getEnderecoPessoas() {
        return this.enderecoPessoas;
    }

    public void setEnderecoPessoas(Set<EnderecoPessoa> enderecoPessoas) {
        if (this.enderecoPessoas != null) {
            this.enderecoPessoas.forEach(i -> i.setCidade(null));
        }
        if (enderecoPessoas != null) {
            enderecoPessoas.forEach(i -> i.setCidade(this));
        }
        this.enderecoPessoas = enderecoPessoas;
    }

    public Cidade enderecoPessoas(Set<EnderecoPessoa> enderecoPessoas) {
        this.setEnderecoPessoas(enderecoPessoas);
        return this;
    }

    public Cidade addEnderecoPessoa(EnderecoPessoa enderecoPessoa) {
        this.enderecoPessoas.add(enderecoPessoa);
        enderecoPessoa.setCidade(this);
        return this;
    }

    public Cidade removeEnderecoPessoa(EnderecoPessoa enderecoPessoa) {
        this.enderecoPessoas.remove(enderecoPessoa);
        enderecoPessoa.setCidade(null);
        return this;
    }

    public Set<EnderecoEmpresa> getEnderecoEmpresas() {
        return this.enderecoEmpresas;
    }

    public void setEnderecoEmpresas(Set<EnderecoEmpresa> enderecoEmpresas) {
        if (this.enderecoEmpresas != null) {
            this.enderecoEmpresas.forEach(i -> i.setCidade(null));
        }
        if (enderecoEmpresas != null) {
            enderecoEmpresas.forEach(i -> i.setCidade(this));
        }
        this.enderecoEmpresas = enderecoEmpresas;
    }

    public Cidade enderecoEmpresas(Set<EnderecoEmpresa> enderecoEmpresas) {
        this.setEnderecoEmpresas(enderecoEmpresas);
        return this;
    }

    public Cidade addEnderecoEmpresa(EnderecoEmpresa enderecoEmpresa) {
        this.enderecoEmpresas.add(enderecoEmpresa);
        enderecoEmpresa.setCidade(this);
        return this;
    }

    public Cidade removeEnderecoEmpresa(EnderecoEmpresa enderecoEmpresa) {
        this.enderecoEmpresas.remove(enderecoEmpresa);
        enderecoEmpresa.setCidade(null);
        return this;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Cidade estado(Estado estado) {
        this.setEstado(estado);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cidade)) {
            return false;
        }
        return getId() != null && getId().equals(((Cidade) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cidade{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", contratacao='" + getContratacao() + "'" +
            ", abertura='" + getAbertura() + "'" +
            "}";
    }
}
