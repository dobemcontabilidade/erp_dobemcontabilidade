package com.dobemcontabilidade.service.criteria;

import com.dobemcontabilidade.domain.enumeration.SituacaoFuncionarioEnum;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Funcionario} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.FuncionarioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /funcionarios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuncionarioCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SituacaoFuncionarioEnum
     */
    public static class SituacaoFuncionarioEnumFilter extends Filter<SituacaoFuncionarioEnum> {

        public SituacaoFuncionarioEnumFilter() {}

        public SituacaoFuncionarioEnumFilter(SituacaoFuncionarioEnumFilter filter) {
            super(filter);
        }

        @Override
        public SituacaoFuncionarioEnumFilter copy() {
            return new SituacaoFuncionarioEnumFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private DoubleFilter salario;

    private StringFilter ctps;

    private StringFilter cargo;

    private SituacaoFuncionarioEnumFilter situacao;

    private LongFilter pessoaId;

    private LongFilter departamentoFuncionarioId;

    private LongFilter empresaId;

    private Boolean distinct;

    public FuncionarioCriteria() {}

    public FuncionarioCriteria(FuncionarioCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.salario = other.optionalSalario().map(DoubleFilter::copy).orElse(null);
        this.ctps = other.optionalCtps().map(StringFilter::copy).orElse(null);
        this.cargo = other.optionalCargo().map(StringFilter::copy).orElse(null);
        this.situacao = other.optionalSituacao().map(SituacaoFuncionarioEnumFilter::copy).orElse(null);
        this.pessoaId = other.optionalPessoaId().map(LongFilter::copy).orElse(null);
        this.departamentoFuncionarioId = other.optionalDepartamentoFuncionarioId().map(LongFilter::copy).orElse(null);
        this.empresaId = other.optionalEmpresaId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public FuncionarioCriteria copy() {
        return new FuncionarioCriteria(this);
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

    public DoubleFilter getSalario() {
        return salario;
    }

    public Optional<DoubleFilter> optionalSalario() {
        return Optional.ofNullable(salario);
    }

    public DoubleFilter salario() {
        if (salario == null) {
            setSalario(new DoubleFilter());
        }
        return salario;
    }

    public void setSalario(DoubleFilter salario) {
        this.salario = salario;
    }

    public StringFilter getCtps() {
        return ctps;
    }

    public Optional<StringFilter> optionalCtps() {
        return Optional.ofNullable(ctps);
    }

    public StringFilter ctps() {
        if (ctps == null) {
            setCtps(new StringFilter());
        }
        return ctps;
    }

    public void setCtps(StringFilter ctps) {
        this.ctps = ctps;
    }

    public StringFilter getCargo() {
        return cargo;
    }

    public Optional<StringFilter> optionalCargo() {
        return Optional.ofNullable(cargo);
    }

    public StringFilter cargo() {
        if (cargo == null) {
            setCargo(new StringFilter());
        }
        return cargo;
    }

    public void setCargo(StringFilter cargo) {
        this.cargo = cargo;
    }

    public SituacaoFuncionarioEnumFilter getSituacao() {
        return situacao;
    }

    public Optional<SituacaoFuncionarioEnumFilter> optionalSituacao() {
        return Optional.ofNullable(situacao);
    }

    public SituacaoFuncionarioEnumFilter situacao() {
        if (situacao == null) {
            setSituacao(new SituacaoFuncionarioEnumFilter());
        }
        return situacao;
    }

    public void setSituacao(SituacaoFuncionarioEnumFilter situacao) {
        this.situacao = situacao;
    }

    public LongFilter getPessoaId() {
        return pessoaId;
    }

    public Optional<LongFilter> optionalPessoaId() {
        return Optional.ofNullable(pessoaId);
    }

    public LongFilter pessoaId() {
        if (pessoaId == null) {
            setPessoaId(new LongFilter());
        }
        return pessoaId;
    }

    public void setPessoaId(LongFilter pessoaId) {
        this.pessoaId = pessoaId;
    }

    public LongFilter getDepartamentoFuncionarioId() {
        return departamentoFuncionarioId;
    }

    public Optional<LongFilter> optionalDepartamentoFuncionarioId() {
        return Optional.ofNullable(departamentoFuncionarioId);
    }

    public LongFilter departamentoFuncionarioId() {
        if (departamentoFuncionarioId == null) {
            setDepartamentoFuncionarioId(new LongFilter());
        }
        return departamentoFuncionarioId;
    }

    public void setDepartamentoFuncionarioId(LongFilter departamentoFuncionarioId) {
        this.departamentoFuncionarioId = departamentoFuncionarioId;
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
        final FuncionarioCriteria that = (FuncionarioCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(salario, that.salario) &&
            Objects.equals(ctps, that.ctps) &&
            Objects.equals(cargo, that.cargo) &&
            Objects.equals(situacao, that.situacao) &&
            Objects.equals(pessoaId, that.pessoaId) &&
            Objects.equals(departamentoFuncionarioId, that.departamentoFuncionarioId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, salario, ctps, cargo, situacao, pessoaId, departamentoFuncionarioId, empresaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuncionarioCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalSalario().map(f -> "salario=" + f + ", ").orElse("") +
            optionalCtps().map(f -> "ctps=" + f + ", ").orElse("") +
            optionalCargo().map(f -> "cargo=" + f + ", ").orElse("") +
            optionalSituacao().map(f -> "situacao=" + f + ", ").orElse("") +
            optionalPessoaId().map(f -> "pessoaId=" + f + ", ").orElse("") +
            optionalDepartamentoFuncionarioId().map(f -> "departamentoFuncionarioId=" + f + ", ").orElse("") +
            optionalEmpresaId().map(f -> "empresaId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
