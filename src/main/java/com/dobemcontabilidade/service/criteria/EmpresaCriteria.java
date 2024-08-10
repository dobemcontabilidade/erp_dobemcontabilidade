package com.dobemcontabilidade.service.criteria;

import com.dobemcontabilidade.domain.enumeration.TipoSegmentoEnum;
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

    /**
     * Class for filtering TipoSegmentoEnum
     */
    public static class TipoSegmentoEnumFilter extends Filter<TipoSegmentoEnum> {

        public TipoSegmentoEnumFilter() {}

        public TipoSegmentoEnumFilter(TipoSegmentoEnumFilter filter) {
            super(filter);
        }

        @Override
        public TipoSegmentoEnumFilter copy() {
            return new TipoSegmentoEnumFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter razaoSocial;

    private StringFilter nomeFantasia;

    private StringFilter cnpj;

    private InstantFilter dataAbertura;

    private StringFilter urlContratoSocial;

    private DoubleFilter capitalSocial;

    private TipoSegmentoEnumFilter tipoSegmento;

    private LongFilter assinaturaEmpresaId;

    private LongFilter funcionarioId;

    private LongFilter departamentoEmpresaId;

    private LongFilter tarefaEmpresaId;

    private LongFilter enderecoEmpresaId;

    private LongFilter atividadeEmpresaId;

    private LongFilter socioId;

    private LongFilter anexoEmpresaId;

    private LongFilter certificadoDigitalId;

    private LongFilter usuarioEmpresaId;

    private LongFilter opcaoRazaoSocialEmpresaId;

    private LongFilter opcaoNomeFantasiaEmpresaId;

    private LongFilter ramoId;

    private LongFilter tributacaoId;

    private LongFilter enquadramentoId;

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
        this.tipoSegmento = other.optionalTipoSegmento().map(TipoSegmentoEnumFilter::copy).orElse(null);
        this.assinaturaEmpresaId = other.optionalAssinaturaEmpresaId().map(LongFilter::copy).orElse(null);
        this.funcionarioId = other.optionalFuncionarioId().map(LongFilter::copy).orElse(null);
        this.departamentoEmpresaId = other.optionalDepartamentoEmpresaId().map(LongFilter::copy).orElse(null);
        this.tarefaEmpresaId = other.optionalTarefaEmpresaId().map(LongFilter::copy).orElse(null);
        this.enderecoEmpresaId = other.optionalEnderecoEmpresaId().map(LongFilter::copy).orElse(null);
        this.atividadeEmpresaId = other.optionalAtividadeEmpresaId().map(LongFilter::copy).orElse(null);
        this.socioId = other.optionalSocioId().map(LongFilter::copy).orElse(null);
        this.anexoEmpresaId = other.optionalAnexoEmpresaId().map(LongFilter::copy).orElse(null);
        this.certificadoDigitalId = other.optionalCertificadoDigitalId().map(LongFilter::copy).orElse(null);
        this.usuarioEmpresaId = other.optionalUsuarioEmpresaId().map(LongFilter::copy).orElse(null);
        this.opcaoRazaoSocialEmpresaId = other.optionalOpcaoRazaoSocialEmpresaId().map(LongFilter::copy).orElse(null);
        this.opcaoNomeFantasiaEmpresaId = other.optionalOpcaoNomeFantasiaEmpresaId().map(LongFilter::copy).orElse(null);
        this.ramoId = other.optionalRamoId().map(LongFilter::copy).orElse(null);
        this.tributacaoId = other.optionalTributacaoId().map(LongFilter::copy).orElse(null);
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

    public TipoSegmentoEnumFilter getTipoSegmento() {
        return tipoSegmento;
    }

    public Optional<TipoSegmentoEnumFilter> optionalTipoSegmento() {
        return Optional.ofNullable(tipoSegmento);
    }

    public TipoSegmentoEnumFilter tipoSegmento() {
        if (tipoSegmento == null) {
            setTipoSegmento(new TipoSegmentoEnumFilter());
        }
        return tipoSegmento;
    }

    public void setTipoSegmento(TipoSegmentoEnumFilter tipoSegmento) {
        this.tipoSegmento = tipoSegmento;
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
            Objects.equals(nomeFantasia, that.nomeFantasia) &&
            Objects.equals(cnpj, that.cnpj) &&
            Objects.equals(dataAbertura, that.dataAbertura) &&
            Objects.equals(urlContratoSocial, that.urlContratoSocial) &&
            Objects.equals(capitalSocial, that.capitalSocial) &&
            Objects.equals(tipoSegmento, that.tipoSegmento) &&
            Objects.equals(assinaturaEmpresaId, that.assinaturaEmpresaId) &&
            Objects.equals(funcionarioId, that.funcionarioId) &&
            Objects.equals(departamentoEmpresaId, that.departamentoEmpresaId) &&
            Objects.equals(tarefaEmpresaId, that.tarefaEmpresaId) &&
            Objects.equals(enderecoEmpresaId, that.enderecoEmpresaId) &&
            Objects.equals(atividadeEmpresaId, that.atividadeEmpresaId) &&
            Objects.equals(socioId, that.socioId) &&
            Objects.equals(anexoEmpresaId, that.anexoEmpresaId) &&
            Objects.equals(certificadoDigitalId, that.certificadoDigitalId) &&
            Objects.equals(usuarioEmpresaId, that.usuarioEmpresaId) &&
            Objects.equals(opcaoRazaoSocialEmpresaId, that.opcaoRazaoSocialEmpresaId) &&
            Objects.equals(opcaoNomeFantasiaEmpresaId, that.opcaoNomeFantasiaEmpresaId) &&
            Objects.equals(ramoId, that.ramoId) &&
            Objects.equals(tributacaoId, that.tributacaoId) &&
            Objects.equals(enquadramentoId, that.enquadramentoId) &&
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
            tipoSegmento,
            assinaturaEmpresaId,
            funcionarioId,
            departamentoEmpresaId,
            tarefaEmpresaId,
            enderecoEmpresaId,
            atividadeEmpresaId,
            socioId,
            anexoEmpresaId,
            certificadoDigitalId,
            usuarioEmpresaId,
            opcaoRazaoSocialEmpresaId,
            opcaoNomeFantasiaEmpresaId,
            ramoId,
            tributacaoId,
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
            optionalNomeFantasia().map(f -> "nomeFantasia=" + f + ", ").orElse("") +
            optionalCnpj().map(f -> "cnpj=" + f + ", ").orElse("") +
            optionalDataAbertura().map(f -> "dataAbertura=" + f + ", ").orElse("") +
            optionalUrlContratoSocial().map(f -> "urlContratoSocial=" + f + ", ").orElse("") +
            optionalCapitalSocial().map(f -> "capitalSocial=" + f + ", ").orElse("") +
            optionalTipoSegmento().map(f -> "tipoSegmento=" + f + ", ").orElse("") +
            optionalAssinaturaEmpresaId().map(f -> "assinaturaEmpresaId=" + f + ", ").orElse("") +
            optionalFuncionarioId().map(f -> "funcionarioId=" + f + ", ").orElse("") +
            optionalDepartamentoEmpresaId().map(f -> "departamentoEmpresaId=" + f + ", ").orElse("") +
            optionalTarefaEmpresaId().map(f -> "tarefaEmpresaId=" + f + ", ").orElse("") +
            optionalEnderecoEmpresaId().map(f -> "enderecoEmpresaId=" + f + ", ").orElse("") +
            optionalAtividadeEmpresaId().map(f -> "atividadeEmpresaId=" + f + ", ").orElse("") +
            optionalSocioId().map(f -> "socioId=" + f + ", ").orElse("") +
            optionalAnexoEmpresaId().map(f -> "anexoEmpresaId=" + f + ", ").orElse("") +
            optionalCertificadoDigitalId().map(f -> "certificadoDigitalId=" + f + ", ").orElse("") +
            optionalUsuarioEmpresaId().map(f -> "usuarioEmpresaId=" + f + ", ").orElse("") +
            optionalOpcaoRazaoSocialEmpresaId().map(f -> "opcaoRazaoSocialEmpresaId=" + f + ", ").orElse("") +
            optionalOpcaoNomeFantasiaEmpresaId().map(f -> "opcaoNomeFantasiaEmpresaId=" + f + ", ").orElse("") +
            optionalRamoId().map(f -> "ramoId=" + f + ", ").orElse("") +
            optionalTributacaoId().map(f -> "tributacaoId=" + f + ", ").orElse("") +
            optionalEnquadramentoId().map(f -> "enquadramentoId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
