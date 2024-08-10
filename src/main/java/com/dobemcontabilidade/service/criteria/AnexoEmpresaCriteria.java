package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.AnexoEmpresa} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.AnexoEmpresaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /anexo-empresas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnexoEmpresaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tipo;

    private LongFilter empresaId;

    private Boolean distinct;

    public AnexoEmpresaCriteria() {}

    public AnexoEmpresaCriteria(AnexoEmpresaCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.tipo = other.optionalTipo().map(StringFilter::copy).orElse(null);
        this.empresaId = other.optionalEmpresaId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AnexoEmpresaCriteria copy() {
        return new AnexoEmpresaCriteria(this);
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

    public StringFilter getTipo() {
        return tipo;
    }

    public Optional<StringFilter> optionalTipo() {
        return Optional.ofNullable(tipo);
    }

    public StringFilter tipo() {
        if (tipo == null) {
            setTipo(new StringFilter());
        }
        return tipo;
    }

    public void setTipo(StringFilter tipo) {
        this.tipo = tipo;
    }

    public LongFilter getEmpresaId() {
        return empresaId;
    }

    public Optional<LongFilter> optionalEmpresaId() {
        return Optional.ofNullable(empresaId);
    }

    public LongFilter empresaId() {
        if (empresaId == null) {
            setEmpresaId(new LongFilter());
        }
        return empresaId;
    }

    public void setEmpresaId(LongFilter empresaId) {
        this.empresaId = empresaId;
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
        final AnexoEmpresaCriteria that = (AnexoEmpresaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipo, empresaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnexoEmpresaCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalTipo().map(f -> "tipo=" + f + ", ").orElse("") +
            optionalEmpresaId().map(f -> "empresaId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
