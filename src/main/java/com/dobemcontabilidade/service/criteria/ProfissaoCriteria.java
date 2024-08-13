package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Profissao} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.ProfissaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /profissaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProfissaoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private IntegerFilter cbo;

    private StringFilter categoria;

    private LongFilter funcionarioId;

    private LongFilter socioId;

    private Boolean distinct;

    public ProfissaoCriteria() {}

    public ProfissaoCriteria(ProfissaoCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.cbo = other.optionalCbo().map(IntegerFilter::copy).orElse(null);
        this.categoria = other.optionalCategoria().map(StringFilter::copy).orElse(null);
        this.funcionarioId = other.optionalFuncionarioId().map(LongFilter::copy).orElse(null);
        this.socioId = other.optionalSocioId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ProfissaoCriteria copy() {
        return new ProfissaoCriteria(this);
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

    public IntegerFilter getCbo() {
        return cbo;
    }

    public Optional<IntegerFilter> optionalCbo() {
        return Optional.ofNullable(cbo);
    }

    public IntegerFilter cbo() {
        if (cbo == null) {
            setCbo(new IntegerFilter());
        }
        return cbo;
    }

    public void setCbo(IntegerFilter cbo) {
        this.cbo = cbo;
    }

    public StringFilter getCategoria() {
        return categoria;
    }

    public Optional<StringFilter> optionalCategoria() {
        return Optional.ofNullable(categoria);
    }

    public StringFilter categoria() {
        if (categoria == null) {
            setCategoria(new StringFilter());
        }
        return categoria;
    }

    public void setCategoria(StringFilter categoria) {
        this.categoria = categoria;
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
        final ProfissaoCriteria that = (ProfissaoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(cbo, that.cbo) &&
            Objects.equals(categoria, that.categoria) &&
            Objects.equals(funcionarioId, that.funcionarioId) &&
            Objects.equals(socioId, that.socioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cbo, categoria, funcionarioId, socioId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfissaoCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalCbo().map(f -> "cbo=" + f + ", ").orElse("") +
            optionalCategoria().map(f -> "categoria=" + f + ", ").orElse("") +
            optionalFuncionarioId().map(f -> "funcionarioId=" + f + ", ").orElse("") +
            optionalSocioId().map(f -> "socioId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
