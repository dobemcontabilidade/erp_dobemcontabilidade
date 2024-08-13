package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Enquadramento} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.EnquadramentoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /enquadramentos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EnquadramentoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter sigla;

    private DoubleFilter limiteInicial;

    private DoubleFilter limiteFinal;

    private LongFilter anexoRequeridoEmpresaId;

    private LongFilter empresaModeloId;

    private LongFilter adicionalEnquadramentoId;

    private Boolean distinct;

    public EnquadramentoCriteria() {}

    public EnquadramentoCriteria(EnquadramentoCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.sigla = other.optionalSigla().map(StringFilter::copy).orElse(null);
        this.limiteInicial = other.optionalLimiteInicial().map(DoubleFilter::copy).orElse(null);
        this.limiteFinal = other.optionalLimiteFinal().map(DoubleFilter::copy).orElse(null);
        this.anexoRequeridoEmpresaId = other.optionalAnexoRequeridoEmpresaId().map(LongFilter::copy).orElse(null);
        this.empresaModeloId = other.optionalEmpresaModeloId().map(LongFilter::copy).orElse(null);
        this.adicionalEnquadramentoId = other.optionalAdicionalEnquadramentoId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public EnquadramentoCriteria copy() {
        return new EnquadramentoCriteria(this);
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

    public StringFilter getSigla() {
        return sigla;
    }

    public Optional<StringFilter> optionalSigla() {
        return Optional.ofNullable(sigla);
    }

    public StringFilter sigla() {
        if (sigla == null) {
            setSigla(new StringFilter());
        }
        return sigla;
    }

    public void setSigla(StringFilter sigla) {
        this.sigla = sigla;
    }

    public DoubleFilter getLimiteInicial() {
        return limiteInicial;
    }

    public Optional<DoubleFilter> optionalLimiteInicial() {
        return Optional.ofNullable(limiteInicial);
    }

    public DoubleFilter limiteInicial() {
        if (limiteInicial == null) {
            setLimiteInicial(new DoubleFilter());
        }
        return limiteInicial;
    }

    public void setLimiteInicial(DoubleFilter limiteInicial) {
        this.limiteInicial = limiteInicial;
    }

    public DoubleFilter getLimiteFinal() {
        return limiteFinal;
    }

    public Optional<DoubleFilter> optionalLimiteFinal() {
        return Optional.ofNullable(limiteFinal);
    }

    public DoubleFilter limiteFinal() {
        if (limiteFinal == null) {
            setLimiteFinal(new DoubleFilter());
        }
        return limiteFinal;
    }

    public void setLimiteFinal(DoubleFilter limiteFinal) {
        this.limiteFinal = limiteFinal;
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

    public LongFilter getAdicionalEnquadramentoId() {
        return adicionalEnquadramentoId;
    }

    public Optional<LongFilter> optionalAdicionalEnquadramentoId() {
        return Optional.ofNullable(adicionalEnquadramentoId);
    }

    public LongFilter adicionalEnquadramentoId() {
        if (adicionalEnquadramentoId == null) {
            setAdicionalEnquadramentoId(new LongFilter());
        }
        return adicionalEnquadramentoId;
    }

    public void setAdicionalEnquadramentoId(LongFilter adicionalEnquadramentoId) {
        this.adicionalEnquadramentoId = adicionalEnquadramentoId;
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
        final EnquadramentoCriteria that = (EnquadramentoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(sigla, that.sigla) &&
            Objects.equals(limiteInicial, that.limiteInicial) &&
            Objects.equals(limiteFinal, that.limiteFinal) &&
            Objects.equals(anexoRequeridoEmpresaId, that.anexoRequeridoEmpresaId) &&
            Objects.equals(empresaModeloId, that.empresaModeloId) &&
            Objects.equals(adicionalEnquadramentoId, that.adicionalEnquadramentoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nome,
            sigla,
            limiteInicial,
            limiteFinal,
            anexoRequeridoEmpresaId,
            empresaModeloId,
            adicionalEnquadramentoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnquadramentoCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalSigla().map(f -> "sigla=" + f + ", ").orElse("") +
            optionalLimiteInicial().map(f -> "limiteInicial=" + f + ", ").orElse("") +
            optionalLimiteFinal().map(f -> "limiteFinal=" + f + ", ").orElse("") +
            optionalAnexoRequeridoEmpresaId().map(f -> "anexoRequeridoEmpresaId=" + f + ", ").orElse("") +
            optionalEmpresaModeloId().map(f -> "empresaModeloId=" + f + ", ").orElse("") +
            optionalAdicionalEnquadramentoId().map(f -> "adicionalEnquadramentoId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
