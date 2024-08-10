package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.FormaDePagamento} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.FormaDePagamentoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /forma-de-pagamentos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FormaDePagamentoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter forma;

    private BooleanFilter disponivel;

    private LongFilter assinaturaEmpresaId;

    private Boolean distinct;

    public FormaDePagamentoCriteria() {}

    public FormaDePagamentoCriteria(FormaDePagamentoCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.forma = other.optionalForma().map(StringFilter::copy).orElse(null);
        this.disponivel = other.optionalDisponivel().map(BooleanFilter::copy).orElse(null);
        this.assinaturaEmpresaId = other.optionalAssinaturaEmpresaId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public FormaDePagamentoCriteria copy() {
        return new FormaDePagamentoCriteria(this);
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

    public StringFilter getForma() {
        return forma;
    }

    public Optional<StringFilter> optionalForma() {
        return Optional.ofNullable(forma);
    }

    public StringFilter forma() {
        if (forma == null) {
            setForma(new StringFilter());
        }
        return forma;
    }

    public void setForma(StringFilter forma) {
        this.forma = forma;
    }

    public BooleanFilter getDisponivel() {
        return disponivel;
    }

    public Optional<BooleanFilter> optionalDisponivel() {
        return Optional.ofNullable(disponivel);
    }

    public BooleanFilter disponivel() {
        if (disponivel == null) {
            setDisponivel(new BooleanFilter());
        }
        return disponivel;
    }

    public void setDisponivel(BooleanFilter disponivel) {
        this.disponivel = disponivel;
    }

    public LongFilter getAssinaturaEmpresaId() {
        return assinaturaEmpresaId;
    }

    public Optional<LongFilter> optionalAssinaturaEmpresaId() {
        return Optional.ofNullable(assinaturaEmpresaId);
    }

    public LongFilter assinaturaEmpresaId() {
        if (assinaturaEmpresaId == null) {
            setAssinaturaEmpresaId(new LongFilter());
        }
        return assinaturaEmpresaId;
    }

    public void setAssinaturaEmpresaId(LongFilter assinaturaEmpresaId) {
        this.assinaturaEmpresaId = assinaturaEmpresaId;
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
        final FormaDePagamentoCriteria that = (FormaDePagamentoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(forma, that.forma) &&
            Objects.equals(disponivel, that.disponivel) &&
            Objects.equals(assinaturaEmpresaId, that.assinaturaEmpresaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, forma, disponivel, assinaturaEmpresaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormaDePagamentoCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalForma().map(f -> "forma=" + f + ", ").orElse("") +
            optionalDisponivel().map(f -> "disponivel=" + f + ", ").orElse("") +
            optionalAssinaturaEmpresaId().map(f -> "assinaturaEmpresaId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
