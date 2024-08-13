package com.dobemcontabilidade.service.criteria;

import com.dobemcontabilidade.domain.enumeration.SituacaoUsuarioContadorEnum;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.UsuarioContador} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.UsuarioContadorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /usuario-contadors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UsuarioContadorCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SituacaoUsuarioContadorEnum
     */
    public static class SituacaoUsuarioContadorEnumFilter extends Filter<SituacaoUsuarioContadorEnum> {

        public SituacaoUsuarioContadorEnumFilter() {}

        public SituacaoUsuarioContadorEnumFilter(SituacaoUsuarioContadorEnumFilter filter) {
            super(filter);
        }

        @Override
        public SituacaoUsuarioContadorEnumFilter copy() {
            return new SituacaoUsuarioContadorEnumFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter email;

    private BooleanFilter ativo;

    private InstantFilter dataHoraAtivacao;

    private InstantFilter dataLimiteAcesso;

    private SituacaoUsuarioContadorEnumFilter situacao;

    private LongFilter grupoAcessoEmpresaUsuarioContadorId;

    private LongFilter grupoAcessoUsuarioContadorId;

    private LongFilter feedBackUsuarioParaContadorId;

    private LongFilter contadorId;

    private Boolean distinct;

    public UsuarioContadorCriteria() {}

    public UsuarioContadorCriteria(UsuarioContadorCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.email = other.optionalEmail().map(StringFilter::copy).orElse(null);
        this.ativo = other.optionalAtivo().map(BooleanFilter::copy).orElse(null);
        this.dataHoraAtivacao = other.optionalDataHoraAtivacao().map(InstantFilter::copy).orElse(null);
        this.dataLimiteAcesso = other.optionalDataLimiteAcesso().map(InstantFilter::copy).orElse(null);
        this.situacao = other.optionalSituacao().map(SituacaoUsuarioContadorEnumFilter::copy).orElse(null);
        this.grupoAcessoEmpresaUsuarioContadorId = other.optionalGrupoAcessoEmpresaUsuarioContadorId().map(LongFilter::copy).orElse(null);
        this.grupoAcessoUsuarioContadorId = other.optionalGrupoAcessoUsuarioContadorId().map(LongFilter::copy).orElse(null);
        this.feedBackUsuarioParaContadorId = other.optionalFeedBackUsuarioParaContadorId().map(LongFilter::copy).orElse(null);
        this.contadorId = other.optionalContadorId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public UsuarioContadorCriteria copy() {
        return new UsuarioContadorCriteria(this);
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

    public BooleanFilter getAtivo() {
        return ativo;
    }

    public Optional<BooleanFilter> optionalAtivo() {
        return Optional.ofNullable(ativo);
    }

    public BooleanFilter ativo() {
        if (ativo == null) {
            setAtivo(new BooleanFilter());
        }
        return ativo;
    }

    public void setAtivo(BooleanFilter ativo) {
        this.ativo = ativo;
    }

    public InstantFilter getDataHoraAtivacao() {
        return dataHoraAtivacao;
    }

    public Optional<InstantFilter> optionalDataHoraAtivacao() {
        return Optional.ofNullable(dataHoraAtivacao);
    }

    public InstantFilter dataHoraAtivacao() {
        if (dataHoraAtivacao == null) {
            setDataHoraAtivacao(new InstantFilter());
        }
        return dataHoraAtivacao;
    }

    public void setDataHoraAtivacao(InstantFilter dataHoraAtivacao) {
        this.dataHoraAtivacao = dataHoraAtivacao;
    }

    public InstantFilter getDataLimiteAcesso() {
        return dataLimiteAcesso;
    }

    public Optional<InstantFilter> optionalDataLimiteAcesso() {
        return Optional.ofNullable(dataLimiteAcesso);
    }

    public InstantFilter dataLimiteAcesso() {
        if (dataLimiteAcesso == null) {
            setDataLimiteAcesso(new InstantFilter());
        }
        return dataLimiteAcesso;
    }

    public void setDataLimiteAcesso(InstantFilter dataLimiteAcesso) {
        this.dataLimiteAcesso = dataLimiteAcesso;
    }

    public SituacaoUsuarioContadorEnumFilter getSituacao() {
        return situacao;
    }

    public Optional<SituacaoUsuarioContadorEnumFilter> optionalSituacao() {
        return Optional.ofNullable(situacao);
    }

    public SituacaoUsuarioContadorEnumFilter situacao() {
        if (situacao == null) {
            setSituacao(new SituacaoUsuarioContadorEnumFilter());
        }
        return situacao;
    }

    public void setSituacao(SituacaoUsuarioContadorEnumFilter situacao) {
        this.situacao = situacao;
    }

    public LongFilter getGrupoAcessoEmpresaUsuarioContadorId() {
        return grupoAcessoEmpresaUsuarioContadorId;
    }

    public Optional<LongFilter> optionalGrupoAcessoEmpresaUsuarioContadorId() {
        return Optional.ofNullable(grupoAcessoEmpresaUsuarioContadorId);
    }

    public LongFilter grupoAcessoEmpresaUsuarioContadorId() {
        if (grupoAcessoEmpresaUsuarioContadorId == null) {
            setGrupoAcessoEmpresaUsuarioContadorId(new LongFilter());
        }
        return grupoAcessoEmpresaUsuarioContadorId;
    }

    public void setGrupoAcessoEmpresaUsuarioContadorId(LongFilter grupoAcessoEmpresaUsuarioContadorId) {
        this.grupoAcessoEmpresaUsuarioContadorId = grupoAcessoEmpresaUsuarioContadorId;
    }

    public LongFilter getGrupoAcessoUsuarioContadorId() {
        return grupoAcessoUsuarioContadorId;
    }

    public Optional<LongFilter> optionalGrupoAcessoUsuarioContadorId() {
        return Optional.ofNullable(grupoAcessoUsuarioContadorId);
    }

    public LongFilter grupoAcessoUsuarioContadorId() {
        if (grupoAcessoUsuarioContadorId == null) {
            setGrupoAcessoUsuarioContadorId(new LongFilter());
        }
        return grupoAcessoUsuarioContadorId;
    }

    public void setGrupoAcessoUsuarioContadorId(LongFilter grupoAcessoUsuarioContadorId) {
        this.grupoAcessoUsuarioContadorId = grupoAcessoUsuarioContadorId;
    }

    public LongFilter getFeedBackUsuarioParaContadorId() {
        return feedBackUsuarioParaContadorId;
    }

    public Optional<LongFilter> optionalFeedBackUsuarioParaContadorId() {
        return Optional.ofNullable(feedBackUsuarioParaContadorId);
    }

    public LongFilter feedBackUsuarioParaContadorId() {
        if (feedBackUsuarioParaContadorId == null) {
            setFeedBackUsuarioParaContadorId(new LongFilter());
        }
        return feedBackUsuarioParaContadorId;
    }

    public void setFeedBackUsuarioParaContadorId(LongFilter feedBackUsuarioParaContadorId) {
        this.feedBackUsuarioParaContadorId = feedBackUsuarioParaContadorId;
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
        final UsuarioContadorCriteria that = (UsuarioContadorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(email, that.email) &&
            Objects.equals(ativo, that.ativo) &&
            Objects.equals(dataHoraAtivacao, that.dataHoraAtivacao) &&
            Objects.equals(dataLimiteAcesso, that.dataLimiteAcesso) &&
            Objects.equals(situacao, that.situacao) &&
            Objects.equals(grupoAcessoEmpresaUsuarioContadorId, that.grupoAcessoEmpresaUsuarioContadorId) &&
            Objects.equals(grupoAcessoUsuarioContadorId, that.grupoAcessoUsuarioContadorId) &&
            Objects.equals(feedBackUsuarioParaContadorId, that.feedBackUsuarioParaContadorId) &&
            Objects.equals(contadorId, that.contadorId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            email,
            ativo,
            dataHoraAtivacao,
            dataLimiteAcesso,
            situacao,
            grupoAcessoEmpresaUsuarioContadorId,
            grupoAcessoUsuarioContadorId,
            feedBackUsuarioParaContadorId,
            contadorId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsuarioContadorCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalEmail().map(f -> "email=" + f + ", ").orElse("") +
            optionalAtivo().map(f -> "ativo=" + f + ", ").orElse("") +
            optionalDataHoraAtivacao().map(f -> "dataHoraAtivacao=" + f + ", ").orElse("") +
            optionalDataLimiteAcesso().map(f -> "dataLimiteAcesso=" + f + ", ").orElse("") +
            optionalSituacao().map(f -> "situacao=" + f + ", ").orElse("") +
            optionalGrupoAcessoEmpresaUsuarioContadorId().map(f -> "grupoAcessoEmpresaUsuarioContadorId=" + f + ", ").orElse("") +
            optionalGrupoAcessoUsuarioContadorId().map(f -> "grupoAcessoUsuarioContadorId=" + f + ", ").orElse("") +
            optionalFeedBackUsuarioParaContadorId().map(f -> "feedBackUsuarioParaContadorId=" + f + ", ").orElse("") +
            optionalContadorId().map(f -> "contadorId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
