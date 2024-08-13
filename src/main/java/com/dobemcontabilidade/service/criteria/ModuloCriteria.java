package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Modulo} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.ModuloResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /modulos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModuloCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter descricao;

    private LongFilter funcionalidadeId;

    private LongFilter sistemaId;

    private Boolean distinct;

    public ModuloCriteria() {}

    public ModuloCriteria(ModuloCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.descricao = other.optionalDescricao().map(StringFilter::copy).orElse(null);
        this.funcionalidadeId = other.optionalFuncionalidadeId().map(LongFilter::copy).orElse(null);
        this.sistemaId = other.optionalSistemaId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ModuloCriteria copy() {
        return new ModuloCriteria(this);
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

    public StringFilter getDescricao() {
        return descricao;
    }

    public Optional<StringFilter> optionalDescricao() {
        return Optional.ofNullable(descricao);
    }

    public StringFilter descricao() {
        if (descricao == null) {
            setDescricao(new StringFilter());
        }
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public LongFilter getFuncionalidadeId() {
        return funcionalidadeId;
    }

    public Optional<LongFilter> optionalFuncionalidadeId() {
        return Optional.ofNullable(funcionalidadeId);
    }

    public LongFilter funcionalidadeId() {
        if (funcionalidadeId == null) {
            setFuncionalidadeId(new LongFilter());
        }
        return funcionalidadeId;
    }

    public void setFuncionalidadeId(LongFilter funcionalidadeId) {
        this.funcionalidadeId = funcionalidadeId;
    }

    public LongFilter getSistemaId() {
        return sistemaId;
    }

    public Optional<LongFilter> optionalSistemaId() {
        return Optional.ofNullable(sistemaId);
    }

    public LongFilter sistemaId() {
        if (sistemaId == null) {
            setSistemaId(new LongFilter());
        }
        return sistemaId;
    }

    public void setSistemaId(LongFilter sistemaId) {
        this.sistemaId = sistemaId;
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
        final ModuloCriteria that = (ModuloCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(funcionalidadeId, that.funcionalidadeId) &&
            Objects.equals(sistemaId, that.sistemaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descricao, funcionalidadeId, sistemaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ModuloCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalDescricao().map(f -> "descricao=" + f + ", ").orElse("") +
            optionalFuncionalidadeId().map(f -> "funcionalidadeId=" + f + ", ").orElse("") +
            optionalSistemaId().map(f -> "sistemaId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
