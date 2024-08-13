package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.AdicionalEnquadramento} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.AdicionalEnquadramentoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /adicional-enquadramentos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdicionalEnquadramentoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter valor;

    private LongFilter enquadramentoId;

    private LongFilter planoContabilId;

    private Boolean distinct;

    public AdicionalEnquadramentoCriteria() {}

    public AdicionalEnquadramentoCriteria(AdicionalEnquadramentoCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.valor = other.optionalValor().map(DoubleFilter::copy).orElse(null);
        this.enquadramentoId = other.optionalEnquadramentoId().map(LongFilter::copy).orElse(null);
        this.planoContabilId = other.optionalPlanoContabilId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AdicionalEnquadramentoCriteria copy() {
        return new AdicionalEnquadramentoCriteria(this);
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

    public DoubleFilter getValor() {
        return valor;
    }

    public Optional<DoubleFilter> optionalValor() {
        return Optional.ofNullable(valor);
    }

    public DoubleFilter valor() {
        if (valor == null) {
            setValor(new DoubleFilter());
        }
        return valor;
    }

    public void setValor(DoubleFilter valor) {
        this.valor = valor;
    }

    public LongFilter getEnquadramentoId() {
        return enquadramentoId;
    }

    public Optional<LongFilter> optionalEnquadramentoId() {
        return Optional.ofNullable(enquadramentoId);
    }

    public LongFilter enquadramentoId() {
        if (enquadramentoId == null) {
            setEnquadramentoId(new LongFilter());
        }
        return enquadramentoId;
    }

    public void setEnquadramentoId(LongFilter enquadramentoId) {
        this.enquadramentoId = enquadramentoId;
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
        final AdicionalEnquadramentoCriteria that = (AdicionalEnquadramentoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(enquadramentoId, that.enquadramentoId) &&
            Objects.equals(planoContabilId, that.planoContabilId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, valor, enquadramentoId, planoContabilId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdicionalEnquadramentoCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalValor().map(f -> "valor=" + f + ", ").orElse("") +
            optionalEnquadramentoId().map(f -> "enquadramentoId=" + f + ", ").orElse("") +
            optionalPlanoContabilId().map(f -> "planoContabilId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
