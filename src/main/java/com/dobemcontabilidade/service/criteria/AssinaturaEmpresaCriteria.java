package com.dobemcontabilidade.service.criteria;

import com.dobemcontabilidade.domain.enumeration.SituacaoContratoEmpresaEnum;
import com.dobemcontabilidade.domain.enumeration.TipoContratoEnum;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.AssinaturaEmpresa} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.AssinaturaEmpresaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /assinatura-empresas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssinaturaEmpresaCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SituacaoContratoEmpresaEnum
     */
    public static class SituacaoContratoEmpresaEnumFilter extends Filter<SituacaoContratoEmpresaEnum> {

        public SituacaoContratoEmpresaEnumFilter() {}

        public SituacaoContratoEmpresaEnumFilter(SituacaoContratoEmpresaEnumFilter filter) {
            super(filter);
        }

        @Override
        public SituacaoContratoEmpresaEnumFilter copy() {
            return new SituacaoContratoEmpresaEnumFilter(this);
        }
    }

    /**
     * Class for filtering TipoContratoEnum
     */
    public static class TipoContratoEnumFilter extends Filter<TipoContratoEnum> {

        public TipoContratoEnumFilter() {}

        public TipoContratoEnumFilter(TipoContratoEnumFilter filter) {
            super(filter);
        }

        @Override
        public TipoContratoEnumFilter copy() {
            return new TipoContratoEnumFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigoAssinatura;

    private DoubleFilter valorEnquadramento;

    private DoubleFilter valorTributacao;

    private DoubleFilter valorRamo;

    private DoubleFilter valorFuncionarios;

    private DoubleFilter valorSocios;

    private DoubleFilter valorFaturamento;

    private DoubleFilter valorPlanoContabil;

    private DoubleFilter valorPlanoContabilComDesconto;

    private DoubleFilter valorMensalidade;

    private DoubleFilter valorPeriodo;

    private DoubleFilter valorAno;

    private InstantFilter dataContratacao;

    private InstantFilter dataEncerramento;

    private IntegerFilter diaVencimento;

    private SituacaoContratoEmpresaEnumFilter situacao;

    private TipoContratoEnumFilter tipoContrato;

    private LongFilter calculoPlanoAssinaturaId;

    private LongFilter pagamentoId;

    private LongFilter periodoPagamentoId;

    private LongFilter formaDePagamentoId;

    private LongFilter planoContabilId;

    private LongFilter empresaId;

    private LongFilter planoContaAzulId;

    private Boolean distinct;

    public AssinaturaEmpresaCriteria() {}

    public AssinaturaEmpresaCriteria(AssinaturaEmpresaCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.codigoAssinatura = other.optionalCodigoAssinatura().map(StringFilter::copy).orElse(null);
        this.valorEnquadramento = other.optionalValorEnquadramento().map(DoubleFilter::copy).orElse(null);
        this.valorTributacao = other.optionalValorTributacao().map(DoubleFilter::copy).orElse(null);
        this.valorRamo = other.optionalValorRamo().map(DoubleFilter::copy).orElse(null);
        this.valorFuncionarios = other.optionalValorFuncionarios().map(DoubleFilter::copy).orElse(null);
        this.valorSocios = other.optionalValorSocios().map(DoubleFilter::copy).orElse(null);
        this.valorFaturamento = other.optionalValorFaturamento().map(DoubleFilter::copy).orElse(null);
        this.valorPlanoContabil = other.optionalValorPlanoContabil().map(DoubleFilter::copy).orElse(null);
        this.valorPlanoContabilComDesconto = other.optionalValorPlanoContabilComDesconto().map(DoubleFilter::copy).orElse(null);
        this.valorMensalidade = other.optionalValorMensalidade().map(DoubleFilter::copy).orElse(null);
        this.valorPeriodo = other.optionalValorPeriodo().map(DoubleFilter::copy).orElse(null);
        this.valorAno = other.optionalValorAno().map(DoubleFilter::copy).orElse(null);
        this.dataContratacao = other.optionalDataContratacao().map(InstantFilter::copy).orElse(null);
        this.dataEncerramento = other.optionalDataEncerramento().map(InstantFilter::copy).orElse(null);
        this.diaVencimento = other.optionalDiaVencimento().map(IntegerFilter::copy).orElse(null);
        this.situacao = other.optionalSituacao().map(SituacaoContratoEmpresaEnumFilter::copy).orElse(null);
        this.tipoContrato = other.optionalTipoContrato().map(TipoContratoEnumFilter::copy).orElse(null);
        this.calculoPlanoAssinaturaId = other.optionalCalculoPlanoAssinaturaId().map(LongFilter::copy).orElse(null);
        this.pagamentoId = other.optionalPagamentoId().map(LongFilter::copy).orElse(null);
        this.periodoPagamentoId = other.optionalPeriodoPagamentoId().map(LongFilter::copy).orElse(null);
        this.formaDePagamentoId = other.optionalFormaDePagamentoId().map(LongFilter::copy).orElse(null);
        this.planoContabilId = other.optionalPlanoContabilId().map(LongFilter::copy).orElse(null);
        this.empresaId = other.optionalEmpresaId().map(LongFilter::copy).orElse(null);
        this.planoContaAzulId = other.optionalPlanoContaAzulId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AssinaturaEmpresaCriteria copy() {
        return new AssinaturaEmpresaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCodigoAssinatura() {
        return codigoAssinatura;
    }

    public Optional<StringFilter> optionalCodigoAssinatura() {
        return Optional.ofNullable(codigoAssinatura);
    }

    public StringFilter codigoAssinatura() {
        if (codigoAssinatura == null) {
            setCodigoAssinatura(new StringFilter());
        }
        return codigoAssinatura;
    }

    public void setCodigoAssinatura(StringFilter codigoAssinatura) {
        this.codigoAssinatura = codigoAssinatura;
    }

    public DoubleFilter getValorEnquadramento() {
        return valorEnquadramento;
    }

    public Optional<DoubleFilter> optionalValorEnquadramento() {
        return Optional.ofNullable(valorEnquadramento);
    }

    public DoubleFilter valorEnquadramento() {
        if (valorEnquadramento == null) {
            setValorEnquadramento(new DoubleFilter());
        }
        return valorEnquadramento;
    }

    public void setValorEnquadramento(DoubleFilter valorEnquadramento) {
        this.valorEnquadramento = valorEnquadramento;
    }

    public DoubleFilter getValorTributacao() {
        return valorTributacao;
    }

    public Optional<DoubleFilter> optionalValorTributacao() {
        return Optional.ofNullable(valorTributacao);
    }

    public DoubleFilter valorTributacao() {
        if (valorTributacao == null) {
            setValorTributacao(new DoubleFilter());
        }
        return valorTributacao;
    }

    public void setValorTributacao(DoubleFilter valorTributacao) {
        this.valorTributacao = valorTributacao;
    }

    public DoubleFilter getValorRamo() {
        return valorRamo;
    }

    public Optional<DoubleFilter> optionalValorRamo() {
        return Optional.ofNullable(valorRamo);
    }

    public DoubleFilter valorRamo() {
        if (valorRamo == null) {
            setValorRamo(new DoubleFilter());
        }
        return valorRamo;
    }

    public void setValorRamo(DoubleFilter valorRamo) {
        this.valorRamo = valorRamo;
    }

    public DoubleFilter getValorFuncionarios() {
        return valorFuncionarios;
    }

    public Optional<DoubleFilter> optionalValorFuncionarios() {
        return Optional.ofNullable(valorFuncionarios);
    }

    public DoubleFilter valorFuncionarios() {
        if (valorFuncionarios == null) {
            setValorFuncionarios(new DoubleFilter());
        }
        return valorFuncionarios;
    }

    public void setValorFuncionarios(DoubleFilter valorFuncionarios) {
        this.valorFuncionarios = valorFuncionarios;
    }

    public DoubleFilter getValorSocios() {
        return valorSocios;
    }

    public Optional<DoubleFilter> optionalValorSocios() {
        return Optional.ofNullable(valorSocios);
    }

    public DoubleFilter valorSocios() {
        if (valorSocios == null) {
            setValorSocios(new DoubleFilter());
        }
        return valorSocios;
    }

    public void setValorSocios(DoubleFilter valorSocios) {
        this.valorSocios = valorSocios;
    }

    public DoubleFilter getValorFaturamento() {
        return valorFaturamento;
    }

    public Optional<DoubleFilter> optionalValorFaturamento() {
        return Optional.ofNullable(valorFaturamento);
    }

    public DoubleFilter valorFaturamento() {
        if (valorFaturamento == null) {
            setValorFaturamento(new DoubleFilter());
        }
        return valorFaturamento;
    }

    public void setValorFaturamento(DoubleFilter valorFaturamento) {
        this.valorFaturamento = valorFaturamento;
    }

    public DoubleFilter getValorPlanoContabil() {
        return valorPlanoContabil;
    }

    public Optional<DoubleFilter> optionalValorPlanoContabil() {
        return Optional.ofNullable(valorPlanoContabil);
    }

    public DoubleFilter valorPlanoContabil() {
        if (valorPlanoContabil == null) {
            setValorPlanoContabil(new DoubleFilter());
        }
        return valorPlanoContabil;
    }

    public void setValorPlanoContabil(DoubleFilter valorPlanoContabil) {
        this.valorPlanoContabil = valorPlanoContabil;
    }

    public DoubleFilter getValorPlanoContabilComDesconto() {
        return valorPlanoContabilComDesconto;
    }

    public Optional<DoubleFilter> optionalValorPlanoContabilComDesconto() {
        return Optional.ofNullable(valorPlanoContabilComDesconto);
    }

    public DoubleFilter valorPlanoContabilComDesconto() {
        if (valorPlanoContabilComDesconto == null) {
            setValorPlanoContabilComDesconto(new DoubleFilter());
        }
        return valorPlanoContabilComDesconto;
    }

    public void setValorPlanoContabilComDesconto(DoubleFilter valorPlanoContabilComDesconto) {
        this.valorPlanoContabilComDesconto = valorPlanoContabilComDesconto;
    }

    public DoubleFilter getValorMensalidade() {
        return valorMensalidade;
    }

    public Optional<DoubleFilter> optionalValorMensalidade() {
        return Optional.ofNullable(valorMensalidade);
    }

    public DoubleFilter valorMensalidade() {
        if (valorMensalidade == null) {
            setValorMensalidade(new DoubleFilter());
        }
        return valorMensalidade;
    }

    public void setValorMensalidade(DoubleFilter valorMensalidade) {
        this.valorMensalidade = valorMensalidade;
    }

    public DoubleFilter getValorPeriodo() {
        return valorPeriodo;
    }

    public Optional<DoubleFilter> optionalValorPeriodo() {
        return Optional.ofNullable(valorPeriodo);
    }

    public DoubleFilter valorPeriodo() {
        if (valorPeriodo == null) {
            setValorPeriodo(new DoubleFilter());
        }
        return valorPeriodo;
    }

    public void setValorPeriodo(DoubleFilter valorPeriodo) {
        this.valorPeriodo = valorPeriodo;
    }

    public DoubleFilter getValorAno() {
        return valorAno;
    }

    public Optional<DoubleFilter> optionalValorAno() {
        return Optional.ofNullable(valorAno);
    }

    public DoubleFilter valorAno() {
        if (valorAno == null) {
            setValorAno(new DoubleFilter());
        }
        return valorAno;
    }

    public void setValorAno(DoubleFilter valorAno) {
        this.valorAno = valorAno;
    }

    public InstantFilter getDataContratacao() {
        return dataContratacao;
    }

    public Optional<InstantFilter> optionalDataContratacao() {
        return Optional.ofNullable(dataContratacao);
    }

    public InstantFilter dataContratacao() {
        if (dataContratacao == null) {
            setDataContratacao(new InstantFilter());
        }
        return dataContratacao;
    }

    public void setDataContratacao(InstantFilter dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public InstantFilter getDataEncerramento() {
        return dataEncerramento;
    }

    public Optional<InstantFilter> optionalDataEncerramento() {
        return Optional.ofNullable(dataEncerramento);
    }

    public InstantFilter dataEncerramento() {
        if (dataEncerramento == null) {
            setDataEncerramento(new InstantFilter());
        }
        return dataEncerramento;
    }

    public void setDataEncerramento(InstantFilter dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public IntegerFilter getDiaVencimento() {
        return diaVencimento;
    }

    public Optional<IntegerFilter> optionalDiaVencimento() {
        return Optional.ofNullable(diaVencimento);
    }

    public IntegerFilter diaVencimento() {
        if (diaVencimento == null) {
            setDiaVencimento(new IntegerFilter());
        }
        return diaVencimento;
    }

    public void setDiaVencimento(IntegerFilter diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public SituacaoContratoEmpresaEnumFilter getSituacao() {
        return situacao;
    }

    public Optional<SituacaoContratoEmpresaEnumFilter> optionalSituacao() {
        return Optional.ofNullable(situacao);
    }

    public SituacaoContratoEmpresaEnumFilter situacao() {
        if (situacao == null) {
            setSituacao(new SituacaoContratoEmpresaEnumFilter());
        }
        return situacao;
    }

    public void setSituacao(SituacaoContratoEmpresaEnumFilter situacao) {
        this.situacao = situacao;
    }

    public TipoContratoEnumFilter getTipoContrato() {
        return tipoContrato;
    }

    public Optional<TipoContratoEnumFilter> optionalTipoContrato() {
        return Optional.ofNullable(tipoContrato);
    }

    public TipoContratoEnumFilter tipoContrato() {
        if (tipoContrato == null) {
            setTipoContrato(new TipoContratoEnumFilter());
        }
        return tipoContrato;
    }

    public void setTipoContrato(TipoContratoEnumFilter tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public LongFilter getCalculoPlanoAssinaturaId() {
        return calculoPlanoAssinaturaId;
    }

    public Optional<LongFilter> optionalCalculoPlanoAssinaturaId() {
        return Optional.ofNullable(calculoPlanoAssinaturaId);
    }

    public LongFilter calculoPlanoAssinaturaId() {
        if (calculoPlanoAssinaturaId == null) {
            setCalculoPlanoAssinaturaId(new LongFilter());
        }
        return calculoPlanoAssinaturaId;
    }

    public void setCalculoPlanoAssinaturaId(LongFilter calculoPlanoAssinaturaId) {
        this.calculoPlanoAssinaturaId = calculoPlanoAssinaturaId;
    }

    public LongFilter getPagamentoId() {
        return pagamentoId;
    }

    public Optional<LongFilter> optionalPagamentoId() {
        return Optional.ofNullable(pagamentoId);
    }

    public LongFilter pagamentoId() {
        if (pagamentoId == null) {
            setPagamentoId(new LongFilter());
        }
        return pagamentoId;
    }

    public void setPagamentoId(LongFilter pagamentoId) {
        this.pagamentoId = pagamentoId;
    }

    public LongFilter getPeriodoPagamentoId() {
        return periodoPagamentoId;
    }

    public Optional<LongFilter> optionalPeriodoPagamentoId() {
        return Optional.ofNullable(periodoPagamentoId);
    }

    public LongFilter periodoPagamentoId() {
        if (periodoPagamentoId == null) {
            setPeriodoPagamentoId(new LongFilter());
        }
        return periodoPagamentoId;
    }

    public void setPeriodoPagamentoId(LongFilter periodoPagamentoId) {
        this.periodoPagamentoId = periodoPagamentoId;
    }

    public LongFilter getFormaDePagamentoId() {
        return formaDePagamentoId;
    }

    public Optional<LongFilter> optionalFormaDePagamentoId() {
        return Optional.ofNullable(formaDePagamentoId);
    }

    public LongFilter formaDePagamentoId() {
        if (formaDePagamentoId == null) {
            setFormaDePagamentoId(new LongFilter());
        }
        return formaDePagamentoId;
    }

    public void setFormaDePagamentoId(LongFilter formaDePagamentoId) {
        this.formaDePagamentoId = formaDePagamentoId;
    }

    public LongFilter getPlanoContabilId() {
        return planoContabilId;
    }

    public Optional<LongFilter> optionalPlanoContabilId() {
        return Optional.ofNullable(planoContabilId);
    }

    public LongFilter planoContabilId() {
        if (planoContabilId == null) {
            setPlanoContabilId(new LongFilter());
        }
        return planoContabilId;
    }

    public void setPlanoContabilId(LongFilter planoContabilId) {
        this.planoContabilId = planoContabilId;
    }

    public LongFilter getEmpresaId() {
        return empresaId;
    }

    public Optional<LongFilter> optionalEmpresaId() {
        return Optional.ofNullable(empresaId);
    }

    public LongFilter empresaId() {
        if (empresaId == null) {
            setEmpresaId(new LongFilter());
        }
        return empresaId;
    }

    public void setEmpresaId(LongFilter empresaId) {
        this.empresaId = empresaId;
    }

    public LongFilter getPlanoContaAzulId() {
        return planoContaAzulId;
    }

    public Optional<LongFilter> optionalPlanoContaAzulId() {
        return Optional.ofNullable(planoContaAzulId);
    }

    public LongFilter planoContaAzulId() {
        if (planoContaAzulId == null) {
            setPlanoContaAzulId(new LongFilter());
        }
        return planoContaAzulId;
    }

    public void setPlanoContaAzulId(LongFilter planoContaAzulId) {
        this.planoContaAzulId = planoContaAzulId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AssinaturaEmpresaCriteria that = (AssinaturaEmpresaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codigoAssinatura, that.codigoAssinatura) &&
            Objects.equals(valorEnquadramento, that.valorEnquadramento) &&
            Objects.equals(valorTributacao, that.valorTributacao) &&
            Objects.equals(valorRamo, that.valorRamo) &&
            Objects.equals(valorFuncionarios, that.valorFuncionarios) &&
            Objects.equals(valorSocios, that.valorSocios) &&
            Objects.equals(valorFaturamento, that.valorFaturamento) &&
            Objects.equals(valorPlanoContabil, that.valorPlanoContabil) &&
            Objects.equals(valorPlanoContabilComDesconto, that.valorPlanoContabilComDesconto) &&
            Objects.equals(valorMensalidade, that.valorMensalidade) &&
            Objects.equals(valorPeriodo, that.valorPeriodo) &&
            Objects.equals(valorAno, that.valorAno) &&
            Objects.equals(dataContratacao, that.dataContratacao) &&
            Objects.equals(dataEncerramento, that.dataEncerramento) &&
            Objects.equals(diaVencimento, that.diaVencimento) &&
            Objects.equals(situacao, that.situacao) &&
            Objects.equals(tipoContrato, that.tipoContrato) &&
            Objects.equals(calculoPlanoAssinaturaId, that.calculoPlanoAssinaturaId) &&
            Objects.equals(pagamentoId, that.pagamentoId) &&
            Objects.equals(periodoPagamentoId, that.periodoPagamentoId) &&
            Objects.equals(formaDePagamentoId, that.formaDePagamentoId) &&
            Objects.equals(planoContabilId, that.planoContabilId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(planoContaAzulId, that.planoContaAzulId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            codigoAssinatura,
            valorEnquadramento,
            valorTributacao,
            valorRamo,
            valorFuncionarios,
            valorSocios,
            valorFaturamento,
            valorPlanoContabil,
            valorPlanoContabilComDesconto,
            valorMensalidade,
            valorPeriodo,
            valorAno,
            dataContratacao,
            dataEncerramento,
            diaVencimento,
            situacao,
            tipoContrato,
            calculoPlanoAssinaturaId,
            pagamentoId,
            periodoPagamentoId,
            formaDePagamentoId,
            planoContabilId,
            empresaId,
            planoContaAzulId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssinaturaEmpresaCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCodigoAssinatura().map(f -> "codigoAssinatura=" + f + ", ").orElse("") +
            optionalValorEnquadramento().map(f -> "valorEnquadramento=" + f + ", ").orElse("") +
            optionalValorTributacao().map(f -> "valorTributacao=" + f + ", ").orElse("") +
            optionalValorRamo().map(f -> "valorRamo=" + f + ", ").orElse("") +
            optionalValorFuncionarios().map(f -> "valorFuncionarios=" + f + ", ").orElse("") +
            optionalValorSocios().map(f -> "valorSocios=" + f + ", ").orElse("") +
            optionalValorFaturamento().map(f -> "valorFaturamento=" + f + ", ").orElse("") +
            optionalValorPlanoContabil().map(f -> "valorPlanoContabil=" + f + ", ").orElse("") +
            optionalValorPlanoContabilComDesconto().map(f -> "valorPlanoContabilComDesconto=" + f + ", ").orElse("") +
            optionalValorMensalidade().map(f -> "valorMensalidade=" + f + ", ").orElse("") +
            optionalValorPeriodo().map(f -> "valorPeriodo=" + f + ", ").orElse("") +
            optionalValorAno().map(f -> "valorAno=" + f + ", ").orElse("") +
            optionalDataContratacao().map(f -> "dataContratacao=" + f + ", ").orElse("") +
            optionalDataEncerramento().map(f -> "dataEncerramento=" + f + ", ").orElse("") +
            optionalDiaVencimento().map(f -> "diaVencimento=" + f + ", ").orElse("") +
            optionalSituacao().map(f -> "situacao=" + f + ", ").orElse("") +
            optionalTipoContrato().map(f -> "tipoContrato=" + f + ", ").orElse("") +
            optionalCalculoPlanoAssinaturaId().map(f -> "calculoPlanoAssinaturaId=" + f + ", ").orElse("") +
            optionalPagamentoId().map(f -> "pagamentoId=" + f + ", ").orElse("") +
            optionalPeriodoPagamentoId().map(f -> "periodoPagamentoId=" + f + ", ").orElse("") +
            optionalFormaDePagamentoId().map(f -> "formaDePagamentoId=" + f + ", ").orElse("") +
            optionalPlanoContabilId().map(f -> "planoContabilId=" + f + ", ").orElse("") +
            optionalEmpresaId().map(f -> "empresaId=" + f + ", ").orElse("") +
            optionalPlanoContaAzulId().map(f -> "planoContaAzulId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
