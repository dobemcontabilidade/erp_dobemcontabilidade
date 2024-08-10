package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.CalculoPlanoAssinatura} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.CalculoPlanoAssinaturaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /calculo-plano-assinaturas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CalculoPlanoAssinaturaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigoAtendimento;

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

    private LongFilter periodoPagamentoId;

    private LongFilter planoContabilId;

    private LongFilter ramoId;

    private LongFilter tributacaoId;

    private LongFilter descontoPlanoContabilId;

    private LongFilter assinaturaEmpresaId;

    private LongFilter descontoPlanoContaAzulId;

    private LongFilter planoContaAzulId;

    private Boolean distinct;

    public CalculoPlanoAssinaturaCriteria() {}

    public CalculoPlanoAssinaturaCriteria(CalculoPlanoAssinaturaCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.codigoAtendimento = other.optionalCodigoAtendimento().map(StringFilter::copy).orElse(null);
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
        this.periodoPagamentoId = other.optionalPeriodoPagamentoId().map(LongFilter::copy).orElse(null);
        this.planoContabilId = other.optionalPlanoContabilId().map(LongFilter::copy).orElse(null);
        this.ramoId = other.optionalRamoId().map(LongFilter::copy).orElse(null);
        this.tributacaoId = other.optionalTributacaoId().map(LongFilter::copy).orElse(null);
        this.descontoPlanoContabilId = other.optionalDescontoPlanoContabilId().map(LongFilter::copy).orElse(null);
        this.assinaturaEmpresaId = other.optionalAssinaturaEmpresaId().map(LongFilter::copy).orElse(null);
        this.descontoPlanoContaAzulId = other.optionalDescontoPlanoContaAzulId().map(LongFilter::copy).orElse(null);
        this.planoContaAzulId = other.optionalPlanoContaAzulId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public CalculoPlanoAssinaturaCriteria copy() {
        return new CalculoPlanoAssinaturaCriteria(this);
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

    public StringFilter getCodigoAtendimento() {
        return codigoAtendimento;
    }

    public Optional<StringFilter> optionalCodigoAtendimento() {
        return Optional.ofNullable(codigoAtendimento);
    }

    public StringFilter codigoAtendimento() {
        if (codigoAtendimento == null) {
            setCodigoAtendimento(new StringFilter());
        }
        return codigoAtendimento;
    }

    public void setCodigoAtendimento(StringFilter codigoAtendimento) {
        this.codigoAtendimento = codigoAtendimento;
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

    public LongFilter getRamoId() {
        return ramoId;
    }

    public Optional<LongFilter> optionalRamoId() {
        return Optional.ofNullable(ramoId);
    }

    public LongFilter ramoId() {
        if (ramoId == null) {
            setRamoId(new LongFilter());
        }
        return ramoId;
    }

    public void setRamoId(LongFilter ramoId) {
        this.ramoId = ramoId;
    }

    public LongFilter getTributacaoId() {
        return tributacaoId;
    }

    public Optional<LongFilter> optionalTributacaoId() {
        return Optional.ofNullable(tributacaoId);
    }

    public LongFilter tributacaoId() {
        if (tributacaoId == null) {
            setTributacaoId(new LongFilter());
        }
        return tributacaoId;
    }

    public void setTributacaoId(LongFilter tributacaoId) {
        this.tributacaoId = tributacaoId;
    }

    public LongFilter getDescontoPlanoContabilId() {
        return descontoPlanoContabilId;
    }

    public Optional<LongFilter> optionalDescontoPlanoContabilId() {
        return Optional.ofNullable(descontoPlanoContabilId);
    }

    public LongFilter descontoPlanoContabilId() {
        if (descontoPlanoContabilId == null) {
            setDescontoPlanoContabilId(new LongFilter());
        }
        return descontoPlanoContabilId;
    }

    public void setDescontoPlanoContabilId(LongFilter descontoPlanoContabilId) {
        this.descontoPlanoContabilId = descontoPlanoContabilId;
    }

    public LongFilter getAssinaturaEmpresaId() {
        return assinaturaEmpresaId;
    }

    public Optional<LongFilter> optionalAssinaturaEmpresaId() {
        return Optional.ofNullable(assinaturaEmpresaId);
    }

    public LongFilter assinaturaEmpresaId() {
        if (assinaturaEmpresaId == null) {
            setAssinaturaEmpresaId(new LongFilter());
        }
        return assinaturaEmpresaId;
    }

    public void setAssinaturaEmpresaId(LongFilter assinaturaEmpresaId) {
        this.assinaturaEmpresaId = assinaturaEmpresaId;
    }

    public LongFilter getDescontoPlanoContaAzulId() {
        return descontoPlanoContaAzulId;
    }

    public Optional<LongFilter> optionalDescontoPlanoContaAzulId() {
        return Optional.ofNullable(descontoPlanoContaAzulId);
    }

    public LongFilter descontoPlanoContaAzulId() {
        if (descontoPlanoContaAzulId == null) {
            setDescontoPlanoContaAzulId(new LongFilter());
        }
        return descontoPlanoContaAzulId;
    }

    public void setDescontoPlanoContaAzulId(LongFilter descontoPlanoContaAzulId) {
        this.descontoPlanoContaAzulId = descontoPlanoContaAzulId;
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
        final CalculoPlanoAssinaturaCriteria that = (CalculoPlanoAssinaturaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codigoAtendimento, that.codigoAtendimento) &&
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
            Objects.equals(periodoPagamentoId, that.periodoPagamentoId) &&
            Objects.equals(planoContabilId, that.planoContabilId) &&
            Objects.equals(ramoId, that.ramoId) &&
            Objects.equals(tributacaoId, that.tributacaoId) &&
            Objects.equals(descontoPlanoContabilId, that.descontoPlanoContabilId) &&
            Objects.equals(assinaturaEmpresaId, that.assinaturaEmpresaId) &&
            Objects.equals(descontoPlanoContaAzulId, that.descontoPlanoContaAzulId) &&
            Objects.equals(planoContaAzulId, that.planoContaAzulId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            codigoAtendimento,
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
            periodoPagamentoId,
            planoContabilId,
            ramoId,
            tributacaoId,
            descontoPlanoContabilId,
            assinaturaEmpresaId,
            descontoPlanoContaAzulId,
            planoContaAzulId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CalculoPlanoAssinaturaCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCodigoAtendimento().map(f -> "codigoAtendimento=" + f + ", ").orElse("") +
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
            optionalPeriodoPagamentoId().map(f -> "periodoPagamentoId=" + f + ", ").orElse("") +
            optionalPlanoContabilId().map(f -> "planoContabilId=" + f + ", ").orElse("") +
            optionalRamoId().map(f -> "ramoId=" + f + ", ").orElse("") +
            optionalTributacaoId().map(f -> "tributacaoId=" + f + ", ").orElse("") +
            optionalDescontoPlanoContabilId().map(f -> "descontoPlanoContabilId=" + f + ", ").orElse("") +
            optionalAssinaturaEmpresaId().map(f -> "assinaturaEmpresaId=" + f + ", ").orElse("") +
            optionalDescontoPlanoContaAzulId().map(f -> "descontoPlanoContaAzulId=" + f + ", ").orElse("") +
            optionalPlanoContaAzulId().map(f -> "planoContaAzulId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
