package com.dobemcontabilidade.service.criteria;

import com.dobemcontabilidade.domain.enumeration.TipoTelefoneEnum;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Telefone} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.TelefoneResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /telefones?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TelefoneCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TipoTelefoneEnum
     */
    public static class TipoTelefoneEnumFilter extends Filter<TipoTelefoneEnum> {

        public TipoTelefoneEnumFilter() {}

        public TipoTelefoneEnumFilter(TipoTelefoneEnumFilter filter) {
            super(filter);
        }

        @Override
        public TipoTelefoneEnumFilter copy() {
            return new TipoTelefoneEnumFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigoArea;

    private StringFilter telefone;

    private BooleanFilter principal;

    private TipoTelefoneEnumFilter tipoTelefone;

    private LongFilter pessoaId;

    private Boolean distinct;

    public TelefoneCriteria() {}

    public TelefoneCriteria(TelefoneCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.codigoArea = other.optionalCodigoArea().map(StringFilter::copy).orElse(null);
        this.telefone = other.optionalTelefone().map(StringFilter::copy).orElse(null);
        this.principal = other.optionalPrincipal().map(BooleanFilter::copy).orElse(null);
        this.tipoTelefone = other.optionalTipoTelefone().map(TipoTelefoneEnumFilter::copy).orElse(null);
        this.pessoaId = other.optionalPessoaId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public TelefoneCriteria copy() {
        return new TelefoneCriteria(this);
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

    public StringFilter getCodigoArea() {
        return codigoArea;
    }

    public Optional<StringFilter> optionalCodigoArea() {
        return Optional.ofNullable(codigoArea);
    }

    public StringFilter codigoArea() {
        if (codigoArea == null) {
            setCodigoArea(new StringFilter());
        }
        return codigoArea;
    }

    public void setCodigoArea(StringFilter codigoArea) {
        this.codigoArea = codigoArea;
    }

    public StringFilter getTelefone() {
        return telefone;
    }

    public Optional<StringFilter> optionalTelefone() {
        return Optional.ofNullable(telefone);
    }

    public StringFilter telefone() {
        if (telefone == null) {
            setTelefone(new StringFilter());
        }
        return telefone;
    }

    public void setTelefone(StringFilter telefone) {
        this.telefone = telefone;
    }

    public BooleanFilter getPrincipal() {
        return principal;
    }

    public Optional<BooleanFilter> optionalPrincipal() {
        return Optional.ofNullable(principal);
    }

    public BooleanFilter principal() {
        if (principal == null) {
            setPrincipal(new BooleanFilter());
        }
        return principal;
    }

    public void setPrincipal(BooleanFilter principal) {
        this.principal = principal;
    }

    public TipoTelefoneEnumFilter getTipoTelefone() {
        return tipoTelefone;
    }

    public Optional<TipoTelefoneEnumFilter> optionalTipoTelefone() {
        return Optional.ofNullable(tipoTelefone);
    }

    public TipoTelefoneEnumFilter tipoTelefone() {
        if (tipoTelefone == null) {
            setTipoTelefone(new TipoTelefoneEnumFilter());
        }
        return tipoTelefone;
    }

    public void setTipoTelefone(TipoTelefoneEnumFilter tipoTelefone) {
        this.tipoTelefone = tipoTelefone;
    }

    public LongFilter getPessoaId() {
        return pessoaId;
    }

    public Optional<LongFilter> optionalPessoaId() {
        return Optional.ofNullable(pessoaId);
    }

    public LongFilter pessoaId() {
        if (pessoaId == null) {
            setPessoaId(new LongFilter());
        }
        return pessoaId;
    }

    public void setPessoaId(LongFilter pessoaId) {
        this.pessoaId = pessoaId;
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
        final TelefoneCriteria that = (TelefoneCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codigoArea, that.codigoArea) &&
            Objects.equals(telefone, that.telefone) &&
            Objects.equals(principal, that.principal) &&
            Objects.equals(tipoTelefone, that.tipoTelefone) &&
            Objects.equals(pessoaId, that.pessoaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigoArea, telefone, principal, tipoTelefone, pessoaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TelefoneCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCodigoArea().map(f -> "codigoArea=" + f + ", ").orElse("") +
            optionalTelefone().map(f -> "telefone=" + f + ", ").orElse("") +
            optionalPrincipal().map(f -> "principal=" + f + ", ").orElse("") +
            optionalTipoTelefone().map(f -> "tipoTelefone=" + f + ", ").orElse("") +
            optionalPessoaId().map(f -> "pessoaId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
