package com.dobemcontabilidade.service.dto;

import com.dobemcontabilidade.domain.enumeration.SituacaoPagamentoEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.Pagamento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PagamentoDTO implements Serializable {

    private Long id;

    private Instant dataCobranca;

    private Instant dataVencimento;

    private Instant dataPagamento;

    private Double valorPago;

    private Double valorCobrado;

    private Double acrescimo;

    private Double multa;

    private Double juros;

    @NotNull
    private SituacaoPagamentoEnum situacao;

    @NotNull
    private AssinaturaEmpresaDTO assinaturaEmpresa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataCobranca() {
        return dataCobranca;
    }

    public void setDataCobranca(Instant dataCobranca) {
        this.dataCobranca = dataCobranca;
    }

    public Instant getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Instant dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Instant getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Instant dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public Double getValorCobrado() {
        return valorCobrado;
    }

    public void setValorCobrado(Double valorCobrado) {
        this.valorCobrado = valorCobrado;
    }

    public Double getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(Double acrescimo) {
        this.acrescimo = acrescimo;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

    public Double getJuros() {
        return juros;
    }

    public void setJuros(Double juros) {
        this.juros = juros;
    }

    public SituacaoPagamentoEnum getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoPagamentoEnum situacao) {
        this.situacao = situacao;
    }

    public AssinaturaEmpresaDTO getAssinaturaEmpresa() {
        return assinaturaEmpresa;
    }

    public void setAssinaturaEmpresa(AssinaturaEmpresaDTO assinaturaEmpresa) {
        this.assinaturaEmpresa = assinaturaEmpresa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PagamentoDTO)) {
            return false;
        }

        PagamentoDTO pagamentoDTO = (PagamentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pagamentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PagamentoDTO{" +
            "id=" + getId() +
            ", dataCobranca='" + getDataCobranca() + "'" +
            ", dataVencimento='" + getDataVencimento() + "'" +
            ", dataPagamento='" + getDataPagamento() + "'" +
            ", valorPago=" + getValorPago() +
            ", valorCobrado=" + getValorCobrado() +
            ", acrescimo=" + getAcrescimo() +
            ", multa=" + getMulta() +
            ", juros=" + getJuros() +
            ", situacao='" + getSituacao() + "'" +
            ", assinaturaEmpresa=" + getAssinaturaEmpresa() +
            "}";
    }
}
