package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.ObservacaoCnae} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.ObservacaoCnaeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /observacao-cnaes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ObservacaoCnaeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter subclasseId;

    private Boolean distinct;

    public ObservacaoCnaeCriteria() {}

    public ObservacaoCnaeCriteria(ObservacaoCnaeCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.subclasseId = other.optionalSubclasseId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ObservacaoCnaeCriteria copy() {
        return new ObservacaoCnaeCriteria(this);
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

    public LongFilter getSubclasseId() {
        return subclasseId;
    }

    public Optional<LongFilter> optionalSubclasseId() {
        return Optional.ofNullable(subclasseId);
    }

    public LongFilter subclasseId() {
        if (subclasseId == null) {
            setSubclasseId(new LongFilter());
        }
        return subclasseId;
    }

    public void setSubclasseId(LongFilter subclasseId) {
        this.subclasseId = subclasseId;
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
        final ObservacaoCnaeCriteria that = (ObservacaoCnaeCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(subclasseId, that.subclasseId) && Objects.equals(distinct, that.distinct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subclasseId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObservacaoCnaeCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalSubclasseId().map(f -> "subclasseId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
