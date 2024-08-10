package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.TarefaEmpresa} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.TarefaEmpresaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tarefa-empresas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TarefaEmpresaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter dataHora;

    private LongFilter empresaId;

    private LongFilter contadorId;

    private LongFilter tarefaId;

    private Boolean distinct;

    public TarefaEmpresaCriteria() {}

    public TarefaEmpresaCriteria(TarefaEmpresaCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.dataHora = other.optionalDataHora().map(InstantFilter::copy).orElse(null);
        this.empresaId = other.optionalEmpresaId().map(LongFilter::copy).orElse(null);
        this.contadorId = other.optionalContadorId().map(LongFilter::copy).orElse(null);
        this.tarefaId = other.optionalTarefaId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public TarefaEmpresaCriteria copy() {
        return new TarefaEmpresaCriteria(this);
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

    public InstantFilter getDataHora() {
        return dataHora;
    }

    public Optional<InstantFilter> optionalDataHora() {
        return Optional.ofNullable(dataHora);
    }

    public InstantFilter dataHora() {
        if (dataHora == null) {
            setDataHora(new InstantFilter());
        }
        return dataHora;
    }

    public void setDataHora(InstantFilter dataHora) {
        this.dataHora = dataHora;
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

    public LongFilter getContadorId() {
        return contadorId;
    }

    public Optional<LongFilter> optionalContadorId() {
        return Optional.ofNullable(contadorId);
    }

    public LongFilter contadorId() {
        if (contadorId == null) {
            setContadorId(new LongFilter());
        }
        return contadorId;
    }

    public void setContadorId(LongFilter contadorId) {
        this.contadorId = contadorId;
    }

    public LongFilter getTarefaId() {
        return tarefaId;
    }

    public Optional<LongFilter> optionalTarefaId() {
        return Optional.ofNullable(tarefaId);
    }

    public LongFilter tarefaId() {
        if (tarefaId == null) {
            setTarefaId(new LongFilter());
        }
        return tarefaId;
    }

    public void setTarefaId(LongFilter tarefaId) {
        this.tarefaId = tarefaId;
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
        final TarefaEmpresaCriteria that = (TarefaEmpresaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dataHora, that.dataHora) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(contadorId, that.contadorId) &&
            Objects.equals(tarefaId, that.tarefaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataHora, empresaId, contadorId, tarefaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TarefaEmpresaCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalDataHora().map(f -> "dataHora=" + f + ", ").orElse("") +
            optionalEmpresaId().map(f -> "empresaId=" + f + ", ").orElse("") +
            optionalContadorId().map(f -> "contadorId=" + f + ", ").orElse("") +
            optionalTarefaId().map(f -> "tarefaId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
