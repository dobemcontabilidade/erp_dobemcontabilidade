package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.AdicionalRamo} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.AdicionalRamoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /adicional-ramos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdicionalRamoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter valor;

    private LongFilter ramoId;

    private LongFilter planoContabilId;

    private Boolean distinct;

    public AdicionalRamoCriteria() {}

    public AdicionalRamoCriteria(AdicionalRamoCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.valor = other.optionalValor().map(DoubleFilter::copy).orElse(null);
        this.ramoId = other.optionalRamoId().map(LongFilter::copy).orElse(null);
        this.planoContabilId = other.optionalPlanoContabilId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AdicionalRamoCriteria copy() {
        return new AdicionalRamoCriteria(this);
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

    public LongFilter getRamoId() {
        return ramoId;
    }

    public Optional<LongFilter> optionalRamoId() {
        return Optional.ofNullable(ramoId);
    }

    public LongFilter ramoId() {
        if (ramoId == null) {
            setRamoId(new LongFilter());
        }
        return ramoId;
    }

    public void setRamoId(LongFilter ramoId) {
        this.ramoId = ramoId;
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
        final AdicionalRamoCriteria that = (AdicionalRamoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(ramoId, that.ramoId) &&
            Objects.equals(planoContabilId, that.planoContabilId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, valor, ramoId, planoContabilId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdicionalRamoCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalValor().map(f -> "valor=" + f + ", ").orElse("") +
            optionalRamoId().map(f -> "ramoId=" + f + ", ").orElse("") +
            optionalPlanoContabilId().map(f -> "planoContabilId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
