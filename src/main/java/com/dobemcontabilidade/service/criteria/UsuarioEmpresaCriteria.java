package com.dobemcontabilidade.service.criteria;

import com.dobemcontabilidade.domain.enumeration.SituacaoUsuarioEmpresaEnum;
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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter email;

    private InstantFilter dataHoraAtivacao;

    private InstantFilter dataLimiteAcesso;

    private SituacaoUsuarioEmpresaEnumFilter situacao;

    private LongFilter pessoaId;

    private LongFilter empresaId;

    private Boolean distinct;

    public UsuarioEmpresaCriteria() {}

    public UsuarioEmpresaCriteria(UsuarioEmpresaCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.email = other.optionalEmail().map(StringFilter::copy).orElse(null);
        this.dataHoraAtivacao = other.optionalDataHoraAtivacao().map(InstantFilter::copy).orElse(null);
        this.dataLimiteAcesso = other.optionalDataLimiteAcesso().map(InstantFilter::copy).orElse(null);
        this.situacao = other.optionalSituacao().map(SituacaoUsuarioEmpresaEnumFilter::copy).orElse(null);
        this.pessoaId = other.optionalPessoaId().map(LongFilter::copy).orElse(null);
        this.empresaId = other.optionalEmpresaId().map(LongFilter::copy).orElse(null);
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

    public SituacaoUsuarioEmpresaEnumFilter getSituacao() {
        return situacao;
    }

    public Optional<SituacaoUsuarioEmpresaEnumFilter> optionalSituacao() {
        return Optional.ofNullable(situacao);
    }

    public SituacaoUsuarioEmpresaEnumFilter situacao() {
        if (situacao == null) {
            setSituacao(new SituacaoUsuarioEmpresaEnumFilter());
        }
        return situacao;
    }

    public void setSituacao(SituacaoUsuarioEmpresaEnumFilter situacao) {
        this.situacao = situacao;
    }

    public LongFilter getPessoaId() {
        return pessoaId;
    }

    public Optional<LongFilter> optionalPessoaId() {
        return Optional.ofNullable(pessoaId);
    }

    public LongFilter pessoaId() {
        if (pessoaId == null) {
            setPessoaId(new LongFilter());
        }
        return pessoaId;
    }

    public void setPessoaId(LongFilter pessoaId) {
        this.pessoaId = pessoaId;
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
        final UsuarioEmpresaCriteria that = (UsuarioEmpresaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(email, that.email) &&
            Objects.equals(dataHoraAtivacao, that.dataHoraAtivacao) &&
            Objects.equals(dataLimiteAcesso, that.dataLimiteAcesso) &&
            Objects.equals(situacao, that.situacao) &&
            Objects.equals(pessoaId, that.pessoaId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, dataHoraAtivacao, dataLimiteAcesso, situacao, pessoaId, empresaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsuarioEmpresaCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalEmail().map(f -> "email=" + f + ", ").orElse("") +
            optionalDataHoraAtivacao().map(f -> "dataHoraAtivacao=" + f + ", ").orElse("") +
            optionalDataLimiteAcesso().map(f -> "dataLimiteAcesso=" + f + ", ").orElse("") +
            optionalSituacao().map(f -> "situacao=" + f + ", ").orElse("") +
            optionalPessoaId().map(f -> "pessoaId=" + f + ", ").orElse("") +
            optionalEmpresaId().map(f -> "empresaId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
