package com.dobemcontabilidade.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.Empresa} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmpresaDTO implements Serializable {

    private Long id;

    @NotNull
    private String razaoSocial;

    @NotNull
    private String nomeFantasia;

    @Lob
    private String descricaoDoNegocio;

    @Size(max = 20)
    private String cnpj;

    private Instant dataAbertura;

    private String urlContratoSocial;

    private Double capitalSocial;

    @NotNull
    private RamoDTO ramo;

    @NotNull
    private TributacaoDTO tributacao;

    @NotNull
    private EnquadramentoDTO enquadramento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getDescricaoDoNegocio() {
        return descricaoDoNegocio;
    }

    public void setDescricaoDoNegocio(String descricaoDoNegocio) {
        this.descricaoDoNegocio = descricaoDoNegocio;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Instant getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Instant dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getUrlContratoSocial() {
        return urlContratoSocial;
    }

    public void setUrlContratoSocial(String urlContratoSocial) {
        this.urlContratoSocial = urlContratoSocial;
    }

    public Double getCapitalSocial() {
        return capitalSocial;
    }

    public void setCapitalSocial(Double capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    public RamoDTO getRamo() {
        return ramo;
    }

    public void setRamo(RamoDTO ramo) {
        this.ramo = ramo;
    }

    public TributacaoDTO getTributacao() {
        return tributacao;
    }

    public void setTributacao(TributacaoDTO tributacao) {
        this.tributacao = tributacao;
    }

    public EnquadramentoDTO getEnquadramento() {
        return enquadramento;
    }

    public void setEnquadramento(EnquadramentoDTO enquadramento) {
        this.enquadramento = enquadramento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmpresaDTO)) {
            return false;
        }

        EmpresaDTO empresaDTO = (EmpresaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, empresaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpresaDTO{" +
            "id=" + getId() +
            ", razaoSocial='" + getRazaoSocial() + "'" +
            ", nomeFantasia='" + getNomeFantasia() + "'" +
            ", descricaoDoNegocio='" + getDescricaoDoNegocio() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", dataAbertura='" + getDataAbertura() + "'" +
            ", urlContratoSocial='" + getUrlContratoSocial() + "'" +
            ", capitalSocial=" + getCapitalSocial() +
            ", ramo=" + getRamo() +
            ", tributacao=" + getTributacao() +
            ", enquadramento=" + getEnquadramento() +
            "}";
    }
}
