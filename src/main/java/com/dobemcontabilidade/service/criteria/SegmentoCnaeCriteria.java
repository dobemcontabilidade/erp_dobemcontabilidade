package com.dobemcontabilidade.service.criteria;

import com.dobemcontabilidade.domain.enumeration.TipoSegmentoEnum;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.SegmentoCnae} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.SegmentoCnaeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /segmento-cnaes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SegmentoCnaeCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TipoSegmentoEnum
     */
    public static class TipoSegmentoEnumFilter extends Filter<TipoSegmentoEnum> {

        public TipoSegmentoEnumFilter() {}

        public TipoSegmentoEnumFilter(TipoSegmentoEnumFilter filter) {
            super(filter);
        }

        @Override
        public TipoSegmentoEnumFilter copy() {
            return new TipoSegmentoEnumFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter icon;

    private StringFilter imagem;

    private TipoSegmentoEnumFilter tipo;

    private LongFilter subclasseCnaeId;

    private LongFilter ramoId;

    private LongFilter empresaId;

    private Boolean distinct;

    public SegmentoCnaeCriteria() {}

    public SegmentoCnaeCriteria(SegmentoCnaeCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.icon = other.optionalIcon().map(StringFilter::copy).orElse(null);
        this.imagem = other.optionalImagem().map(StringFilter::copy).orElse(null);
        this.tipo = other.optionalTipo().map(TipoSegmentoEnumFilter::copy).orElse(null);
        this.subclasseCnaeId = other.optionalSubclasseCnaeId().map(LongFilter::copy).orElse(null);
        this.ramoId = other.optionalRamoId().map(LongFilter::copy).orElse(null);
        this.empresaId = other.optionalEmpresaId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SegmentoCnaeCriteria copy() {
        return new SegmentoCnaeCriteria(this);
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

    public StringFilter getIcon() {
        return icon;
    }

    public Optional<StringFilter> optionalIcon() {
        return Optional.ofNullable(icon);
    }

    public StringFilter icon() {
        if (icon == null) {
            setIcon(new StringFilter());
        }
        return icon;
    }

    public void setIcon(StringFilter icon) {
        this.icon = icon;
    }

    public StringFilter getImagem() {
        return imagem;
    }

    public Optional<StringFilter> optionalImagem() {
        return Optional.ofNullable(imagem);
    }

    public StringFilter imagem() {
        if (imagem == null) {
            setImagem(new StringFilter());
        }
        return imagem;
    }

    public void setImagem(StringFilter imagem) {
        this.imagem = imagem;
    }

    public TipoSegmentoEnumFilter getTipo() {
        return tipo;
    }

    public Optional<TipoSegmentoEnumFilter> optionalTipo() {
        return Optional.ofNullable(tipo);
    }

    public TipoSegmentoEnumFilter tipo() {
        if (tipo == null) {
            setTipo(new TipoSegmentoEnumFilter());
        }
        return tipo;
    }

    public void setTipo(TipoSegmentoEnumFilter tipo) {
        this.tipo = tipo;
    }

    public LongFilter getSubclasseCnaeId() {
        return subclasseCnaeId;
    }

    public Optional<LongFilter> optionalSubclasseCnaeId() {
        return Optional.ofNullable(subclasseCnaeId);
    }

    public LongFilter subclasseCnaeId() {
        if (subclasseCnaeId == null) {
            setSubclasseCnaeId(new LongFilter());
        }
        return subclasseCnaeId;
    }

    public void setSubclasseCnaeId(LongFilter subclasseCnaeId) {
        this.subclasseCnaeId = subclasseCnaeId;
    }

    public LongFilter getRamoId() {
        return ramoId;
    }

    public Optional<LongFilter> optionalRamoId() {
        return Optional.ofNullable(ramoId);
    }

    public LongFilter ramoId() {
        if (ramoId == null) {
            setRamoId(new LongFilter());
        }
        return ramoId;
    }

    public void setRamoId(LongFilter ramoId) {
        this.ramoId = ramoId;
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
        final SegmentoCnaeCriteria that = (SegmentoCnaeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(icon, that.icon) &&
            Objects.equals(imagem, that.imagem) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(subclasseCnaeId, that.subclasseCnaeId) &&
            Objects.equals(ramoId, that.ramoId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, icon, imagem, tipo, subclasseCnaeId, ramoId, empresaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SegmentoCnaeCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalIcon().map(f -> "icon=" + f + ", ").orElse("") +
            optionalImagem().map(f -> "imagem=" + f + ", ").orElse("") +
            optionalTipo().map(f -> "tipo=" + f + ", ").orElse("") +
            optionalSubclasseCnaeId().map(f -> "subclasseCnaeId=" + f + ", ").orElse("") +
            optionalRamoId().map(f -> "ramoId=" + f + ", ").orElse("") +
            optionalEmpresaId().map(f -> "empresaId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
