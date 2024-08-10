package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Competencia} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.CompetenciaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /competencias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompetenciaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private IntegerFilter numero;

    private LongFilter tarefaId;

    private Boolean distinct;

    public CompetenciaCriteria() {}

    public CompetenciaCriteria(CompetenciaCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.numero = other.optionalNumero().map(IntegerFilter::copy).orElse(null);
        this.tarefaId = other.optionalTarefaId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public CompetenciaCriteria copy() {
        return new CompetenciaCriteria(this);
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

    public IntegerFilter getNumero() {
        return numero;
    }

    public Optional<IntegerFilter> optionalNumero() {
        return Optional.ofNullable(numero);
    }

    public IntegerFilter numero() {
        if (numero == null) {
            setNumero(new IntegerFilter());
        }
        return numero;
    }

    public void setNumero(IntegerFilter numero) {
        this.numero = numero;
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
        final CompetenciaCriteria that = (CompetenciaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(tarefaId, that.tarefaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, numero, tarefaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompetenciaCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalNumero().map(f -> "numero=" + f + ", ").orElse("") +
            optionalTarefaId().map(f -> "tarefaId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
