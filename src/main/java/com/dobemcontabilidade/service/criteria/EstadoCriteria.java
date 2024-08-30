package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Estado} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.EstadoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /estados?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstadoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter naturalidade;

    private StringFilter sigla;

    private LongFilter cidadeId;

    private LongFilter paisId;

    private Boolean distinct;

    public EstadoCriteria() {}

    public EstadoCriteria(EstadoCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.naturalidade = other.optionalNaturalidade().map(StringFilter::copy).orElse(null);
        this.sigla = other.optionalSigla().map(StringFilter::copy).orElse(null);
        this.cidadeId = other.optionalCidadeId().map(LongFilter::copy).orElse(null);
        this.paisId = other.optionalPaisId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public EstadoCriteria copy() {
        return new EstadoCriteria(this);
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

    public StringFilter getNaturalidade() {
        return naturalidade;
    }

    public Optional<StringFilter> optionalNaturalidade() {
        return Optional.ofNullable(naturalidade);
    }

    public StringFilter naturalidade() {
        if (naturalidade == null) {
            setNaturalidade(new StringFilter());
        }
        return naturalidade;
    }

    public void setNaturalidade(StringFilter naturalidade) {
        this.naturalidade = naturalidade;
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

    public LongFilter getCidadeId() {
        return cidadeId;
    }

    public Optional<LongFilter> optionalCidadeId() {
        return Optional.ofNullable(cidadeId);
    }

    public LongFilter cidadeId() {
        if (cidadeId == null) {
            setCidadeId(new LongFilter());
        }
        return cidadeId;
    }

    public void setCidadeId(LongFilter cidadeId) {
        this.cidadeId = cidadeId;
    }

    public LongFilter getPaisId() {
        return paisId;
    }

    public Optional<LongFilter> optionalPaisId() {
        return Optional.ofNullable(paisId);
    }

    public LongFilter paisId() {
        if (paisId == null) {
            setPaisId(new LongFilter());
        }
        return paisId;
    }

    public void setPaisId(LongFilter paisId) {
        this.paisId = paisId;
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
        final EstadoCriteria that = (EstadoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(naturalidade, that.naturalidade) &&
            Objects.equals(sigla, that.sigla) &&
            Objects.equals(cidadeId, that.cidadeId) &&
            Objects.equals(paisId, that.paisId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, naturalidade, sigla, cidadeId, paisId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstadoCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalNaturalidade().map(f -> "naturalidade=" + f + ", ").orElse("") +
            optionalSigla().map(f -> "sigla=" + f + ", ").orElse("") +
            optionalCidadeId().map(f -> "cidadeId=" + f + ", ").orElse("") +
            optionalPaisId().map(f -> "paisId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
