package com.dobemcontabilidade.service.criteria;

import com.dobemcontabilidade.domain.enumeration.SituacaoUsuarioEmpresaEnum;
import com.dobemcontabilidade.domain.enumeration.TipoUsuarioEmpresaEnum;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.UsuarioEmpresa} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.UsuarioEmpresaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /usuario-empresas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UsuarioEmpresaCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SituacaoUsuarioEmpresaEnum
     */
    public static class SituacaoUsuarioEmpresaEnumFilter extends Filter<SituacaoUsuarioEmpresaEnum> {

        public SituacaoUsuarioEmpresaEnumFilter() {}

        public SituacaoUsuarioEmpresaEnumFilter(SituacaoUsuarioEmpresaEnumFilter filter) {
            super(filter);
        }

        @Override
        public SituacaoUsuarioEmpresaEnumFilter copy() {
            return new SituacaoUsuarioEmpresaEnumFilter(this);
        }
    }

    /**
     * Class for filtering TipoUsuarioEmpresaEnum
     */
    public static class TipoUsuarioEmpresaEnumFilter extends Filter<TipoUsuarioEmpresaEnum> {

        public TipoUsuarioEmpresaEnumFilter() {}

        public TipoUsuarioEmpresaEnumFilter(TipoUsuarioEmpresaEnumFilter filter) {
            super(filter);
        }

        @Override
        public TipoUsuarioEmpresaEnumFilter copy() {
            return new TipoUsuarioEmpresaEnumFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter email;

    private BooleanFilter ativo;

    private InstantFilter dataHoraAtivacao;

    private InstantFilter dataLimiteAcesso;

    private SituacaoUsuarioEmpresaEnumFilter situacaoUsuarioEmpresa;

    private TipoUsuarioEmpresaEnumFilter tipoUsuarioEmpresaEnum;

    private LongFilter grupoAcessoUsuarioEmpresaId;

    private LongFilter feedBackUsuarioParaContadorId;

    private LongFilter feedBackContadorParaUsuarioId;

    private LongFilter assinaturaEmpresaId;

    private LongFilter funcionarioId;

    private LongFilter socioId;

    private Boolean distinct;

    public UsuarioEmpresaCriteria() {}

    public UsuarioEmpresaCriteria(UsuarioEmpresaCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.email = other.optionalEmail().map(StringFilter::copy).orElse(null);
        this.ativo = other.optionalAtivo().map(BooleanFilter::copy).orElse(null);
        this.dataHoraAtivacao = other.optionalDataHoraAtivacao().map(InstantFilter::copy).orElse(null);
        this.dataLimiteAcesso = other.optionalDataLimiteAcesso().map(InstantFilter::copy).orElse(null);
        this.situacaoUsuarioEmpresa = other.optionalSituacaoUsuarioEmpresa().map(SituacaoUsuarioEmpresaEnumFilter::copy).orElse(null);
        this.tipoUsuarioEmpresaEnum = other.optionalTipoUsuarioEmpresaEnum().map(TipoUsuarioEmpresaEnumFilter::copy).orElse(null);
        this.grupoAcessoUsuarioEmpresaId = other.optionalGrupoAcessoUsuarioEmpresaId().map(LongFilter::copy).orElse(null);
        this.feedBackUsuarioParaContadorId = other.optionalFeedBackUsuarioParaContadorId().map(LongFilter::copy).orElse(null);
        this.feedBackContadorParaUsuarioId = other.optionalFeedBackContadorParaUsuarioId().map(LongFilter::copy).orElse(null);
        this.assinaturaEmpresaId = other.optionalAssinaturaEmpresaId().map(LongFilter::copy).orElse(null);
        this.funcionarioId = other.optionalFuncionarioId().map(LongFilter::copy).orElse(null);
        this.socioId = other.optionalSocioId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public UsuarioEmpresaCriteria copy() {
        return new UsuarioEmpresaCriteria(this);
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

    public SituacaoUsuarioEmpresaEnumFilter getSituacaoUsuarioEmpresa() {
        return situacaoUsuarioEmpresa;
    }

    public Optional<SituacaoUsuarioEmpresaEnumFilter> optionalSituacaoUsuarioEmpresa() {
        return Optional.ofNullable(situacaoUsuarioEmpresa);
    }

    public SituacaoUsuarioEmpresaEnumFilter situacaoUsuarioEmpresa() {
        if (situacaoUsuarioEmpresa == null) {
            setSituacaoUsuarioEmpresa(new SituacaoUsuarioEmpresaEnumFilter());
        }
        return situacaoUsuarioEmpresa;
    }

    public void setSituacaoUsuarioEmpresa(SituacaoUsuarioEmpresaEnumFilter situacaoUsuarioEmpresa) {
        this.situacaoUsuarioEmpresa = situacaoUsuarioEmpresa;
    }

    public TipoUsuarioEmpresaEnumFilter getTipoUsuarioEmpresaEnum() {
        return tipoUsuarioEmpresaEnum;
    }

    public Optional<TipoUsuarioEmpresaEnumFilter> optionalTipoUsuarioEmpresaEnum() {
        return Optional.ofNullable(tipoUsuarioEmpresaEnum);
    }

    public TipoUsuarioEmpresaEnumFilter tipoUsuarioEmpresaEnum() {
        if (tipoUsuarioEmpresaEnum == null) {
            setTipoUsuarioEmpresaEnum(new TipoUsuarioEmpresaEnumFilter());
        }
        return tipoUsuarioEmpresaEnum;
    }

    public void setTipoUsuarioEmpresaEnum(TipoUsuarioEmpresaEnumFilter tipoUsuarioEmpresaEnum) {
        this.tipoUsuarioEmpresaEnum = tipoUsuarioEmpresaEnum;
    }

    public LongFilter getGrupoAcessoUsuarioEmpresaId() {
        return grupoAcessoUsuarioEmpresaId;
    }

    public Optional<LongFilter> optionalGrupoAcessoUsuarioEmpresaId() {
        return Optional.ofNullable(grupoAcessoUsuarioEmpresaId);
    }

    public LongFilter grupoAcessoUsuarioEmpresaId() {
        if (grupoAcessoUsuarioEmpresaId == null) {
            setGrupoAcessoUsuarioEmpresaId(new LongFilter());
        }
        return grupoAcessoUsuarioEmpresaId;
    }

    public void setGrupoAcessoUsuarioEmpresaId(LongFilter grupoAcessoUsuarioEmpresaId) {
        this.grupoAcessoUsuarioEmpresaId = grupoAcessoUsuarioEmpresaId;
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

    public LongFilter getFeedBackContadorParaUsuarioId() {
        return feedBackContadorParaUsuarioId;
    }

    public Optional<LongFilter> optionalFeedBackContadorParaUsuarioId() {
        return Optional.ofNullable(feedBackContadorParaUsuarioId);
    }

    public LongFilter feedBackContadorParaUsuarioId() {
        if (feedBackContadorParaUsuarioId == null) {
            setFeedBackContadorParaUsuarioId(new LongFilter());
        }
        return feedBackContadorParaUsuarioId;
    }

    public void setFeedBackContadorParaUsuarioId(LongFilter feedBackContadorParaUsuarioId) {
        this.feedBackContadorParaUsuarioId = feedBackContadorParaUsuarioId;
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

    public LongFilter getFuncionarioId() {
        return funcionarioId;
    }

    public Optional<LongFilter> optionalFuncionarioId() {
        return Optional.ofNullable(funcionarioId);
    }

    public LongFilter funcionarioId() {
        if (funcionarioId == null) {
            setFuncionarioId(new LongFilter());
        }
        return funcionarioId;
    }

    public void setFuncionarioId(LongFilter funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public LongFilter getSocioId() {
        return socioId;
    }

    public Optional<LongFilter> optionalSocioId() {
        return Optional.ofNullable(socioId);
    }

    public LongFilter socioId() {
        if (socioId == null) {
            setSocioId(new LongFilter());
        }
        return socioId;
    }

    public void setSocioId(LongFilter socioId) {
        this.socioId = socioId;
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
        final UsuarioEmpresaCriteria that = (UsuarioEmpresaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(email, that.email) &&
            Objects.equals(ativo, that.ativo) &&
            Objects.equals(dataHoraAtivacao, that.dataHoraAtivacao) &&
            Objects.equals(dataLimiteAcesso, that.dataLimiteAcesso) &&
            Objects.equals(situacaoUsuarioEmpresa, that.situacaoUsuarioEmpresa) &&
            Objects.equals(tipoUsuarioEmpresaEnum, that.tipoUsuarioEmpresaEnum) &&
            Objects.equals(grupoAcessoUsuarioEmpresaId, that.grupoAcessoUsuarioEmpresaId) &&
            Objects.equals(feedBackUsuarioParaContadorId, that.feedBackUsuarioParaContadorId) &&
            Objects.equals(feedBackContadorParaUsuarioId, that.feedBackContadorParaUsuarioId) &&
            Objects.equals(assinaturaEmpresaId, that.assinaturaEmpresaId) &&
            Objects.equals(funcionarioId, that.funcionarioId) &&
            Objects.equals(socioId, that.socioId) &&
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
            situacaoUsuarioEmpresa,
            tipoUsuarioEmpresaEnum,
            grupoAcessoUsuarioEmpresaId,
            feedBackUsuarioParaContadorId,
            feedBackContadorParaUsuarioId,
            assinaturaEmpresaId,
            funcionarioId,
            socioId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsuarioEmpresaCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalEmail().map(f -> "email=" + f + ", ").orElse("") +
            optionalAtivo().map(f -> "ativo=" + f + ", ").orElse("") +
            optionalDataHoraAtivacao().map(f -> "dataHoraAtivacao=" + f + ", ").orElse("") +
            optionalDataLimiteAcesso().map(f -> "dataLimiteAcesso=" + f + ", ").orElse("") +
            optionalSituacaoUsuarioEmpresa().map(f -> "situacaoUsuarioEmpresa=" + f + ", ").orElse("") +
            optionalTipoUsuarioEmpresaEnum().map(f -> "tipoUsuarioEmpresaEnum=" + f + ", ").orElse("") +
            optionalGrupoAcessoUsuarioEmpresaId().map(f -> "grupoAcessoUsuarioEmpresaId=" + f + ", ").orElse("") +
            optionalFeedBackUsuarioParaContadorId().map(f -> "feedBackUsuarioParaContadorId=" + f + ", ").orElse("") +
            optionalFeedBackContadorParaUsuarioId().map(f -> "feedBackContadorParaUsuarioId=" + f + ", ").orElse("") +
            optionalAssinaturaEmpresaId().map(f -> "assinaturaEmpresaId=" + f + ", ").orElse("") +
            optionalFuncionarioId().map(f -> "funcionarioId=" + f + ", ").orElse("") +
            optionalSocioId().map(f -> "socioId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
