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
 * A PlanoContabil.
 */
@Entity
@Table(name = "plano_contabil")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanoContabil implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planoContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "periodoPagamento", "planoContabil", "ramo", "tributacao", "descontoPlanoContabil", "assinaturaEmpresa" },
        allowSetters = true
    )
    private Set<CalculoPlanoAssinatura> calculoPlanoAssinaturas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planoContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "calculoPlanoAssinaturas", "pagamentos", "periodoPagamento", "formaDePagamento", "planoContabil", "empresa" },
        allowSetters = true
    )
    private Set<AssinaturaEmpresa> assinaturaEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planoContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "calculoPlanoAssinaturas", "periodoPagamento", "planoContabil" }, allowSetters = true)
    private Set<DescontoPlanoContabil> descontoPlanoContabils = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planoContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ramo", "planoContabil" }, allowSetters = true)
    private Set<AdicionalRamo> adicionalRamos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planoContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tributacao", "planoContabil" }, allowSetters = true)
    private Set<AdicionalTributacao> adicionalTributacaos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planoContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "planoContabil" }, allowSetters = true)
    private Set<TermoContratoContabil> termoContratoContabils = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planoContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "enquadramento", "planoContabil" }, allowSetters = true)
    private Set<AdicionalEnquadramento> adicionalEnquadramentos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planoContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ramo", "planoContabil" }, allowSetters = true)
    private Set<ValorBaseRamo> valorBaseRamos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlanoContabil id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public PlanoContabil nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getAdicionalSocio() {
        return this.adicionalSocio;
    }

    public PlanoContabil adicionalSocio(Double adicionalSocio) {
        this.setAdicionalSocio(adicionalSocio);
        return this;
    }

    public void setAdicionalSocio(Double adicionalSocio) {
        this.adicionalSocio = adicionalSocio;
    }

    public Double getAdicionalFuncionario() {
        return this.adicionalFuncionario;
    }

    public PlanoContabil adicionalFuncionario(Double adicionalFuncionario) {
        this.setAdicionalFuncionario(adicionalFuncionario);
        return this;
    }

    public void setAdicionalFuncionario(Double adicionalFuncionario) {
        this.adicionalFuncionario = adicionalFuncionario;
    }

    public Integer getSociosIsentos() {
        return this.sociosIsentos;
    }

    public PlanoContabil sociosIsentos(Integer sociosIsentos) {
        this.setSociosIsentos(sociosIsentos);
        return this;
    }

    public void setSociosIsentos(Integer sociosIsentos) {
        this.sociosIsentos = sociosIsentos;
    }

    public Double getAdicionalFaturamento() {
        return this.adicionalFaturamento;
    }

    public PlanoContabil adicionalFaturamento(Double adicionalFaturamento) {
        this.setAdicionalFaturamento(adicionalFaturamento);
        return this;
    }

    public void setAdicionalFaturamento(Double adicionalFaturamento) {
        this.adicionalFaturamento = adicionalFaturamento;
    }

    public Double getValorBaseFaturamento() {
        return this.valorBaseFaturamento;
    }

    public PlanoContabil valorBaseFaturamento(Double valorBaseFaturamento) {
        this.setValorBaseFaturamento(valorBaseFaturamento);
        return this;
    }

    public void setValorBaseFaturamento(Double valorBaseFaturamento) {
        this.valorBaseFaturamento = valorBaseFaturamento;
    }

    public Double getValorBaseAbertura() {
        return this.valorBaseAbertura;
    }

    public PlanoContabil valorBaseAbertura(Double valorBaseAbertura) {
        this.setValorBaseAbertura(valorBaseAbertura);
        return this;
    }

    public void setValorBaseAbertura(Double valorBaseAbertura) {
        this.valorBaseAbertura = valorBaseAbertura;
    }

    public SituacaoPlanoContabilEnum getSituacao() {
        return this.situacao;
    }

    public PlanoContabil situacao(SituacaoPlanoContabilEnum situacao) {
        this.setSituacao(situacao);
        return this;
    }

    public void setSituacao(SituacaoPlanoContabilEnum situacao) {
        this.situacao = situacao;
    }

    public Set<CalculoPlanoAssinatura> getCalculoPlanoAssinaturas() {
        return this.calculoPlanoAssinaturas;
    }

    public void setCalculoPlanoAssinaturas(Set<CalculoPlanoAssinatura> calculoPlanoAssinaturas) {
        if (this.calculoPlanoAssinaturas != null) {
            this.calculoPlanoAssinaturas.forEach(i -> i.setPlanoContabil(null));
        }
        if (calculoPlanoAssinaturas != null) {
            calculoPlanoAssinaturas.forEach(i -> i.setPlanoContabil(this));
        }
        this.calculoPlanoAssinaturas = calculoPlanoAssinaturas;
    }

    public PlanoContabil calculoPlanoAssinaturas(Set<CalculoPlanoAssinatura> calculoPlanoAssinaturas) {
        this.setCalculoPlanoAssinaturas(calculoPlanoAssinaturas);
        return this;
    }

    public PlanoContabil addCalculoPlanoAssinatura(CalculoPlanoAssinatura calculoPlanoAssinatura) {
        this.calculoPlanoAssinaturas.add(calculoPlanoAssinatura);
        calculoPlanoAssinatura.setPlanoContabil(this);
        return this;
    }

    public PlanoContabil removeCalculoPlanoAssinatura(CalculoPlanoAssinatura calculoPlanoAssinatura) {
        this.calculoPlanoAssinaturas.remove(calculoPlanoAssinatura);
        calculoPlanoAssinatura.setPlanoContabil(null);
        return this;
    }

    public Set<AssinaturaEmpresa> getAssinaturaEmpresas() {
        return this.assinaturaEmpresas;
    }

    public void setAssinaturaEmpresas(Set<AssinaturaEmpresa> assinaturaEmpresas) {
        if (this.assinaturaEmpresas != null) {
            this.assinaturaEmpresas.forEach(i -> i.setPlanoContabil(null));
        }
        if (assinaturaEmpresas != null) {
            assinaturaEmpresas.forEach(i -> i.setPlanoContabil(this));
        }
        this.assinaturaEmpresas = assinaturaEmpresas;
    }

    public PlanoContabil assinaturaEmpresas(Set<AssinaturaEmpresa> assinaturaEmpresas) {
        this.setAssinaturaEmpresas(assinaturaEmpresas);
        return this;
    }

    public PlanoContabil addAssinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.assinaturaEmpresas.add(assinaturaEmpresa);
        assinaturaEmpresa.setPlanoContabil(this);
        return this;
    }

    public PlanoContabil removeAssinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.assinaturaEmpresas.remove(assinaturaEmpresa);
        assinaturaEmpresa.setPlanoContabil(null);
        return this;
    }

    public Set<DescontoPlanoContabil> getDescontoPlanoContabils() {
        return this.descontoPlanoContabils;
    }

    public void setDescontoPlanoContabils(Set<DescontoPlanoContabil> descontoPlanoContabils) {
        if (this.descontoPlanoContabils != null) {
            this.descontoPlanoContabils.forEach(i -> i.setPlanoContabil(null));
        }
        if (descontoPlanoContabils != null) {
            descontoPlanoContabils.forEach(i -> i.setPlanoContabil(this));
        }
        this.descontoPlanoContabils = descontoPlanoContabils;
    }

    public PlanoContabil descontoPlanoContabils(Set<DescontoPlanoContabil> descontoPlanoContabils) {
        this.setDescontoPlanoContabils(descontoPlanoContabils);
        return this;
    }

    public PlanoContabil addDescontoPlanoContabil(DescontoPlanoContabil descontoPlanoContabil) {
        this.descontoPlanoContabils.add(descontoPlanoContabil);
        descontoPlanoContabil.setPlanoContabil(this);
        return this;
    }

    public PlanoContabil removeDescontoPlanoContabil(DescontoPlanoContabil descontoPlanoContabil) {
        this.descontoPlanoContabils.remove(descontoPlanoContabil);
        descontoPlanoContabil.setPlanoContabil(null);
        return this;
    }

    public Set<AdicionalRamo> getAdicionalRamos() {
        return this.adicionalRamos;
    }

    public void setAdicionalRamos(Set<AdicionalRamo> adicionalRamos) {
        if (this.adicionalRamos != null) {
            this.adicionalRamos.forEach(i -> i.setPlanoContabil(null));
        }
        if (adicionalRamos != null) {
            adicionalRamos.forEach(i -> i.setPlanoContabil(this));
        }
        this.adicionalRamos = adicionalRamos;
    }

    public PlanoContabil adicionalRamos(Set<AdicionalRamo> adicionalRamos) {
        this.setAdicionalRamos(adicionalRamos);
        return this;
    }

    public PlanoContabil addAdicionalRamo(AdicionalRamo adicionalRamo) {
        this.adicionalRamos.add(adicionalRamo);
        adicionalRamo.setPlanoContabil(this);
        return this;
    }

    public PlanoContabil removeAdicionalRamo(AdicionalRamo adicionalRamo) {
        this.adicionalRamos.remove(adicionalRamo);
        adicionalRamo.setPlanoContabil(null);
        return this;
    }

    public Set<AdicionalTributacao> getAdicionalTributacaos() {
        return this.adicionalTributacaos;
    }

    public void setAdicionalTributacaos(Set<AdicionalTributacao> adicionalTributacaos) {
        if (this.adicionalTributacaos != null) {
            this.adicionalTributacaos.forEach(i -> i.setPlanoContabil(null));
        }
        if (adicionalTributacaos != null) {
            adicionalTributacaos.forEach(i -> i.setPlanoContabil(this));
        }
        this.adicionalTributacaos = adicionalTributacaos;
    }

    public PlanoContabil adicionalTributacaos(Set<AdicionalTributacao> adicionalTributacaos) {
        this.setAdicionalTributacaos(adicionalTributacaos);
        return this;
    }

    public PlanoContabil addAdicionalTributacao(AdicionalTributacao adicionalTributacao) {
        this.adicionalTributacaos.add(adicionalTributacao);
        adicionalTributacao.setPlanoContabil(this);
        return this;
    }

    public PlanoContabil removeAdicionalTributacao(AdicionalTributacao adicionalTributacao) {
        this.adicionalTributacaos.remove(adicionalTributacao);
        adicionalTributacao.setPlanoContabil(null);
        return this;
    }

    public Set<TermoContratoContabil> getTermoContratoContabils() {
        return this.termoContratoContabils;
    }

    public void setTermoContratoContabils(Set<TermoContratoContabil> termoContratoContabils) {
        if (this.termoContratoContabils != null) {
            this.termoContratoContabils.forEach(i -> i.setPlanoContabil(null));
        }
        if (termoContratoContabils != null) {
            termoContratoContabils.forEach(i -> i.setPlanoContabil(this));
        }
        this.termoContratoContabils = termoContratoContabils;
    }

    public PlanoContabil termoContratoContabils(Set<TermoContratoContabil> termoContratoContabils) {
        this.setTermoContratoContabils(termoContratoContabils);
        return this;
    }

    public PlanoContabil addTermoContratoContabil(TermoContratoContabil termoContratoContabil) {
        this.termoContratoContabils.add(termoContratoContabil);
        termoContratoContabil.setPlanoContabil(this);
        return this;
    }

    public PlanoContabil removeTermoContratoContabil(TermoContratoContabil termoContratoContabil) {
        this.termoContratoContabils.remove(termoContratoContabil);
        termoContratoContabil.setPlanoContabil(null);
        return this;
    }

    public Set<AdicionalEnquadramento> getAdicionalEnquadramentos() {
        return this.adicionalEnquadramentos;
    }

    public void setAdicionalEnquadramentos(Set<AdicionalEnquadramento> adicionalEnquadramentos) {
        if (this.adicionalEnquadramentos != null) {
            this.adicionalEnquadramentos.forEach(i -> i.setPlanoContabil(null));
        }
        if (adicionalEnquadramentos != null) {
            adicionalEnquadramentos.forEach(i -> i.setPlanoContabil(this));
        }
        this.adicionalEnquadramentos = adicionalEnquadramentos;
    }

    public PlanoContabil adicionalEnquadramentos(Set<AdicionalEnquadramento> adicionalEnquadramentos) {
        this.setAdicionalEnquadramentos(adicionalEnquadramentos);
        return this;
    }

    public PlanoContabil addAdicionalEnquadramento(AdicionalEnquadramento adicionalEnquadramento) {
        this.adicionalEnquadramentos.add(adicionalEnquadramento);
        adicionalEnquadramento.setPlanoContabil(this);
        return this;
    }

    public PlanoContabil removeAdicionalEnquadramento(AdicionalEnquadramento adicionalEnquadramento) {
        this.adicionalEnquadramentos.remove(adicionalEnquadramento);
        adicionalEnquadramento.setPlanoContabil(null);
        return this;
    }

    public Set<ValorBaseRamo> getValorBaseRamos() {
        return this.valorBaseRamos;
    }

    public void setValorBaseRamos(Set<ValorBaseRamo> valorBaseRamos) {
        if (this.valorBaseRamos != null) {
            this.valorBaseRamos.forEach(i -> i.setPlanoContabil(null));
        }
        if (valorBaseRamos != null) {
            valorBaseRamos.forEach(i -> i.setPlanoContabil(this));
        }
        this.valorBaseRamos = valorBaseRamos;
    }

    public PlanoContabil valorBaseRamos(Set<ValorBaseRamo> valorBaseRamos) {
        this.setValorBaseRamos(valorBaseRamos);
        return this;
    }

    public PlanoContabil addValorBaseRamo(ValorBaseRamo valorBaseRamo) {
        this.valorBaseRamos.add(valorBaseRamo);
        valorBaseRamo.setPlanoContabil(this);
        return this;
    }

    public PlanoContabil removeValorBaseRamo(ValorBaseRamo valorBaseRamo) {
        this.valorBaseRamos.remove(valorBaseRamo);
        valorBaseRamo.setPlanoContabil(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoContabil)) {
            return false;
        }
        return getId() != null && getId().equals(((PlanoContabil) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoContabil{" +
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
