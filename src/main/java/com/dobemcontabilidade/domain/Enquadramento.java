package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Enquadramento.
 */
@Entity
@Table(name = "enquadramento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Enquadramento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "sigla")
    private String sigla;

    @Column(name = "limite_inicial")
    private Double limiteInicial;

    @Column(name = "limite_final")
    private Double limiteFinal;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "enquadramento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "pessoaJuridica", "socios", "assinaturaEmpresas", "tributacao", "ramo", "enquadramento" },
        allowSetters = true
    )
    private Set<Empresa> empresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "enquadramento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "enquadramento", "planoAssinaturaContabil" }, allowSetters = true)
    private Set<AdicionalEnquadramento> adicionalEnquadramentos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Enquadramento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Enquadramento nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return this.sigla;
    }

    public Enquadramento sigla(String sigla) {
        this.setSigla(sigla);
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Double getLimiteInicial() {
        return this.limiteInicial;
    }

    public Enquadramento limiteInicial(Double limiteInicial) {
        this.setLimiteInicial(limiteInicial);
        return this;
    }

    public void setLimiteInicial(Double limiteInicial) {
        this.limiteInicial = limiteInicial;
    }

    public Double getLimiteFinal() {
        return this.limiteFinal;
    }

    public Enquadramento limiteFinal(Double limiteFinal) {
        this.setLimiteFinal(limiteFinal);
        return this;
    }

    public void setLimiteFinal(Double limiteFinal) {
        this.limiteFinal = limiteFinal;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Enquadramento descricao(String descricao) {
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
            this.empresas.forEach(i -> i.setEnquadramento(null));
        }
        if (empresas != null) {
            empresas.forEach(i -> i.setEnquadramento(this));
        }
        this.empresas = empresas;
    }

    public Enquadramento empresas(Set<Empresa> empresas) {
        this.setEmpresas(empresas);
        return this;
    }

    public Enquadramento addEmpresa(Empresa empresa) {
        this.empresas.add(empresa);
        empresa.setEnquadramento(this);
        return this;
    }

    public Enquadramento removeEmpresa(Empresa empresa) {
        this.empresas.remove(empresa);
        empresa.setEnquadramento(null);
        return this;
    }

    public Set<AdicionalEnquadramento> getAdicionalEnquadramentos() {
        return this.adicionalEnquadramentos;
    }

    public void setAdicionalEnquadramentos(Set<AdicionalEnquadramento> adicionalEnquadramentos) {
        if (this.adicionalEnquadramentos != null) {
            this.adicionalEnquadramentos.forEach(i -> i.setEnquadramento(null));
        }
        if (adicionalEnquadramentos != null) {
            adicionalEnquadramentos.forEach(i -> i.setEnquadramento(this));
        }
        this.adicionalEnquadramentos = adicionalEnquadramentos;
    }

    public Enquadramento adicionalEnquadramentos(Set<AdicionalEnquadramento> adicionalEnquadramentos) {
        this.setAdicionalEnquadramentos(adicionalEnquadramentos);
        return this;
    }

    public Enquadramento addAdicionalEnquadramento(AdicionalEnquadramento adicionalEnquadramento) {
        this.adicionalEnquadramentos.add(adicionalEnquadramento);
        adicionalEnquadramento.setEnquadramento(this);
        return this;
    }

    public Enquadramento removeAdicionalEnquadramento(AdicionalEnquadramento adicionalEnquadramento) {
        this.adicionalEnquadramentos.remove(adicionalEnquadramento);
        adicionalEnquadramento.setEnquadramento(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Enquadramento)) {
            return false;
        }
        return getId() != null && getId().equals(((Enquadramento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Enquadramento{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", limiteInicial=" + getLimiteInicial() +
            ", limiteFinal=" + getLimiteFinal() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
