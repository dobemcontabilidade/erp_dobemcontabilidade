package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.DivisaoCnae} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.DivisaoCnaeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /divisao-cnaes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DivisaoCnaeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigo;

    private LongFilter grupoCnaeId;

    private LongFilter secaoId;

    private Boolean distinct;

    public DivisaoCnaeCriteria() {}

    public DivisaoCnaeCriteria(DivisaoCnaeCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.codigo = other.optionalCodigo().map(StringFilter::copy).orElse(null);
        this.grupoCnaeId = other.optionalGrupoCnaeId().map(LongFilter::copy).orElse(null);
        this.secaoId = other.optionalSecaoId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public DivisaoCnaeCriteria copy() {
        return new DivisaoCnaeCriteria(this);
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

    public LongFilter getGrupoCnaeId() {
        return grupoCnaeId;
    }

    public Optional<LongFilter> optionalGrupoCnaeId() {
        return Optional.ofNullable(grupoCnaeId);
    }

    public LongFilter grupoCnaeId() {
        if (grupoCnaeId == null) {
            setGrupoCnaeId(new LongFilter());
        }
        return grupoCnaeId;
    }

    public void setGrupoCnaeId(LongFilter grupoCnaeId) {
        this.grupoCnaeId = grupoCnaeId;
    }

    public LongFilter getSecaoId() {
        return secaoId;
    }

    public Optional<LongFilter> optionalSecaoId() {
        return Optional.ofNullable(secaoId);
    }

    public LongFilter secaoId() {
        if (secaoId == null) {
            setSecaoId(new LongFilter());
        }
        return secaoId;
    }

    public void setSecaoId(LongFilter secaoId) {
        this.secaoId = secaoId;
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
        final DivisaoCnaeCriteria that = (DivisaoCnaeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codigo, that.codigo) &&
            Objects.equals(grupoCnaeId, that.grupoCnaeId) &&
            Objects.equals(secaoId, that.secaoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, grupoCnaeId, secaoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DivisaoCnaeCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCodigo().map(f -> "codigo=" + f + ", ").orElse("") +
            optionalGrupoCnaeId().map(f -> "grupoCnaeId=" + f + ", ").orElse("") +
            optionalSecaoId().map(f -> "secaoId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
