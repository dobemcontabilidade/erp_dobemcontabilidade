package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CalculoPlanoAssinatura.
 */
@Entity
@Table(name = "calculo_plano_assinatura")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CalculoPlanoAssinatura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo_atendimento")
    private String codigoAtendimento;

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "calculoPlanoAssinaturas", "assinaturaEmpresas", "descontoPlanoContaAzuls", "descontoPlanoContabils" },
        allowSetters = true
    )
    private PeriodoPagamento periodoPagamento;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "calculoPlanoAssinaturas", "assinaturaEmpresas", "descontoPlanoContaAzuls" }, allowSetters = true)
    private PlanoContaAzul planoContaAzul;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "calculoPlanoAssinaturas",
            "assinaturaEmpresas",
            "descontoPlanoContabils",
            "adicionalRamos",
            "adicionalTributacaos",
            "termoContratoContabils",
            "adicionalEnquadramentos",
            "valorBaseRamos",
            "termoAdesaoEmpresas",
        },
        allowSetters = true
    )
    private PlanoContabil planoContabil;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "anexoRequeridoEmpresas", "empresaModelos", "calculoPlanoAssinaturas", "adicionalRamos", "valorBaseRamos", "segmentoCnaes",
        },
        allowSetters = true
    )
    private Ramo ramo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "anexoRequeridoEmpresas", "empresaModelos", "calculoPlanoAssinaturas", "adicionalTributacaos" },
        allowSetters = true
    )
    private Tributacao tributacao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "calculoPlanoAssinaturas", "periodoPagamento", "planoContabil" }, allowSetters = true)
    private DescontoPlanoContabil descontoPlanoContabil;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "calculoPlanoAssinaturas", "planoContaAzul", "periodoPagamento" }, allowSetters = true)
    private DescontoPlanoContaAzul descontoPlanoContaAzul;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "grupoAcessoEmpresas",
            "areaContabilAssinaturaEmpresas",
            "servicoContabilAssinaturaEmpresas",
            "gatewayAssinaturaEmpresas",
            "calculoPlanoAssinaturas",
            "pagamentos",
            "cobrancaEmpresas",
            "usuarioEmpresas",
            "periodoPagamento",
            "formaDePagamento",
            "planoContaAzul",
            "planoContabil",
            "empresa",
        },
        allowSetters = true
    )
    private AssinaturaEmpresa assinaturaEmpresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CalculoPlanoAssinatura id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoAtendimento() {
        return this.codigoAtendimento;
    }

    public CalculoPlanoAssinatura codigoAtendimento(String codigoAtendimento) {
        this.setCodigoAtendimento(codigoAtendimento);
        return this;
    }

    public void setCodigoAtendimento(String codigoAtendimento) {
        this.codigoAtendimento = codigoAtendimento;
    }

    public Double getValorEnquadramento() {
        return this.valorEnquadramento;
    }

    public CalculoPlanoAssinatura valorEnquadramento(Double valorEnquadramento) {
        this.setValorEnquadramento(valorEnquadramento);
        return this;
    }

    public void setValorEnquadramento(Double valorEnquadramento) {
        this.valorEnquadramento = valorEnquadramento;
    }

    public Double getValorTributacao() {
        return this.valorTributacao;
    }

    public CalculoPlanoAssinatura valorTributacao(Double valorTributacao) {
        this.setValorTributacao(valorTributacao);
        return this;
    }

    public void setValorTributacao(Double valorTributacao) {
        this.valorTributacao = valorTributacao;
    }

    public Double getValorRamo() {
        return this.valorRamo;
    }

    public CalculoPlanoAssinatura valorRamo(Double valorRamo) {
        this.setValorRamo(valorRamo);
        return this;
    }

    public void setValorRamo(Double valorRamo) {
        this.valorRamo = valorRamo;
    }

    public Double getValorFuncionarios() {
        return this.valorFuncionarios;
    }

    public CalculoPlanoAssinatura valorFuncionarios(Double valorFuncionarios) {
        this.setValorFuncionarios(valorFuncionarios);
        return this;
    }

    public void setValorFuncionarios(Double valorFuncionarios) {
        this.valorFuncionarios = valorFuncionarios;
    }

    public Double getValorSocios() {
        return this.valorSocios;
    }

    public CalculoPlanoAssinatura valorSocios(Double valorSocios) {
        this.setValorSocios(valorSocios);
        return this;
    }

    public void setValorSocios(Double valorSocios) {
        this.valorSocios = valorSocios;
    }

    public Double getValorFaturamento() {
        return this.valorFaturamento;
    }

    public CalculoPlanoAssinatura valorFaturamento(Double valorFaturamento) {
        this.setValorFaturamento(valorFaturamento);
        return this;
    }

    public void setValorFaturamento(Double valorFaturamento) {
        this.valorFaturamento = valorFaturamento;
    }

    public Double getValorPlanoContabil() {
        return this.valorPlanoContabil;
    }

    public CalculoPlanoAssinatura valorPlanoContabil(Double valorPlanoContabil) {
        this.setValorPlanoContabil(valorPlanoContabil);
        return this;
    }

    public void setValorPlanoContabil(Double valorPlanoContabil) {
        this.valorPlanoContabil = valorPlanoContabil;
    }

    public Double getValorPlanoContabilComDesconto() {
        return this.valorPlanoContabilComDesconto;
    }

    public CalculoPlanoAssinatura valorPlanoContabilComDesconto(Double valorPlanoContabilComDesconto) {
        this.setValorPlanoContabilComDesconto(valorPlanoContabilComDesconto);
        return this;
    }

    public void setValorPlanoContabilComDesconto(Double valorPlanoContabilComDesconto) {
        this.valorPlanoContabilComDesconto = valorPlanoContabilComDesconto;
    }

    public Double getValorPlanoContaAzulComDesconto() {
        return this.valorPlanoContaAzulComDesconto;
    }

    public CalculoPlanoAssinatura valorPlanoContaAzulComDesconto(Double valorPlanoContaAzulComDesconto) {
        this.setValorPlanoContaAzulComDesconto(valorPlanoContaAzulComDesconto);
        return this;
    }

    public void setValorPlanoContaAzulComDesconto(Double valorPlanoContaAzulComDesconto) {
        this.valorPlanoContaAzulComDesconto = valorPlanoContaAzulComDesconto;
    }

    public Double getValorMensalidade() {
        return this.valorMensalidade;
    }

    public CalculoPlanoAssinatura valorMensalidade(Double valorMensalidade) {
        this.setValorMensalidade(valorMensalidade);
        return this;
    }

    public void setValorMensalidade(Double valorMensalidade) {
        this.valorMensalidade = valorMensalidade;
    }

    public Double getValorPeriodo() {
        return this.valorPeriodo;
    }

    public CalculoPlanoAssinatura valorPeriodo(Double valorPeriodo) {
        this.setValorPeriodo(valorPeriodo);
        return this;
    }

    public void setValorPeriodo(Double valorPeriodo) {
        this.valorPeriodo = valorPeriodo;
    }

    public Double getValorAno() {
        return this.valorAno;
    }

    public CalculoPlanoAssinatura valorAno(Double valorAno) {
        this.setValorAno(valorAno);
        return this;
    }

    public void setValorAno(Double valorAno) {
        this.valorAno = valorAno;
    }

    public PeriodoPagamento getPeriodoPagamento() {
        return this.periodoPagamento;
    }

    public void setPeriodoPagamento(PeriodoPagamento periodoPagamento) {
        this.periodoPagamento = periodoPagamento;
    }

    public CalculoPlanoAssinatura periodoPagamento(PeriodoPagamento periodoPagamento) {
        this.setPeriodoPagamento(periodoPagamento);
        return this;
    }

    public PlanoContaAzul getPlanoContaAzul() {
        return this.planoContaAzul;
    }

    public void setPlanoContaAzul(PlanoContaAzul planoContaAzul) {
        this.planoContaAzul = planoContaAzul;
    }

    public CalculoPlanoAssinatura planoContaAzul(PlanoContaAzul planoContaAzul) {
        this.setPlanoContaAzul(planoContaAzul);
        return this;
    }

    public PlanoContabil getPlanoContabil() {
        return this.planoContabil;
    }

    public void setPlanoContabil(PlanoContabil planoContabil) {
        this.planoContabil = planoContabil;
    }

    public CalculoPlanoAssinatura planoContabil(PlanoContabil planoContabil) {
        this.setPlanoContabil(planoContabil);
        return this;
    }

    public Ramo getRamo() {
        return this.ramo;
    }

    public void setRamo(Ramo ramo) {
        this.ramo = ramo;
    }

    public CalculoPlanoAssinatura ramo(Ramo ramo) {
        this.setRamo(ramo);
        return this;
    }

    public Tributacao getTributacao() {
        return this.tributacao;
    }

    public void setTributacao(Tributacao tributacao) {
        this.tributacao = tributacao;
    }

    public CalculoPlanoAssinatura tributacao(Tributacao tributacao) {
        this.setTributacao(tributacao);
        return this;
    }

    public DescontoPlanoContabil getDescontoPlanoContabil() {
        return this.descontoPlanoContabil;
    }

    public void setDescontoPlanoContabil(DescontoPlanoContabil descontoPlanoContabil) {
        this.descontoPlanoContabil = descontoPlanoContabil;
    }

    public CalculoPlanoAssinatura descontoPlanoContabil(DescontoPlanoContabil descontoPlanoContabil) {
        this.setDescontoPlanoContabil(descontoPlanoContabil);
        return this;
    }

    public DescontoPlanoContaAzul getDescontoPlanoContaAzul() {
        return this.descontoPlanoContaAzul;
    }

    public void setDescontoPlanoContaAzul(DescontoPlanoContaAzul descontoPlanoContaAzul) {
        this.descontoPlanoContaAzul = descontoPlanoContaAzul;
    }

    public CalculoPlanoAssinatura descontoPlanoContaAzul(DescontoPlanoContaAzul descontoPlanoContaAzul) {
        this.setDescontoPlanoContaAzul(descontoPlanoContaAzul);
        return this;
    }

    public AssinaturaEmpresa getAssinaturaEmpresa() {
        return this.assinaturaEmpresa;
    }

    public void setAssinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.assinaturaEmpresa = assinaturaEmpresa;
    }

    public CalculoPlanoAssinatura assinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.setAssinaturaEmpresa(assinaturaEmpresa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CalculoPlanoAssinatura)) {
            return false;
        }
        return getId() != null && getId().equals(((CalculoPlanoAssinatura) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CalculoPlanoAssinatura{" +
            "id=" + getId() +
            ", codigoAtendimento='" + getCodigoAtendimento() + "'" +
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
            "}";
    }
}
