package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.AdicionalTributacao} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.AdicionalTributacaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /adicional-tributacaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdicionalTributacaoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter valor;

    private LongFilter tributacaoId;

    private LongFilter planoAssinaturaContabilId;

    private Boolean distinct;

    public AdicionalTributacaoCriteria() {}

    public AdicionalTributacaoCriteria(AdicionalTributacaoCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.valor = other.optionalValor().map(DoubleFilter::copy).orElse(null);
        this.tributacaoId = other.optionalTributacaoId().map(LongFilter::copy).orElse(null);
        this.planoAssinaturaContabilId = other.optionalPlanoAssinaturaContabilId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AdicionalTributacaoCriteria copy() {
        return new AdicionalTributacaoCriteria(this);
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

    public LongFilter getTributacaoId() {
        return tributacaoId;
    }

    public Optional<LongFilter> optionalTributacaoId() {
        return Optional.ofNullable(tributacaoId);
    }

    public LongFilter tributacaoId() {
        if (tributacaoId == null) {
            setTributacaoId(new LongFilter());
        }
        return tributacaoId;
    }

    public void setTributacaoId(LongFilter tributacaoId) {
        this.tributacaoId = tributacaoId;
    }

    public LongFilter getPlanoAssinaturaContabilId() {
        return planoAssinaturaContabilId;
    }

    public Optional<LongFilter> optionalPlanoAssinaturaContabilId() {
        return Optional.ofNullable(planoAssinaturaContabilId);
    }

    public LongFilter planoAssinaturaContabilId() {
        if (planoAssinaturaContabilId == null) {
            setPlanoAssinaturaContabilId(new LongFilter());
        }
        return planoAssinaturaContabilId;
    }

    public void setPlanoAssinaturaContabilId(LongFilter planoAssinaturaContabilId) {
        this.planoAssinaturaContabilId = planoAssinaturaContabilId;
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
        final AdicionalTributacaoCriteria that = (AdicionalTributacaoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(tributacaoId, that.tributacaoId) &&
            Objects.equals(planoAssinaturaContabilId, that.planoAssinaturaContabilId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, valor, tributacaoId, planoAssinaturaContabilId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdicionalTributacaoCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalValor().map(f -> "valor=" + f + ", ").orElse("") +
            optionalTributacaoId().map(f -> "tributacaoId=" + f + ", ").orElse("") +
            optionalPlanoAssinaturaContabilId().map(f -> "planoAssinaturaContabilId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
