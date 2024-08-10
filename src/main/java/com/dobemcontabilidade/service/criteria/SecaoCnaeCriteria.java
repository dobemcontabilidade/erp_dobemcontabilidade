package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.SecaoCnae} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.SecaoCnaeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /secao-cnaes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SecaoCnaeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigo;

    private LongFilter divisaoCnaeId;

    private Boolean distinct;

    public SecaoCnaeCriteria() {}

    public SecaoCnaeCriteria(SecaoCnaeCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.codigo = other.optionalCodigo().map(StringFilter::copy).orElse(null);
        this.divisaoCnaeId = other.optionalDivisaoCnaeId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SecaoCnaeCriteria copy() {
        return new SecaoCnaeCriteria(this);
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

    public StringFilter getCodigo() {
        return codigo;
    }

    public Optional<StringFilter> optionalCodigo() {
        return Optional.ofNullable(codigo);
    }

    public StringFilter codigo() {
        if (codigo == null) {
            setCodigo(new StringFilter());
        }
        return codigo;
    }

    public void setCodigo(StringFilter codigo) {
        this.codigo = codigo;
    }

    public LongFilter getDivisaoCnaeId() {
        return divisaoCnaeId;
    }

    public Optional<LongFilter> optionalDivisaoCnaeId() {
        return Optional.ofNullable(divisaoCnaeId);
    }

    public LongFilter divisaoCnaeId() {
        if (divisaoCnaeId == null) {
            setDivisaoCnaeId(new LongFilter());
        }
        return divisaoCnaeId;
    }

    public void setDivisaoCnaeId(LongFilter divisaoCnaeId) {
        this.divisaoCnaeId = divisaoCnaeId;
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
        final SecaoCnaeCriteria that = (SecaoCnaeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codigo, that.codigo) &&
            Objects.equals(divisaoCnaeId, that.divisaoCnaeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, divisaoCnaeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SecaoCnaeCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCodigo().map(f -> "codigo=" + f + ", ").orElse("") +
            optionalDivisaoCnaeId().map(f -> "divisaoCnaeId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
