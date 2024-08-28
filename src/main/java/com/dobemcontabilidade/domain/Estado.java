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
 * A Estado.
 */
@Entity
@Table(name = "estado")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Estado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "naturalidade")
    private String naturalidade;

    @Column(name = "sigla")
    private String sigla;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "enderecoPessoas", "enderecoEmpresas", "estado" }, allowSetters = true)
    private Set<Cidade> cidades = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "estados" }, allowSetters = true)
    private Pais pais;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Estado id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Estado nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNaturalidade() {
        return this.naturalidade;
    }

    public Estado naturalidade(String naturalidade) {
        this.setNaturalidade(naturalidade);
        return this;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getSigla() {
        return this.sigla;
    }

    public Estado sigla(String sigla) {
        this.setSigla(sigla);
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Set<Cidade> getCidades() {
        return this.cidades;
    }

    public void setCidades(Set<Cidade> cidades) {
        if (this.cidades != null) {
            this.cidades.forEach(i -> i.setEstado(null));
        }
        if (cidades != null) {
            cidades.forEach(i -> i.setEstado(this));
        }
        this.cidades = cidades;
    }

    public Estado cidades(Set<Cidade> cidades) {
        this.setCidades(cidades);
        return this;
    }

    public Estado addCidade(Cidade cidade) {
        this.cidades.add(cidade);
        cidade.setEstado(this);
        return this;
    }

    public Estado removeCidade(Cidade cidade) {
        this.cidades.remove(cidade);
        cidade.setEstado(null);
        return this;
    }

    public Pais getPais() {
        return this.pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Estado pais(Pais pais) {
        this.setPais(pais);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Estado)) {
            return false;
        }
        return getId() != null && getId().equals(((Estado) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Estado{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", naturalidade='" + getNaturalidade() + "'" +
            ", sigla='" + getSigla() + "'" +
            "}";
    }
}
