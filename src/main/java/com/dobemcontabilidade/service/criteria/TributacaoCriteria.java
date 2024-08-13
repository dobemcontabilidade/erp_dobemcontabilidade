package com.dobemcontabilidade.service.criteria;

import com.dobemcontabilidade.domain.enumeration.SituacaoTributacaoEnum;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Tributacao} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.TributacaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tributacaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TributacaoCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SituacaoTributacaoEnum
     */
    public static class SituacaoTributacaoEnumFilter extends Filter<SituacaoTributacaoEnum> {

        public SituacaoTributacaoEnumFilter() {}

        public SituacaoTributacaoEnumFilter(SituacaoTributacaoEnumFilter filter) {
            super(filter);
        }

        @Override
        public SituacaoTributacaoEnumFilter copy() {
            return new SituacaoTributacaoEnumFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private SituacaoTributacaoEnumFilter situacao;

    private LongFilter anexoRequeridoEmpresaId;

    private LongFilter empresaModeloId;

    private LongFilter calculoPlanoAssinaturaId;

    private LongFilter adicionalTributacaoId;

    private Boolean distinct;

    public TributacaoCriteria() {}

    public TributacaoCriteria(TributacaoCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.situacao = other.optionalSituacao().map(SituacaoTributacaoEnumFilter::copy).orElse(null);
        this.anexoRequeridoEmpresaId = other.optionalAnexoRequeridoEmpresaId().map(LongFilter::copy).orElse(null);
        this.empresaModeloId = other.optionalEmpresaModeloId().map(LongFilter::copy).orElse(null);
        this.calculoPlanoAssinaturaId = other.optionalCalculoPlanoAssinaturaId().map(LongFilter::copy).orElse(null);
        this.adicionalTributacaoId = other.optionalAdicionalTributacaoId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public TributacaoCriteria copy() {
        return new TributacaoCriteria(this);
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

    public SituacaoTributacaoEnumFilter getSituacao() {
        return situacao;
    }

    public Optional<SituacaoTributacaoEnumFilter> optionalSituacao() {
        return Optional.ofNullable(situacao);
    }

    public SituacaoTributacaoEnumFilter situacao() {
        if (situacao == null) {
            setSituacao(new SituacaoTributacaoEnumFilter());
        }
        return situacao;
    }

    public void setSituacao(SituacaoTributacaoEnumFilter situacao) {
        this.situacao = situacao;
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

    public LongFilter getAdicionalTributacaoId() {
        return adicionalTributacaoId;
    }

    public Optional<LongFilter> optionalAdicionalTributacaoId() {
        return Optional.ofNullable(adicionalTributacaoId);
    }

    public LongFilter adicionalTributacaoId() {
        if (adicionalTributacaoId == null) {
            setAdicionalTributacaoId(new LongFilter());
        }
        return adicionalTributacaoId;
    }

    public void setAdicionalTributacaoId(LongFilter adicionalTributacaoId) {
        this.adicionalTributacaoId = adicionalTributacaoId;
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
        final TributacaoCriteria that = (TributacaoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(situacao, that.situacao) &&
            Objects.equals(anexoRequeridoEmpresaId, that.anexoRequeridoEmpresaId) &&
            Objects.equals(empresaModeloId, that.empresaModeloId) &&
            Objects.equals(calculoPlanoAssinaturaId, that.calculoPlanoAssinaturaId) &&
            Objects.equals(adicionalTributacaoId, that.adicionalTributacaoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nome,
            situacao,
            anexoRequeridoEmpresaId,
            empresaModeloId,
            calculoPlanoAssinaturaId,
            adicionalTributacaoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TributacaoCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalSituacao().map(f -> "situacao=" + f + ", ").orElse("") +
            optionalAnexoRequeridoEmpresaId().map(f -> "anexoRequeridoEmpresaId=" + f + ", ").orElse("") +
            optionalEmpresaModeloId().map(f -> "empresaModeloId=" + f + ", ").orElse("") +
            optionalCalculoPlanoAssinaturaId().map(f -> "calculoPlanoAssinaturaId=" + f + ", ").orElse("") +
            optionalAdicionalTributacaoId().map(f -> "adicionalTributacaoId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
