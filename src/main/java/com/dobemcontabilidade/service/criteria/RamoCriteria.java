package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Ramo} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.RamoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ramos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RamoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private LongFilter empresaId;

    private LongFilter adicionalRamoId;

    private Boolean distinct;

    public RamoCriteria() {}

    public RamoCriteria(RamoCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.empresaId = other.optionalEmpresaId().map(LongFilter::copy).orElse(null);
        this.adicionalRamoId = other.optionalAdicionalRamoId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public RamoCriteria copy() {
        return new RamoCriteria(this);
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

    public StringFilter getNome() {
        return nome;
    }

    public Optional<StringFilter> optionalNome() {
        return Optional.ofNullable(nome);
    }

    public StringFilter nome() {
        if (nome == null) {
            setNome(new StringFilter());
        }
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
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

    public LongFilter getAdicionalRamoId() {
        return adicionalRamoId;
    }

    public Optional<LongFilter> optionalAdicionalRamoId() {
        return Optional.ofNullable(adicionalRamoId);
    }

    public LongFilter adicionalRamoId() {
        if (adicionalRamoId == null) {
            setAdicionalRamoId(new LongFilter());
        }
        return adicionalRamoId;
    }

    public void setAdicionalRamoId(LongFilter adicionalRamoId) {
        this.adicionalRamoId = adicionalRamoId;
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
        final RamoCriteria that = (RamoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(adicionalRamoId, that.adicionalRamoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, empresaId, adicionalRamoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RamoCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalEmpresaId().map(f -> "empresaId=" + f + ", ").orElse("") +
            optionalAdicionalRamoId().map(f -> "adicionalRamoId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
