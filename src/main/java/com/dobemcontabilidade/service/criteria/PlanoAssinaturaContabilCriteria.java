package com.dobemcontabilidade.service.criteria;

import com.dobemcontabilidade.domain.enumeration.SituacaoPlanoContabilEnum;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.PlanoAssinaturaContabil} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.PlanoAssinaturaContabilResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /plano-assinatura-contabils?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanoAssinaturaContabilCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SituacaoPlanoContabilEnum
     */
    public static class SituacaoPlanoContabilEnumFilter extends Filter<SituacaoPlanoContabilEnum> {

        public SituacaoPlanoContabilEnumFilter() {}

        public SituacaoPlanoContabilEnumFilter(SituacaoPlanoContabilEnumFilter filter) {
            super(filter);
        }

        @Override
        public SituacaoPlanoContabilEnumFilter copy() {
            return new SituacaoPlanoContabilEnumFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private DoubleFilter adicionalSocio;

    private DoubleFilter adicionalFuncionario;

    private IntegerFilter sociosIsentos;

    private DoubleFilter adicionalFaturamento;

    private DoubleFilter valorBaseFaturamento;

    private DoubleFilter valorBaseAbertura;

    private SituacaoPlanoContabilEnumFilter situacao;

    private LongFilter descontoPeriodoPagamentoId;

    private LongFilter adicionalRamoId;

    private LongFilter adicionalTributacaoId;

    private LongFilter adicionalEnquadramentoId;

    private Boolean distinct;

    public PlanoAssinaturaContabilCriteria() {}

    public PlanoAssinaturaContabilCriteria(PlanoAssinaturaContabilCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.adicionalSocio = other.optionalAdicionalSocio().map(DoubleFilter::copy).orElse(null);
        this.adicionalFuncionario = other.optionalAdicionalFuncionario().map(DoubleFilter::copy).orElse(null);
        this.sociosIsentos = other.optionalSociosIsentos().map(IntegerFilter::copy).orElse(null);
        this.adicionalFaturamento = other.optionalAdicionalFaturamento().map(DoubleFilter::copy).orElse(null);
        this.valorBaseFaturamento = other.optionalValorBaseFaturamento().map(DoubleFilter::copy).orElse(null);
        this.valorBaseAbertura = other.optionalValorBaseAbertura().map(DoubleFilter::copy).orElse(null);
        this.situacao = other.optionalSituacao().map(SituacaoPlanoContabilEnumFilter::copy).orElse(null);
        this.descontoPeriodoPagamentoId = other.optionalDescontoPeriodoPagamentoId().map(LongFilter::copy).orElse(null);
        this.adicionalRamoId = other.optionalAdicionalRamoId().map(LongFilter::copy).orElse(null);
        this.adicionalTributacaoId = other.optionalAdicionalTributacaoId().map(LongFilter::copy).orElse(null);
        this.adicionalEnquadramentoId = other.optionalAdicionalEnquadramentoId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PlanoAssinaturaContabilCriteria copy() {
        return new PlanoAssinaturaContabilCriteria(this);
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

    public StringFilter getNome() {
        return nome;
    }

    public Optional<StringFilter> optionalNome() {
        return Optional.ofNullable(nome);
    }

    public StringFilter nome() {
        if (nome == null) {
            setNome(new StringFilter());
        }
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public DoubleFilter getAdicionalSocio() {
        return adicionalSocio;
    }

    public Optional<DoubleFilter> optionalAdicionalSocio() {
        return Optional.ofNullable(adicionalSocio);
    }

    public DoubleFilter adicionalSocio() {
        if (adicionalSocio == null) {
            setAdicionalSocio(new DoubleFilter());
        }
        return adicionalSocio;
    }

    public void setAdicionalSocio(DoubleFilter adicionalSocio) {
        this.adicionalSocio = adicionalSocio;
    }

    public DoubleFilter getAdicionalFuncionario() {
        return adicionalFuncionario;
    }

    public Optional<DoubleFilter> optionalAdicionalFuncionario() {
        return Optional.ofNullable(adicionalFuncionario);
    }

    public DoubleFilter adicionalFuncionario() {
        if (adicionalFuncionario == null) {
            setAdicionalFuncionario(new DoubleFilter());
        }
        return adicionalFuncionario;
    }

    public void setAdicionalFuncionario(DoubleFilter adicionalFuncionario) {
        this.adicionalFuncionario = adicionalFuncionario;
    }

    public IntegerFilter getSociosIsentos() {
        return sociosIsentos;
    }

    public Optional<IntegerFilter> optionalSociosIsentos() {
        return Optional.ofNullable(sociosIsentos);
    }

    public IntegerFilter sociosIsentos() {
        if (sociosIsentos == null) {
            setSociosIsentos(new IntegerFilter());
        }
        return sociosIsentos;
    }

    public void setSociosIsentos(IntegerFilter sociosIsentos) {
        this.sociosIsentos = sociosIsentos;
    }

    public DoubleFilter getAdicionalFaturamento() {
        return adicionalFaturamento;
    }

    public Optional<DoubleFilter> optionalAdicionalFaturamento() {
        return Optional.ofNullable(adicionalFaturamento);
    }

    public DoubleFilter adicionalFaturamento() {
        if (adicionalFaturamento == null) {
            setAdicionalFaturamento(new DoubleFilter());
        }
        return adicionalFaturamento;
    }

    public void setAdicionalFaturamento(DoubleFilter adicionalFaturamento) {
        this.adicionalFaturamento = adicionalFaturamento;
    }

    public DoubleFilter getValorBaseFaturamento() {
        return valorBaseFaturamento;
    }

    public Optional<DoubleFilter> optionalValorBaseFaturamento() {
        return Optional.ofNullable(valorBaseFaturamento);
    }

    public DoubleFilter valorBaseFaturamento() {
        if (valorBaseFaturamento == null) {
            setValorBaseFaturamento(new DoubleFilter());
        }
        return valorBaseFaturamento;
    }

    public void setValorBaseFaturamento(DoubleFilter valorBaseFaturamento) {
        this.valorBaseFaturamento = valorBaseFaturamento;
    }

    public DoubleFilter getValorBaseAbertura() {
        return valorBaseAbertura;
    }

    public Optional<DoubleFilter> optionalValorBaseAbertura() {
        return Optional.ofNullable(valorBaseAbertura);
    }

    public DoubleFilter valorBaseAbertura() {
        if (valorBaseAbertura == null) {
            setValorBaseAbertura(new DoubleFilter());
        }
        return valorBaseAbertura;
    }

    public void setValorBaseAbertura(DoubleFilter valorBaseAbertura) {
        this.valorBaseAbertura = valorBaseAbertura;
    }

    public SituacaoPlanoContabilEnumFilter getSituacao() {
        return situacao;
    }

    public Optional<SituacaoPlanoContabilEnumFilter> optionalSituacao() {
        return Optional.ofNullable(situacao);
    }

    public SituacaoPlanoContabilEnumFilter situacao() {
        if (situacao == null) {
            setSituacao(new SituacaoPlanoContabilEnumFilter());
        }
        return situacao;
    }

    public void setSituacao(SituacaoPlanoContabilEnumFilter situacao) {
        this.situacao = situacao;
    }

    public LongFilter getDescontoPeriodoPagamentoId() {
        return descontoPeriodoPagamentoId;
    }

    public Optional<LongFilter> optionalDescontoPeriodoPagamentoId() {
        return Optional.ofNullable(descontoPeriodoPagamentoId);
    }

    public LongFilter descontoPeriodoPagamentoId() {
        if (descontoPeriodoPagamentoId == null) {
            setDescontoPeriodoPagamentoId(new LongFilter());
        }
        return descontoPeriodoPagamentoId;
    }

    public void setDescontoPeriodoPagamentoId(LongFilter descontoPeriodoPagamentoId) {
        this.descontoPeriodoPagamentoId = descontoPeriodoPagamentoId;
    }

    public LongFilter getAdicionalRamoId() {
        return adicionalRamoId;
    }

    public Optional<LongFilter> optionalAdicionalRamoId() {
        return Optional.ofNullable(adicionalRamoId);
    }

    public LongFilter adicionalRamoId() {
        if (adicionalRamoId == null) {
            setAdicionalRamoId(new LongFilter());
        }
        return adicionalRamoId;
    }

    public void setAdicionalRamoId(LongFilter adicionalRamoId) {
        this.adicionalRamoId = adicionalRamoId;
    }

    public LongFilter getAdicionalTributacaoId() {
        return adicionalTributacaoId;
    }

    public Optional<LongFilter> optionalAdicionalTributacaoId() {
        return Optional.ofNullable(adicionalTributacaoId);
    }

    public LongFilter adicionalTributacaoId() {
        if (adicionalTributacaoId == null) {
            setAdicionalTributacaoId(new LongFilter());
        }
        return adicionalTributacaoId;
    }

    public void setAdicionalTributacaoId(LongFilter adicionalTributacaoId) {
        this.adicionalTributacaoId = adicionalTributacaoId;
    }

    public LongFilter getAdicionalEnquadramentoId() {
        return adicionalEnquadramentoId;
    }

    public Optional<LongFilter> optionalAdicionalEnquadramentoId() {
        return Optional.ofNullable(adicionalEnquadramentoId);
    }

    public LongFilter adicionalEnquadramentoId() {
        if (adicionalEnquadramentoId == null) {
            setAdicionalEnquadramentoId(new LongFilter());
        }
        return adicionalEnquadramentoId;
    }

    public void setAdicionalEnquadramentoId(LongFilter adicionalEnquadramentoId) {
        this.adicionalEnquadramentoId = adicionalEnquadramentoId;
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
        final PlanoAssinaturaContabilCriteria that = (PlanoAssinaturaContabilCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(adicionalSocio, that.adicionalSocio) &&
            Objects.equals(adicionalFuncionario, that.adicionalFuncionario) &&
            Objects.equals(sociosIsentos, that.sociosIsentos) &&
            Objects.equals(adicionalFaturamento, that.adicionalFaturamento) &&
            Objects.equals(valorBaseFaturamento, that.valorBaseFaturamento) &&
            Objects.equals(valorBaseAbertura, that.valorBaseAbertura) &&
            Objects.equals(situacao, that.situacao) &&
            Objects.equals(descontoPeriodoPagamentoId, that.descontoPeriodoPagamentoId) &&
            Objects.equals(adicionalRamoId, that.adicionalRamoId) &&
            Objects.equals(adicionalTributacaoId, that.adicionalTributacaoId) &&
            Objects.equals(adicionalEnquadramentoId, that.adicionalEnquadramentoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nome,
            adicionalSocio,
            adicionalFuncionario,
            sociosIsentos,
            adicionalFaturamento,
            valorBaseFaturamento,
            valorBaseAbertura,
            situacao,
            descontoPeriodoPagamentoId,
            adicionalRamoId,
            adicionalTributacaoId,
            adicionalEnquadramentoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoAssinaturaContabilCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalAdicionalSocio().map(f -> "adicionalSocio=" + f + ", ").orElse("") +
            optionalAdicionalFuncionario().map(f -> "adicionalFuncionario=" + f + ", ").orElse("") +
            optionalSociosIsentos().map(f -> "sociosIsentos=" + f + ", ").orElse("") +
            optionalAdicionalFaturamento().map(f -> "adicionalFaturamento=" + f + ", ").orElse("") +
            optionalValorBaseFaturamento().map(f -> "valorBaseFaturamento=" + f + ", ").orElse("") +
            optionalValorBaseAbertura().map(f -> "valorBaseAbertura=" + f + ", ").orElse("") +
            optionalSituacao().map(f -> "situacao=" + f + ", ").orElse("") +
            optionalDescontoPeriodoPagamentoId().map(f -> "descontoPeriodoPagamentoId=" + f + ", ").orElse("") +
            optionalAdicionalRamoId().map(f -> "adicionalRamoId=" + f + ", ").orElse("") +
            optionalAdicionalTributacaoId().map(f -> "adicionalTributacaoId=" + f + ", ").orElse("") +
            optionalAdicionalEnquadramentoId().map(f -> "adicionalEnquadramentoId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
