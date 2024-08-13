package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Funcionalidade} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.FuncionalidadeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /funcionalidades?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuncionalidadeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private BooleanFilter ativa;

    private LongFilter funcionalidadeGrupoAcessoPadraoId;

    private LongFilter funcionalidadeGrupoAcessoEmpresaId;

    private LongFilter moduloId;

    private Boolean distinct;

    public FuncionalidadeCriteria() {}

    public FuncionalidadeCriteria(FuncionalidadeCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.ativa = other.optionalAtiva().map(BooleanFilter::copy).orElse(null);
        this.funcionalidadeGrupoAcessoPadraoId = other.optionalFuncionalidadeGrupoAcessoPadraoId().map(LongFilter::copy).orElse(null);
        this.funcionalidadeGrupoAcessoEmpresaId = other.optionalFuncionalidadeGrupoAcessoEmpresaId().map(LongFilter::copy).orElse(null);
        this.moduloId = other.optionalModuloId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public FuncionalidadeCriteria copy() {
        return new FuncionalidadeCriteria(this);
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

    public BooleanFilter getAtiva() {
        return ativa;
    }

    public Optional<BooleanFilter> optionalAtiva() {
        return Optional.ofNullable(ativa);
    }

    public BooleanFilter ativa() {
        if (ativa == null) {
            setAtiva(new BooleanFilter());
        }
        return ativa;
    }

    public void setAtiva(BooleanFilter ativa) {
        this.ativa = ativa;
    }

    public LongFilter getFuncionalidadeGrupoAcessoPadraoId() {
        return funcionalidadeGrupoAcessoPadraoId;
    }

    public Optional<LongFilter> optionalFuncionalidadeGrupoAcessoPadraoId() {
        return Optional.ofNullable(funcionalidadeGrupoAcessoPadraoId);
    }

    public LongFilter funcionalidadeGrupoAcessoPadraoId() {
        if (funcionalidadeGrupoAcessoPadraoId == null) {
            setFuncionalidadeGrupoAcessoPadraoId(new LongFilter());
        }
        return funcionalidadeGrupoAcessoPadraoId;
    }

    public void setFuncionalidadeGrupoAcessoPadraoId(LongFilter funcionalidadeGrupoAcessoPadraoId) {
        this.funcionalidadeGrupoAcessoPadraoId = funcionalidadeGrupoAcessoPadraoId;
    }

    public LongFilter getFuncionalidadeGrupoAcessoEmpresaId() {
        return funcionalidadeGrupoAcessoEmpresaId;
    }

    public Optional<LongFilter> optionalFuncionalidadeGrupoAcessoEmpresaId() {
        return Optional.ofNullable(funcionalidadeGrupoAcessoEmpresaId);
    }

    public LongFilter funcionalidadeGrupoAcessoEmpresaId() {
        if (funcionalidadeGrupoAcessoEmpresaId == null) {
            setFuncionalidadeGrupoAcessoEmpresaId(new LongFilter());
        }
        return funcionalidadeGrupoAcessoEmpresaId;
    }

    public void setFuncionalidadeGrupoAcessoEmpresaId(LongFilter funcionalidadeGrupoAcessoEmpresaId) {
        this.funcionalidadeGrupoAcessoEmpresaId = funcionalidadeGrupoAcessoEmpresaId;
    }

    public LongFilter getModuloId() {
        return moduloId;
    }

    public Optional<LongFilter> optionalModuloId() {
        return Optional.ofNullable(moduloId);
    }

    public LongFilter moduloId() {
        if (moduloId == null) {
            setModuloId(new LongFilter());
        }
        return moduloId;
    }

    public void setModuloId(LongFilter moduloId) {
        this.moduloId = moduloId;
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
        final FuncionalidadeCriteria that = (FuncionalidadeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(ativa, that.ativa) &&
            Objects.equals(funcionalidadeGrupoAcessoPadraoId, that.funcionalidadeGrupoAcessoPadraoId) &&
            Objects.equals(funcionalidadeGrupoAcessoEmpresaId, that.funcionalidadeGrupoAcessoEmpresaId) &&
            Objects.equals(moduloId, that.moduloId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, ativa, funcionalidadeGrupoAcessoPadraoId, funcionalidadeGrupoAcessoEmpresaId, moduloId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuncionalidadeCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalAtiva().map(f -> "ativa=" + f + ", ").orElse("") +
            optionalFuncionalidadeGrupoAcessoPadraoId().map(f -> "funcionalidadeGrupoAcessoPadraoId=" + f + ", ").orElse("") +
            optionalFuncionalidadeGrupoAcessoEmpresaId().map(f -> "funcionalidadeGrupoAcessoEmpresaId=" + f + ", ").orElse("") +
            optionalModuloId().map(f -> "moduloId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
