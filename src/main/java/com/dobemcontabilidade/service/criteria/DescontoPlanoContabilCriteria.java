package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.DescontoPlanoContabil} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.DescontoPlanoContabilResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /desconto-plano-contabils?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DescontoPlanoContabilCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter percentual;

    private LongFilter calculoPlanoAssinaturaId;

    private LongFilter periodoPagamentoId;

    private LongFilter planoContabilId;

    private Boolean distinct;

    public DescontoPlanoContabilCriteria() {}

    public DescontoPlanoContabilCriteria(DescontoPlanoContabilCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.percentual = other.optionalPercentual().map(DoubleFilter::copy).orElse(null);
        this.calculoPlanoAssinaturaId = other.optionalCalculoPlanoAssinaturaId().map(LongFilter::copy).orElse(null);
        this.periodoPagamentoId = other.optionalPeriodoPagamentoId().map(LongFilter::copy).orElse(null);
        this.planoContabilId = other.optionalPlanoContabilId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public DescontoPlanoContabilCriteria copy() {
        return new DescontoPlanoContabilCriteria(this);
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

    public DoubleFilter getPercentual() {
        return percentual;
    }

    public Optional<DoubleFilter> optionalPercentual() {
        return Optional.ofNullable(percentual);
    }

    public DoubleFilter percentual() {
        if (percentual == null) {
            setPercentual(new DoubleFilter());
        }
        return percentual;
    }

    public void setPercentual(DoubleFilter percentual) {
        this.percentual = percentual;
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
        final DescontoPlanoContabilCriteria that = (DescontoPlanoContabilCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(percentual, that.percentual) &&
            Objects.equals(calculoPlanoAssinaturaId, that.calculoPlanoAssinaturaId) &&
            Objects.equals(periodoPagamentoId, that.periodoPagamentoId) &&
            Objects.equals(planoContabilId, that.planoContabilId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, percentual, calculoPlanoAssinaturaId, periodoPagamentoId, planoContabilId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DescontoPlanoContabilCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalPercentual().map(f -> "percentual=" + f + ", ").orElse("") +
            optionalCalculoPlanoAssinaturaId().map(f -> "calculoPlanoAssinaturaId=" + f + ", ").orElse("") +
            optionalPeriodoPagamentoId().map(f -> "periodoPagamentoId=" + f + ", ").orElse("") +
            optionalPlanoContabilId().map(f -> "planoContabilId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
