package com.dobemcontabilidade.service.criteria;

import com.dobemcontabilidade.domain.enumeration.SituacaoPlanoContabilEnum;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.PlanoContabil} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.PlanoContabilResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /plano-contabils?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanoContabilCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SituacaoPlanoContabilEnum
     */
    public static class SituacaoPlanoContabilEnumFilter extends Filter<SituacaoPlanoContabilEnum> {

        public SituacaoPlanoContabilEnumFilter() {}

        public SituacaoPlanoContabilEnumFilter(SituacaoPlanoContabilEnumFilter filter) {
            super(filter);
        }

        @Override
        public SituacaoPlanoContabilEnumFilter copy() {
            return new SituacaoPlanoContabilEnumFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private DoubleFilter adicionalSocio;

    private DoubleFilter adicionalFuncionario;

    private IntegerFilter sociosIsentos;

    private DoubleFilter adicionalFaturamento;

    private DoubleFilter valorBaseFaturamento;

    private DoubleFilter valorBaseAbertura;

    private SituacaoPlanoContabilEnumFilter situacao;

    private LongFilter calculoPlanoAssinaturaId;

    private LongFilter assinaturaEmpresaId;

    private LongFilter descontoPlanoContabilId;

    private LongFilter adicionalRamoId;

    private LongFilter adicionalTributacaoId;

    private LongFilter termoContratoContabilId;

    private LongFilter adicionalEnquadramentoId;

    private LongFilter valorBaseRamoId;

    private LongFilter termoAdesaoEmpresaId;

    private Boolean distinct;

    public PlanoContabilCriteria() {}

    public PlanoContabilCriteria(PlanoContabilCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.adicionalSocio = other.optionalAdicionalSocio().map(DoubleFilter::copy).orElse(null);
        this.adicionalFuncionario = other.optionalAdicionalFuncionario().map(DoubleFilter::copy).orElse(null);
        this.sociosIsentos = other.optionalSociosIsentos().map(IntegerFilter::copy).orElse(null);
        this.adicionalFaturamento = other.optionalAdicionalFaturamento().map(DoubleFilter::copy).orElse(null);
        this.valorBaseFaturamento = other.optionalValorBaseFaturamento().map(DoubleFilter::copy).orElse(null);
        this.valorBaseAbertura = other.optionalValorBaseAbertura().map(DoubleFilter::copy).orElse(null);
        this.situacao = other.optionalSituacao().map(SituacaoPlanoContabilEnumFilter::copy).orElse(null);
        this.calculoPlanoAssinaturaId = other.optionalCalculoPlanoAssinaturaId().map(LongFilter::copy).orElse(null);
        this.assinaturaEmpresaId = other.optionalAssinaturaEmpresaId().map(LongFilter::copy).orElse(null);
        this.descontoPlanoContabilId = other.optionalDescontoPlanoContabilId().map(LongFilter::copy).orElse(null);
        this.adicionalRamoId = other.optionalAdicionalRamoId().map(LongFilter::copy).orElse(null);
        this.adicionalTributacaoId = other.optionalAdicionalTributacaoId().map(LongFilter::copy).orElse(null);
        this.termoContratoContabilId = other.optionalTermoContratoContabilId().map(LongFilter::copy).orElse(null);
        this.adicionalEnquadramentoId = other.optionalAdicionalEnquadramentoId().map(LongFilter::copy).orElse(null);
        this.valorBaseRamoId = other.optionalValorBaseRamoId().map(LongFilter::copy).orElse(null);
        this.termoAdesaoEmpresaId = other.optionalTermoAdesaoEmpresaId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PlanoContabilCriteria copy() {
        return new PlanoContabilCriteria(this);
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

    public DoubleFilter getAdicionalSocio() {
        return adicionalSocio;
    }

    public Optional<DoubleFilter> optionalAdicionalSocio() {
        return Optional.ofNullable(adicionalSocio);
    }

    public DoubleFilter adicionalSocio() {
        if (adicionalSocio == null) {
            setAdicionalSocio(new DoubleFilter());
        }
        return adicionalSocio;
    }

    public void setAdicionalSocio(DoubleFilter adicionalSocio) {
        this.adicionalSocio = adicionalSocio;
    }

    public DoubleFilter getAdicionalFuncionario() {
        return adicionalFuncionario;
    }

    public Optional<DoubleFilter> optionalAdicionalFuncionario() {
        return Optional.ofNullable(adicionalFuncionario);
    }

    public DoubleFilter adicionalFuncionario() {
        if (adicionalFuncionario == null) {
            setAdicionalFuncionario(new DoubleFilter());
        }
        return adicionalFuncionario;
    }

    public void setAdicionalFuncionario(DoubleFilter adicionalFuncionario) {
        this.adicionalFuncionario = adicionalFuncionario;
    }

    public IntegerFilter getSociosIsentos() {
        return sociosIsentos;
    }

    public Optional<IntegerFilter> optionalSociosIsentos() {
        return Optional.ofNullable(sociosIsentos);
    }

    public IntegerFilter sociosIsentos() {
        if (sociosIsentos == null) {
            setSociosIsentos(new IntegerFilter());
        }
        return sociosIsentos;
    }

    public void setSociosIsentos(IntegerFilter sociosIsentos) {
        this.sociosIsentos = sociosIsentos;
    }

    public DoubleFilter getAdicionalFaturamento() {
        return adicionalFaturamento;
    }

    public Optional<DoubleFilter> optionalAdicionalFaturamento() {
        return Optional.ofNullable(adicionalFaturamento);
    }

    public DoubleFilter adicionalFaturamento() {
        if (adicionalFaturamento == null) {
            setAdicionalFaturamento(new DoubleFilter());
        }
        return adicionalFaturamento;
    }

    public void setAdicionalFaturamento(DoubleFilter adicionalFaturamento) {
        this.adicionalFaturamento = adicionalFaturamento;
    }

    public DoubleFilter getValorBaseFaturamento() {
        return valorBaseFaturamento;
    }

    public Optional<DoubleFilter> optionalValorBaseFaturamento() {
        return Optional.ofNullable(valorBaseFaturamento);
    }

    public DoubleFilter valorBaseFaturamento() {
        if (valorBaseFaturamento == null) {
            setValorBaseFaturamento(new DoubleFilter());
        }
        return valorBaseFaturamento;
    }

    public void setValorBaseFaturamento(DoubleFilter valorBaseFaturamento) {
        this.valorBaseFaturamento = valorBaseFaturamento;
    }

    public DoubleFilter getValorBaseAbertura() {
        return valorBaseAbertura;
    }

    public Optional<DoubleFilter> optionalValorBaseAbertura() {
        return Optional.ofNullable(valorBaseAbertura);
    }

    public DoubleFilter valorBaseAbertura() {
        if (valorBaseAbertura == null) {
            setValorBaseAbertura(new DoubleFilter());
        }
        return valorBaseAbertura;
    }

    public void setValorBaseAbertura(DoubleFilter valorBaseAbertura) {
        this.valorBaseAbertura = valorBaseAbertura;
    }

    public SituacaoPlanoContabilEnumFilter getSituacao() {
        return situacao;
    }

    public Optional<SituacaoPlanoContabilEnumFilter> optionalSituacao() {
        return Optional.ofNullable(situacao);
    }

    public SituacaoPlanoContabilEnumFilter situacao() {
        if (situacao == null) {
            setSituacao(new SituacaoPlanoContabilEnumFilter());
        }
        return situacao;
    }

    public void setSituacao(SituacaoPlanoContabilEnumFilter situacao) {
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

    public LongFilter getDescontoPlanoContabilId() {
        return descontoPlanoContabilId;
    }

    public Optional<LongFilter> optionalDescontoPlanoContabilId() {
        return Optional.ofNullable(descontoPlanoContabilId);
    }

    public LongFilter descontoPlanoContabilId() {
        if (descontoPlanoContabilId == null) {
            setDescontoPlanoContabilId(new LongFilter());
        }
        return descontoPlanoContabilId;
    }

    public void setDescontoPlanoContabilId(LongFilter descontoPlanoContabilId) {
        this.descontoPlanoContabilId = descontoPlanoContabilId;
    }

    public LongFilter getAdicionalRamoId() {
        return adicionalRamoId;
    }

    public Optional<LongFilter> optionalAdicionalRamoId() {
        return Optional.ofNullable(adicionalRamoId);
    }

    public LongFilter adicionalRamoId() {
        if (adicionalRamoId == null) {
            setAdicionalRamoId(new LongFilter());
        }
        return adicionalRamoId;
    }

    public void setAdicionalRamoId(LongFilter adicionalRamoId) {
        this.adicionalRamoId = adicionalRamoId;
    }

    public LongFilter getAdicionalTributacaoId() {
        return adicionalTributacaoId;
    }

    public Optional<LongFilter> optionalAdicionalTributacaoId() {
        return Optional.ofNullable(adicionalTributacaoId);
    }

    public LongFilter adicionalTributacaoId() {
        if (adicionalTributacaoId == null) {
            setAdicionalTributacaoId(new LongFilter());
        }
        return adicionalTributacaoId;
    }

    public void setAdicionalTributacaoId(LongFilter adicionalTributacaoId) {
        this.adicionalTributacaoId = adicionalTributacaoId;
    }

    public LongFilter getTermoContratoContabilId() {
        return termoContratoContabilId;
    }

    public Optional<LongFilter> optionalTermoContratoContabilId() {
        return Optional.ofNullable(termoContratoContabilId);
    }

    public LongFilter termoContratoContabilId() {
        if (termoContratoContabilId == null) {
            setTermoContratoContabilId(new LongFilter());
        }
        return termoContratoContabilId;
    }

    public void setTermoContratoContabilId(LongFilter termoContratoContabilId) {
        this.termoContratoContabilId = termoContratoContabilId;
    }

    public LongFilter getAdicionalEnquadramentoId() {
        return adicionalEnquadramentoId;
    }

    public Optional<LongFilter> optionalAdicionalEnquadramentoId() {
        return Optional.ofNullable(adicionalEnquadramentoId);
    }

    public LongFilter adicionalEnquadramentoId() {
        if (adicionalEnquadramentoId == null) {
            setAdicionalEnquadramentoId(new LongFilter());
        }
        return adicionalEnquadramentoId;
    }

    public void setAdicionalEnquadramentoId(LongFilter adicionalEnquadramentoId) {
        this.adicionalEnquadramentoId = adicionalEnquadramentoId;
    }

    public LongFilter getValorBaseRamoId() {
        return valorBaseRamoId;
    }

    public Optional<LongFilter> optionalValorBaseRamoId() {
        return Optional.ofNullable(valorBaseRamoId);
    }

    public LongFilter valorBaseRamoId() {
        if (valorBaseRamoId == null) {
            setValorBaseRamoId(new LongFilter());
        }
        return valorBaseRamoId;
    }

    public void setValorBaseRamoId(LongFilter valorBaseRamoId) {
        this.valorBaseRamoId = valorBaseRamoId;
    }

    public LongFilter getTermoAdesaoEmpresaId() {
        return termoAdesaoEmpresaId;
    }

    public Optional<LongFilter> optionalTermoAdesaoEmpresaId() {
        return Optional.ofNullable(termoAdesaoEmpresaId);
    }

    public LongFilter termoAdesaoEmpresaId() {
        if (termoAdesaoEmpresaId == null) {
            setTermoAdesaoEmpresaId(new LongFilter());
        }
        return termoAdesaoEmpresaId;
    }

    public void setTermoAdesaoEmpresaId(LongFilter termoAdesaoEmpresaId) {
        this.termoAdesaoEmpresaId = termoAdesaoEmpresaId;
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
        final PlanoContabilCriteria that = (PlanoContabilCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(adicionalSocio, that.adicionalSocio) &&
            Objects.equals(adicionalFuncionario, that.adicionalFuncionario) &&
            Objects.equals(sociosIsentos, that.sociosIsentos) &&
            Objects.equals(adicionalFaturamento, that.adicionalFaturamento) &&
            Objects.equals(valorBaseFaturamento, that.valorBaseFaturamento) &&
            Objects.equals(valorBaseAbertura, that.valorBaseAbertura) &&
            Objects.equals(situacao, that.situacao) &&
            Objects.equals(calculoPlanoAssinaturaId, that.calculoPlanoAssinaturaId) &&
            Objects.equals(assinaturaEmpresaId, that.assinaturaEmpresaId) &&
            Objects.equals(descontoPlanoContabilId, that.descontoPlanoContabilId) &&
            Objects.equals(adicionalRamoId, that.adicionalRamoId) &&
            Objects.equals(adicionalTributacaoId, that.adicionalTributacaoId) &&
            Objects.equals(termoContratoContabilId, that.termoContratoContabilId) &&
            Objects.equals(adicionalEnquadramentoId, that.adicionalEnquadramentoId) &&
            Objects.equals(valorBaseRamoId, that.valorBaseRamoId) &&
            Objects.equals(termoAdesaoEmpresaId, that.termoAdesaoEmpresaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nome,
            adicionalSocio,
            adicionalFuncionario,
            sociosIsentos,
            adicionalFaturamento,
            valorBaseFaturamento,
            valorBaseAbertura,
            situacao,
            calculoPlanoAssinaturaId,
            assinaturaEmpresaId,
            descontoPlanoContabilId,
            adicionalRamoId,
            adicionalTributacaoId,
            termoContratoContabilId,
            adicionalEnquadramentoId,
            valorBaseRamoId,
            termoAdesaoEmpresaId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoContabilCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalAdicionalSocio().map(f -> "adicionalSocio=" + f + ", ").orElse("") +
            optionalAdicionalFuncionario().map(f -> "adicionalFuncionario=" + f + ", ").orElse("") +
            optionalSociosIsentos().map(f -> "sociosIsentos=" + f + ", ").orElse("") +
            optionalAdicionalFaturamento().map(f -> "adicionalFaturamento=" + f + ", ").orElse("") +
            optionalValorBaseFaturamento().map(f -> "valorBaseFaturamento=" + f + ", ").orElse("") +
            optionalValorBaseAbertura().map(f -> "valorBaseAbertura=" + f + ", ").orElse("") +
            optionalSituacao().map(f -> "situacao=" + f + ", ").orElse("") +
            optionalCalculoPlanoAssinaturaId().map(f -> "calculoPlanoAssinaturaId=" + f + ", ").orElse("") +
            optionalAssinaturaEmpresaId().map(f -> "assinaturaEmpresaId=" + f + ", ").orElse("") +
            optionalDescontoPlanoContabilId().map(f -> "descontoPlanoContabilId=" + f + ", ").orElse("") +
            optionalAdicionalRamoId().map(f -> "adicionalRamoId=" + f + ", ").orElse("") +
            optionalAdicionalTributacaoId().map(f -> "adicionalTributacaoId=" + f + ", ").orElse("") +
            optionalTermoContratoContabilId().map(f -> "termoContratoContabilId=" + f + ", ").orElse("") +
            optionalAdicionalEnquadramentoId().map(f -> "adicionalEnquadramentoId=" + f + ", ").orElse("") +
            optionalValorBaseRamoId().map(f -> "valorBaseRamoId=" + f + ", ").orElse("") +
            optionalTermoAdesaoEmpresaId().map(f -> "termoAdesaoEmpresaId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
