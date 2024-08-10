package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.TermoContratoContabil} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.TermoContratoContabilResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /termo-contrato-contabils?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TermoContratoContabilCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter documento;

    private StringFilter titulo;

    private LongFilter planoContabilId;

    private Boolean distinct;

    public TermoContratoContabilCriteria() {}

    public TermoContratoContabilCriteria(TermoContratoContabilCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.documento = other.optionalDocumento().map(StringFilter::copy).orElse(null);
        this.titulo = other.optionalTitulo().map(StringFilter::copy).orElse(null);
        this.planoContabilId = other.optionalPlanoContabilId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public TermoContratoContabilCriteria copy() {
        return new TermoContratoContabilCriteria(this);
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

    public StringFilter getDocumento() {
        return documento;
    }

    public Optional<StringFilter> optionalDocumento() {
        return Optional.ofNullable(documento);
    }

    public StringFilter documento() {
        if (documento == null) {
            setDocumento(new StringFilter());
        }
        return documento;
    }

    public void setDocumento(StringFilter documento) {
        this.documento = documento;
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
        final TermoContratoContabilCriteria that = (TermoContratoContabilCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(documento, that.documento) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(planoContabilId, that.planoContabilId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documento, titulo, planoContabilId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TermoContratoContabilCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalDocumento().map(f -> "documento=" + f + ", ").orElse("") +
            optionalTitulo().map(f -> "titulo=" + f + ", ").orElse("") +
            optionalPlanoContabilId().map(f -> "planoContabilId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
