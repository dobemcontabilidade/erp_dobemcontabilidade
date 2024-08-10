package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.OpcaoRazaoSocialEmpresa} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.OpcaoRazaoSocialEmpresaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /opcao-razao-social-empresas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OpcaoRazaoSocialEmpresaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private IntegerFilter ordem;

    private BooleanFilter selecionado;

    private LongFilter empresaId;

    private Boolean distinct;

    public OpcaoRazaoSocialEmpresaCriteria() {}

    public OpcaoRazaoSocialEmpresaCriteria(OpcaoRazaoSocialEmpresaCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.ordem = other.optionalOrdem().map(IntegerFilter::copy).orElse(null);
        this.selecionado = other.optionalSelecionado().map(BooleanFilter::copy).orElse(null);
        this.empresaId = other.optionalEmpresaId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public OpcaoRazaoSocialEmpresaCriteria copy() {
        return new OpcaoRazaoSocialEmpresaCriteria(this);
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

    public IntegerFilter getOrdem() {
        return ordem;
    }

    public Optional<IntegerFilter> optionalOrdem() {
        return Optional.ofNullable(ordem);
    }

    public IntegerFilter ordem() {
        if (ordem == null) {
            setOrdem(new IntegerFilter());
        }
        return ordem;
    }

    public void setOrdem(IntegerFilter ordem) {
        this.ordem = ordem;
    }

    public BooleanFilter getSelecionado() {
        return selecionado;
    }

    public Optional<BooleanFilter> optionalSelecionado() {
        return Optional.ofNullable(selecionado);
    }

    public BooleanFilter selecionado() {
        if (selecionado == null) {
            setSelecionado(new BooleanFilter());
        }
        return selecionado;
    }

    public void setSelecionado(BooleanFilter selecionado) {
        this.selecionado = selecionado;
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
        final OpcaoRazaoSocialEmpresaCriteria that = (OpcaoRazaoSocialEmpresaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(ordem, that.ordem) &&
            Objects.equals(selecionado, that.selecionado) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, ordem, selecionado, empresaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OpcaoRazaoSocialEmpresaCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalOrdem().map(f -> "ordem=" + f + ", ").orElse("") +
            optionalSelecionado().map(f -> "selecionado=" + f + ", ").orElse("") +
            optionalEmpresaId().map(f -> "empresaId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
