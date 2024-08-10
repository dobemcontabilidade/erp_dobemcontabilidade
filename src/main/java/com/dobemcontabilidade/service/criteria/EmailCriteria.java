package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Email} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.EmailResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /emails?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmailCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter email;

    private BooleanFilter principal;

    private LongFilter pessoaId;

    private Boolean distinct;

    public EmailCriteria() {}

    public EmailCriteria(EmailCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.email = other.optionalEmail().map(StringFilter::copy).orElse(null);
        this.principal = other.optionalPrincipal().map(BooleanFilter::copy).orElse(null);
        this.pessoaId = other.optionalPessoaId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public EmailCriteria copy() {
        return new EmailCriteria(this);
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

    public StringFilter getEmail() {
        return email;
    }

    public Optional<StringFilter> optionalEmail() {
        return Optional.ofNullable(email);
    }

    public StringFilter email() {
        if (email == null) {
            setEmail(new StringFilter());
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
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
        final EmailCriteria that = (EmailCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(email, that.email) &&
            Objects.equals(principal, that.principal) &&
            Objects.equals(pessoaId, that.pessoaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, principal, pessoaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalEmail().map(f -> "email=" + f + ", ").orElse("") +
            optionalPrincipal().map(f -> "principal=" + f + ", ").orElse("") +
            optionalPessoaId().map(f -> "pessoaId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
