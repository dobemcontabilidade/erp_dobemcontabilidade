package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Ramo} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.RamoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ramos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RamoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private LongFilter anexoRequeridoEmpresaId;

    private LongFilter empresaModeloId;

    private LongFilter calculoPlanoAssinaturaId;

    private LongFilter adicionalRamoId;

    private LongFilter valorBaseRamoId;

    private LongFilter segmentoCnaeId;

    private Boolean distinct;

    public RamoCriteria() {}

    public RamoCriteria(RamoCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.anexoRequeridoEmpresaId = other.optionalAnexoRequeridoEmpresaId().map(LongFilter::copy).orElse(null);
        this.empresaModeloId = other.optionalEmpresaModeloId().map(LongFilter::copy).orElse(null);
        this.calculoPlanoAssinaturaId = other.optionalCalculoPlanoAssinaturaId().map(LongFilter::copy).orElse(null);
        this.adicionalRamoId = other.optionalAdicionalRamoId().map(LongFilter::copy).orElse(null);
        this.valorBaseRamoId = other.optionalValorBaseRamoId().map(LongFilter::copy).orElse(null);
        this.segmentoCnaeId = other.optionalSegmentoCnaeId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public RamoCriteria copy() {
        return new RamoCriteria(this);
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

    public LongFilter getAnexoRequeridoEmpresaId() {
        return anexoRequeridoEmpresaId;
    }

    public Optional<LongFilter> optionalAnexoRequeridoEmpresaId() {
        return Optional.ofNullable(anexoRequeridoEmpresaId);
    }

    public LongFilter anexoRequeridoEmpresaId() {
        if (anexoRequeridoEmpresaId == null) {
            setAnexoRequeridoEmpresaId(new LongFilter());
        }
        return anexoRequeridoEmpresaId;
    }

    public void setAnexoRequeridoEmpresaId(LongFilter anexoRequeridoEmpresaId) {
        this.anexoRequeridoEmpresaId = anexoRequeridoEmpresaId;
    }

    public LongFilter getEmpresaModeloId() {
        return empresaModeloId;
    }

    public Optional<LongFilter> optionalEmpresaModeloId() {
        return Optional.ofNullable(empresaModeloId);
    }

    public LongFilter empresaModeloId() {
        if (empresaModeloId == null) {
            setEmpresaModeloId(new LongFilter());
        }
        return empresaModeloId;
    }

    public void setEmpresaModeloId(LongFilter empresaModeloId) {
        this.empresaModeloId = empresaModeloId;
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

    public LongFilter getAdicionalRamoId() {
        return adicionalRamoId;
    }

    public Optional<LongFilter> optionalAdicionalRamoId() {
        return Optional.ofNullable(adicionalRamoId);
    }

    public LongFilter adicionalRamoId() {
        if (adicionalRamoId == null) {
            setAdicionalRamoId(new LongFilter());
        }
        return adicionalRamoId;
    }

    public void setAdicionalRamoId(LongFilter adicionalRamoId) {
        this.adicionalRamoId = adicionalRamoId;
    }

    public LongFilter getValorBaseRamoId() {
        return valorBaseRamoId;
    }

    public Optional<LongFilter> optionalValorBaseRamoId() {
        return Optional.ofNullable(valorBaseRamoId);
    }

    public LongFilter valorBaseRamoId() {
        if (valorBaseRamoId == null) {
            setValorBaseRamoId(new LongFilter());
        }
        return valorBaseRamoId;
    }

    public void setValorBaseRamoId(LongFilter valorBaseRamoId) {
        this.valorBaseRamoId = valorBaseRamoId;
    }

    public LongFilter getSegmentoCnaeId() {
        return segmentoCnaeId;
    }

    public Optional<LongFilter> optionalSegmentoCnaeId() {
        return Optional.ofNullable(segmentoCnaeId);
    }

    public LongFilter segmentoCnaeId() {
        if (segmentoCnaeId == null) {
            setSegmentoCnaeId(new LongFilter());
        }
        return segmentoCnaeId;
    }

    public void setSegmentoCnaeId(LongFilter segmentoCnaeId) {
        this.segmentoCnaeId = segmentoCnaeId;
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
        final RamoCriteria that = (RamoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(anexoRequeridoEmpresaId, that.anexoRequeridoEmpresaId) &&
            Objects.equals(empresaModeloId, that.empresaModeloId) &&
            Objects.equals(calculoPlanoAssinaturaId, that.calculoPlanoAssinaturaId) &&
            Objects.equals(adicionalRamoId, that.adicionalRamoId) &&
            Objects.equals(valorBaseRamoId, that.valorBaseRamoId) &&
            Objects.equals(segmentoCnaeId, that.segmentoCnaeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nome,
            anexoRequeridoEmpresaId,
            empresaModeloId,
            calculoPlanoAssinaturaId,
            adicionalRamoId,
            valorBaseRamoId,
            segmentoCnaeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RamoCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalAnexoRequeridoEmpresaId().map(f -> "anexoRequeridoEmpresaId=" + f + ", ").orElse("") +
            optionalEmpresaModeloId().map(f -> "empresaModeloId=" + f + ", ").orElse("") +
            optionalCalculoPlanoAssinaturaId().map(f -> "calculoPlanoAssinaturaId=" + f + ", ").orElse("") +
            optionalAdicionalRamoId().map(f -> "adicionalRamoId=" + f + ", ").orElse("") +
            optionalValorBaseRamoId().map(f -> "valorBaseRamoId=" + f + ", ").orElse("") +
            optionalSegmentoCnaeId().map(f -> "segmentoCnaeId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
