package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.SituacaoTributacaoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Tributacao.
 */
@Entity
@Table(name = "tributacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tributacao implements Serializable {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    private SituacaoTributacaoEnum situacao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "pessoaJuridica", "socios", "assinaturaEmpresas", "empresa", "ramo", "enquadramento" },
        allowSetters = true
    )
    private Set<Empresa> empresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tributacao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tributacao", "planoAssinaturaContabil" }, allowSetters = true)
    private Set<AdicionalTributacao> adicionalTributacaos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tributacao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Tributacao nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Tributacao descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public SituacaoTributacaoEnum getSituacao() {
        return this.situacao;
    }

    public Tributacao situacao(SituacaoTributacaoEnum situacao) {
        this.setSituacao(situacao);
        return this;
    }

    public void setSituacao(SituacaoTributacaoEnum situacao) {
        this.situacao = situacao;
    }

    public Set<Empresa> getEmpresas() {
        return this.empresas;
    }

    public void setEmpresas(Set<Empresa> empresas) {
        if (this.empresas != null) {
            this.empresas.forEach(i -> i.setEmpresa(null));
        }
        if (empresas != null) {
            empresas.forEach(i -> i.setEmpresa(this));
        }
        this.empresas = empresas;
    }

    public Tributacao empresas(Set<Empresa> empresas) {
        this.setEmpresas(empresas);
        return this;
    }

    public Tributacao addEmpresa(Empresa empresa) {
        this.empresas.add(empresa);
        empresa.setEmpresa(this);
        return this;
    }

    public Tributacao removeEmpresa(Empresa empresa) {
        this.empresas.remove(empresa);
        empresa.setEmpresa(null);
        return this;
    }

    public Set<AdicionalTributacao> getAdicionalTributacaos() {
        return this.adicionalTributacaos;
    }

    public void setAdicionalTributacaos(Set<AdicionalTributacao> adicionalTributacaos) {
        if (this.adicionalTributacaos != null) {
            this.adicionalTributacaos.forEach(i -> i.setTributacao(null));
        }
        if (adicionalTributacaos != null) {
            adicionalTributacaos.forEach(i -> i.setTributacao(this));
        }
        this.adicionalTributacaos = adicionalTributacaos;
    }

    public Tributacao adicionalTributacaos(Set<AdicionalTributacao> adicionalTributacaos) {
        this.setAdicionalTributacaos(adicionalTributacaos);
        return this;
    }

    public Tributacao addAdicionalTributacao(AdicionalTributacao adicionalTributacao) {
        this.adicionalTributacaos.add(adicionalTributacao);
        adicionalTributacao.setTributacao(this);
        return this;
    }

    public Tributacao removeAdicionalTributacao(AdicionalTributacao adicionalTributacao) {
        this.adicionalTributacaos.remove(adicionalTributacao);
        adicionalTributacao.setTributacao(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tributacao)) {
            return false;
        }
        return getId() != null && getId().equals(((Tributacao) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tributacao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", situacao='" + getSituacao() + "'" +
            "}";
    }
}
