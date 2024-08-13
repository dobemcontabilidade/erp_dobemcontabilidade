package com.dobemcontabilidade.service.criteria;

import com.dobemcontabilidade.domain.enumeration.TipoRedeEnum;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.PerfilRedeSocial} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.PerfilRedeSocialResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /perfil-rede-socials?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerfilRedeSocialCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TipoRedeEnum
     */
    public static class TipoRedeEnumFilter extends Filter<TipoRedeEnum> {

        public TipoRedeEnumFilter() {}

        public TipoRedeEnumFilter(TipoRedeEnumFilter filter) {
            super(filter);
        }

        @Override
        public TipoRedeEnumFilter copy() {
            return new TipoRedeEnumFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter rede;

    private StringFilter urlPerfil;

    private TipoRedeEnumFilter tipoRede;

    private Boolean distinct;

    public PerfilRedeSocialCriteria() {}

    public PerfilRedeSocialCriteria(PerfilRedeSocialCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.rede = other.optionalRede().map(StringFilter::copy).orElse(null);
        this.urlPerfil = other.optionalUrlPerfil().map(StringFilter::copy).orElse(null);
        this.tipoRede = other.optionalTipoRede().map(TipoRedeEnumFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PerfilRedeSocialCriteria copy() {
        return new PerfilRedeSocialCriteria(this);
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

    public StringFilter getRede() {
        return rede;
    }

    public Optional<StringFilter> optionalRede() {
        return Optional.ofNullable(rede);
    }

    public StringFilter rede() {
        if (rede == null) {
            setRede(new StringFilter());
        }
        return rede;
    }

    public void setRede(StringFilter rede) {
        this.rede = rede;
    }

    public StringFilter getUrlPerfil() {
        return urlPerfil;
    }

    public Optional<StringFilter> optionalUrlPerfil() {
        return Optional.ofNullable(urlPerfil);
    }

    public StringFilter urlPerfil() {
        if (urlPerfil == null) {
            setUrlPerfil(new StringFilter());
        }
        return urlPerfil;
    }

    public void setUrlPerfil(StringFilter urlPerfil) {
        this.urlPerfil = urlPerfil;
    }

    public TipoRedeEnumFilter getTipoRede() {
        return tipoRede;
    }

    public Optional<TipoRedeEnumFilter> optionalTipoRede() {
        return Optional.ofNullable(tipoRede);
    }

    public TipoRedeEnumFilter tipoRede() {
        if (tipoRede == null) {
            setTipoRede(new TipoRedeEnumFilter());
        }
        return tipoRede;
    }

    public void setTipoRede(TipoRedeEnumFilter tipoRede) {
        this.tipoRede = tipoRede;
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
        final PerfilRedeSocialCriteria that = (PerfilRedeSocialCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(rede, that.rede) &&
            Objects.equals(urlPerfil, that.urlPerfil) &&
            Objects.equals(tipoRede, that.tipoRede) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rede, urlPerfil, tipoRede, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerfilRedeSocialCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalRede().map(f -> "rede=" + f + ", ").orElse("") +
            optionalUrlPerfil().map(f -> "urlPerfil=" + f + ", ").orElse("") +
            optionalTipoRede().map(f -> "tipoRede=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
