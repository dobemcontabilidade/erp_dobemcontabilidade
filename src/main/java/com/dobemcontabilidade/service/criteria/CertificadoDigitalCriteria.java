package com.dobemcontabilidade.service.criteria;

import com.dobemcontabilidade.domain.enumeration.TipoCertificadoEnum;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.CertificadoDigital} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.CertificadoDigitalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /certificado-digitals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CertificadoDigitalCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TipoCertificadoEnum
     */
    public static class TipoCertificadoEnumFilter extends Filter<TipoCertificadoEnum> {

        public TipoCertificadoEnumFilter() {}

        public TipoCertificadoEnumFilter(TipoCertificadoEnumFilter filter) {
            super(filter);
        }

        @Override
        public TipoCertificadoEnumFilter copy() {
            return new TipoCertificadoEnumFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter dataContratacao;

    private IntegerFilter validade;

    private TipoCertificadoEnumFilter tipoCertificado;

    private LongFilter empresaId;

    private Boolean distinct;

    public CertificadoDigitalCriteria() {}

    public CertificadoDigitalCriteria(CertificadoDigitalCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.dataContratacao = other.optionalDataContratacao().map(InstantFilter::copy).orElse(null);
        this.validade = other.optionalValidade().map(IntegerFilter::copy).orElse(null);
        this.tipoCertificado = other.optionalTipoCertificado().map(TipoCertificadoEnumFilter::copy).orElse(null);
        this.empresaId = other.optionalEmpresaId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public CertificadoDigitalCriteria copy() {
        return new CertificadoDigitalCriteria(this);
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

    public InstantFilter getDataContratacao() {
        return dataContratacao;
    }

    public Optional<InstantFilter> optionalDataContratacao() {
        return Optional.ofNullable(dataContratacao);
    }

    public InstantFilter dataContratacao() {
        if (dataContratacao == null) {
            setDataContratacao(new InstantFilter());
        }
        return dataContratacao;
    }

    public void setDataContratacao(InstantFilter dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public IntegerFilter getValidade() {
        return validade;
    }

    public Optional<IntegerFilter> optionalValidade() {
        return Optional.ofNullable(validade);
    }

    public IntegerFilter validade() {
        if (validade == null) {
            setValidade(new IntegerFilter());
        }
        return validade;
    }

    public void setValidade(IntegerFilter validade) {
        this.validade = validade;
    }

    public TipoCertificadoEnumFilter getTipoCertificado() {
        return tipoCertificado;
    }

    public Optional<TipoCertificadoEnumFilter> optionalTipoCertificado() {
        return Optional.ofNullable(tipoCertificado);
    }

    public TipoCertificadoEnumFilter tipoCertificado() {
        if (tipoCertificado == null) {
            setTipoCertificado(new TipoCertificadoEnumFilter());
        }
        return tipoCertificado;
    }

    public void setTipoCertificado(TipoCertificadoEnumFilter tipoCertificado) {
        this.tipoCertificado = tipoCertificado;
    }

    public LongFilter getEmpresaId() {
        return empresaId;
    }

    public Optional<LongFilter> optionalEmpresaId() {
        return Optional.ofNullable(empresaId);
    }

    public LongFilter empresaId() {
        if (empresaId == null) {
            setEmpresaId(new LongFilter());
        }
        return empresaId;
    }

    public void setEmpresaId(LongFilter empresaId) {
        this.empresaId = empresaId;
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
        final CertificadoDigitalCriteria that = (CertificadoDigitalCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dataContratacao, that.dataContratacao) &&
            Objects.equals(validade, that.validade) &&
            Objects.equals(tipoCertificado, that.tipoCertificado) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataContratacao, validade, tipoCertificado, empresaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CertificadoDigitalCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalDataContratacao().map(f -> "dataContratacao=" + f + ", ").orElse("") +
            optionalValidade().map(f -> "validade=" + f + ", ").orElse("") +
            optionalTipoCertificado().map(f -> "tipoCertificado=" + f + ", ").orElse("") +
            optionalEmpresaId().map(f -> "empresaId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
