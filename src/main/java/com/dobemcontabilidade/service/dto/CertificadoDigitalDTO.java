package com.dobemcontabilidade.service.dto;

import com.dobemcontabilidade.domain.enumeration.TipoCertificadoEnum;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.CertificadoDigital} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CertificadoDigitalDTO implements Serializable {

    private Long id;

    @Lob
    private String urlCertificado;

    private Instant dataContratacao;

    private Integer validade;

    private TipoCertificadoEnum tipoCertificado;

    @NotNull
    private EmpresaDTO empresa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlCertificado() {
        return urlCertificado;
    }

    public void setUrlCertificado(String urlCertificado) {
        this.urlCertificado = urlCertificado;
    }

    public Instant getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(Instant dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public Integer getValidade() {
        return validade;
    }

    public void setValidade(Integer validade) {
        this.validade = validade;
    }

    public TipoCertificadoEnum getTipoCertificado() {
        return tipoCertificado;
    }

    public void setTipoCertificado(TipoCertificadoEnum tipoCertificado) {
        this.tipoCertificado = tipoCertificado;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CertificadoDigitalDTO)) {
            return false;
        }

        CertificadoDigitalDTO certificadoDigitalDTO = (CertificadoDigitalDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, certificadoDigitalDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CertificadoDigitalDTO{" +
            "id=" + getId() +
            ", urlCertificado='" + getUrlCertificado() + "'" +
            ", dataContratacao='" + getDataContratacao() + "'" +
            ", validade=" + getValidade() +
            ", tipoCertificado='" + getTipoCertificado() + "'" +
            ", empresa=" + getEmpresa() +
            "}";
    }
}
