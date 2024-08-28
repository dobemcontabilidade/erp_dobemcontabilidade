package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ramo.
 */
@Entity
@Table(name = "ramo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ramo implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ramo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "pessoaJuridica", "socios", "assinaturaEmpresas", "empresa", "ramo", "enquadramento" },
        allowSetters = true
    )
    private Set<Empresa> empresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ramo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ramo", "planoAssinaturaContabil" }, allowSetters = true)
    private Set<AdicionalRamo> adicionalRamos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ramo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Ramo nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Ramo descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Empresa> getEmpresas() {
        return this.empresas;
    }

    public void setEmpresas(Set<Empresa> empresas) {
        if (this.empresas != null) {
            this.empresas.forEach(i -> i.setRamo(null));
        }
        if (empresas != null) {
            empresas.forEach(i -> i.setRamo(this));
        }
        this.empresas = empresas;
    }

    public Ramo empresas(Set<Empresa> empresas) {
        this.setEmpresas(empresas);
        return this;
    }

    public Ramo addEmpresa(Empresa empresa) {
        this.empresas.add(empresa);
        empresa.setRamo(this);
        return this;
    }

    public Ramo removeEmpresa(Empresa empresa) {
        this.empresas.remove(empresa);
        empresa.setRamo(null);
        return this;
    }

    public Set<AdicionalRamo> getAdicionalRamos() {
        return this.adicionalRamos;
    }

    public void setAdicionalRamos(Set<AdicionalRamo> adicionalRamos) {
        if (this.adicionalRamos != null) {
            this.adicionalRamos.forEach(i -> i.setRamo(null));
        }
        if (adicionalRamos != null) {
            adicionalRamos.forEach(i -> i.setRamo(this));
        }
        this.adicionalRamos = adicionalRamos;
    }

    public Ramo adicionalRamos(Set<AdicionalRamo> adicionalRamos) {
        this.setAdicionalRamos(adicionalRamos);
        return this;
    }

    public Ramo addAdicionalRamo(AdicionalRamo adicionalRamo) {
        this.adicionalRamos.add(adicionalRamo);
        adicionalRamo.setRamo(this);
        return this;
    }

    public Ramo removeAdicionalRamo(AdicionalRamo adicionalRamo) {
        this.adicionalRamos.remove(adicionalRamo);
        adicionalRamo.setRamo(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ramo)) {
            return false;
        }
        return getId() != null && getId().equals(((Ramo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ramo{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
