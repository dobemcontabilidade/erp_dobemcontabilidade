package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.EnderecoEmpresa} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EnderecoEmpresaDTO implements Serializable {

    private Long id;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private String cep;

    private Boolean principal;

    private Boolean filial;

    private Boolean enderecoFiscal;

    @NotNull
    private EmpresaDTO empresa;

    @NotNull
    private CidadeDTO cidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public Boolean getFilial() {
        return filial;
    }

    public void setFilial(Boolean filial) {
        this.filial = filial;
    }

    public Boolean getEnderecoFiscal() {
        return enderecoFiscal;
    }

    public void setEnderecoFiscal(Boolean enderecoFiscal) {
        this.enderecoFiscal = enderecoFiscal;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    public CidadeDTO getCidade() {
        return cidade;
    }

    public void setCidade(CidadeDTO cidade) {
        this.cidade = cidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnderecoEmpresaDTO)) {
            return false;
        }

        EnderecoEmpresaDTO enderecoEmpresaDTO = (EnderecoEmpresaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, enderecoEmpresaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnderecoEmpresaDTO{" +
            "id=" + getId() +
            ", logradouro='" + getLogradouro() + "'" +
            ", numero='" + getNumero() + "'" +
            ", complemento='" + getComplemento() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", cep='" + getCep() + "'" +
            ", principal='" + getPrincipal() + "'" +
            ", filial='" + getFilial() + "'" +
            ", enderecoFiscal='" + getEnderecoFiscal() + "'" +
            ", empresa=" + getEmpresa() +
            ", cidade=" + getCidade() +
            "}";
    }
}
