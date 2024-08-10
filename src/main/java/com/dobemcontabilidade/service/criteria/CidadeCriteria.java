package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Cidade} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.CidadeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cidades?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CidadeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private BooleanFilter contratacao;

    private BooleanFilter abertura;

    private LongFilter enderecoPessoaId;

    private LongFilter enderecoEmpresaId;

    private LongFilter estadoId;

    private Boolean distinct;

    public CidadeCriteria() {}

    public CidadeCriteria(CidadeCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.contratacao = other.optionalContratacao().map(BooleanFilter::copy).orElse(null);
        this.abertura = other.optionalAbertura().map(BooleanFilter::copy).orElse(null);
        this.enderecoPessoaId = other.optionalEnderecoPessoaId().map(LongFilter::copy).orElse(null);
        this.enderecoEmpresaId = other.optionalEnderecoEmpresaId().map(LongFilter::copy).orElse(null);
        this.estadoId = other.optionalEstadoId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public CidadeCriteria copy() {
        return new CidadeCriteria(this);
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

    public BooleanFilter getContratacao() {
        return contratacao;
    }

    public Optional<BooleanFilter> optionalContratacao() {
        return Optional.ofNullable(contratacao);
    }

    public BooleanFilter contratacao() {
        if (contratacao == null) {
            setContratacao(new BooleanFilter());
        }
        return contratacao;
    }

    public void setContratacao(BooleanFilter contratacao) {
        this.contratacao = contratacao;
    }

    public BooleanFilter getAbertura() {
        return abertura;
    }

    public Optional<BooleanFilter> optionalAbertura() {
        return Optional.ofNullable(abertura);
    }

    public BooleanFilter abertura() {
        if (abertura == null) {
            setAbertura(new BooleanFilter());
        }
        return abertura;
    }

    public void setAbertura(BooleanFilter abertura) {
        this.abertura = abertura;
    }

    public LongFilter getEnderecoPessoaId() {
        return enderecoPessoaId;
    }

    public Optional<LongFilter> optionalEnderecoPessoaId() {
        return Optional.ofNullable(enderecoPessoaId);
    }

    public LongFilter enderecoPessoaId() {
        if (enderecoPessoaId == null) {
            setEnderecoPessoaId(new LongFilter());
        }
        return enderecoPessoaId;
    }

    public void setEnderecoPessoaId(LongFilter enderecoPessoaId) {
        this.enderecoPessoaId = enderecoPessoaId;
    }

    public LongFilter getEnderecoEmpresaId() {
        return enderecoEmpresaId;
    }

    public Optional<LongFilter> optionalEnderecoEmpresaId() {
        return Optional.ofNullable(enderecoEmpresaId);
    }

    public LongFilter enderecoEmpresaId() {
        if (enderecoEmpresaId == null) {
            setEnderecoEmpresaId(new LongFilter());
        }
        return enderecoEmpresaId;
    }

    public void setEnderecoEmpresaId(LongFilter enderecoEmpresaId) {
        this.enderecoEmpresaId = enderecoEmpresaId;
    }

    public LongFilter getEstadoId() {
        return estadoId;
    }

    public Optional<LongFilter> optionalEstadoId() {
        return Optional.ofNullable(estadoId);
    }

    public LongFilter estadoId() {
        if (estadoId == null) {
            setEstadoId(new LongFilter());
        }
        return estadoId;
    }

    public void setEstadoId(LongFilter estadoId) {
        this.estadoId = estadoId;
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
        final CidadeCriteria that = (CidadeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(contratacao, that.contratacao) &&
            Objects.equals(abertura, that.abertura) &&
            Objects.equals(enderecoPessoaId, that.enderecoPessoaId) &&
            Objects.equals(enderecoEmpresaId, that.enderecoEmpresaId) &&
            Objects.equals(estadoId, that.estadoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, contratacao, abertura, enderecoPessoaId, enderecoEmpresaId, estadoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CidadeCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalContratacao().map(f -> "contratacao=" + f + ", ").orElse("") +
            optionalAbertura().map(f -> "abertura=" + f + ", ").orElse("") +
            optionalEnderecoPessoaId().map(f -> "enderecoPessoaId=" + f + ", ").orElse("") +
            optionalEnderecoEmpresaId().map(f -> "enderecoEmpresaId=" + f + ", ").orElse("") +
            optionalEstadoId().map(f -> "estadoId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
