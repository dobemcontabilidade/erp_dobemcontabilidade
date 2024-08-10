package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.CalculoPlanoAssinatura} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CalculoPlanoAssinaturaDTO implements Serializable {

    private Long id;

    private String codigoAtendimento;

    private Double valorEnquadramento;

    private Double valorTributacao;

    private Double valorRamo;

    private Double valorFuncionarios;

    private Double valorSocios;

    private Double valorFaturamento;

    private Double valorPlanoContabil;

    private Double valorPlanoContabilComDesconto;

    private Double valorMensalidade;

    private Double valorPeriodo;

    private Double valorAno;

    @NotNull
    private PeriodoPagamentoDTO periodoPagamento;

    @NotNull
    private PlanoContabilDTO planoContabil;

    @NotNull
    private RamoDTO ramo;

    @NotNull
    private TributacaoDTO tributacao;

    @NotNull
    private DescontoPlanoContabilDTO descontoPlanoContabil;

    @NotNull
    private AssinaturaEmpresaDTO assinaturaEmpresa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoAtendimento() {
        return codigoAtendimento;
    }

    public void setCodigoAtendimento(String codigoAtendimento) {
        this.codigoAtendimento = codigoAtendimento;
    }

    public Double getValorEnquadramento() {
        return valorEnquadramento;
    }

    public void setValorEnquadramento(Double valorEnquadramento) {
        this.valorEnquadramento = valorEnquadramento;
    }

    public Double getValorTributacao() {
        return valorTributacao;
    }

    public void setValorTributacao(Double valorTributacao) {
        this.valorTributacao = valorTributacao;
    }

    public Double getValorRamo() {
        return valorRamo;
    }

    public void setValorRamo(Double valorRamo) {
        this.valorRamo = valorRamo;
    }

    public Double getValorFuncionarios() {
        return valorFuncionarios;
    }

    public void setValorFuncionarios(Double valorFuncionarios) {
        this.valorFuncionarios = valorFuncionarios;
    }

    public Double getValorSocios() {
        return valorSocios;
    }

    public void setValorSocios(Double valorSocios) {
        this.valorSocios = valorSocios;
    }

    public Double getValorFaturamento() {
        return valorFaturamento;
    }

    public void setValorFaturamento(Double valorFaturamento) {
        this.valorFaturamento = valorFaturamento;
    }

    public Double getValorPlanoContabil() {
        return valorPlanoContabil;
    }

    public void setValorPlanoContabil(Double valorPlanoContabil) {
        this.valorPlanoContabil = valorPlanoContabil;
    }

    public Double getValorPlanoContabilComDesconto() {
        return valorPlanoContabilComDesconto;
    }

    public void setValorPlanoContabilComDesconto(Double valorPlanoContabilComDesconto) {
        this.valorPlanoContabilComDesconto = valorPlanoContabilComDesconto;
    }

    public Double getValorMensalidade() {
        return valorMensalidade;
    }

    public void setValorMensalidade(Double valorMensalidade) {
        this.valorMensalidade = valorMensalidade;
    }

    public Double getValorPeriodo() {
        return valorPeriodo;
    }

    public void setValorPeriodo(Double valorPeriodo) {
        this.valorPeriodo = valorPeriodo;
    }

    public Double getValorAno() {
        return valorAno;
    }

    public void setValorAno(Double valorAno) {
        this.valorAno = valorAno;
    }

    public PeriodoPagamentoDTO getPeriodoPagamento() {
        return periodoPagamento;
    }

    public void setPeriodoPagamento(PeriodoPagamentoDTO periodoPagamento) {
        this.periodoPagamento = periodoPagamento;
    }

    public PlanoContabilDTO getPlanoContabil() {
        return planoContabil;
    }

    public void setPlanoContabil(PlanoContabilDTO planoContabil) {
        this.planoContabil = planoContabil;
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

    public DescontoPlanoContabilDTO getDescontoPlanoContabil() {
        return descontoPlanoContabil;
    }

    public void setDescontoPlanoContabil(DescontoPlanoContabilDTO descontoPlanoContabil) {
        this.descontoPlanoContabil = descontoPlanoContabil;
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
        if (!(o instanceof CalculoPlanoAssinaturaDTO)) {
            return false;
        }

        CalculoPlanoAssinaturaDTO calculoPlanoAssinaturaDTO = (CalculoPlanoAssinaturaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, calculoPlanoAssinaturaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CalculoPlanoAssinaturaDTO{" +
            "id=" + getId() +
            ", codigoAtendimento='" + getCodigoAtendimento() + "'" +
            ", valorEnquadramento=" + getValorEnquadramento() +
            ", valorTributacao=" + getValorTributacao() +
            ", valorRamo=" + getValorRamo() +
            ", valorFuncionarios=" + getValorFuncionarios() +
            ", valorSocios=" + getValorSocios() +
            ", valorFaturamento=" + getValorFaturamento() +
            ", valorPlanoContabil=" + getValorPlanoContabil() +
            ", valorPlanoContabilComDesconto=" + getValorPlanoContabilComDesconto() +
            ", valorMensalidade=" + getValorMensalidade() +
            ", valorPeriodo=" + getValorPeriodo() +
            ", valorAno=" + getValorAno() +
            ", periodoPagamento=" + getPeriodoPagamento() +
            ", planoContabil=" + getPlanoContabil() +
            ", ramo=" + getRamo() +
            ", tributacao=" + getTributacao() +
            ", descontoPlanoContabil=" + getDescontoPlanoContabil() +
            ", assinaturaEmpresa=" + getAssinaturaEmpresa() +
            "}";
    }
}
