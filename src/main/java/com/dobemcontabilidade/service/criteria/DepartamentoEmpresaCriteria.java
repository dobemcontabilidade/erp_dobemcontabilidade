package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.DepartamentoEmpresa} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.DepartamentoEmpresaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /departamento-empresas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DepartamentoEmpresaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter pontuacao;

    private StringFilter depoimento;

    private StringFilter reclamacao;

    private LongFilter departamentoId;

    private LongFilter empresaId;

    private LongFilter contadorId;

    private Boolean distinct;

    public DepartamentoEmpresaCriteria() {}

    public DepartamentoEmpresaCriteria(DepartamentoEmpresaCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.pontuacao = other.optionalPontuacao().map(DoubleFilter::copy).orElse(null);
        this.depoimento = other.optionalDepoimento().map(StringFilter::copy).orElse(null);
        this.reclamacao = other.optionalReclamacao().map(StringFilter::copy).orElse(null);
        this.departamentoId = other.optionalDepartamentoId().map(LongFilter::copy).orElse(null);
        this.empresaId = other.optionalEmpresaId().map(LongFilter::copy).orElse(null);
        this.contadorId = other.optionalContadorId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public DepartamentoEmpresaCriteria copy() {
        return new DepartamentoEmpresaCriteria(this);
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

    public DoubleFilter getPontuacao() {
        return pontuacao;
    }

    public Optional<DoubleFilter> optionalPontuacao() {
        return Optional.ofNullable(pontuacao);
    }

    public DoubleFilter pontuacao() {
        if (pontuacao == null) {
            setPontuacao(new DoubleFilter());
        }
        return pontuacao;
    }

    public void setPontuacao(DoubleFilter pontuacao) {
        this.pontuacao = pontuacao;
    }

    public StringFilter getDepoimento() {
        return depoimento;
    }

    public Optional<StringFilter> optionalDepoimento() {
        return Optional.ofNullable(depoimento);
    }

    public StringFilter depoimento() {
        if (depoimento == null) {
            setDepoimento(new StringFilter());
        }
        return depoimento;
    }

    public void setDepoimento(StringFilter depoimento) {
        this.depoimento = depoimento;
    }

    public StringFilter getReclamacao() {
        return reclamacao;
    }

    public Optional<StringFilter> optionalReclamacao() {
        return Optional.ofNullable(reclamacao);
    }

    public StringFilter reclamacao() {
        if (reclamacao == null) {
            setReclamacao(new StringFilter());
        }
        return reclamacao;
    }

    public void setReclamacao(StringFilter reclamacao) {
        this.reclamacao = reclamacao;
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
        final DepartamentoEmpresaCriteria that = (DepartamentoEmpresaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(pontuacao, that.pontuacao) &&
            Objects.equals(depoimento, that.depoimento) &&
            Objects.equals(reclamacao, that.reclamacao) &&
            Objects.equals(departamentoId, that.departamentoId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(contadorId, that.contadorId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pontuacao, depoimento, reclamacao, departamentoId, empresaId, contadorId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartamentoEmpresaCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalPontuacao().map(f -> "pontuacao=" + f + ", ").orElse("") +
            optionalDepoimento().map(f -> "depoimento=" + f + ", ").orElse("") +
            optionalReclamacao().map(f -> "reclamacao=" + f + ", ").orElse("") +
            optionalDepartamentoId().map(f -> "departamentoId=" + f + ", ").orElse("") +
            optionalEmpresaId().map(f -> "empresaId=" + f + ", ").orElse("") +
            optionalContadorId().map(f -> "contadorId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
