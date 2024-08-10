package com.dobemcontabilidade.service.criteria;

import com.dobemcontabilidade.domain.enumeration.SituacaoPlanoContaAzulEnum;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.PlanoContaAzul} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.PlanoContaAzulResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /plano-conta-azuls?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanoContaAzulCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SituacaoPlanoContaAzulEnum
     */
    public static class SituacaoPlanoContaAzulEnumFilter extends Filter<SituacaoPlanoContaAzulEnum> {

        public SituacaoPlanoContaAzulEnumFilter() {}

        public SituacaoPlanoContaAzulEnumFilter(SituacaoPlanoContaAzulEnumFilter filter) {
            super(filter);
        }

        @Override
        public SituacaoPlanoContaAzulEnumFilter copy() {
            return new SituacaoPlanoContaAzulEnumFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private DoubleFilter valorBase;

    private IntegerFilter usuarios;

    private IntegerFilter boletos;

    private IntegerFilter notaFiscalProduto;

    private IntegerFilter notaFiscalServico;

    private IntegerFilter notaFiscalCe;

    private BooleanFilter suporte;

    private SituacaoPlanoContaAzulEnumFilter situacao;

    private LongFilter calculoPlanoAssinaturaId;

    private LongFilter assinaturaEmpresaId;

    private LongFilter descontoPlanoContaAzulId;

    private Boolean distinct;

    public PlanoContaAzulCriteria() {}

    public PlanoContaAzulCriteria(PlanoContaAzulCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.valorBase = other.optionalValorBase().map(DoubleFilter::copy).orElse(null);
        this.usuarios = other.optionalUsuarios().map(IntegerFilter::copy).orElse(null);
        this.boletos = other.optionalBoletos().map(IntegerFilter::copy).orElse(null);
        this.notaFiscalProduto = other.optionalNotaFiscalProduto().map(IntegerFilter::copy).orElse(null);
        this.notaFiscalServico = other.optionalNotaFiscalServico().map(IntegerFilter::copy).orElse(null);
        this.notaFiscalCe = other.optionalNotaFiscalCe().map(IntegerFilter::copy).orElse(null);
        this.suporte = other.optionalSuporte().map(BooleanFilter::copy).orElse(null);
        this.situacao = other.optionalSituacao().map(SituacaoPlanoContaAzulEnumFilter::copy).orElse(null);
        this.calculoPlanoAssinaturaId = other.optionalCalculoPlanoAssinaturaId().map(LongFilter::copy).orElse(null);
        this.assinaturaEmpresaId = other.optionalAssinaturaEmpresaId().map(LongFilter::copy).orElse(null);
        this.descontoPlanoContaAzulId = other.optionalDescontoPlanoContaAzulId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PlanoContaAzulCriteria copy() {
        return new PlanoContaAzulCriteria(this);
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

    public DoubleFilter getValorBase() {
        return valorBase;
    }

    public Optional<DoubleFilter> optionalValorBase() {
        return Optional.ofNullable(valorBase);
    }

    public DoubleFilter valorBase() {
        if (valorBase == null) {
            setValorBase(new DoubleFilter());
        }
        return valorBase;
    }

    public void setValorBase(DoubleFilter valorBase) {
        this.valorBase = valorBase;
    }

    public IntegerFilter getUsuarios() {
        return usuarios;
    }

    public Optional<IntegerFilter> optionalUsuarios() {
        return Optional.ofNullable(usuarios);
    }

    public IntegerFilter usuarios() {
        if (usuarios == null) {
            setUsuarios(new IntegerFilter());
        }
        return usuarios;
    }

    public void setUsuarios(IntegerFilter usuarios) {
        this.usuarios = usuarios;
    }

    public IntegerFilter getBoletos() {
        return boletos;
    }

    public Optional<IntegerFilter> optionalBoletos() {
        return Optional.ofNullable(boletos);
    }

    public IntegerFilter boletos() {
        if (boletos == null) {
            setBoletos(new IntegerFilter());
        }
        return boletos;
    }

    public void setBoletos(IntegerFilter boletos) {
        this.boletos = boletos;
    }

    public IntegerFilter getNotaFiscalProduto() {
        return notaFiscalProduto;
    }

    public Optional<IntegerFilter> optionalNotaFiscalProduto() {
        return Optional.ofNullable(notaFiscalProduto);
    }

    public IntegerFilter notaFiscalProduto() {
        if (notaFiscalProduto == null) {
            setNotaFiscalProduto(new IntegerFilter());
        }
        return notaFiscalProduto;
    }

    public void setNotaFiscalProduto(IntegerFilter notaFiscalProduto) {
        this.notaFiscalProduto = notaFiscalProduto;
    }

    public IntegerFilter getNotaFiscalServico() {
        return notaFiscalServico;
    }

    public Optional<IntegerFilter> optionalNotaFiscalServico() {
        return Optional.ofNullable(notaFiscalServico);
    }

    public IntegerFilter notaFiscalServico() {
        if (notaFiscalServico == null) {
            setNotaFiscalServico(new IntegerFilter());
        }
        return notaFiscalServico;
    }

    public void setNotaFiscalServico(IntegerFilter notaFiscalServico) {
        this.notaFiscalServico = notaFiscalServico;
    }

    public IntegerFilter getNotaFiscalCe() {
        return notaFiscalCe;
    }

    public Optional<IntegerFilter> optionalNotaFiscalCe() {
        return Optional.ofNullable(notaFiscalCe);
    }

    public IntegerFilter notaFiscalCe() {
        if (notaFiscalCe == null) {
            setNotaFiscalCe(new IntegerFilter());
        }
        return notaFiscalCe;
    }

    public void setNotaFiscalCe(IntegerFilter notaFiscalCe) {
        this.notaFiscalCe = notaFiscalCe;
    }

    public BooleanFilter getSuporte() {
        return suporte;
    }

    public Optional<BooleanFilter> optionalSuporte() {
        return Optional.ofNullable(suporte);
    }

    public BooleanFilter suporte() {
        if (suporte == null) {
            setSuporte(new BooleanFilter());
        }
        return suporte;
    }

    public void setSuporte(BooleanFilter suporte) {
        this.suporte = suporte;
    }

    public SituacaoPlanoContaAzulEnumFilter getSituacao() {
        return situacao;
    }

    public Optional<SituacaoPlanoContaAzulEnumFilter> optionalSituacao() {
        return Optional.ofNullable(situacao);
    }

    public SituacaoPlanoContaAzulEnumFilter situacao() {
        if (situacao == null) {
            setSituacao(new SituacaoPlanoContaAzulEnumFilter());
        }
        return situacao;
    }

    public void setSituacao(SituacaoPlanoContaAzulEnumFilter situacao) {
        this.situacao = situacao;
    }

    public LongFilter getCalculoPlanoAssinaturaId() {
        return calculoPlanoAssinaturaId;
    }

    public Optional<LongFilter> optionalCalculoPlanoAssinaturaId() {
        return Optional.ofNullable(calculoPlanoAssinaturaId);
    }

    public LongFilter calculoPlanoAssinaturaId() {
        if (calculoPlanoAssinaturaId == null) {
            setCalculoPlanoAssinaturaId(new LongFilter());
        }
        return calculoPlanoAssinaturaId;
    }

    public void setCalculoPlanoAssinaturaId(LongFilter calculoPlanoAssinaturaId) {
        this.calculoPlanoAssinaturaId = calculoPlanoAssinaturaId;
    }

    public LongFilter getAssinaturaEmpresaId() {
        return assinaturaEmpresaId;
    }

    public Optional<LongFilter> optionalAssinaturaEmpresaId() {
        return Optional.ofNullable(assinaturaEmpresaId);
    }

    public LongFilter assinaturaEmpresaId() {
        if (assinaturaEmpresaId == null) {
            setAssinaturaEmpresaId(new LongFilter());
        }
        return assinaturaEmpresaId;
    }

    public void setAssinaturaEmpresaId(LongFilter assinaturaEmpresaId) {
        this.assinaturaEmpresaId = assinaturaEmpresaId;
    }

    public LongFilter getDescontoPlanoContaAzulId() {
        return descontoPlanoContaAzulId;
    }

    public Optional<LongFilter> optionalDescontoPlanoContaAzulId() {
        return Optional.ofNullable(descontoPlanoContaAzulId);
    }

    public LongFilter descontoPlanoContaAzulId() {
        if (descontoPlanoContaAzulId == null) {
            setDescontoPlanoContaAzulId(new LongFilter());
        }
        return descontoPlanoContaAzulId;
    }

    public void setDescontoPlanoContaAzulId(LongFilter descontoPlanoContaAzulId) {
        this.descontoPlanoContaAzulId = descontoPlanoContaAzulId;
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
        final PlanoContaAzulCriteria that = (PlanoContaAzulCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(valorBase, that.valorBase) &&
            Objects.equals(usuarios, that.usuarios) &&
            Objects.equals(boletos, that.boletos) &&
            Objects.equals(notaFiscalProduto, that.notaFiscalProduto) &&
            Objects.equals(notaFiscalServico, that.notaFiscalServico) &&
            Objects.equals(notaFiscalCe, that.notaFiscalCe) &&
            Objects.equals(suporte, that.suporte) &&
            Objects.equals(situacao, that.situacao) &&
            Objects.equals(calculoPlanoAssinaturaId, that.calculoPlanoAssinaturaId) &&
            Objects.equals(assinaturaEmpresaId, that.assinaturaEmpresaId) &&
            Objects.equals(descontoPlanoContaAzulId, that.descontoPlanoContaAzulId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nome,
            valorBase,
            usuarios,
            boletos,
            notaFiscalProduto,
            notaFiscalServico,
            notaFiscalCe,
            suporte,
            situacao,
            calculoPlanoAssinaturaId,
            assinaturaEmpresaId,
            descontoPlanoContaAzulId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoContaAzulCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalValorBase().map(f -> "valorBase=" + f + ", ").orElse("") +
            optionalUsuarios().map(f -> "usuarios=" + f + ", ").orElse("") +
            optionalBoletos().map(f -> "boletos=" + f + ", ").orElse("") +
            optionalNotaFiscalProduto().map(f -> "notaFiscalProduto=" + f + ", ").orElse("") +
            optionalNotaFiscalServico().map(f -> "notaFiscalServico=" + f + ", ").orElse("") +
            optionalNotaFiscalCe().map(f -> "notaFiscalCe=" + f + ", ").orElse("") +
            optionalSuporte().map(f -> "suporte=" + f + ", ").orElse("") +
            optionalSituacao().map(f -> "situacao=" + f + ", ").orElse("") +
            optionalCalculoPlanoAssinaturaId().map(f -> "calculoPlanoAssinaturaId=" + f + ", ").orElse("") +
            optionalAssinaturaEmpresaId().map(f -> "assinaturaEmpresaId=" + f + ", ").orElse("") +
            optionalDescontoPlanoContaAzulId().map(f -> "descontoPlanoContaAzulId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
