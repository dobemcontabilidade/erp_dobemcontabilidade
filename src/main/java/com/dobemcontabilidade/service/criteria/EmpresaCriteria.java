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

    private StringFilter nomeFantasia;

    private StringFilter cnpj;

    private InstantFilter dataAbertura;

    private StringFilter urlContratoSocial;

    private DoubleFilter capitalSocial;

    private LongFilter funcionarioId;

    private LongFilter anexoEmpresaId;

    private LongFilter ordemServicoId;

    private LongFilter anexoRequeridoEmpresaId;

    private LongFilter impostoEmpresaId;

    private LongFilter parcelamentoImpostoId;

    private LongFilter assinaturaEmpresaId;

    private LongFilter departamentoEmpresaId;

    private LongFilter tarefaEmpresaId;

    private LongFilter enderecoEmpresaId;

    private LongFilter atividadeEmpresaId;

    private LongFilter socioId;

    private LongFilter certificadoDigitalId;

    private LongFilter opcaoRazaoSocialEmpresaId;

    private LongFilter opcaoNomeFantasiaEmpresaId;

    private LongFilter termoAdesaoEmpresaId;

    private LongFilter segmentoCnaeId;

    private LongFilter empresaModeloId;

    private Boolean distinct;

    public EmpresaCriteria() {}

    public EmpresaCriteria(EmpresaCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.razaoSocial = other.optionalRazaoSocial().map(StringFilter::copy).orElse(null);
        this.nomeFantasia = other.optionalNomeFantasia().map(StringFilter::copy).orElse(null);
        this.cnpj = other.optionalCnpj().map(StringFilter::copy).orElse(null);
        this.dataAbertura = other.optionalDataAbertura().map(InstantFilter::copy).orElse(null);
        this.urlContratoSocial = other.optionalUrlContratoSocial().map(StringFilter::copy).orElse(null);
        this.capitalSocial = other.optionalCapitalSocial().map(DoubleFilter::copy).orElse(null);
        this.funcionarioId = other.optionalFuncionarioId().map(LongFilter::copy).orElse(null);
        this.anexoEmpresaId = other.optionalAnexoEmpresaId().map(LongFilter::copy).orElse(null);
        this.ordemServicoId = other.optionalOrdemServicoId().map(LongFilter::copy).orElse(null);
        this.anexoRequeridoEmpresaId = other.optionalAnexoRequeridoEmpresaId().map(LongFilter::copy).orElse(null);
        this.impostoEmpresaId = other.optionalImpostoEmpresaId().map(LongFilter::copy).orElse(null);
        this.parcelamentoImpostoId = other.optionalParcelamentoImpostoId().map(LongFilter::copy).orElse(null);
        this.assinaturaEmpresaId = other.optionalAssinaturaEmpresaId().map(LongFilter::copy).orElse(null);
        this.departamentoEmpresaId = other.optionalDepartamentoEmpresaId().map(LongFilter::copy).orElse(null);
        this.tarefaEmpresaId = other.optionalTarefaEmpresaId().map(LongFilter::copy).orElse(null);
        this.enderecoEmpresaId = other.optionalEnderecoEmpresaId().map(LongFilter::copy).orElse(null);
        this.atividadeEmpresaId = other.optionalAtividadeEmpresaId().map(LongFilter::copy).orElse(null);
        this.socioId = other.optionalSocioId().map(LongFilter::copy).orElse(null);
        this.certificadoDigitalId = other.optionalCertificadoDigitalId().map(LongFilter::copy).orElse(null);
        this.opcaoRazaoSocialEmpresaId = other.optionalOpcaoRazaoSocialEmpresaId().map(LongFilter::copy).orElse(null);
        this.opcaoNomeFantasiaEmpresaId = other.optionalOpcaoNomeFantasiaEmpresaId().map(LongFilter::copy).orElse(null);
        this.termoAdesaoEmpresaId = other.optionalTermoAdesaoEmpresaId().map(LongFilter::copy).orElse(null);
        this.segmentoCnaeId = other.optionalSegmentoCnaeId().map(LongFilter::copy).orElse(null);
        this.empresaModeloId = other.optionalEmpresaModeloId().map(LongFilter::copy).orElse(null);
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

    public StringFilter getNomeFantasia() {
        return nomeFantasia;
    }

    public Optional<StringFilter> optionalNomeFantasia() {
        return Optional.ofNullable(nomeFantasia);
    }

    public StringFilter nomeFantasia() {
        if (nomeFantasia == null) {
            setNomeFantasia(new StringFilter());
        }
        return nomeFantasia;
    }

    public void setNomeFantasia(StringFilter nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public StringFilter getCnpj() {
        return cnpj;
    }

    public Optional<StringFilter> optionalCnpj() {
        return Optional.ofNullable(cnpj);
    }

    public StringFilter cnpj() {
        if (cnpj == null) {
            setCnpj(new StringFilter());
        }
        return cnpj;
    }

    public void setCnpj(StringFilter cnpj) {
        this.cnpj = cnpj;
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

    public LongFilter getFuncionarioId() {
        return funcionarioId;
    }

    public Optional<LongFilter> optionalFuncionarioId() {
        return Optional.ofNullable(funcionarioId);
    }

    public LongFilter funcionarioId() {
        if (funcionarioId == null) {
            setFuncionarioId(new LongFilter());
        }
        return funcionarioId;
    }

    public void setFuncionarioId(LongFilter funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public LongFilter getAnexoEmpresaId() {
        return anexoEmpresaId;
    }

    public Optional<LongFilter> optionalAnexoEmpresaId() {
        return Optional.ofNullable(anexoEmpresaId);
    }

    public LongFilter anexoEmpresaId() {
        if (anexoEmpresaId == null) {
            setAnexoEmpresaId(new LongFilter());
        }
        return anexoEmpresaId;
    }

    public void setAnexoEmpresaId(LongFilter anexoEmpresaId) {
        this.anexoEmpresaId = anexoEmpresaId;
    }

    public LongFilter getOrdemServicoId() {
        return ordemServicoId;
    }

    public Optional<LongFilter> optionalOrdemServicoId() {
        return Optional.ofNullable(ordemServicoId);
    }

    public LongFilter ordemServicoId() {
        if (ordemServicoId == null) {
            setOrdemServicoId(new LongFilter());
        }
        return ordemServicoId;
    }

    public void setOrdemServicoId(LongFilter ordemServicoId) {
        this.ordemServicoId = ordemServicoId;
    }

    public LongFilter getAnexoRequeridoEmpresaId() {
        return anexoRequeridoEmpresaId;
    }

    public Optional<LongFilter> optionalAnexoRequeridoEmpresaId() {
        return Optional.ofNullable(anexoRequeridoEmpresaId);
    }

    public LongFilter anexoRequeridoEmpresaId() {
        if (anexoRequeridoEmpresaId == null) {
            setAnexoRequeridoEmpresaId(new LongFilter());
        }
        return anexoRequeridoEmpresaId;
    }

    public void setAnexoRequeridoEmpresaId(LongFilter anexoRequeridoEmpresaId) {
        this.anexoRequeridoEmpresaId = anexoRequeridoEmpresaId;
    }

    public LongFilter getImpostoEmpresaId() {
        return impostoEmpresaId;
    }

    public Optional<LongFilter> optionalImpostoEmpresaId() {
        return Optional.ofNullable(impostoEmpresaId);
    }

    public LongFilter impostoEmpresaId() {
        if (impostoEmpresaId == null) {
            setImpostoEmpresaId(new LongFilter());
        }
        return impostoEmpresaId;
    }

    public void setImpostoEmpresaId(LongFilter impostoEmpresaId) {
        this.impostoEmpresaId = impostoEmpresaId;
    }

    public LongFilter getParcelamentoImpostoId() {
        return parcelamentoImpostoId;
    }

    public Optional<LongFilter> optionalParcelamentoImpostoId() {
        return Optional.ofNullable(parcelamentoImpostoId);
    }

    public LongFilter parcelamentoImpostoId() {
        if (parcelamentoImpostoId == null) {
            setParcelamentoImpostoId(new LongFilter());
        }
        return parcelamentoImpostoId;
    }

    public void setParcelamentoImpostoId(LongFilter parcelamentoImpostoId) {
        this.parcelamentoImpostoId = parcelamentoImpostoId;
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

    public LongFilter getDepartamentoEmpresaId() {
        return departamentoEmpresaId;
    }

    public Optional<LongFilter> optionalDepartamentoEmpresaId() {
        return Optional.ofNullable(departamentoEmpresaId);
    }

    public LongFilter departamentoEmpresaId() {
        if (departamentoEmpresaId == null) {
            setDepartamentoEmpresaId(new LongFilter());
        }
        return departamentoEmpresaId;
    }

    public void setDepartamentoEmpresaId(LongFilter departamentoEmpresaId) {
        this.departamentoEmpresaId = departamentoEmpresaId;
    }

    public LongFilter getTarefaEmpresaId() {
        return tarefaEmpresaId;
    }

    public Optional<LongFilter> optionalTarefaEmpresaId() {
        return Optional.ofNullable(tarefaEmpresaId);
    }

    public LongFilter tarefaEmpresaId() {
        if (tarefaEmpresaId == null) {
            setTarefaEmpresaId(new LongFilter());
        }
        return tarefaEmpresaId;
    }

    public void setTarefaEmpresaId(LongFilter tarefaEmpresaId) {
        this.tarefaEmpresaId = tarefaEmpresaId;
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

    public LongFilter getAtividadeEmpresaId() {
        return atividadeEmpresaId;
    }

    public Optional<LongFilter> optionalAtividadeEmpresaId() {
        return Optional.ofNullable(atividadeEmpresaId);
    }

    public LongFilter atividadeEmpresaId() {
        if (atividadeEmpresaId == null) {
            setAtividadeEmpresaId(new LongFilter());
        }
        return atividadeEmpresaId;
    }

    public void setAtividadeEmpresaId(LongFilter atividadeEmpresaId) {
        this.atividadeEmpresaId = atividadeEmpresaId;
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

    public LongFilter getCertificadoDigitalId() {
        return certificadoDigitalId;
    }

    public Optional<LongFilter> optionalCertificadoDigitalId() {
        return Optional.ofNullable(certificadoDigitalId);
    }

    public LongFilter certificadoDigitalId() {
        if (certificadoDigitalId == null) {
            setCertificadoDigitalId(new LongFilter());
        }
        return certificadoDigitalId;
    }

    public void setCertificadoDigitalId(LongFilter certificadoDigitalId) {
        this.certificadoDigitalId = certificadoDigitalId;
    }

    public LongFilter getOpcaoRazaoSocialEmpresaId() {
        return opcaoRazaoSocialEmpresaId;
    }

    public Optional<LongFilter> optionalOpcaoRazaoSocialEmpresaId() {
        return Optional.ofNullable(opcaoRazaoSocialEmpresaId);
    }

    public LongFilter opcaoRazaoSocialEmpresaId() {
        if (opcaoRazaoSocialEmpresaId == null) {
            setOpcaoRazaoSocialEmpresaId(new LongFilter());
        }
        return opcaoRazaoSocialEmpresaId;
    }

    public void setOpcaoRazaoSocialEmpresaId(LongFilter opcaoRazaoSocialEmpresaId) {
        this.opcaoRazaoSocialEmpresaId = opcaoRazaoSocialEmpresaId;
    }

    public LongFilter getOpcaoNomeFantasiaEmpresaId() {
        return opcaoNomeFantasiaEmpresaId;
    }

    public Optional<LongFilter> optionalOpcaoNomeFantasiaEmpresaId() {
        return Optional.ofNullable(opcaoNomeFantasiaEmpresaId);
    }

    public LongFilter opcaoNomeFantasiaEmpresaId() {
        if (opcaoNomeFantasiaEmpresaId == null) {
            setOpcaoNomeFantasiaEmpresaId(new LongFilter());
        }
        return opcaoNomeFantasiaEmpresaId;
    }

    public void setOpcaoNomeFantasiaEmpresaId(LongFilter opcaoNomeFantasiaEmpresaId) {
        this.opcaoNomeFantasiaEmpresaId = opcaoNomeFantasiaEmpresaId;
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

    public LongFilter getSegmentoCnaeId() {
        return segmentoCnaeId;
    }

    public Optional<LongFilter> optionalSegmentoCnaeId() {
        return Optional.ofNullable(segmentoCnaeId);
    }

    public LongFilter segmentoCnaeId() {
        if (segmentoCnaeId == null) {
            setSegmentoCnaeId(new LongFilter());
        }
        return segmentoCnaeId;
    }

    public void setSegmentoCnaeId(LongFilter segmentoCnaeId) {
        this.segmentoCnaeId = segmentoCnaeId;
    }

    public LongFilter getEmpresaModeloId() {
        return empresaModeloId;
    }

    public Optional<LongFilter> optionalEmpresaModeloId() {
        return Optional.ofNullable(empresaModeloId);
    }

    public LongFilter empresaModeloId() {
        if (empresaModeloId == null) {
            setEmpresaModeloId(new LongFilter());
        }
        return empresaModeloId;
    }

    public void setEmpresaModeloId(LongFilter empresaModeloId) {
        this.empresaModeloId = empresaModeloId;
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
            Objects.equals(nomeFantasia, that.nomeFantasia) &&
            Objects.equals(cnpj, that.cnpj) &&
            Objects.equals(dataAbertura, that.dataAbertura) &&
            Objects.equals(urlContratoSocial, that.urlContratoSocial) &&
            Objects.equals(capitalSocial, that.capitalSocial) &&
            Objects.equals(funcionarioId, that.funcionarioId) &&
            Objects.equals(anexoEmpresaId, that.anexoEmpresaId) &&
            Objects.equals(ordemServicoId, that.ordemServicoId) &&
            Objects.equals(anexoRequeridoEmpresaId, that.anexoRequeridoEmpresaId) &&
            Objects.equals(impostoEmpresaId, that.impostoEmpresaId) &&
            Objects.equals(parcelamentoImpostoId, that.parcelamentoImpostoId) &&
            Objects.equals(assinaturaEmpresaId, that.assinaturaEmpresaId) &&
            Objects.equals(departamentoEmpresaId, that.departamentoEmpresaId) &&
            Objects.equals(tarefaEmpresaId, that.tarefaEmpresaId) &&
            Objects.equals(enderecoEmpresaId, that.enderecoEmpresaId) &&
            Objects.equals(atividadeEmpresaId, that.atividadeEmpresaId) &&
            Objects.equals(socioId, that.socioId) &&
            Objects.equals(certificadoDigitalId, that.certificadoDigitalId) &&
            Objects.equals(opcaoRazaoSocialEmpresaId, that.opcaoRazaoSocialEmpresaId) &&
            Objects.equals(opcaoNomeFantasiaEmpresaId, that.opcaoNomeFantasiaEmpresaId) &&
            Objects.equals(termoAdesaoEmpresaId, that.termoAdesaoEmpresaId) &&
            Objects.equals(segmentoCnaeId, that.segmentoCnaeId) &&
            Objects.equals(empresaModeloId, that.empresaModeloId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            razaoSocial,
            nomeFantasia,
            cnpj,
            dataAbertura,
            urlContratoSocial,
            capitalSocial,
            funcionarioId,
            anexoEmpresaId,
            ordemServicoId,
            anexoRequeridoEmpresaId,
            impostoEmpresaId,
            parcelamentoImpostoId,
            assinaturaEmpresaId,
            departamentoEmpresaId,
            tarefaEmpresaId,
            enderecoEmpresaId,
            atividadeEmpresaId,
            socioId,
            certificadoDigitalId,
            opcaoRazaoSocialEmpresaId,
            opcaoNomeFantasiaEmpresaId,
            termoAdesaoEmpresaId,
            segmentoCnaeId,
            empresaModeloId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpresaCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalRazaoSocial().map(f -> "razaoSocial=" + f + ", ").orElse("") +
            optionalNomeFantasia().map(f -> "nomeFantasia=" + f + ", ").orElse("") +
            optionalCnpj().map(f -> "cnpj=" + f + ", ").orElse("") +
            optionalDataAbertura().map(f -> "dataAbertura=" + f + ", ").orElse("") +
            optionalUrlContratoSocial().map(f -> "urlContratoSocial=" + f + ", ").orElse("") +
            optionalCapitalSocial().map(f -> "capitalSocial=" + f + ", ").orElse("") +
            optionalFuncionarioId().map(f -> "funcionarioId=" + f + ", ").orElse("") +
            optionalAnexoEmpresaId().map(f -> "anexoEmpresaId=" + f + ", ").orElse("") +
            optionalOrdemServicoId().map(f -> "ordemServicoId=" + f + ", ").orElse("") +
            optionalAnexoRequeridoEmpresaId().map(f -> "anexoRequeridoEmpresaId=" + f + ", ").orElse("") +
            optionalImpostoEmpresaId().map(f -> "impostoEmpresaId=" + f + ", ").orElse("") +
            optionalParcelamentoImpostoId().map(f -> "parcelamentoImpostoId=" + f + ", ").orElse("") +
            optionalAssinaturaEmpresaId().map(f -> "assinaturaEmpresaId=" + f + ", ").orElse("") +
            optionalDepartamentoEmpresaId().map(f -> "departamentoEmpresaId=" + f + ", ").orElse("") +
            optionalTarefaEmpresaId().map(f -> "tarefaEmpresaId=" + f + ", ").orElse("") +
            optionalEnderecoEmpresaId().map(f -> "enderecoEmpresaId=" + f + ", ").orElse("") +
            optionalAtividadeEmpresaId().map(f -> "atividadeEmpresaId=" + f + ", ").orElse("") +
            optionalSocioId().map(f -> "socioId=" + f + ", ").orElse("") +
            optionalCertificadoDigitalId().map(f -> "certificadoDigitalId=" + f + ", ").orElse("") +
            optionalOpcaoRazaoSocialEmpresaId().map(f -> "opcaoRazaoSocialEmpresaId=" + f + ", ").orElse("") +
            optionalOpcaoNomeFantasiaEmpresaId().map(f -> "opcaoNomeFantasiaEmpresaId=" + f + ", ").orElse("") +
            optionalTermoAdesaoEmpresaId().map(f -> "termoAdesaoEmpresaId=" + f + ", ").orElse("") +
            optionalSegmentoCnaeId().map(f -> "segmentoCnaeId=" + f + ", ").orElse("") +
            optionalEmpresaModeloId().map(f -> "empresaModeloId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
