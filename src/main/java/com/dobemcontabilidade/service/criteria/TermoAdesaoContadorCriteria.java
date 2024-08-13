package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.TermoAdesaoContador} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.TermoAdesaoContadorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /termo-adesao-contadors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TermoAdesaoContadorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter dataAdesao;

    private BooleanFilter checked;

    private StringFilter urlDoc;

    private LongFilter contadorId;

    private LongFilter termoDeAdesaoId;

    private Boolean distinct;

    public TermoAdesaoContadorCriteria() {}

    public TermoAdesaoContadorCriteria(TermoAdesaoContadorCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.dataAdesao = other.optionalDataAdesao().map(InstantFilter::copy).orElse(null);
        this.checked = other.optionalChecked().map(BooleanFilter::copy).orElse(null);
        this.urlDoc = other.optionalUrlDoc().map(StringFilter::copy).orElse(null);
        this.contadorId = other.optionalContadorId().map(LongFilter::copy).orElse(null);
        this.termoDeAdesaoId = other.optionalTermoDeAdesaoId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public TermoAdesaoContadorCriteria copy() {
        return new TermoAdesaoContadorCriteria(this);
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

    public InstantFilter getDataAdesao() {
        return dataAdesao;
    }

    public Optional<InstantFilter> optionalDataAdesao() {
        return Optional.ofNullable(dataAdesao);
    }

    public InstantFilter dataAdesao() {
        if (dataAdesao == null) {
            setDataAdesao(new InstantFilter());
        }
        return dataAdesao;
    }

    public void setDataAdesao(InstantFilter dataAdesao) {
        this.dataAdesao = dataAdesao;
    }

    public BooleanFilter getChecked() {
        return checked;
    }

    public Optional<BooleanFilter> optionalChecked() {
        return Optional.ofNullable(checked);
    }

    public BooleanFilter checked() {
        if (checked == null) {
            setChecked(new BooleanFilter());
        }
        return checked;
    }

    public void setChecked(BooleanFilter checked) {
        this.checked = checked;
    }

    public StringFilter getUrlDoc() {
        return urlDoc;
    }

    public Optional<StringFilter> optionalUrlDoc() {
        return Optional.ofNullable(urlDoc);
    }

    public StringFilter urlDoc() {
        if (urlDoc == null) {
            setUrlDoc(new StringFilter());
        }
        return urlDoc;
    }

    public void setUrlDoc(StringFilter urlDoc) {
        this.urlDoc = urlDoc;
    }

    public LongFilter getContadorId() {
        return contadorId;
    }

    public Optional<LongFilter> optionalContadorId() {
        return Optional.ofNullable(contadorId);
    }

    public LongFilter contadorId() {
        if (contadorId == null) {
            setContadorId(new LongFilter());
        }
        return contadorId;
    }

    public void setContadorId(LongFilter contadorId) {
        this.contadorId = contadorId;
    }

    public LongFilter getTermoDeAdesaoId() {
        return termoDeAdesaoId;
    }

    public Optional<LongFilter> optionalTermoDeAdesaoId() {
        return Optional.ofNullable(termoDeAdesaoId);
    }

    public LongFilter termoDeAdesaoId() {
        if (termoDeAdesaoId == null) {
            setTermoDeAdesaoId(new LongFilter());
        }
        return termoDeAdesaoId;
    }

    public void setTermoDeAdesaoId(LongFilter termoDeAdesaoId) {
        this.termoDeAdesaoId = termoDeAdesaoId;
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
        final TermoAdesaoContadorCriteria that = (TermoAdesaoContadorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dataAdesao, that.dataAdesao) &&
            Objects.equals(checked, that.checked) &&
            Objects.equals(urlDoc, that.urlDoc) &&
            Objects.equals(contadorId, that.contadorId) &&
            Objects.equals(termoDeAdesaoId, that.termoDeAdesaoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataAdesao, checked, urlDoc, contadorId, termoDeAdesaoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TermoAdesaoContadorCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalDataAdesao().map(f -> "dataAdesao=" + f + ", ").orElse("") +
            optionalChecked().map(f -> "checked=" + f + ", ").orElse("") +
            optionalUrlDoc().map(f -> "urlDoc=" + f + ", ").orElse("") +
            optionalContadorId().map(f -> "contadorId=" + f + ", ").orElse("") +
            optionalTermoDeAdesaoId().map(f -> "termoDeAdesaoId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
