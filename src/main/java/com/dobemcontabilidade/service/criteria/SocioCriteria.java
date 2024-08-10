package com.dobemcontabilidade.service.criteria;

import com.dobemcontabilidade.domain.enumeration.FuncaoSocioEnum;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Socio} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.SocioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /socios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SocioCriteria implements Serializable, Criteria {

    /**
     * Class for filtering FuncaoSocioEnum
     */
    public static class FuncaoSocioEnumFilter extends Filter<FuncaoSocioEnum> {

        public FuncaoSocioEnumFilter() {}

        public FuncaoSocioEnumFilter(FuncaoSocioEnumFilter filter) {
            super(filter);
        }

        @Override
        public FuncaoSocioEnumFilter copy() {
            return new FuncaoSocioEnumFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private BooleanFilter prolabore;

    private DoubleFilter percentualSociedade;

    private BooleanFilter adminstrador;

    private BooleanFilter distribuicaoLucro;

    private BooleanFilter responsavelReceita;

    private DoubleFilter percentualDistribuicaoLucro;

    private FuncaoSocioEnumFilter funcaoSocio;

    private LongFilter pessoaId;

    private LongFilter profissaoId;

    private LongFilter empresaId;

    private Boolean distinct;

    public SocioCriteria() {}

    public SocioCriteria(SocioCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.prolabore = other.optionalProlabore().map(BooleanFilter::copy).orElse(null);
        this.percentualSociedade = other.optionalPercentualSociedade().map(DoubleFilter::copy).orElse(null);
        this.adminstrador = other.optionalAdminstrador().map(BooleanFilter::copy).orElse(null);
        this.distribuicaoLucro = other.optionalDistribuicaoLucro().map(BooleanFilter::copy).orElse(null);
        this.responsavelReceita = other.optionalResponsavelReceita().map(BooleanFilter::copy).orElse(null);
        this.percentualDistribuicaoLucro = other.optionalPercentualDistribuicaoLucro().map(DoubleFilter::copy).orElse(null);
        this.funcaoSocio = other.optionalFuncaoSocio().map(FuncaoSocioEnumFilter::copy).orElse(null);
        this.pessoaId = other.optionalPessoaId().map(LongFilter::copy).orElse(null);
        this.profissaoId = other.optionalProfissaoId().map(LongFilter::copy).orElse(null);
        this.empresaId = other.optionalEmpresaId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SocioCriteria copy() {
        return new SocioCriteria(this);
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

    public BooleanFilter getProlabore() {
        return prolabore;
    }

    public Optional<BooleanFilter> optionalProlabore() {
        return Optional.ofNullable(prolabore);
    }

    public BooleanFilter prolabore() {
        if (prolabore == null) {
            setProlabore(new BooleanFilter());
        }
        return prolabore;
    }

    public void setProlabore(BooleanFilter prolabore) {
        this.prolabore = prolabore;
    }

    public DoubleFilter getPercentualSociedade() {
        return percentualSociedade;
    }

    public Optional<DoubleFilter> optionalPercentualSociedade() {
        return Optional.ofNullable(percentualSociedade);
    }

    public DoubleFilter percentualSociedade() {
        if (percentualSociedade == null) {
            setPercentualSociedade(new DoubleFilter());
        }
        return percentualSociedade;
    }

    public void setPercentualSociedade(DoubleFilter percentualSociedade) {
        this.percentualSociedade = percentualSociedade;
    }

    public BooleanFilter getAdminstrador() {
        return adminstrador;
    }

    public Optional<BooleanFilter> optionalAdminstrador() {
        return Optional.ofNullable(adminstrador);
    }

    public BooleanFilter adminstrador() {
        if (adminstrador == null) {
            setAdminstrador(new BooleanFilter());
        }
        return adminstrador;
    }

    public void setAdminstrador(BooleanFilter adminstrador) {
        this.adminstrador = adminstrador;
    }

    public BooleanFilter getDistribuicaoLucro() {
        return distribuicaoLucro;
    }

    public Optional<BooleanFilter> optionalDistribuicaoLucro() {
        return Optional.ofNullable(distribuicaoLucro);
    }

    public BooleanFilter distribuicaoLucro() {
        if (distribuicaoLucro == null) {
            setDistribuicaoLucro(new BooleanFilter());
        }
        return distribuicaoLucro;
    }

    public void setDistribuicaoLucro(BooleanFilter distribuicaoLucro) {
        this.distribuicaoLucro = distribuicaoLucro;
    }

    public BooleanFilter getResponsavelReceita() {
        return responsavelReceita;
    }

    public Optional<BooleanFilter> optionalResponsavelReceita() {
        return Optional.ofNullable(responsavelReceita);
    }

    public BooleanFilter responsavelReceita() {
        if (responsavelReceita == null) {
            setResponsavelReceita(new BooleanFilter());
        }
        return responsavelReceita;
    }

    public void setResponsavelReceita(BooleanFilter responsavelReceita) {
        this.responsavelReceita = responsavelReceita;
    }

    public DoubleFilter getPercentualDistribuicaoLucro() {
        return percentualDistribuicaoLucro;
    }

    public Optional<DoubleFilter> optionalPercentualDistribuicaoLucro() {
        return Optional.ofNullable(percentualDistribuicaoLucro);
    }

    public DoubleFilter percentualDistribuicaoLucro() {
        if (percentualDistribuicaoLucro == null) {
            setPercentualDistribuicaoLucro(new DoubleFilter());
        }
        return percentualDistribuicaoLucro;
    }

    public void setPercentualDistribuicaoLucro(DoubleFilter percentualDistribuicaoLucro) {
        this.percentualDistribuicaoLucro = percentualDistribuicaoLucro;
    }

    public FuncaoSocioEnumFilter getFuncaoSocio() {
        return funcaoSocio;
    }

    public Optional<FuncaoSocioEnumFilter> optionalFuncaoSocio() {
        return Optional.ofNullable(funcaoSocio);
    }

    public FuncaoSocioEnumFilter funcaoSocio() {
        if (funcaoSocio == null) {
            setFuncaoSocio(new FuncaoSocioEnumFilter());
        }
        return funcaoSocio;
    }

    public void setFuncaoSocio(FuncaoSocioEnumFilter funcaoSocio) {
        this.funcaoSocio = funcaoSocio;
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

    public LongFilter getProfissaoId() {
        return profissaoId;
    }

    public Optional<LongFilter> optionalProfissaoId() {
        return Optional.ofNullable(profissaoId);
    }

    public LongFilter profissaoId() {
        if (profissaoId == null) {
            setProfissaoId(new LongFilter());
        }
        return profissaoId;
    }

    public void setProfissaoId(LongFilter profissaoId) {
        this.profissaoId = profissaoId;
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
        final SocioCriteria that = (SocioCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(prolabore, that.prolabore) &&
            Objects.equals(percentualSociedade, that.percentualSociedade) &&
            Objects.equals(adminstrador, that.adminstrador) &&
            Objects.equals(distribuicaoLucro, that.distribuicaoLucro) &&
            Objects.equals(responsavelReceita, that.responsavelReceita) &&
            Objects.equals(percentualDistribuicaoLucro, that.percentualDistribuicaoLucro) &&
            Objects.equals(funcaoSocio, that.funcaoSocio) &&
            Objects.equals(pessoaId, that.pessoaId) &&
            Objects.equals(profissaoId, that.profissaoId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nome,
            prolabore,
            percentualSociedade,
            adminstrador,
            distribuicaoLucro,
            responsavelReceita,
            percentualDistribuicaoLucro,
            funcaoSocio,
            pessoaId,
            profissaoId,
            empresaId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocioCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalProlabore().map(f -> "prolabore=" + f + ", ").orElse("") +
            optionalPercentualSociedade().map(f -> "percentualSociedade=" + f + ", ").orElse("") +
            optionalAdminstrador().map(f -> "adminstrador=" + f + ", ").orElse("") +
            optionalDistribuicaoLucro().map(f -> "distribuicaoLucro=" + f + ", ").orElse("") +
            optionalResponsavelReceita().map(f -> "responsavelReceita=" + f + ", ").orElse("") +
            optionalPercentualDistribuicaoLucro().map(f -> "percentualDistribuicaoLucro=" + f + ", ").orElse("") +
            optionalFuncaoSocio().map(f -> "funcaoSocio=" + f + ", ").orElse("") +
            optionalPessoaId().map(f -> "pessoaId=" + f + ", ").orElse("") +
            optionalProfissaoId().map(f -> "profissaoId=" + f + ", ").orElse("") +
            optionalEmpresaId().map(f -> "empresaId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
