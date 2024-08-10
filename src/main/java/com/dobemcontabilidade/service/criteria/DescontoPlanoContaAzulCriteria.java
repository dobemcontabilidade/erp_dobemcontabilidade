package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.DescontoPlanoContaAzul} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.DescontoPlanoContaAzulResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /desconto-plano-conta-azuls?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DescontoPlanoContaAzulCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter percentual;

    private LongFilter calculoPlanoAssinaturaId;

    private LongFilter planoContaAzulId;

    private LongFilter periodoPagamentoId;

    private Boolean distinct;

    public DescontoPlanoContaAzulCriteria() {}

    public DescontoPlanoContaAzulCriteria(DescontoPlanoContaAzulCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.percentual = other.optionalPercentual().map(DoubleFilter::copy).orElse(null);
        this.calculoPlanoAssinaturaId = other.optionalCalculoPlanoAssinaturaId().map(LongFilter::copy).orElse(null);
        this.planoContaAzulId = other.optionalPlanoContaAzulId().map(LongFilter::copy).orElse(null);
        this.periodoPagamentoId = other.optionalPeriodoPagamentoId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public DescontoPlanoContaAzulCriteria copy() {
        return new DescontoPlanoContaAzulCriteria(this);
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
        final DescontoPlanoContaAzulCriteria that = (DescontoPlanoContaAzulCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(percentual, that.percentual) &&
            Objects.equals(calculoPlanoAssinaturaId, that.calculoPlanoAssinaturaId) &&
            Objects.equals(planoContaAzulId, that.planoContaAzulId) &&
            Objects.equals(periodoPagamentoId, that.periodoPagamentoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, percentual, calculoPlanoAssinaturaId, planoContaAzulId, periodoPagamentoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DescontoPlanoContaAzulCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalPercentual().map(f -> "percentual=" + f + ", ").orElse("") +
            optionalCalculoPlanoAssinaturaId().map(f -> "calculoPlanoAssinaturaId=" + f + ", ").orElse("") +
            optionalPlanoContaAzulId().map(f -> "planoContaAzulId=" + f + ", ").orElse("") +
            optionalPeriodoPagamentoId().map(f -> "periodoPagamentoId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
