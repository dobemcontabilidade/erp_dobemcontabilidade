package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.SituacaoPlanoContabilEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PlanoAssinaturaContabil.
 */
@Entity
@Table(name = "plano_assinatura_contabil")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanoAssinaturaContabil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "adicional_socio")
    private Double adicionalSocio;

    @Column(name = "adicional_funcionario")
    private Double adicionalFuncionario;

    @Column(name = "socios_isentos")
    private Integer sociosIsentos;

    @Column(name = "adicional_faturamento")
    private Double adicionalFaturamento;

    @Column(name = "valor_base_faturamento")
    private Double valorBaseFaturamento;

    @Column(name = "valor_base_abertura")
    private Double valorBaseAbertura;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    private SituacaoPlanoContabilEnum situacao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planoAssinaturaContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "periodoPagamento", "planoAssinaturaContabil" }, allowSetters = true)
    private Set<DescontoPeriodoPagamento> descontoPeriodoPagamentos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planoAssinaturaContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ramo", "planoAssinaturaContabil" }, allowSetters = true)
    private Set<AdicionalRamo> adicionalRamos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planoAssinaturaContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tributacao", "planoAssinaturaContabil" }, allowSetters = true)
    private Set<AdicionalTributacao> adicionalTributacaos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planoAssinaturaContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "enquadramento", "planoAssinaturaContabil" }, allowSetters = true)
    private Set<AdicionalEnquadramento> adicionalEnquadramentos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlanoAssinaturaContabil id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public PlanoAssinaturaContabil nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getAdicionalSocio() {
        return this.adicionalSocio;
    }

    public PlanoAssinaturaContabil adicionalSocio(Double adicionalSocio) {
        this.setAdicionalSocio(adicionalSocio);
        return this;
    }

    public void setAdicionalSocio(Double adicionalSocio) {
        this.adicionalSocio = adicionalSocio;
    }

    public Double getAdicionalFuncionario() {
        return this.adicionalFuncionario;
    }

    public PlanoAssinaturaContabil adicionalFuncionario(Double adicionalFuncionario) {
        this.setAdicionalFuncionario(adicionalFuncionario);
        return this;
    }

    public void setAdicionalFuncionario(Double adicionalFuncionario) {
        this.adicionalFuncionario = adicionalFuncionario;
    }

    public Integer getSociosIsentos() {
        return this.sociosIsentos;
    }

    public PlanoAssinaturaContabil sociosIsentos(Integer sociosIsentos) {
        this.setSociosIsentos(sociosIsentos);
        return this;
    }

    public void setSociosIsentos(Integer sociosIsentos) {
        this.sociosIsentos = sociosIsentos;
    }

    public Double getAdicionalFaturamento() {
        return this.adicionalFaturamento;
    }

    public PlanoAssinaturaContabil adicionalFaturamento(Double adicionalFaturamento) {
        this.setAdicionalFaturamento(adicionalFaturamento);
        return this;
    }

    public void setAdicionalFaturamento(Double adicionalFaturamento) {
        this.adicionalFaturamento = adicionalFaturamento;
    }

    public Double getValorBaseFaturamento() {
        return this.valorBaseFaturamento;
    }

    public PlanoAssinaturaContabil valorBaseFaturamento(Double valorBaseFaturamento) {
        this.setValorBaseFaturamento(valorBaseFaturamento);
        return this;
    }

    public void setValorBaseFaturamento(Double valorBaseFaturamento) {
        this.valorBaseFaturamento = valorBaseFaturamento;
    }

    public Double getValorBaseAbertura() {
        return this.valorBaseAbertura;
    }

    public PlanoAssinaturaContabil valorBaseAbertura(Double valorBaseAbertura) {
        this.setValorBaseAbertura(valorBaseAbertura);
        return this;
    }

    public void setValorBaseAbertura(Double valorBaseAbertura) {
        this.valorBaseAbertura = valorBaseAbertura;
    }

    public SituacaoPlanoContabilEnum getSituacao() {
        return this.situacao;
    }

    public PlanoAssinaturaContabil situacao(SituacaoPlanoContabilEnum situacao) {
        this.setSituacao(situacao);
        return this;
    }

    public void setSituacao(SituacaoPlanoContabilEnum situacao) {
        this.situacao = situacao;
    }

    public Set<DescontoPeriodoPagamento> getDescontoPeriodoPagamentos() {
        return this.descontoPeriodoPagamentos;
    }

    public void setDescontoPeriodoPagamentos(Set<DescontoPeriodoPagamento> descontoPeriodoPagamentos) {
        if (this.descontoPeriodoPagamentos != null) {
            this.descontoPeriodoPagamentos.forEach(i -> i.setPlanoAssinaturaContabil(null));
        }
        if (descontoPeriodoPagamentos != null) {
            descontoPeriodoPagamentos.forEach(i -> i.setPlanoAssinaturaContabil(this));
        }
        this.descontoPeriodoPagamentos = descontoPeriodoPagamentos;
    }

    public PlanoAssinaturaContabil descontoPeriodoPagamentos(Set<DescontoPeriodoPagamento> descontoPeriodoPagamentos) {
        this.setDescontoPeriodoPagamentos(descontoPeriodoPagamentos);
        return this;
    }

    public PlanoAssinaturaContabil addDescontoPeriodoPagamento(DescontoPeriodoPagamento descontoPeriodoPagamento) {
        this.descontoPeriodoPagamentos.add(descontoPeriodoPagamento);
        descontoPeriodoPagamento.setPlanoAssinaturaContabil(this);
        return this;
    }

    public PlanoAssinaturaContabil removeDescontoPeriodoPagamento(DescontoPeriodoPagamento descontoPeriodoPagamento) {
        this.descontoPeriodoPagamentos.remove(descontoPeriodoPagamento);
        descontoPeriodoPagamento.setPlanoAssinaturaContabil(null);
        return this;
    }

    public Set<AdicionalRamo> getAdicionalRamos() {
        return this.adicionalRamos;
    }

    public void setAdicionalRamos(Set<AdicionalRamo> adicionalRamos) {
        if (this.adicionalRamos != null) {
            this.adicionalRamos.forEach(i -> i.setPlanoAssinaturaContabil(null));
        }
        if (adicionalRamos != null) {
            adicionalRamos.forEach(i -> i.setPlanoAssinaturaContabil(this));
        }
        this.adicionalRamos = adicionalRamos;
    }

    public PlanoAssinaturaContabil adicionalRamos(Set<AdicionalRamo> adicionalRamos) {
        this.setAdicionalRamos(adicionalRamos);
        return this;
    }

    public PlanoAssinaturaContabil addAdicionalRamo(AdicionalRamo adicionalRamo) {
        this.adicionalRamos.add(adicionalRamo);
        adicionalRamo.setPlanoAssinaturaContabil(this);
        return this;
    }

    public PlanoAssinaturaContabil removeAdicionalRamo(AdicionalRamo adicionalRamo) {
        this.adicionalRamos.remove(adicionalRamo);
        adicionalRamo.setPlanoAssinaturaContabil(null);
        return this;
    }

    public Set<AdicionalTributacao> getAdicionalTributacaos() {
        return this.adicionalTributacaos;
    }

    public void setAdicionalTributacaos(Set<AdicionalTributacao> adicionalTributacaos) {
        if (this.adicionalTributacaos != null) {
            this.adicionalTributacaos.forEach(i -> i.setPlanoAssinaturaContabil(null));
        }
        if (adicionalTributacaos != null) {
            adicionalTributacaos.forEach(i -> i.setPlanoAssinaturaContabil(this));
        }
        this.adicionalTributacaos = adicionalTributacaos;
    }

    public PlanoAssinaturaContabil adicionalTributacaos(Set<AdicionalTributacao> adicionalTributacaos) {
        this.setAdicionalTributacaos(adicionalTributacaos);
        return this;
    }

    public PlanoAssinaturaContabil addAdicionalTributacao(AdicionalTributacao adicionalTributacao) {
        this.adicionalTributacaos.add(adicionalTributacao);
        adicionalTributacao.setPlanoAssinaturaContabil(this);
        return this;
    }

    public PlanoAssinaturaContabil removeAdicionalTributacao(AdicionalTributacao adicionalTributacao) {
        this.adicionalTributacaos.remove(adicionalTributacao);
        adicionalTributacao.setPlanoAssinaturaContabil(null);
        return this;
    }

    public Set<AdicionalEnquadramento> getAdicionalEnquadramentos() {
        return this.adicionalEnquadramentos;
    }

    public void setAdicionalEnquadramentos(Set<AdicionalEnquadramento> adicionalEnquadramentos) {
        if (this.adicionalEnquadramentos != null) {
            this.adicionalEnquadramentos.forEach(i -> i.setPlanoAssinaturaContabil(null));
        }
        if (adicionalEnquadramentos != null) {
            adicionalEnquadramentos.forEach(i -> i.setPlanoAssinaturaContabil(this));
        }
        this.adicionalEnquadramentos = adicionalEnquadramentos;
    }

    public PlanoAssinaturaContabil adicionalEnquadramentos(Set<AdicionalEnquadramento> adicionalEnquadramentos) {
        this.setAdicionalEnquadramentos(adicionalEnquadramentos);
        return this;
    }

    public PlanoAssinaturaContabil addAdicionalEnquadramento(AdicionalEnquadramento adicionalEnquadramento) {
        this.adicionalEnquadramentos.add(adicionalEnquadramento);
        adicionalEnquadramento.setPlanoAssinaturaContabil(this);
        return this;
    }

    public PlanoAssinaturaContabil removeAdicionalEnquadramento(AdicionalEnquadramento adicionalEnquadramento) {
        this.adicionalEnquadramentos.remove(adicionalEnquadramento);
        adicionalEnquadramento.setPlanoAssinaturaContabil(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoAssinaturaContabil)) {
            return false;
        }
        return getId() != null && getId().equals(((PlanoAssinaturaContabil) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoAssinaturaContabil{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", adicionalSocio=" + getAdicionalSocio() +
            ", adicionalFuncionario=" + getAdicionalFuncionario() +
            ", sociosIsentos=" + getSociosIsentos() +
            ", adicionalFaturamento=" + getAdicionalFaturamento() +
            ", valorBaseFaturamento=" + getValorBaseFaturamento() +
            ", valorBaseAbertura=" + getValorBaseAbertura() +
            ", situacao='" + getSituacao() + "'" +
            "}";
    }
}
