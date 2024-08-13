package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.PrazoAssinatura} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.PrazoAssinaturaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /prazo-assinaturas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrazoAssinaturaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter prazo;

    private IntegerFilter meses;

    private Boolean distinct;

    public PrazoAssinaturaCriteria() {}

    public PrazoAssinaturaCriteria(PrazoAssinaturaCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.prazo = other.optionalPrazo().map(StringFilter::copy).orElse(null);
        this.meses = other.optionalMeses().map(IntegerFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PrazoAssinaturaCriteria copy() {
        return new PrazoAssinaturaCriteria(this);
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

    public StringFilter getPrazo() {
        return prazo;
    }

    public Optional<StringFilter> optionalPrazo() {
        return Optional.ofNullable(prazo);
    }

    public StringFilter prazo() {
        if (prazo == null) {
            setPrazo(new StringFilter());
        }
        return prazo;
    }

    public void setPrazo(StringFilter prazo) {
        this.prazo = prazo;
    }

    public IntegerFilter getMeses() {
        return meses;
    }

    public Optional<IntegerFilter> optionalMeses() {
        return Optional.ofNullable(meses);
    }

    public IntegerFilter meses() {
        if (meses == null) {
            setMeses(new IntegerFilter());
        }
        return meses;
    }

    public void setMeses(IntegerFilter meses) {
        this.meses = meses;
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
        final PrazoAssinaturaCriteria that = (PrazoAssinaturaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(prazo, that.prazo) &&
            Objects.equals(meses, that.meses) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, prazo, meses, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrazoAssinaturaCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalPrazo().map(f -> "prazo=" + f + ", ").orElse("") +
            optionalMeses().map(f -> "meses=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
