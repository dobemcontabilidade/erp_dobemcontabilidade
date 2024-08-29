package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.SituacaoContratoEmpresaEnum;
import com.dobemcontabilidade.domain.enumeration.TipoContratoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AssinaturaEmpresa.
 */
@Entity
@Table(name = "assinatura_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssinaturaEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @Column(name = "codigo_assinatura")
    private String codigoAssinatura;

    @Column(name = "valor_enquadramento")
    private Double valorEnquadramento;

    @Column(name = "valor_tributacao")
    private Double valorTributacao;

    @Column(name = "valor_ramo")
    private Double valorRamo;

    @Column(name = "valor_funcionarios")
    private Double valorFuncionarios;

    @Column(name = "valor_socios")
    private Double valorSocios;

    @Column(name = "valor_faturamento")
    private Double valorFaturamento;

    @Column(name = "valor_plano_contabil")
    private Double valorPlanoContabil;

    @Column(name = "valor_plano_contabil_com_desconto")
    private Double valorPlanoContabilComDesconto;

    @Column(name = "valor_plano_conta_azul_com_desconto")
    private Double valorPlanoContaAzulComDesconto;

    @Column(name = "valor_mensalidade")
    private Double valorMensalidade;

    @Column(name = "valor_periodo")
    private Double valorPeriodo;

    @Column(name = "valor_ano")
    private Double valorAno;

    @Column(name = "data_contratacao")
    private Instant dataContratacao;

    @Column(name = "data_encerramento")
    private Instant dataEncerramento;

    @Column(name = "dia_vencimento")
    private Integer diaVencimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    private SituacaoContratoEmpresaEnum situacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_contrato")
    private TipoContratoEnum tipoContrato;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "termoContratoContabil", "empresa" }, allowSetters = true)
    private Set<TermoContratoAssinaturaEmpresa> termoContratoAssinaturaEmpresas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "assinaturaEmpresas", "descontoPeriodoPagamentos" }, allowSetters = true)
    private PeriodoPagamento periodoPagamento;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "assinaturaEmpresas" }, allowSetters = true)
    private FormaDePagamento formaDePagamento;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "pessoaJuridica", "socios", "assinaturaEmpresas", "tributacao", "ramo", "enquadramento" },
        allowSetters = true
    )
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AssinaturaEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return this.razaoSocial;
    }

    public AssinaturaEmpresa razaoSocial(String razaoSocial) {
        this.setRazaoSocial(razaoSocial);
        return this;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCodigoAssinatura() {
        return this.codigoAssinatura;
    }

    public AssinaturaEmpresa codigoAssinatura(String codigoAssinatura) {
        this.setCodigoAssinatura(codigoAssinatura);
        return this;
    }

    public void setCodigoAssinatura(String codigoAssinatura) {
        this.codigoAssinatura = codigoAssinatura;
    }

    public Double getValorEnquadramento() {
        return this.valorEnquadramento;
    }

    public AssinaturaEmpresa valorEnquadramento(Double valorEnquadramento) {
        this.setValorEnquadramento(valorEnquadramento);
        return this;
    }

    public void setValorEnquadramento(Double valorEnquadramento) {
        this.valorEnquadramento = valorEnquadramento;
    }

    public Double getValorTributacao() {
        return this.valorTributacao;
    }

    public AssinaturaEmpresa valorTributacao(Double valorTributacao) {
        this.setValorTributacao(valorTributacao);
        return this;
    }

    public void setValorTributacao(Double valorTributacao) {
        this.valorTributacao = valorTributacao;
    }

    public Double getValorRamo() {
        return this.valorRamo;
    }

    public AssinaturaEmpresa valorRamo(Double valorRamo) {
        this.setValorRamo(valorRamo);
        return this;
    }

    public void setValorRamo(Double valorRamo) {
        this.valorRamo = valorRamo;
    }

    public Double getValorFuncionarios() {
        return this.valorFuncionarios;
    }

    public AssinaturaEmpresa valorFuncionarios(Double valorFuncionarios) {
        this.setValorFuncionarios(valorFuncionarios);
        return this;
    }

    public void setValorFuncionarios(Double valorFuncionarios) {
        this.valorFuncionarios = valorFuncionarios;
    }

    public Double getValorSocios() {
        return this.valorSocios;
    }

    public AssinaturaEmpresa valorSocios(Double valorSocios) {
        this.setValorSocios(valorSocios);
        return this;
    }

    public void setValorSocios(Double valorSocios) {
        this.valorSocios = valorSocios;
    }

    public Double getValorFaturamento() {
        return this.valorFaturamento;
    }

    public AssinaturaEmpresa valorFaturamento(Double valorFaturamento) {
        this.setValorFaturamento(valorFaturamento);
        return this;
    }

    public void setValorFaturamento(Double valorFaturamento) {
        this.valorFaturamento = valorFaturamento;
    }

    public Double getValorPlanoContabil() {
        return this.valorPlanoContabil;
    }

    public AssinaturaEmpresa valorPlanoContabil(Double valorPlanoContabil) {
        this.setValorPlanoContabil(valorPlanoContabil);
        return this;
    }

    public void setValorPlanoContabil(Double valorPlanoContabil) {
        this.valorPlanoContabil = valorPlanoContabil;
    }

    public Double getValorPlanoContabilComDesconto() {
        return this.valorPlanoContabilComDesconto;
    }

    public AssinaturaEmpresa valorPlanoContabilComDesconto(Double valorPlanoContabilComDesconto) {
        this.setValorPlanoContabilComDesconto(valorPlanoContabilComDesconto);
        return this;
    }

    public void setValorPlanoContabilComDesconto(Double valorPlanoContabilComDesconto) {
        this.valorPlanoContabilComDesconto = valorPlanoContabilComDesconto;
    }

    public Double getValorPlanoContaAzulComDesconto() {
        return this.valorPlanoContaAzulComDesconto;
    }

    public AssinaturaEmpresa valorPlanoContaAzulComDesconto(Double valorPlanoContaAzulComDesconto) {
        this.setValorPlanoContaAzulComDesconto(valorPlanoContaAzulComDesconto);
        return this;
    }

    public void setValorPlanoContaAzulComDesconto(Double valorPlanoContaAzulComDesconto) {
        this.valorPlanoContaAzulComDesconto = valorPlanoContaAzulComDesconto;
    }

    public Double getValorMensalidade() {
        return this.valorMensalidade;
    }

    public AssinaturaEmpresa valorMensalidade(Double valorMensalidade) {
        this.setValorMensalidade(valorMensalidade);
        return this;
    }

    public void setValorMensalidade(Double valorMensalidade) {
        this.valorMensalidade = valorMensalidade;
    }

    public Double getValorPeriodo() {
        return this.valorPeriodo;
    }

    public AssinaturaEmpresa valorPeriodo(Double valorPeriodo) {
        this.setValorPeriodo(valorPeriodo);
        return this;
    }

    public void setValorPeriodo(Double valorPeriodo) {
        this.valorPeriodo = valorPeriodo;
    }

    public Double getValorAno() {
        return this.valorAno;
    }

    public AssinaturaEmpresa valorAno(Double valorAno) {
        this.setValorAno(valorAno);
        return this;
    }

    public void setValorAno(Double valorAno) {
        this.valorAno = valorAno;
    }

    public Instant getDataContratacao() {
        return this.dataContratacao;
    }

    public AssinaturaEmpresa dataContratacao(Instant dataContratacao) {
        this.setDataContratacao(dataContratacao);
        return this;
    }

    public void setDataContratacao(Instant dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public Instant getDataEncerramento() {
        return this.dataEncerramento;
    }

    public AssinaturaEmpresa dataEncerramento(Instant dataEncerramento) {
        this.setDataEncerramento(dataEncerramento);
        return this;
    }

    public void setDataEncerramento(Instant dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public Integer getDiaVencimento() {
        return this.diaVencimento;
    }

    public AssinaturaEmpresa diaVencimento(Integer diaVencimento) {
        this.setDiaVencimento(diaVencimento);
        return this;
    }

    public void setDiaVencimento(Integer diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public SituacaoContratoEmpresaEnum getSituacao() {
        return this.situacao;
    }

    public AssinaturaEmpresa situacao(SituacaoContratoEmpresaEnum situacao) {
        this.setSituacao(situacao);
        return this;
    }

    public void setSituacao(SituacaoContratoEmpresaEnum situacao) {
        this.situacao = situacao;
    }

    public TipoContratoEnum getTipoContrato() {
        return this.tipoContrato;
    }

    public AssinaturaEmpresa tipoContrato(TipoContratoEnum tipoContrato) {
        this.setTipoContrato(tipoContrato);
        return this;
    }

    public void setTipoContrato(TipoContratoEnum tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public Set<TermoContratoAssinaturaEmpresa> getTermoContratoAssinaturaEmpresas() {
        return this.termoContratoAssinaturaEmpresas;
    }

    public void setTermoContratoAssinaturaEmpresas(Set<TermoContratoAssinaturaEmpresa> termoContratoAssinaturaEmpresas) {
        if (this.termoContratoAssinaturaEmpresas != null) {
            this.termoContratoAssinaturaEmpresas.forEach(i -> i.setEmpresa(null));
        }
        if (termoContratoAssinaturaEmpresas != null) {
            termoContratoAssinaturaEmpresas.forEach(i -> i.setEmpresa(this));
        }
        this.termoContratoAssinaturaEmpresas = termoContratoAssinaturaEmpresas;
    }

    public AssinaturaEmpresa termoContratoAssinaturaEmpresas(Set<TermoContratoAssinaturaEmpresa> termoContratoAssinaturaEmpresas) {
        this.setTermoContratoAssinaturaEmpresas(termoContratoAssinaturaEmpresas);
        return this;
    }

    public AssinaturaEmpresa addTermoContratoAssinaturaEmpresa(TermoContratoAssinaturaEmpresa termoContratoAssinaturaEmpresa) {
        this.termoContratoAssinaturaEmpresas.add(termoContratoAssinaturaEmpresa);
        termoContratoAssinaturaEmpresa.setEmpresa(this);
        return this;
    }

    public AssinaturaEmpresa removeTermoContratoAssinaturaEmpresa(TermoContratoAssinaturaEmpresa termoContratoAssinaturaEmpresa) {
        this.termoContratoAssinaturaEmpresas.remove(termoContratoAssinaturaEmpresa);
        termoContratoAssinaturaEmpresa.setEmpresa(null);
        return this;
    }

    public PeriodoPagamento getPeriodoPagamento() {
        return this.periodoPagamento;
    }

    public void setPeriodoPagamento(PeriodoPagamento periodoPagamento) {
        this.periodoPagamento = periodoPagamento;
    }

    public AssinaturaEmpresa periodoPagamento(PeriodoPagamento periodoPagamento) {
        this.setPeriodoPagamento(periodoPagamento);
        return this;
    }

    public FormaDePagamento getFormaDePagamento() {
        return this.formaDePagamento;
    }

    public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }

    public AssinaturaEmpresa formaDePagamento(FormaDePagamento formaDePagamento) {
        this.setFormaDePagamento(formaDePagamento);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public AssinaturaEmpresa empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssinaturaEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((AssinaturaEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssinaturaEmpresa{" +
            "id=" + getId() +
            ", razaoSocial='" + getRazaoSocial() + "'" +
            ", codigoAssinatura='" + getCodigoAssinatura() + "'" +
            ", valorEnquadramento=" + getValorEnquadramento() +
            ", valorTributacao=" + getValorTributacao() +
            ", valorRamo=" + getValorRamo() +
            ", valorFuncionarios=" + getValorFuncionarios() +
            ", valorSocios=" + getValorSocios() +
            ", valorFaturamento=" + getValorFaturamento() +
            ", valorPlanoContabil=" + getValorPlanoContabil() +
            ", valorPlanoContabilComDesconto=" + getValorPlanoContabilComDesconto() +
            ", valorPlanoContaAzulComDesconto=" + getValorPlanoContaAzulComDesconto() +
            ", valorMensalidade=" + getValorMensalidade() +
            ", valorPeriodo=" + getValorPeriodo() +
            ", valorAno=" + getValorAno() +
            ", dataContratacao='" + getDataContratacao() + "'" +
            ", dataEncerramento='" + getDataEncerramento() + "'" +
            ", diaVencimento=" + getDiaVencimento() +
            ", situacao='" + getSituacao() + "'" +
            ", tipoContrato='" + getTipoContrato() + "'" +
            "}";
    }
}
