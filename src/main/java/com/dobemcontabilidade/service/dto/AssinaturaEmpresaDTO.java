package com.dobemcontabilidade.service.dto;

import com.dobemcontabilidade.domain.enumeration.SituacaoContratoEmpresaEnum;
import com.dobemcontabilidade.domain.enumeration.TipoContratoEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.AssinaturaEmpresa} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssinaturaEmpresaDTO implements Serializable {

    private Long id;

    private String codigoAssinatura;

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

    private Instant dataContratacao;

    private Instant dataEncerramento;

    private Integer diaVencimento;

    private SituacaoContratoEmpresaEnum situacao;

    private TipoContratoEnum tipoContrato;

    @NotNull
    private PeriodoPagamentoDTO periodoPagamento;

    @NotNull
    private FormaDePagamentoDTO formaDePagamento;

    @NotNull
    private PlanoContabilDTO planoContabil;

    @NotNull
    private EmpresaDTO empresa;

    private PlanoContaAzulDTO planoContaAzul;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoAssinatura() {
        return codigoAssinatura;
    }

    public void setCodigoAssinatura(String codigoAssinatura) {
        this.codigoAssinatura = codigoAssinatura;
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

    public Instant getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(Instant dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public Instant getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(Instant dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public Integer getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(Integer diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public SituacaoContratoEmpresaEnum getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoContratoEmpresaEnum situacao) {
        this.situacao = situacao;
    }

    public TipoContratoEnum getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContratoEnum tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public PeriodoPagamentoDTO getPeriodoPagamento() {
        return periodoPagamento;
    }

    public void setPeriodoPagamento(PeriodoPagamentoDTO periodoPagamento) {
        this.periodoPagamento = periodoPagamento;
    }

    public FormaDePagamentoDTO getFormaDePagamento() {
        return formaDePagamento;
    }

    public void setFormaDePagamento(FormaDePagamentoDTO formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }

    public PlanoContabilDTO getPlanoContabil() {
        return planoContabil;
    }

    public void setPlanoContabil(PlanoContabilDTO planoContabil) {
        this.planoContabil = planoContabil;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    public PlanoContaAzulDTO getPlanoContaAzul() {
        return planoContaAzul;
    }

    public void setPlanoContaAzul(PlanoContaAzulDTO planoContaAzul) {
        this.planoContaAzul = planoContaAzul;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssinaturaEmpresaDTO)) {
            return false;
        }

        AssinaturaEmpresaDTO assinaturaEmpresaDTO = (AssinaturaEmpresaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, assinaturaEmpresaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssinaturaEmpresaDTO{" +
            "id=" + getId() +
            ", codigoAssinatura='" + getCodigoAssinatura() + "'" +
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
            ", dataContratacao='" + getDataContratacao() + "'" +
            ", dataEncerramento='" + getDataEncerramento() + "'" +
            ", diaVencimento=" + getDiaVencimento() +
            ", situacao='" + getSituacao() + "'" +
            ", tipoContrato='" + getTipoContrato() + "'" +
            ", periodoPagamento=" + getPeriodoPagamento() +
            ", formaDePagamento=" + getFormaDePagamento() +
            ", planoContabil=" + getPlanoContabil() +
            ", empresa=" + getEmpresa() +
            ", planoContaAzul=" + getPlanoContaAzul() +
            "}";
    }
}
