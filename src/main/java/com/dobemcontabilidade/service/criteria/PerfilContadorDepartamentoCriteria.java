package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.PerfilContadorDepartamento} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.PerfilContadorDepartamentoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /perfil-contador-departamentos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerfilContadorDepartamentoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter quantidadeEmpresas;

    private DoubleFilter percentualExperiencia;

    private LongFilter departamentoId;

    private LongFilter perfilContadorId;

    private Boolean distinct;

    public PerfilContadorDepartamentoCriteria() {}

    public PerfilContadorDepartamentoCriteria(PerfilContadorDepartamentoCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.quantidadeEmpresas = other.optionalQuantidadeEmpresas().map(IntegerFilter::copy).orElse(null);
        this.percentualExperiencia = other.optionalPercentualExperiencia().map(DoubleFilter::copy).orElse(null);
        this.departamentoId = other.optionalDepartamentoId().map(LongFilter::copy).orElse(null);
        this.perfilContadorId = other.optionalPerfilContadorId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PerfilContadorDepartamentoCriteria copy() {
        return new PerfilContadorDepartamentoCriteria(this);
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

    public IntegerFilter getQuantidadeEmpresas() {
        return quantidadeEmpresas;
    }

    public Optional<IntegerFilter> optionalQuantidadeEmpresas() {
        return Optional.ofNullable(quantidadeEmpresas);
    }

    public IntegerFilter quantidadeEmpresas() {
        if (quantidadeEmpresas == null) {
            setQuantidadeEmpresas(new IntegerFilter());
        }
        return quantidadeEmpresas;
    }

    public void setQuantidadeEmpresas(IntegerFilter quantidadeEmpresas) {
        this.quantidadeEmpresas = quantidadeEmpresas;
    }

    public DoubleFilter getPercentualExperiencia() {
        return percentualExperiencia;
    }

    public Optional<DoubleFilter> optionalPercentualExperiencia() {
        return Optional.ofNullable(percentualExperiencia);
    }

    public DoubleFilter percentualExperiencia() {
        if (percentualExperiencia == null) {
            setPercentualExperiencia(new DoubleFilter());
        }
        return percentualExperiencia;
    }

    public void setPercentualExperiencia(DoubleFilter percentualExperiencia) {
        this.percentualExperiencia = percentualExperiencia;
    }

    public LongFilter getDepartamentoId() {
        return departamentoId;
    }

    public Optional<LongFilter> optionalDepartamentoId() {
        return Optional.ofNullable(departamentoId);
    }

    public LongFilter departamentoId() {
        if (departamentoId == null) {
            setDepartamentoId(new LongFilter());
        }
        return departamentoId;
    }

    public void setDepartamentoId(LongFilter departamentoId) {
        this.departamentoId = departamentoId;
    }

    public LongFilter getPerfilContadorId() {
        return perfilContadorId;
    }

    public Optional<LongFilter> optionalPerfilContadorId() {
        return Optional.ofNullable(perfilContadorId);
    }

    public LongFilter perfilContadorId() {
        if (perfilContadorId == null) {
            setPerfilContadorId(new LongFilter());
        }
        return perfilContadorId;
    }

    public void setPerfilContadorId(LongFilter perfilContadorId) {
        this.perfilContadorId = perfilContadorId;
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
        final PerfilContadorDepartamentoCriteria that = (PerfilContadorDepartamentoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(quantidadeEmpresas, that.quantidadeEmpresas) &&
            Objects.equals(percentualExperiencia, that.percentualExperiencia) &&
            Objects.equals(departamentoId, that.departamentoId) &&
            Objects.equals(perfilContadorId, that.perfilContadorId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantidadeEmpresas, percentualExperiencia, departamentoId, perfilContadorId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerfilContadorDepartamentoCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalQuantidadeEmpresas().map(f -> "quantidadeEmpresas=" + f + ", ").orElse("") +
            optionalPercentualExperiencia().map(f -> "percentualExperiencia=" + f + ", ").orElse("") +
            optionalDepartamentoId().map(f -> "departamentoId=" + f + ", ").orElse("") +
            optionalPerfilContadorId().map(f -> "perfilContadorId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
