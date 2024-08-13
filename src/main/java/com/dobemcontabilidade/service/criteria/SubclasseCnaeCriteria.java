package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.SubclasseCnae} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.SubclasseCnaeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /subclasse-cnaes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubclasseCnaeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigo;

    private IntegerFilter anexo;

    private BooleanFilter atendidoFreemium;

    private BooleanFilter atendido;

    private BooleanFilter optanteSimples;

    private BooleanFilter aceitaMEI;

    private StringFilter categoria;

    private LongFilter observacaoCnaeId;

    private LongFilter atividadeEmpresaId;

    private LongFilter classeId;

    private LongFilter segmentoCnaeId;

    private Boolean distinct;

    public SubclasseCnaeCriteria() {}

    public SubclasseCnaeCriteria(SubclasseCnaeCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.codigo = other.optionalCodigo().map(StringFilter::copy).orElse(null);
        this.anexo = other.optionalAnexo().map(IntegerFilter::copy).orElse(null);
        this.atendidoFreemium = other.optionalAtendidoFreemium().map(BooleanFilter::copy).orElse(null);
        this.atendido = other.optionalAtendido().map(BooleanFilter::copy).orElse(null);
        this.optanteSimples = other.optionalOptanteSimples().map(BooleanFilter::copy).orElse(null);
        this.aceitaMEI = other.optionalAceitaMEI().map(BooleanFilter::copy).orElse(null);
        this.categoria = other.optionalCategoria().map(StringFilter::copy).orElse(null);
        this.observacaoCnaeId = other.optionalObservacaoCnaeId().map(LongFilter::copy).orElse(null);
        this.atividadeEmpresaId = other.optionalAtividadeEmpresaId().map(LongFilter::copy).orElse(null);
        this.classeId = other.optionalClasseId().map(LongFilter::copy).orElse(null);
        this.segmentoCnaeId = other.optionalSegmentoCnaeId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SubclasseCnaeCriteria copy() {
        return new SubclasseCnaeCriteria(this);
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

    public StringFilter getCodigo() {
        return codigo;
    }

    public Optional<StringFilter> optionalCodigo() {
        return Optional.ofNullable(codigo);
    }

    public StringFilter codigo() {
        if (codigo == null) {
            setCodigo(new StringFilter());
        }
        return codigo;
    }

    public void setCodigo(StringFilter codigo) {
        this.codigo = codigo;
    }

    public IntegerFilter getAnexo() {
        return anexo;
    }

    public Optional<IntegerFilter> optionalAnexo() {
        return Optional.ofNullable(anexo);
    }

    public IntegerFilter anexo() {
        if (anexo == null) {
            setAnexo(new IntegerFilter());
        }
        return anexo;
    }

    public void setAnexo(IntegerFilter anexo) {
        this.anexo = anexo;
    }

    public BooleanFilter getAtendidoFreemium() {
        return atendidoFreemium;
    }

    public Optional<BooleanFilter> optionalAtendidoFreemium() {
        return Optional.ofNullable(atendidoFreemium);
    }

    public BooleanFilter atendidoFreemium() {
        if (atendidoFreemium == null) {
            setAtendidoFreemium(new BooleanFilter());
        }
        return atendidoFreemium;
    }

    public void setAtendidoFreemium(BooleanFilter atendidoFreemium) {
        this.atendidoFreemium = atendidoFreemium;
    }

    public BooleanFilter getAtendido() {
        return atendido;
    }

    public Optional<BooleanFilter> optionalAtendido() {
        return Optional.ofNullable(atendido);
    }

    public BooleanFilter atendido() {
        if (atendido == null) {
            setAtendido(new BooleanFilter());
        }
        return atendido;
    }

    public void setAtendido(BooleanFilter atendido) {
        this.atendido = atendido;
    }

    public BooleanFilter getOptanteSimples() {
        return optanteSimples;
    }

    public Optional<BooleanFilter> optionalOptanteSimples() {
        return Optional.ofNullable(optanteSimples);
    }

    public BooleanFilter optanteSimples() {
        if (optanteSimples == null) {
            setOptanteSimples(new BooleanFilter());
        }
        return optanteSimples;
    }

    public void setOptanteSimples(BooleanFilter optanteSimples) {
        this.optanteSimples = optanteSimples;
    }

    public BooleanFilter getAceitaMEI() {
        return aceitaMEI;
    }

    public Optional<BooleanFilter> optionalAceitaMEI() {
        return Optional.ofNullable(aceitaMEI);
    }

    public BooleanFilter aceitaMEI() {
        if (aceitaMEI == null) {
            setAceitaMEI(new BooleanFilter());
        }
        return aceitaMEI;
    }

    public void setAceitaMEI(BooleanFilter aceitaMEI) {
        this.aceitaMEI = aceitaMEI;
    }

    public StringFilter getCategoria() {
        return categoria;
    }

    public Optional<StringFilter> optionalCategoria() {
        return Optional.ofNullable(categoria);
    }

    public StringFilter categoria() {
        if (categoria == null) {
            setCategoria(new StringFilter());
        }
        return categoria;
    }

    public void setCategoria(StringFilter categoria) {
        this.categoria = categoria;
    }

    public LongFilter getObservacaoCnaeId() {
        return observacaoCnaeId;
    }

    public Optional<LongFilter> optionalObservacaoCnaeId() {
        return Optional.ofNullable(observacaoCnaeId);
    }

    public LongFilter observacaoCnaeId() {
        if (observacaoCnaeId == null) {
            setObservacaoCnaeId(new LongFilter());
        }
        return observacaoCnaeId;
    }

    public void setObservacaoCnaeId(LongFilter observacaoCnaeId) {
        this.observacaoCnaeId = observacaoCnaeId;
    }

    public LongFilter getAtividadeEmpresaId() {
        return atividadeEmpresaId;
    }

    public Optional<LongFilter> optionalAtividadeEmpresaId() {
        return Optional.ofNullable(atividadeEmpresaId);
    }

    public LongFilter atividadeEmpresaId() {
        if (atividadeEmpresaId == null) {
            setAtividadeEmpresaId(new LongFilter());
        }
        return atividadeEmpresaId;
    }

    public void setAtividadeEmpresaId(LongFilter atividadeEmpresaId) {
        this.atividadeEmpresaId = atividadeEmpresaId;
    }

    public LongFilter getClasseId() {
        return classeId;
    }

    public Optional<LongFilter> optionalClasseId() {
        return Optional.ofNullable(classeId);
    }

    public LongFilter classeId() {
        if (classeId == null) {
            setClasseId(new LongFilter());
        }
        return classeId;
    }

    public void setClasseId(LongFilter classeId) {
        this.classeId = classeId;
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
        final SubclasseCnaeCriteria that = (SubclasseCnaeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codigo, that.codigo) &&
            Objects.equals(anexo, that.anexo) &&
            Objects.equals(atendidoFreemium, that.atendidoFreemium) &&
            Objects.equals(atendido, that.atendido) &&
            Objects.equals(optanteSimples, that.optanteSimples) &&
            Objects.equals(aceitaMEI, that.aceitaMEI) &&
            Objects.equals(categoria, that.categoria) &&
            Objects.equals(observacaoCnaeId, that.observacaoCnaeId) &&
            Objects.equals(atividadeEmpresaId, that.atividadeEmpresaId) &&
            Objects.equals(classeId, that.classeId) &&
            Objects.equals(segmentoCnaeId, that.segmentoCnaeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            codigo,
            anexo,
            atendidoFreemium,
            atendido,
            optanteSimples,
            aceitaMEI,
            categoria,
            observacaoCnaeId,
            atividadeEmpresaId,
            classeId,
            segmentoCnaeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubclasseCnaeCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCodigo().map(f -> "codigo=" + f + ", ").orElse("") +
            optionalAnexo().map(f -> "anexo=" + f + ", ").orElse("") +
            optionalAtendidoFreemium().map(f -> "atendidoFreemium=" + f + ", ").orElse("") +
            optionalAtendido().map(f -> "atendido=" + f + ", ").orElse("") +
            optionalOptanteSimples().map(f -> "optanteSimples=" + f + ", ").orElse("") +
            optionalAceitaMEI().map(f -> "aceitaMEI=" + f + ", ").orElse("") +
            optionalCategoria().map(f -> "categoria=" + f + ", ").orElse("") +
            optionalObservacaoCnaeId().map(f -> "observacaoCnaeId=" + f + ", ").orElse("") +
            optionalAtividadeEmpresaId().map(f -> "atividadeEmpresaId=" + f + ", ").orElse("") +
            optionalClasseId().map(f -> "classeId=" + f + ", ").orElse("") +
            optionalSegmentoCnaeId().map(f -> "segmentoCnaeId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
