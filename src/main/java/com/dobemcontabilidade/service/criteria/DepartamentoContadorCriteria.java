package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.DepartamentoContador} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.DepartamentoContadorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /departamento-contadors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DepartamentoContadorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter percentualExperiencia;

    private StringFilter descricaoExperiencia;

    private DoubleFilter pontuacaoEntrevista;

    private DoubleFilter pontuacaoAvaliacao;

    private LongFilter departamentoId;

    private LongFilter contadorId;

    private Boolean distinct;

    public DepartamentoContadorCriteria() {}

    public DepartamentoContadorCriteria(DepartamentoContadorCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.percentualExperiencia = other.optionalPercentualExperiencia().map(DoubleFilter::copy).orElse(null);
        this.descricaoExperiencia = other.optionalDescricaoExperiencia().map(StringFilter::copy).orElse(null);
        this.pontuacaoEntrevista = other.optionalPontuacaoEntrevista().map(DoubleFilter::copy).orElse(null);
        this.pontuacaoAvaliacao = other.optionalPontuacaoAvaliacao().map(DoubleFilter::copy).orElse(null);
        this.departamentoId = other.optionalDepartamentoId().map(LongFilter::copy).orElse(null);
        this.contadorId = other.optionalContadorId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public DepartamentoContadorCriteria copy() {
        return new DepartamentoContadorCriteria(this);
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

    public DoubleFilter getPercentualExperiencia() {
        return percentualExperiencia;
    }

    public Optional<DoubleFilter> optionalPercentualExperiencia() {
        return Optional.ofNullable(percentualExperiencia);
    }

    public DoubleFilter percentualExperiencia() {
        if (percentualExperiencia == null) {
            setPercentualExperiencia(new DoubleFilter());
        }
        return percentualExperiencia;
    }

    public void setPercentualExperiencia(DoubleFilter percentualExperiencia) {
        this.percentualExperiencia = percentualExperiencia;
    }

    public StringFilter getDescricaoExperiencia() {
        return descricaoExperiencia;
    }

    public Optional<StringFilter> optionalDescricaoExperiencia() {
        return Optional.ofNullable(descricaoExperiencia);
    }

    public StringFilter descricaoExperiencia() {
        if (descricaoExperiencia == null) {
            setDescricaoExperiencia(new StringFilter());
        }
        return descricaoExperiencia;
    }

    public void setDescricaoExperiencia(StringFilter descricaoExperiencia) {
        this.descricaoExperiencia = descricaoExperiencia;
    }

    public DoubleFilter getPontuacaoEntrevista() {
        return pontuacaoEntrevista;
    }

    public Optional<DoubleFilter> optionalPontuacaoEntrevista() {
        return Optional.ofNullable(pontuacaoEntrevista);
    }

    public DoubleFilter pontuacaoEntrevista() {
        if (pontuacaoEntrevista == null) {
            setPontuacaoEntrevista(new DoubleFilter());
        }
        return pontuacaoEntrevista;
    }

    public void setPontuacaoEntrevista(DoubleFilter pontuacaoEntrevista) {
        this.pontuacaoEntrevista = pontuacaoEntrevista;
    }

    public DoubleFilter getPontuacaoAvaliacao() {
        return pontuacaoAvaliacao;
    }

    public Optional<DoubleFilter> optionalPontuacaoAvaliacao() {
        return Optional.ofNullable(pontuacaoAvaliacao);
    }

    public DoubleFilter pontuacaoAvaliacao() {
        if (pontuacaoAvaliacao == null) {
            setPontuacaoAvaliacao(new DoubleFilter());
        }
        return pontuacaoAvaliacao;
    }

    public void setPontuacaoAvaliacao(DoubleFilter pontuacaoAvaliacao) {
        this.pontuacaoAvaliacao = pontuacaoAvaliacao;
    }

    public LongFilter getDepartamentoId() {
        return departamentoId;
    }

    public Optional<LongFilter> optionalDepartamentoId() {
        return Optional.ofNullable(departamentoId);
    }

    public LongFilter departamentoId() {
        if (departamentoId == null) {
            setDepartamentoId(new LongFilter());
        }
        return departamentoId;
    }

    public void setDepartamentoId(LongFilter departamentoId) {
        this.departamentoId = departamentoId;
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
        final DepartamentoContadorCriteria that = (DepartamentoContadorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(percentualExperiencia, that.percentualExperiencia) &&
            Objects.equals(descricaoExperiencia, that.descricaoExperiencia) &&
            Objects.equals(pontuacaoEntrevista, that.pontuacaoEntrevista) &&
            Objects.equals(pontuacaoAvaliacao, that.pontuacaoAvaliacao) &&
            Objects.equals(departamentoId, that.departamentoId) &&
            Objects.equals(contadorId, that.contadorId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            percentualExperiencia,
            descricaoExperiencia,
            pontuacaoEntrevista,
            pontuacaoAvaliacao,
            departamentoId,
            contadorId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartamentoContadorCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalPercentualExperiencia().map(f -> "percentualExperiencia=" + f + ", ").orElse("") +
            optionalDescricaoExperiencia().map(f -> "descricaoExperiencia=" + f + ", ").orElse("") +
            optionalPontuacaoEntrevista().map(f -> "pontuacaoEntrevista=" + f + ", ").orElse("") +
            optionalPontuacaoAvaliacao().map(f -> "pontuacaoAvaliacao=" + f + ", ").orElse("") +
            optionalDepartamentoId().map(f -> "departamentoId=" + f + ", ").orElse("") +
            optionalContadorId().map(f -> "contadorId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
