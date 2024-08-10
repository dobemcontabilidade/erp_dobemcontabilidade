package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.DepartamentoFuncionario} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.DepartamentoFuncionarioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /departamento-funcionarios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DepartamentoFuncionarioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cargo;

    private LongFilter funcionarioId;

    private LongFilter departamentoId;

    private Boolean distinct;

    public DepartamentoFuncionarioCriteria() {}

    public DepartamentoFuncionarioCriteria(DepartamentoFuncionarioCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.cargo = other.optionalCargo().map(StringFilter::copy).orElse(null);
        this.funcionarioId = other.optionalFuncionarioId().map(LongFilter::copy).orElse(null);
        this.departamentoId = other.optionalDepartamentoId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public DepartamentoFuncionarioCriteria copy() {
        return new DepartamentoFuncionarioCriteria(this);
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

    public StringFilter getCargo() {
        return cargo;
    }

    public Optional<StringFilter> optionalCargo() {
        return Optional.ofNullable(cargo);
    }

    public StringFilter cargo() {
        if (cargo == null) {
            setCargo(new StringFilter());
        }
        return cargo;
    }

    public void setCargo(StringFilter cargo) {
        this.cargo = cargo;
    }

    public LongFilter getFuncionarioId() {
        return funcionarioId;
    }

    public Optional<LongFilter> optionalFuncionarioId() {
        return Optional.ofNullable(funcionarioId);
    }

    public LongFilter funcionarioId() {
        if (funcionarioId == null) {
            setFuncionarioId(new LongFilter());
        }
        return funcionarioId;
    }

    public void setFuncionarioId(LongFilter funcionarioId) {
        this.funcionarioId = funcionarioId;
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
        final DepartamentoFuncionarioCriteria that = (DepartamentoFuncionarioCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(cargo, that.cargo) &&
            Objects.equals(funcionarioId, that.funcionarioId) &&
            Objects.equals(departamentoId, that.departamentoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cargo, funcionarioId, departamentoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartamentoFuncionarioCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCargo().map(f -> "cargo=" + f + ", ").orElse("") +
            optionalFuncionarioId().map(f -> "funcionarioId=" + f + ", ").orElse("") +
            optionalDepartamentoId().map(f -> "departamentoId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
