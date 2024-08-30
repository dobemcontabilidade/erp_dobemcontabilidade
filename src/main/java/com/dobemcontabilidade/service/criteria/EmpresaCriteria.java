package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Empresa} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.EmpresaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /empresas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmpresaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter razaoSocial;

    private InstantFilter dataAbertura;

    private StringFilter urlContratoSocial;

    private DoubleFilter capitalSocial;

    private StringFilter cnae;

    private LongFilter pessoaJuridicaId;

    private LongFilter socioId;

    private LongFilter assinaturaEmpresaId;

    private LongFilter tributacaoId;

    private LongFilter ramoId;

    private LongFilter enquadramentoId;

    private Boolean distinct;

    public EmpresaCriteria() {}

    public EmpresaCriteria(EmpresaCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.razaoSocial = other.optionalRazaoSocial().map(StringFilter::copy).orElse(null);
        this.dataAbertura = other.optionalDataAbertura().map(InstantFilter::copy).orElse(null);
        this.urlContratoSocial = other.optionalUrlContratoSocial().map(StringFilter::copy).orElse(null);
        this.capitalSocial = other.optionalCapitalSocial().map(DoubleFilter::copy).orElse(null);
        this.cnae = other.optionalCnae().map(StringFilter::copy).orElse(null);
        this.pessoaJuridicaId = other.optionalPessoaJuridicaId().map(LongFilter::copy).orElse(null);
        this.socioId = other.optionalSocioId().map(LongFilter::copy).orElse(null);
        this.assinaturaEmpresaId = other.optionalAssinaturaEmpresaId().map(LongFilter::copy).orElse(null);
        this.tributacaoId = other.optionalTributacaoId().map(LongFilter::copy).orElse(null);
        this.ramoId = other.optionalRamoId().map(LongFilter::copy).orElse(null);
        this.enquadramentoId = other.optionalEnquadramentoId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public EmpresaCriteria copy() {
        return new EmpresaCriteria(this);
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

    public StringFilter getRazaoSocial() {
        return razaoSocial;
    }

    public Optional<StringFilter> optionalRazaoSocial() {
        return Optional.ofNullable(razaoSocial);
    }

    public StringFilter razaoSocial() {
        if (razaoSocial == null) {
            setRazaoSocial(new StringFilter());
        }
        return razaoSocial;
    }

    public void setRazaoSocial(StringFilter razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public InstantFilter getDataAbertura() {
        return dataAbertura;
    }

    public Optional<InstantFilter> optionalDataAbertura() {
        return Optional.ofNullable(dataAbertura);
    }

    public InstantFilter dataAbertura() {
        if (dataAbertura == null) {
            setDataAbertura(new InstantFilter());
        }
        return dataAbertura;
    }

    public void setDataAbertura(InstantFilter dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public StringFilter getUrlContratoSocial() {
        return urlContratoSocial;
    }

    public Optional<StringFilter> optionalUrlContratoSocial() {
        return Optional.ofNullable(urlContratoSocial);
    }

    public StringFilter urlContratoSocial() {
        if (urlContratoSocial == null) {
            setUrlContratoSocial(new StringFilter());
        }
        return urlContratoSocial;
    }

    public void setUrlContratoSocial(StringFilter urlContratoSocial) {
        this.urlContratoSocial = urlContratoSocial;
    }

    public DoubleFilter getCapitalSocial() {
        return capitalSocial;
    }

    public Optional<DoubleFilter> optionalCapitalSocial() {
        return Optional.ofNullable(capitalSocial);
    }

    public DoubleFilter capitalSocial() {
        if (capitalSocial == null) {
            setCapitalSocial(new DoubleFilter());
        }
        return capitalSocial;
    }

    public void setCapitalSocial(DoubleFilter capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    public StringFilter getCnae() {
        return cnae;
    }

    public Optional<StringFilter> optionalCnae() {
        return Optional.ofNullable(cnae);
    }

    public StringFilter cnae() {
        if (cnae == null) {
            setCnae(new StringFilter());
        }
        return cnae;
    }

    public void setCnae(StringFilter cnae) {
        this.cnae = cnae;
    }

    public LongFilter getPessoaJuridicaId() {
        return pessoaJuridicaId;
    }

    public Optional<LongFilter> optionalPessoaJuridicaId() {
        return Optional.ofNullable(pessoaJuridicaId);
    }

    public LongFilter pessoaJuridicaId() {
        if (pessoaJuridicaId == null) {
            setPessoaJuridicaId(new LongFilter());
        }
        return pessoaJuridicaId;
    }

    public void setPessoaJuridicaId(LongFilter pessoaJuridicaId) {
        this.pessoaJuridicaId = pessoaJuridicaId;
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

    public LongFilter getTributacaoId() {
        return tributacaoId;
    }

    public Optional<LongFilter> optionalTributacaoId() {
        return Optional.ofNullable(tributacaoId);
    }

    public LongFilter tributacaoId() {
        if (tributacaoId == null) {
            setTributacaoId(new LongFilter());
        }
        return tributacaoId;
    }

    public void setTributacaoId(LongFilter tributacaoId) {
        this.tributacaoId = tributacaoId;
    }

    public LongFilter getRamoId() {
        return ramoId;
    }

    public Optional<LongFilter> optionalRamoId() {
        return Optional.ofNullable(ramoId);
    }

    public LongFilter ramoId() {
        if (ramoId == null) {
            setRamoId(new LongFilter());
        }
        return ramoId;
    }

    public void setRamoId(LongFilter ramoId) {
        this.ramoId = ramoId;
    }

    public LongFilter getEnquadramentoId() {
        return enquadramentoId;
    }

    public Optional<LongFilter> optionalEnquadramentoId() {
        return Optional.ofNullable(enquadramentoId);
    }

    public LongFilter enquadramentoId() {
        if (enquadramentoId == null) {
            setEnquadramentoId(new LongFilter());
        }
        return enquadramentoId;
    }

    public void setEnquadramentoId(LongFilter enquadramentoId) {
        this.enquadramentoId = enquadramentoId;
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
        final EmpresaCriteria that = (EmpresaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(razaoSocial, that.razaoSocial) &&
            Objects.equals(dataAbertura, that.dataAbertura) &&
            Objects.equals(urlContratoSocial, that.urlContratoSocial) &&
            Objects.equals(capitalSocial, that.capitalSocial) &&
            Objects.equals(cnae, that.cnae) &&
            Objects.equals(pessoaJuridicaId, that.pessoaJuridicaId) &&
            Objects.equals(socioId, that.socioId) &&
            Objects.equals(assinaturaEmpresaId, that.assinaturaEmpresaId) &&
            Objects.equals(tributacaoId, that.tributacaoId) &&
            Objects.equals(ramoId, that.ramoId) &&
            Objects.equals(enquadramentoId, that.enquadramentoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            razaoSocial,
            dataAbertura,
            urlContratoSocial,
            capitalSocial,
            cnae,
            pessoaJuridicaId,
            socioId,
            assinaturaEmpresaId,
            tributacaoId,
            ramoId,
            enquadramentoId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpresaCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalRazaoSocial().map(f -> "razaoSocial=" + f + ", ").orElse("") +
            optionalDataAbertura().map(f -> "dataAbertura=" + f + ", ").orElse("") +
            optionalUrlContratoSocial().map(f -> "urlContratoSocial=" + f + ", ").orElse("") +
            optionalCapitalSocial().map(f -> "capitalSocial=" + f + ", ").orElse("") +
            optionalCnae().map(f -> "cnae=" + f + ", ").orElse("") +
            optionalPessoaJuridicaId().map(f -> "pessoaJuridicaId=" + f + ", ").orElse("") +
            optionalSocioId().map(f -> "socioId=" + f + ", ").orElse("") +
            optionalAssinaturaEmpresaId().map(f -> "assinaturaEmpresaId=" + f + ", ").orElse("") +
            optionalTributacaoId().map(f -> "tributacaoId=" + f + ", ").orElse("") +
            optionalRamoId().map(f -> "ramoId=" + f + ", ").orElse("") +
            optionalEnquadramentoId().map(f -> "enquadramentoId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
