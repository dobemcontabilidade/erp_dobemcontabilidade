package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.PerfilContador} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.PerfilContadorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /perfil-contadors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerfilContadorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter perfil;

    private StringFilter descricao;

    private IntegerFilter limiteEmpresas;

    private IntegerFilter limiteDepartamentos;

    private IntegerFilter limiteAreaContabils;

    private DoubleFilter limiteFaturamento;

    private LongFilter perfilContadorAreaContabilId;

    private LongFilter contadorId;

    private LongFilter perfilContadorDepartamentoId;

    private Boolean distinct;

    public PerfilContadorCriteria() {}

    public PerfilContadorCriteria(PerfilContadorCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.perfil = other.optionalPerfil().map(StringFilter::copy).orElse(null);
        this.descricao = other.optionalDescricao().map(StringFilter::copy).orElse(null);
        this.limiteEmpresas = other.optionalLimiteEmpresas().map(IntegerFilter::copy).orElse(null);
        this.limiteDepartamentos = other.optionalLimiteDepartamentos().map(IntegerFilter::copy).orElse(null);
        this.limiteAreaContabils = other.optionalLimiteAreaContabils().map(IntegerFilter::copy).orElse(null);
        this.limiteFaturamento = other.optionalLimiteFaturamento().map(DoubleFilter::copy).orElse(null);
        this.perfilContadorAreaContabilId = other.optionalPerfilContadorAreaContabilId().map(LongFilter::copy).orElse(null);
        this.contadorId = other.optionalContadorId().map(LongFilter::copy).orElse(null);
        this.perfilContadorDepartamentoId = other.optionalPerfilContadorDepartamentoId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PerfilContadorCriteria copy() {
        return new PerfilContadorCriteria(this);
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

    public StringFilter getPerfil() {
        return perfil;
    }

    public Optional<StringFilter> optionalPerfil() {
        return Optional.ofNullable(perfil);
    }

    public StringFilter perfil() {
        if (perfil == null) {
            setPerfil(new StringFilter());
        }
        return perfil;
    }

    public void setPerfil(StringFilter perfil) {
        this.perfil = perfil;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public Optional<StringFilter> optionalDescricao() {
        return Optional.ofNullable(descricao);
    }

    public StringFilter descricao() {
        if (descricao == null) {
            setDescricao(new StringFilter());
        }
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public IntegerFilter getLimiteEmpresas() {
        return limiteEmpresas;
    }

    public Optional<IntegerFilter> optionalLimiteEmpresas() {
        return Optional.ofNullable(limiteEmpresas);
    }

    public IntegerFilter limiteEmpresas() {
        if (limiteEmpresas == null) {
            setLimiteEmpresas(new IntegerFilter());
        }
        return limiteEmpresas;
    }

    public void setLimiteEmpresas(IntegerFilter limiteEmpresas) {
        this.limiteEmpresas = limiteEmpresas;
    }

    public IntegerFilter getLimiteDepartamentos() {
        return limiteDepartamentos;
    }

    public Optional<IntegerFilter> optionalLimiteDepartamentos() {
        return Optional.ofNullable(limiteDepartamentos);
    }

    public IntegerFilter limiteDepartamentos() {
        if (limiteDepartamentos == null) {
            setLimiteDepartamentos(new IntegerFilter());
        }
        return limiteDepartamentos;
    }

    public void setLimiteDepartamentos(IntegerFilter limiteDepartamentos) {
        this.limiteDepartamentos = limiteDepartamentos;
    }

    public IntegerFilter getLimiteAreaContabils() {
        return limiteAreaContabils;
    }

    public Optional<IntegerFilter> optionalLimiteAreaContabils() {
        return Optional.ofNullable(limiteAreaContabils);
    }

    public IntegerFilter limiteAreaContabils() {
        if (limiteAreaContabils == null) {
            setLimiteAreaContabils(new IntegerFilter());
        }
        return limiteAreaContabils;
    }

    public void setLimiteAreaContabils(IntegerFilter limiteAreaContabils) {
        this.limiteAreaContabils = limiteAreaContabils;
    }

    public DoubleFilter getLimiteFaturamento() {
        return limiteFaturamento;
    }

    public Optional<DoubleFilter> optionalLimiteFaturamento() {
        return Optional.ofNullable(limiteFaturamento);
    }

    public DoubleFilter limiteFaturamento() {
        if (limiteFaturamento == null) {
            setLimiteFaturamento(new DoubleFilter());
        }
        return limiteFaturamento;
    }

    public void setLimiteFaturamento(DoubleFilter limiteFaturamento) {
        this.limiteFaturamento = limiteFaturamento;
    }

    public LongFilter getPerfilContadorAreaContabilId() {
        return perfilContadorAreaContabilId;
    }

    public Optional<LongFilter> optionalPerfilContadorAreaContabilId() {
        return Optional.ofNullable(perfilContadorAreaContabilId);
    }

    public LongFilter perfilContadorAreaContabilId() {
        if (perfilContadorAreaContabilId == null) {
            setPerfilContadorAreaContabilId(new LongFilter());
        }
        return perfilContadorAreaContabilId;
    }

    public void setPerfilContadorAreaContabilId(LongFilter perfilContadorAreaContabilId) {
        this.perfilContadorAreaContabilId = perfilContadorAreaContabilId;
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

    public LongFilter getPerfilContadorDepartamentoId() {
        return perfilContadorDepartamentoId;
    }

    public Optional<LongFilter> optionalPerfilContadorDepartamentoId() {
        return Optional.ofNullable(perfilContadorDepartamentoId);
    }

    public LongFilter perfilContadorDepartamentoId() {
        if (perfilContadorDepartamentoId == null) {
            setPerfilContadorDepartamentoId(new LongFilter());
        }
        return perfilContadorDepartamentoId;
    }

    public void setPerfilContadorDepartamentoId(LongFilter perfilContadorDepartamentoId) {
        this.perfilContadorDepartamentoId = perfilContadorDepartamentoId;
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
        final PerfilContadorCriteria that = (PerfilContadorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(perfil, that.perfil) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(limiteEmpresas, that.limiteEmpresas) &&
            Objects.equals(limiteDepartamentos, that.limiteDepartamentos) &&
            Objects.equals(limiteAreaContabils, that.limiteAreaContabils) &&
            Objects.equals(limiteFaturamento, that.limiteFaturamento) &&
            Objects.equals(perfilContadorAreaContabilId, that.perfilContadorAreaContabilId) &&
            Objects.equals(contadorId, that.contadorId) &&
            Objects.equals(perfilContadorDepartamentoId, that.perfilContadorDepartamentoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            perfil,
            descricao,
            limiteEmpresas,
            limiteDepartamentos,
            limiteAreaContabils,
            limiteFaturamento,
            perfilContadorAreaContabilId,
            contadorId,
            perfilContadorDepartamentoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerfilContadorCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalPerfil().map(f -> "perfil=" + f + ", ").orElse("") +
            optionalDescricao().map(f -> "descricao=" + f + ", ").orElse("") +
            optionalLimiteEmpresas().map(f -> "limiteEmpresas=" + f + ", ").orElse("") +
            optionalLimiteDepartamentos().map(f -> "limiteDepartamentos=" + f + ", ").orElse("") +
            optionalLimiteAreaContabils().map(f -> "limiteAreaContabils=" + f + ", ").orElse("") +
            optionalLimiteFaturamento().map(f -> "limiteFaturamento=" + f + ", ").orElse("") +
            optionalPerfilContadorAreaContabilId().map(f -> "perfilContadorAreaContabilId=" + f + ", ").orElse("") +
            optionalContadorId().map(f -> "contadorId=" + f + ", ").orElse("") +
            optionalPerfilContadorDepartamentoId().map(f -> "perfilContadorDepartamentoId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
