package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.PerfilAcessoUsuario} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.PerfilAcessoUsuarioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /perfil-acesso-usuarios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerfilAcessoUsuarioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private BooleanFilter autorizado;

    private InstantFilter dataExpiracao;

    private Boolean distinct;

    public PerfilAcessoUsuarioCriteria() {}

    public PerfilAcessoUsuarioCriteria(PerfilAcessoUsuarioCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.autorizado = other.optionalAutorizado().map(BooleanFilter::copy).orElse(null);
        this.dataExpiracao = other.optionalDataExpiracao().map(InstantFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PerfilAcessoUsuarioCriteria copy() {
        return new PerfilAcessoUsuarioCriteria(this);
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

    public BooleanFilter getAutorizado() {
        return autorizado;
    }

    public Optional<BooleanFilter> optionalAutorizado() {
        return Optional.ofNullable(autorizado);
    }

    public BooleanFilter autorizado() {
        if (autorizado == null) {
            setAutorizado(new BooleanFilter());
        }
        return autorizado;
    }

    public void setAutorizado(BooleanFilter autorizado) {
        this.autorizado = autorizado;
    }

    public InstantFilter getDataExpiracao() {
        return dataExpiracao;
    }

    public Optional<InstantFilter> optionalDataExpiracao() {
        return Optional.ofNullable(dataExpiracao);
    }

    public InstantFilter dataExpiracao() {
        if (dataExpiracao == null) {
            setDataExpiracao(new InstantFilter());
        }
        return dataExpiracao;
    }

    public void setDataExpiracao(InstantFilter dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
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
        final PerfilAcessoUsuarioCriteria that = (PerfilAcessoUsuarioCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(autorizado, that.autorizado) &&
            Objects.equals(dataExpiracao, that.dataExpiracao) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, autorizado, dataExpiracao, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerfilAcessoUsuarioCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalAutorizado().map(f -> "autorizado=" + f + ", ").orElse("") +
            optionalDataExpiracao().map(f -> "dataExpiracao=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
