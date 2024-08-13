package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.PeriodoPagamento} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.PeriodoPagamentoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /periodo-pagamentos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PeriodoPagamentoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter periodo;

    private IntegerFilter numeroDias;

    private StringFilter idPlanGnet;

    private LongFilter calculoPlanoAssinaturaId;

    private LongFilter assinaturaEmpresaId;

    private LongFilter descontoPlanoContaAzulId;

    private LongFilter descontoPlanoContabilId;

    private Boolean distinct;

    public PeriodoPagamentoCriteria() {}

    public PeriodoPagamentoCriteria(PeriodoPagamentoCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.periodo = other.optionalPeriodo().map(StringFilter::copy).orElse(null);
        this.numeroDias = other.optionalNumeroDias().map(IntegerFilter::copy).orElse(null);
        this.idPlanGnet = other.optionalIdPlanGnet().map(StringFilter::copy).orElse(null);
        this.calculoPlanoAssinaturaId = other.optionalCalculoPlanoAssinaturaId().map(LongFilter::copy).orElse(null);
        this.assinaturaEmpresaId = other.optionalAssinaturaEmpresaId().map(LongFilter::copy).orElse(null);
        this.descontoPlanoContaAzulId = other.optionalDescontoPlanoContaAzulId().map(LongFilter::copy).orElse(null);
        this.descontoPlanoContabilId = other.optionalDescontoPlanoContabilId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PeriodoPagamentoCriteria copy() {
        return new PeriodoPagamentoCriteria(this);
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

    public StringFilter getPeriodo() {
        return periodo;
    }

    public Optional<StringFilter> optionalPeriodo() {
        return Optional.ofNullable(periodo);
    }

    public StringFilter periodo() {
        if (periodo == null) {
            setPeriodo(new StringFilter());
        }
        return periodo;
    }

    public void setPeriodo(StringFilter periodo) {
        this.periodo = periodo;
    }

    public IntegerFilter getNumeroDias() {
        return numeroDias;
    }

    public Optional<IntegerFilter> optionalNumeroDias() {
        return Optional.ofNullable(numeroDias);
    }

    public IntegerFilter numeroDias() {
        if (numeroDias == null) {
            setNumeroDias(new IntegerFilter());
        }
        return numeroDias;
    }

    public void setNumeroDias(IntegerFilter numeroDias) {
        this.numeroDias = numeroDias;
    }

    public StringFilter getIdPlanGnet() {
        return idPlanGnet;
    }

    public Optional<StringFilter> optionalIdPlanGnet() {
        return Optional.ofNullable(idPlanGnet);
    }

    public StringFilter idPlanGnet() {
        if (idPlanGnet == null) {
            setIdPlanGnet(new StringFilter());
        }
        return idPlanGnet;
    }

    public void setIdPlanGnet(StringFilter idPlanGnet) {
        this.idPlanGnet = idPlanGnet;
    }

    public LongFilter getCalculoPlanoAssinaturaId() {
        return calculoPlanoAssinaturaId;
    }

    public Optional<LongFilter> optionalCalculoPlanoAssinaturaId() {
        return Optional.ofNullable(calculoPlanoAssinaturaId);
    }

    public LongFilter calculoPlanoAssinaturaId() {
        if (calculoPlanoAssinaturaId == null) {
            setCalculoPlanoAssinaturaId(new LongFilter());
        }
        return calculoPlanoAssinaturaId;
    }

    public void setCalculoPlanoAssinaturaId(LongFilter calculoPlanoAssinaturaId) {
        this.calculoPlanoAssinaturaId = calculoPlanoAssinaturaId;
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

    public LongFilter getDescontoPlanoContaAzulId() {
        return descontoPlanoContaAzulId;
    }

    public Optional<LongFilter> optionalDescontoPlanoContaAzulId() {
        return Optional.ofNullable(descontoPlanoContaAzulId);
    }

    public LongFilter descontoPlanoContaAzulId() {
        if (descontoPlanoContaAzulId == null) {
            setDescontoPlanoContaAzulId(new LongFilter());
        }
        return descontoPlanoContaAzulId;
    }

    public void setDescontoPlanoContaAzulId(LongFilter descontoPlanoContaAzulId) {
        this.descontoPlanoContaAzulId = descontoPlanoContaAzulId;
    }

    public LongFilter getDescontoPlanoContabilId() {
        return descontoPlanoContabilId;
    }

    public Optional<LongFilter> optionalDescontoPlanoContabilId() {
        return Optional.ofNullable(descontoPlanoContabilId);
    }

    public LongFilter descontoPlanoContabilId() {
        if (descontoPlanoContabilId == null) {
            setDescontoPlanoContabilId(new LongFilter());
        }
        return descontoPlanoContabilId;
    }

    public void setDescontoPlanoContabilId(LongFilter descontoPlanoContabilId) {
        this.descontoPlanoContabilId = descontoPlanoContabilId;
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
        final PeriodoPagamentoCriteria that = (PeriodoPagamentoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(periodo, that.periodo) &&
            Objects.equals(numeroDias, that.numeroDias) &&
            Objects.equals(idPlanGnet, that.idPlanGnet) &&
            Objects.equals(calculoPlanoAssinaturaId, that.calculoPlanoAssinaturaId) &&
            Objects.equals(assinaturaEmpresaId, that.assinaturaEmpresaId) &&
            Objects.equals(descontoPlanoContaAzulId, that.descontoPlanoContaAzulId) &&
            Objects.equals(descontoPlanoContabilId, that.descontoPlanoContabilId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            periodo,
            numeroDias,
            idPlanGnet,
            calculoPlanoAssinaturaId,
            assinaturaEmpresaId,
            descontoPlanoContaAzulId,
            descontoPlanoContabilId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PeriodoPagamentoCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalPeriodo().map(f -> "periodo=" + f + ", ").orElse("") +
            optionalNumeroDias().map(f -> "numeroDias=" + f + ", ").orElse("") +
            optionalIdPlanGnet().map(f -> "idPlanGnet=" + f + ", ").orElse("") +
            optionalCalculoPlanoAssinaturaId().map(f -> "calculoPlanoAssinaturaId=" + f + ", ").orElse("") +
            optionalAssinaturaEmpresaId().map(f -> "assinaturaEmpresaId=" + f + ", ").orElse("") +
            optionalDescontoPlanoContaAzulId().map(f -> "descontoPlanoContaAzulId=" + f + ", ").orElse("") +
            optionalDescontoPlanoContabilId().map(f -> "descontoPlanoContabilId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
