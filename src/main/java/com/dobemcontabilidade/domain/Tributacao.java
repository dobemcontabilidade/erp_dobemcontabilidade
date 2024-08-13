package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.SituacaoTributacaoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @Size(max = 200)
    @Column(name = "nome", length = 200)
    private String nome;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    private SituacaoTributacaoEnum situacao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tributacao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "anexoEmpresas", "anexoRequerido", "enquadramento", "tributacao", "ramo", "empresa", "empresaModelo" },
        allowSetters = true
    )
    private Set<AnexoRequeridoEmpresa> anexoRequeridoEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tributacao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "servicoContabilEmpresaModelos",
            "impostoEmpresaModelos",
            "anexoRequeridoEmpresas",
            "tarefaEmpresaModelos",
            "empresas",
            "segmentoCnaes",
            "ramo",
            "enquadramento",
            "tributacao",
            "cidade",
        },
        allowSetters = true
    )
    private Set<EmpresaModelo> empresaModelos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tributacao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "periodoPagamento",
            "planoContaAzul",
            "planoContabil",
            "ramo",
            "tributacao",
            "descontoPlanoContabil",
            "descontoPlanoContaAzul",
            "assinaturaEmpresa",
        },
        allowSetters = true
    )
    private Set<CalculoPlanoAssinatura> calculoPlanoAssinaturas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tributacao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tributacao", "planoContabil" }, allowSetters = true)
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

    public Set<AnexoRequeridoEmpresa> getAnexoRequeridoEmpresas() {
        return this.anexoRequeridoEmpresas;
    }

    public void setAnexoRequeridoEmpresas(Set<AnexoRequeridoEmpresa> anexoRequeridoEmpresas) {
        if (this.anexoRequeridoEmpresas != null) {
            this.anexoRequeridoEmpresas.forEach(i -> i.setTributacao(null));
        }
        if (anexoRequeridoEmpresas != null) {
            anexoRequeridoEmpresas.forEach(i -> i.setTributacao(this));
        }
        this.anexoRequeridoEmpresas = anexoRequeridoEmpresas;
    }

    public Tributacao anexoRequeridoEmpresas(Set<AnexoRequeridoEmpresa> anexoRequeridoEmpresas) {
        this.setAnexoRequeridoEmpresas(anexoRequeridoEmpresas);
        return this;
    }

    public Tributacao addAnexoRequeridoEmpresa(AnexoRequeridoEmpresa anexoRequeridoEmpresa) {
        this.anexoRequeridoEmpresas.add(anexoRequeridoEmpresa);
        anexoRequeridoEmpresa.setTributacao(this);
        return this;
    }

    public Tributacao removeAnexoRequeridoEmpresa(AnexoRequeridoEmpresa anexoRequeridoEmpresa) {
        this.anexoRequeridoEmpresas.remove(anexoRequeridoEmpresa);
        anexoRequeridoEmpresa.setTributacao(null);
        return this;
    }

    public Set<EmpresaModelo> getEmpresaModelos() {
        return this.empresaModelos;
    }

    public void setEmpresaModelos(Set<EmpresaModelo> empresaModelos) {
        if (this.empresaModelos != null) {
            this.empresaModelos.forEach(i -> i.setTributacao(null));
        }
        if (empresaModelos != null) {
            empresaModelos.forEach(i -> i.setTributacao(this));
        }
        this.empresaModelos = empresaModelos;
    }

    public Tributacao empresaModelos(Set<EmpresaModelo> empresaModelos) {
        this.setEmpresaModelos(empresaModelos);
        return this;
    }

    public Tributacao addEmpresaModelo(EmpresaModelo empresaModelo) {
        this.empresaModelos.add(empresaModelo);
        empresaModelo.setTributacao(this);
        return this;
    }

    public Tributacao removeEmpresaModelo(EmpresaModelo empresaModelo) {
        this.empresaModelos.remove(empresaModelo);
        empresaModelo.setTributacao(null);
        return this;
    }

    public Set<CalculoPlanoAssinatura> getCalculoPlanoAssinaturas() {
        return this.calculoPlanoAssinaturas;
    }

    public void setCalculoPlanoAssinaturas(Set<CalculoPlanoAssinatura> calculoPlanoAssinaturas) {
        if (this.calculoPlanoAssinaturas != null) {
            this.calculoPlanoAssinaturas.forEach(i -> i.setTributacao(null));
        }
        if (calculoPlanoAssinaturas != null) {
            calculoPlanoAssinaturas.forEach(i -> i.setTributacao(this));
        }
        this.calculoPlanoAssinaturas = calculoPlanoAssinaturas;
    }

    public Tributacao calculoPlanoAssinaturas(Set<CalculoPlanoAssinatura> calculoPlanoAssinaturas) {
        this.setCalculoPlanoAssinaturas(calculoPlanoAssinaturas);
        return this;
    }

    public Tributacao addCalculoPlanoAssinatura(CalculoPlanoAssinatura calculoPlanoAssinatura) {
        this.calculoPlanoAssinaturas.add(calculoPlanoAssinatura);
        calculoPlanoAssinatura.setTributacao(this);
        return this;
    }

    public Tributacao removeCalculoPlanoAssinatura(CalculoPlanoAssinatura calculoPlanoAssinatura) {
        this.calculoPlanoAssinaturas.remove(calculoPlanoAssinatura);
        calculoPlanoAssinatura.setTributacao(null);
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
