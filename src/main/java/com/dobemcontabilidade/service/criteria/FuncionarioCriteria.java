package com.dobemcontabilidade.service.criteria;

import com.dobemcontabilidade.domain.enumeration.TipoFuncionarioEnum;
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
     * Class for filtering TipoFuncionarioEnum
     */
    public static class TipoFuncionarioEnumFilter extends Filter<TipoFuncionarioEnum> {

        public TipoFuncionarioEnumFilter() {}

        public TipoFuncionarioEnumFilter(TipoFuncionarioEnumFilter filter) {
            super(filter);
        }

        @Override
        public TipoFuncionarioEnumFilter copy() {
            return new TipoFuncionarioEnumFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter numeroPisNisPasep;

    private BooleanFilter reintegrado;

    private BooleanFilter primeiroEmprego;

    private BooleanFilter multiploVinculos;

    private StringFilter dataOpcaoFgts;

    private BooleanFilter filiacaoSindical;

    private StringFilter cnpjSindicato;

    private TipoFuncionarioEnumFilter tipoFuncionarioEnum;

    private LongFilter usuarioEmpresaId;

    private LongFilter estrangeiroId;

    private LongFilter contratoFuncionarioId;

    private LongFilter demissaoFuncionarioId;

    private LongFilter dependentesFuncionarioId;

    private LongFilter empresaVinculadaId;

    private LongFilter departamentoFuncionarioId;

    private LongFilter pessoaId;

    private LongFilter empresaId;

    private LongFilter profissaoId;

    private Boolean distinct;

    public FuncionarioCriteria() {}

    public FuncionarioCriteria(FuncionarioCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.numeroPisNisPasep = other.optionalNumeroPisNisPasep().map(IntegerFilter::copy).orElse(null);
        this.reintegrado = other.optionalReintegrado().map(BooleanFilter::copy).orElse(null);
        this.primeiroEmprego = other.optionalPrimeiroEmprego().map(BooleanFilter::copy).orElse(null);
        this.multiploVinculos = other.optionalMultiploVinculos().map(BooleanFilter::copy).orElse(null);
        this.dataOpcaoFgts = other.optionalDataOpcaoFgts().map(StringFilter::copy).orElse(null);
        this.filiacaoSindical = other.optionalFiliacaoSindical().map(BooleanFilter::copy).orElse(null);
        this.cnpjSindicato = other.optionalCnpjSindicato().map(StringFilter::copy).orElse(null);
        this.tipoFuncionarioEnum = other.optionalTipoFuncionarioEnum().map(TipoFuncionarioEnumFilter::copy).orElse(null);
        this.usuarioEmpresaId = other.optionalUsuarioEmpresaId().map(LongFilter::copy).orElse(null);
        this.estrangeiroId = other.optionalEstrangeiroId().map(LongFilter::copy).orElse(null);
        this.contratoFuncionarioId = other.optionalContratoFuncionarioId().map(LongFilter::copy).orElse(null);
        this.demissaoFuncionarioId = other.optionalDemissaoFuncionarioId().map(LongFilter::copy).orElse(null);
        this.dependentesFuncionarioId = other.optionalDependentesFuncionarioId().map(LongFilter::copy).orElse(null);
        this.empresaVinculadaId = other.optionalEmpresaVinculadaId().map(LongFilter::copy).orElse(null);
        this.departamentoFuncionarioId = other.optionalDepartamentoFuncionarioId().map(LongFilter::copy).orElse(null);
        this.pessoaId = other.optionalPessoaId().map(LongFilter::copy).orElse(null);
        this.empresaId = other.optionalEmpresaId().map(LongFilter::copy).orElse(null);
        this.profissaoId = other.optionalProfissaoId().map(LongFilter::copy).orElse(null);
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

    public IntegerFilter getNumeroPisNisPasep() {
        return numeroPisNisPasep;
    }

    public Optional<IntegerFilter> optionalNumeroPisNisPasep() {
        return Optional.ofNullable(numeroPisNisPasep);
    }

    public IntegerFilter numeroPisNisPasep() {
        if (numeroPisNisPasep == null) {
            setNumeroPisNisPasep(new IntegerFilter());
        }
        return numeroPisNisPasep;
    }

    public void setNumeroPisNisPasep(IntegerFilter numeroPisNisPasep) {
        this.numeroPisNisPasep = numeroPisNisPasep;
    }

    public BooleanFilter getReintegrado() {
        return reintegrado;
    }

    public Optional<BooleanFilter> optionalReintegrado() {
        return Optional.ofNullable(reintegrado);
    }

    public BooleanFilter reintegrado() {
        if (reintegrado == null) {
            setReintegrado(new BooleanFilter());
        }
        return reintegrado;
    }

    public void setReintegrado(BooleanFilter reintegrado) {
        this.reintegrado = reintegrado;
    }

    public BooleanFilter getPrimeiroEmprego() {
        return primeiroEmprego;
    }

    public Optional<BooleanFilter> optionalPrimeiroEmprego() {
        return Optional.ofNullable(primeiroEmprego);
    }

    public BooleanFilter primeiroEmprego() {
        if (primeiroEmprego == null) {
            setPrimeiroEmprego(new BooleanFilter());
        }
        return primeiroEmprego;
    }

    public void setPrimeiroEmprego(BooleanFilter primeiroEmprego) {
        this.primeiroEmprego = primeiroEmprego;
    }

    public BooleanFilter getMultiploVinculos() {
        return multiploVinculos;
    }

    public Optional<BooleanFilter> optionalMultiploVinculos() {
        return Optional.ofNullable(multiploVinculos);
    }

    public BooleanFilter multiploVinculos() {
        if (multiploVinculos == null) {
            setMultiploVinculos(new BooleanFilter());
        }
        return multiploVinculos;
    }

    public void setMultiploVinculos(BooleanFilter multiploVinculos) {
        this.multiploVinculos = multiploVinculos;
    }

    public StringFilter getDataOpcaoFgts() {
        return dataOpcaoFgts;
    }

    public Optional<StringFilter> optionalDataOpcaoFgts() {
        return Optional.ofNullable(dataOpcaoFgts);
    }

    public StringFilter dataOpcaoFgts() {
        if (dataOpcaoFgts == null) {
            setDataOpcaoFgts(new StringFilter());
        }
        return dataOpcaoFgts;
    }

    public void setDataOpcaoFgts(StringFilter dataOpcaoFgts) {
        this.dataOpcaoFgts = dataOpcaoFgts;
    }

    public BooleanFilter getFiliacaoSindical() {
        return filiacaoSindical;
    }

    public Optional<BooleanFilter> optionalFiliacaoSindical() {
        return Optional.ofNullable(filiacaoSindical);
    }

    public BooleanFilter filiacaoSindical() {
        if (filiacaoSindical == null) {
            setFiliacaoSindical(new BooleanFilter());
        }
        return filiacaoSindical;
    }

    public void setFiliacaoSindical(BooleanFilter filiacaoSindical) {
        this.filiacaoSindical = filiacaoSindical;
    }

    public StringFilter getCnpjSindicato() {
        return cnpjSindicato;
    }

    public Optional<StringFilter> optionalCnpjSindicato() {
        return Optional.ofNullable(cnpjSindicato);
    }

    public StringFilter cnpjSindicato() {
        if (cnpjSindicato == null) {
            setCnpjSindicato(new StringFilter());
        }
        return cnpjSindicato;
    }

    public void setCnpjSindicato(StringFilter cnpjSindicato) {
        this.cnpjSindicato = cnpjSindicato;
    }

    public TipoFuncionarioEnumFilter getTipoFuncionarioEnum() {
        return tipoFuncionarioEnum;
    }

    public Optional<TipoFuncionarioEnumFilter> optionalTipoFuncionarioEnum() {
        return Optional.ofNullable(tipoFuncionarioEnum);
    }

    public TipoFuncionarioEnumFilter tipoFuncionarioEnum() {
        if (tipoFuncionarioEnum == null) {
            setTipoFuncionarioEnum(new TipoFuncionarioEnumFilter());
        }
        return tipoFuncionarioEnum;
    }

    public void setTipoFuncionarioEnum(TipoFuncionarioEnumFilter tipoFuncionarioEnum) {
        this.tipoFuncionarioEnum = tipoFuncionarioEnum;
    }

    public LongFilter getUsuarioEmpresaId() {
        return usuarioEmpresaId;
    }

    public Optional<LongFilter> optionalUsuarioEmpresaId() {
        return Optional.ofNullable(usuarioEmpresaId);
    }

    public LongFilter usuarioEmpresaId() {
        if (usuarioEmpresaId == null) {
            setUsuarioEmpresaId(new LongFilter());
        }
        return usuarioEmpresaId;
    }

    public void setUsuarioEmpresaId(LongFilter usuarioEmpresaId) {
        this.usuarioEmpresaId = usuarioEmpresaId;
    }

    public LongFilter getEstrangeiroId() {
        return estrangeiroId;
    }

    public Optional<LongFilter> optionalEstrangeiroId() {
        return Optional.ofNullable(estrangeiroId);
    }

    public LongFilter estrangeiroId() {
        if (estrangeiroId == null) {
            setEstrangeiroId(new LongFilter());
        }
        return estrangeiroId;
    }

    public void setEstrangeiroId(LongFilter estrangeiroId) {
        this.estrangeiroId = estrangeiroId;
    }

    public LongFilter getContratoFuncionarioId() {
        return contratoFuncionarioId;
    }

    public Optional<LongFilter> optionalContratoFuncionarioId() {
        return Optional.ofNullable(contratoFuncionarioId);
    }

    public LongFilter contratoFuncionarioId() {
        if (contratoFuncionarioId == null) {
            setContratoFuncionarioId(new LongFilter());
        }
        return contratoFuncionarioId;
    }

    public void setContratoFuncionarioId(LongFilter contratoFuncionarioId) {
        this.contratoFuncionarioId = contratoFuncionarioId;
    }

    public LongFilter getDemissaoFuncionarioId() {
        return demissaoFuncionarioId;
    }

    public Optional<LongFilter> optionalDemissaoFuncionarioId() {
        return Optional.ofNullable(demissaoFuncionarioId);
    }

    public LongFilter demissaoFuncionarioId() {
        if (demissaoFuncionarioId == null) {
            setDemissaoFuncionarioId(new LongFilter());
        }
        return demissaoFuncionarioId;
    }

    public void setDemissaoFuncionarioId(LongFilter demissaoFuncionarioId) {
        this.demissaoFuncionarioId = demissaoFuncionarioId;
    }

    public LongFilter getDependentesFuncionarioId() {
        return dependentesFuncionarioId;
    }

    public Optional<LongFilter> optionalDependentesFuncionarioId() {
        return Optional.ofNullable(dependentesFuncionarioId);
    }

    public LongFilter dependentesFuncionarioId() {
        if (dependentesFuncionarioId == null) {
            setDependentesFuncionarioId(new LongFilter());
        }
        return dependentesFuncionarioId;
    }

    public void setDependentesFuncionarioId(LongFilter dependentesFuncionarioId) {
        this.dependentesFuncionarioId = dependentesFuncionarioId;
    }

    public LongFilter getEmpresaVinculadaId() {
        return empresaVinculadaId;
    }

    public Optional<LongFilter> optionalEmpresaVinculadaId() {
        return Optional.ofNullable(empresaVinculadaId);
    }

    public LongFilter empresaVinculadaId() {
        if (empresaVinculadaId == null) {
            setEmpresaVinculadaId(new LongFilter());
        }
        return empresaVinculadaId;
    }

    public void setEmpresaVinculadaId(LongFilter empresaVinculadaId) {
        this.empresaVinculadaId = empresaVinculadaId;
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
            Objects.equals(numeroPisNisPasep, that.numeroPisNisPasep) &&
            Objects.equals(reintegrado, that.reintegrado) &&
            Objects.equals(primeiroEmprego, that.primeiroEmprego) &&
            Objects.equals(multiploVinculos, that.multiploVinculos) &&
            Objects.equals(dataOpcaoFgts, that.dataOpcaoFgts) &&
            Objects.equals(filiacaoSindical, that.filiacaoSindical) &&
            Objects.equals(cnpjSindicato, that.cnpjSindicato) &&
            Objects.equals(tipoFuncionarioEnum, that.tipoFuncionarioEnum) &&
            Objects.equals(usuarioEmpresaId, that.usuarioEmpresaId) &&
            Objects.equals(estrangeiroId, that.estrangeiroId) &&
            Objects.equals(contratoFuncionarioId, that.contratoFuncionarioId) &&
            Objects.equals(demissaoFuncionarioId, that.demissaoFuncionarioId) &&
            Objects.equals(dependentesFuncionarioId, that.dependentesFuncionarioId) &&
            Objects.equals(empresaVinculadaId, that.empresaVinculadaId) &&
            Objects.equals(departamentoFuncionarioId, that.departamentoFuncionarioId) &&
            Objects.equals(pessoaId, that.pessoaId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(profissaoId, that.profissaoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            numeroPisNisPasep,
            reintegrado,
            primeiroEmprego,
            multiploVinculos,
            dataOpcaoFgts,
            filiacaoSindical,
            cnpjSindicato,
            tipoFuncionarioEnum,
            usuarioEmpresaId,
            estrangeiroId,
            contratoFuncionarioId,
            demissaoFuncionarioId,
            dependentesFuncionarioId,
            empresaVinculadaId,
            departamentoFuncionarioId,
            pessoaId,
            empresaId,
            profissaoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuncionarioCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNumeroPisNisPasep().map(f -> "numeroPisNisPasep=" + f + ", ").orElse("") +
            optionalReintegrado().map(f -> "reintegrado=" + f + ", ").orElse("") +
            optionalPrimeiroEmprego().map(f -> "primeiroEmprego=" + f + ", ").orElse("") +
            optionalMultiploVinculos().map(f -> "multiploVinculos=" + f + ", ").orElse("") +
            optionalDataOpcaoFgts().map(f -> "dataOpcaoFgts=" + f + ", ").orElse("") +
            optionalFiliacaoSindical().map(f -> "filiacaoSindical=" + f + ", ").orElse("") +
            optionalCnpjSindicato().map(f -> "cnpjSindicato=" + f + ", ").orElse("") +
            optionalTipoFuncionarioEnum().map(f -> "tipoFuncionarioEnum=" + f + ", ").orElse("") +
            optionalUsuarioEmpresaId().map(f -> "usuarioEmpresaId=" + f + ", ").orElse("") +
            optionalEstrangeiroId().map(f -> "estrangeiroId=" + f + ", ").orElse("") +
            optionalContratoFuncionarioId().map(f -> "contratoFuncionarioId=" + f + ", ").orElse("") +
            optionalDemissaoFuncionarioId().map(f -> "demissaoFuncionarioId=" + f + ", ").orElse("") +
            optionalDependentesFuncionarioId().map(f -> "dependentesFuncionarioId=" + f + ", ").orElse("") +
            optionalEmpresaVinculadaId().map(f -> "empresaVinculadaId=" + f + ", ").orElse("") +
            optionalDepartamentoFuncionarioId().map(f -> "departamentoFuncionarioId=" + f + ", ").orElse("") +
            optionalPessoaId().map(f -> "pessoaId=" + f + ", ").orElse("") +
            optionalEmpresaId().map(f -> "empresaId=" + f + ", ").orElse("") +
            optionalProfissaoId().map(f -> "profissaoId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
