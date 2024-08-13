package com.dobemcontabilidade.service.criteria;

import com.dobemcontabilidade.domain.enumeration.SituacaoPagamentoEnum;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Pagamento} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.PagamentoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pagamentos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PagamentoCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SituacaoPagamentoEnum
     */
    public static class SituacaoPagamentoEnumFilter extends Filter<SituacaoPagamentoEnum> {

        public SituacaoPagamentoEnumFilter() {}

        public SituacaoPagamentoEnumFilter(SituacaoPagamentoEnumFilter filter) {
            super(filter);
        }

        @Override
        public SituacaoPagamentoEnumFilter copy() {
            return new SituacaoPagamentoEnumFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter dataCobranca;

    private InstantFilter dataVencimento;

    private InstantFilter dataPagamento;

    private DoubleFilter valorPago;

    private DoubleFilter valorCobrado;

    private DoubleFilter acrescimo;

    private DoubleFilter multa;

    private DoubleFilter juros;

    private SituacaoPagamentoEnumFilter situacao;

    private LongFilter assinaturaEmpresaId;

    private Boolean distinct;

    public PagamentoCriteria() {}

    public PagamentoCriteria(PagamentoCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.dataCobranca = other.optionalDataCobranca().map(InstantFilter::copy).orElse(null);
        this.dataVencimento = other.optionalDataVencimento().map(InstantFilter::copy).orElse(null);
        this.dataPagamento = other.optionalDataPagamento().map(InstantFilter::copy).orElse(null);
        this.valorPago = other.optionalValorPago().map(DoubleFilter::copy).orElse(null);
        this.valorCobrado = other.optionalValorCobrado().map(DoubleFilter::copy).orElse(null);
        this.acrescimo = other.optionalAcrescimo().map(DoubleFilter::copy).orElse(null);
        this.multa = other.optionalMulta().map(DoubleFilter::copy).orElse(null);
        this.juros = other.optionalJuros().map(DoubleFilter::copy).orElse(null);
        this.situacao = other.optionalSituacao().map(SituacaoPagamentoEnumFilter::copy).orElse(null);
        this.assinaturaEmpresaId = other.optionalAssinaturaEmpresaId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PagamentoCriteria copy() {
        return new PagamentoCriteria(this);
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

    public InstantFilter getDataCobranca() {
        return dataCobranca;
    }

    public Optional<InstantFilter> optionalDataCobranca() {
        return Optional.ofNullable(dataCobranca);
    }

    public InstantFilter dataCobranca() {
        if (dataCobranca == null) {
            setDataCobranca(new InstantFilter());
        }
        return dataCobranca;
    }

    public void setDataCobranca(InstantFilter dataCobranca) {
        this.dataCobranca = dataCobranca;
    }

    public InstantFilter getDataVencimento() {
        return dataVencimento;
    }

    public Optional<InstantFilter> optionalDataVencimento() {
        return Optional.ofNullable(dataVencimento);
    }

    public InstantFilter dataVencimento() {
        if (dataVencimento == null) {
            setDataVencimento(new InstantFilter());
        }
        return dataVencimento;
    }

    public void setDataVencimento(InstantFilter dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public InstantFilter getDataPagamento() {
        return dataPagamento;
    }

    public Optional<InstantFilter> optionalDataPagamento() {
        return Optional.ofNullable(dataPagamento);
    }

    public InstantFilter dataPagamento() {
        if (dataPagamento == null) {
            setDataPagamento(new InstantFilter());
        }
        return dataPagamento;
    }

    public void setDataPagamento(InstantFilter dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public DoubleFilter getValorPago() {
        return valorPago;
    }

    public Optional<DoubleFilter> optionalValorPago() {
        return Optional.ofNullable(valorPago);
    }

    public DoubleFilter valorPago() {
        if (valorPago == null) {
            setValorPago(new DoubleFilter());
        }
        return valorPago;
    }

    public void setValorPago(DoubleFilter valorPago) {
        this.valorPago = valorPago;
    }

    public DoubleFilter getValorCobrado() {
        return valorCobrado;
    }

    public Optional<DoubleFilter> optionalValorCobrado() {
        return Optional.ofNullable(valorCobrado);
    }

    public DoubleFilter valorCobrado() {
        if (valorCobrado == null) {
            setValorCobrado(new DoubleFilter());
        }
        return valorCobrado;
    }

    public void setValorCobrado(DoubleFilter valorCobrado) {
        this.valorCobrado = valorCobrado;
    }

    public DoubleFilter getAcrescimo() {
        return acrescimo;
    }

    public Optional<DoubleFilter> optionalAcrescimo() {
        return Optional.ofNullable(acrescimo);
    }

    public DoubleFilter acrescimo() {
        if (acrescimo == null) {
            setAcrescimo(new DoubleFilter());
        }
        return acrescimo;
    }

    public void setAcrescimo(DoubleFilter acrescimo) {
        this.acrescimo = acrescimo;
    }

    public DoubleFilter getMulta() {
        return multa;
    }

    public Optional<DoubleFilter> optionalMulta() {
        return Optional.ofNullable(multa);
    }

    public DoubleFilter multa() {
        if (multa == null) {
            setMulta(new DoubleFilter());
        }
        return multa;
    }

    public void setMulta(DoubleFilter multa) {
        this.multa = multa;
    }

    public DoubleFilter getJuros() {
        return juros;
    }

    public Optional<DoubleFilter> optionalJuros() {
        return Optional.ofNullable(juros);
    }

    public DoubleFilter juros() {
        if (juros == null) {
            setJuros(new DoubleFilter());
        }
        return juros;
    }

    public void setJuros(DoubleFilter juros) {
        this.juros = juros;
    }

    public SituacaoPagamentoEnumFilter getSituacao() {
        return situacao;
    }

    public Optional<SituacaoPagamentoEnumFilter> optionalSituacao() {
        return Optional.ofNullable(situacao);
    }

    public SituacaoPagamentoEnumFilter situacao() {
        if (situacao == null) {
            setSituacao(new SituacaoPagamentoEnumFilter());
        }
        return situacao;
    }

    public void setSituacao(SituacaoPagamentoEnumFilter situacao) {
        this.situacao = situacao;
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
        final PagamentoCriteria that = (PagamentoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dataCobranca, that.dataCobranca) &&
            Objects.equals(dataVencimento, that.dataVencimento) &&
            Objects.equals(dataPagamento, that.dataPagamento) &&
            Objects.equals(valorPago, that.valorPago) &&
            Objects.equals(valorCobrado, that.valorCobrado) &&
            Objects.equals(acrescimo, that.acrescimo) &&
            Objects.equals(multa, that.multa) &&
            Objects.equals(juros, that.juros) &&
            Objects.equals(situacao, that.situacao) &&
            Objects.equals(assinaturaEmpresaId, that.assinaturaEmpresaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            dataCobranca,
            dataVencimento,
            dataPagamento,
            valorPago,
            valorCobrado,
            acrescimo,
            multa,
            juros,
            situacao,
            assinaturaEmpresaId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PagamentoCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalDataCobranca().map(f -> "dataCobranca=" + f + ", ").orElse("") +
            optionalDataVencimento().map(f -> "dataVencimento=" + f + ", ").orElse("") +
            optionalDataPagamento().map(f -> "dataPagamento=" + f + ", ").orElse("") +
            optionalValorPago().map(f -> "valorPago=" + f + ", ").orElse("") +
            optionalValorCobrado().map(f -> "valorCobrado=" + f + ", ").orElse("") +
            optionalAcrescimo().map(f -> "acrescimo=" + f + ", ").orElse("") +
            optionalMulta().map(f -> "multa=" + f + ", ").orElse("") +
            optionalJuros().map(f -> "juros=" + f + ", ").orElse("") +
            optionalSituacao().map(f -> "situacao=" + f + ", ").orElse("") +
            optionalAssinaturaEmpresaId().map(f -> "assinaturaEmpresaId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
