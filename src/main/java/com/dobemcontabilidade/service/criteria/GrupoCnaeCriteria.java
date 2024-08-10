package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.GrupoCnae} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.GrupoCnaeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /grupo-cnaes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GrupoCnaeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigo;

    private LongFilter classeCnaeId;

    private LongFilter divisaoId;

    private Boolean distinct;

    public GrupoCnaeCriteria() {}

    public GrupoCnaeCriteria(GrupoCnaeCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.codigo = other.optionalCodigo().map(StringFilter::copy).orElse(null);
        this.classeCnaeId = other.optionalClasseCnaeId().map(LongFilter::copy).orElse(null);
        this.divisaoId = other.optionalDivisaoId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public GrupoCnaeCriteria copy() {
        return new GrupoCnaeCriteria(this);
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

    public LongFilter getClasseCnaeId() {
        return classeCnaeId;
    }

    public Optional<LongFilter> optionalClasseCnaeId() {
        return Optional.ofNullable(classeCnaeId);
    }

    public LongFilter classeCnaeId() {
        if (classeCnaeId == null) {
            setClasseCnaeId(new LongFilter());
        }
        return classeCnaeId;
    }

    public void setClasseCnaeId(LongFilter classeCnaeId) {
        this.classeCnaeId = classeCnaeId;
    }

    public LongFilter getDivisaoId() {
        return divisaoId;
    }

    public Optional<LongFilter> optionalDivisaoId() {
        return Optional.ofNullable(divisaoId);
    }

    public LongFilter divisaoId() {
        if (divisaoId == null) {
            setDivisaoId(new LongFilter());
        }
        return divisaoId;
    }

    public void setDivisaoId(LongFilter divisaoId) {
        this.divisaoId = divisaoId;
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
        final GrupoCnaeCriteria that = (GrupoCnaeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codigo, that.codigo) &&
            Objects.equals(classeCnaeId, that.classeCnaeId) &&
            Objects.equals(divisaoId, that.divisaoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, classeCnaeId, divisaoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GrupoCnaeCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCodigo().map(f -> "codigo=" + f + ", ").orElse("") +
            optionalClasseCnaeId().map(f -> "classeCnaeId=" + f + ", ").orElse("") +
            optionalDivisaoId().map(f -> "divisaoId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
