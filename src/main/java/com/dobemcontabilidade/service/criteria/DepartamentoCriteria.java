package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Departamento} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.DepartamentoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /departamentos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DepartamentoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private LongFilter departamentoEmpresaId;

    private LongFilter perfilContadorDepartamentoId;

    private LongFilter departamentoContadorId;

    private LongFilter departamentoFuncionarioId;

    private Boolean distinct;

    public DepartamentoCriteria() {}

    public DepartamentoCriteria(DepartamentoCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.departamentoEmpresaId = other.optionalDepartamentoEmpresaId().map(LongFilter::copy).orElse(null);
        this.perfilContadorDepartamentoId = other.optionalPerfilContadorDepartamentoId().map(LongFilter::copy).orElse(null);
        this.departamentoContadorId = other.optionalDepartamentoContadorId().map(LongFilter::copy).orElse(null);
        this.departamentoFuncionarioId = other.optionalDepartamentoFuncionarioId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public DepartamentoCriteria copy() {
        return new DepartamentoCriteria(this);
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

    public LongFilter getDepartamentoEmpresaId() {
        return departamentoEmpresaId;
    }

    public Optional<LongFilter> optionalDepartamentoEmpresaId() {
        return Optional.ofNullable(departamentoEmpresaId);
    }

    public LongFilter departamentoEmpresaId() {
        if (departamentoEmpresaId == null) {
            setDepartamentoEmpresaId(new LongFilter());
        }
        return departamentoEmpresaId;
    }

    public void setDepartamentoEmpresaId(LongFilter departamentoEmpresaId) {
        this.departamentoEmpresaId = departamentoEmpresaId;
    }

    public LongFilter getPerfilContadorDepartamentoId() {
        return perfilContadorDepartamentoId;
    }

    public Optional<LongFilter> optionalPerfilContadorDepartamentoId() {
        return Optional.ofNullable(perfilContadorDepartamentoId);
    }

    public LongFilter perfilContadorDepartamentoId() {
        if (perfilContadorDepartamentoId == null) {
            setPerfilContadorDepartamentoId(new LongFilter());
        }
        return perfilContadorDepartamentoId;
    }

    public void setPerfilContadorDepartamentoId(LongFilter perfilContadorDepartamentoId) {
        this.perfilContadorDepartamentoId = perfilContadorDepartamentoId;
    }

    public LongFilter getDepartamentoContadorId() {
        return departamentoContadorId;
    }

    public Optional<LongFilter> optionalDepartamentoContadorId() {
        return Optional.ofNullable(departamentoContadorId);
    }

    public LongFilter departamentoContadorId() {
        if (departamentoContadorId == null) {
            setDepartamentoContadorId(new LongFilter());
        }
        return departamentoContadorId;
    }

    public void setDepartamentoContadorId(LongFilter departamentoContadorId) {
        this.departamentoContadorId = departamentoContadorId;
    }

    public LongFilter getDepartamentoFuncionarioId() {
        return departamentoFuncionarioId;
    }

    public Optional<LongFilter> optionalDepartamentoFuncionarioId() {
        return Optional.ofNullable(departamentoFuncionarioId);
    }

    public LongFilter departamentoFuncionarioId() {
        if (departamentoFuncionarioId == null) {
            setDepartamentoFuncionarioId(new LongFilter());
        }
        return departamentoFuncionarioId;
    }

    public void setDepartamentoFuncionarioId(LongFilter departamentoFuncionarioId) {
        this.departamentoFuncionarioId = departamentoFuncionarioId;
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
        final DepartamentoCriteria that = (DepartamentoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(departamentoEmpresaId, that.departamentoEmpresaId) &&
            Objects.equals(perfilContadorDepartamentoId, that.perfilContadorDepartamentoId) &&
            Objects.equals(departamentoContadorId, that.departamentoContadorId) &&
            Objects.equals(departamentoFuncionarioId, that.departamentoFuncionarioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nome,
            departamentoEmpresaId,
            perfilContadorDepartamentoId,
            departamentoContadorId,
            departamentoFuncionarioId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartamentoCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalDepartamentoEmpresaId().map(f -> "departamentoEmpresaId=" + f + ", ").orElse("") +
            optionalPerfilContadorDepartamentoId().map(f -> "perfilContadorDepartamentoId=" + f + ", ").orElse("") +
            optionalDepartamentoContadorId().map(f -> "departamentoContadorId=" + f + ", ").orElse("") +
            optionalDepartamentoFuncionarioId().map(f -> "departamentoFuncionarioId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
