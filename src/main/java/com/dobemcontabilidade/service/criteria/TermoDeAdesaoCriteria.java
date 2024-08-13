package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.TermoDeAdesao} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.TermoDeAdesaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /termo-de-adesaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TermoDeAdesaoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter titulo;

    private LongFilter termoAdesaoContadorId;

    private Boolean distinct;

    public TermoDeAdesaoCriteria() {}

    public TermoDeAdesaoCriteria(TermoDeAdesaoCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.titulo = other.optionalTitulo().map(StringFilter::copy).orElse(null);
        this.termoAdesaoContadorId = other.optionalTermoAdesaoContadorId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public TermoDeAdesaoCriteria copy() {
        return new TermoDeAdesaoCriteria(this);
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

    public StringFilter getTitulo() {
        return titulo;
    }

    public Optional<StringFilter> optionalTitulo() {
        return Optional.ofNullable(titulo);
    }

    public StringFilter titulo() {
        if (titulo == null) {
            setTitulo(new StringFilter());
        }
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
    }

    public LongFilter getTermoAdesaoContadorId() {
        return termoAdesaoContadorId;
    }

    public Optional<LongFilter> optionalTermoAdesaoContadorId() {
        return Optional.ofNullable(termoAdesaoContadorId);
    }

    public LongFilter termoAdesaoContadorId() {
        if (termoAdesaoContadorId == null) {
            setTermoAdesaoContadorId(new LongFilter());
        }
        return termoAdesaoContadorId;
    }

    public void setTermoAdesaoContadorId(LongFilter termoAdesaoContadorId) {
        this.termoAdesaoContadorId = termoAdesaoContadorId;
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
        final TermoDeAdesaoCriteria that = (TermoDeAdesaoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(termoAdesaoContadorId, that.termoAdesaoContadorId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, termoAdesaoContadorId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TermoDeAdesaoCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalTitulo().map(f -> "titulo=" + f + ", ").orElse("") +
            optionalTermoAdesaoContadorId().map(f -> "termoAdesaoContadorId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
