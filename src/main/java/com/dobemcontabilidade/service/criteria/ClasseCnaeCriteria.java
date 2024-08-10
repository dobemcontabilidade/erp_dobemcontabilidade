package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.ClasseCnae} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.ClasseCnaeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /classe-cnaes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClasseCnaeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigo;

    private LongFilter subclasseCnaeId;

    private LongFilter grupoId;

    private Boolean distinct;

    public ClasseCnaeCriteria() {}

    public ClasseCnaeCriteria(ClasseCnaeCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.codigo = other.optionalCodigo().map(StringFilter::copy).orElse(null);
        this.subclasseCnaeId = other.optionalSubclasseCnaeId().map(LongFilter::copy).orElse(null);
        this.grupoId = other.optionalGrupoId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ClasseCnaeCriteria copy() {
        return new ClasseCnaeCriteria(this);
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

    public LongFilter getSubclasseCnaeId() {
        return subclasseCnaeId;
    }

    public Optional<LongFilter> optionalSubclasseCnaeId() {
        return Optional.ofNullable(subclasseCnaeId);
    }

    public LongFilter subclasseCnaeId() {
        if (subclasseCnaeId == null) {
            setSubclasseCnaeId(new LongFilter());
        }
        return subclasseCnaeId;
    }

    public void setSubclasseCnaeId(LongFilter subclasseCnaeId) {
        this.subclasseCnaeId = subclasseCnaeId;
    }

    public LongFilter getGrupoId() {
        return grupoId;
    }

    public Optional<LongFilter> optionalGrupoId() {
        return Optional.ofNullable(grupoId);
    }

    public LongFilter grupoId() {
        if (grupoId == null) {
            setGrupoId(new LongFilter());
        }
        return grupoId;
    }

    public void setGrupoId(LongFilter grupoId) {
        this.grupoId = grupoId;
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
        final ClasseCnaeCriteria that = (ClasseCnaeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codigo, that.codigo) &&
            Objects.equals(subclasseCnaeId, that.subclasseCnaeId) &&
            Objects.equals(grupoId, that.grupoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, subclasseCnaeId, grupoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClasseCnaeCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCodigo().map(f -> "codigo=" + f + ", ").orElse("") +
            optionalSubclasseCnaeId().map(f -> "subclasseCnaeId=" + f + ", ").orElse("") +
            optionalGrupoId().map(f -> "grupoId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
