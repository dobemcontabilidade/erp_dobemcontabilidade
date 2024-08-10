package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Pais} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.PaisResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pais?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaisCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter nacionalidade;

    private StringFilter sigla;

    private Boolean distinct;

    public PaisCriteria() {}

    public PaisCriteria(PaisCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.nacionalidade = other.optionalNacionalidade().map(StringFilter::copy).orElse(null);
        this.sigla = other.optionalSigla().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PaisCriteria copy() {
        return new PaisCriteria(this);
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

    public StringFilter getNacionalidade() {
        return nacionalidade;
    }

    public Optional<StringFilter> optionalNacionalidade() {
        return Optional.ofNullable(nacionalidade);
    }

    public StringFilter nacionalidade() {
        if (nacionalidade == null) {
            setNacionalidade(new StringFilter());
        }
        return nacionalidade;
    }

    public void setNacionalidade(StringFilter nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public StringFilter getSigla() {
        return sigla;
    }

    public Optional<StringFilter> optionalSigla() {
        return Optional.ofNullable(sigla);
    }

    public StringFilter sigla() {
        if (sigla == null) {
            setSigla(new StringFilter());
        }
        return sigla;
    }

    public void setSigla(StringFilter sigla) {
        this.sigla = sigla;
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
        final PaisCriteria that = (PaisCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(nacionalidade, that.nacionalidade) &&
            Objects.equals(sigla, that.sigla) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, nacionalidade, sigla, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaisCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalNacionalidade().map(f -> "nacionalidade=" + f + ", ").orElse("") +
            optionalSigla().map(f -> "sigla=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
