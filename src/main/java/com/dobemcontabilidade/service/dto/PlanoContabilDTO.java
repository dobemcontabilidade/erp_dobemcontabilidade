package com.dobemcontabilidade.service.dto;

import com.dobemcontabilidade.domain.enumeration.SituacaoPlanoContabilEnum;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.PlanoContabil} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanoContabilDTO implements Serializable {

    private Long id;

    private String nome;

    private Double adicionalSocio;

    private Double adicionalFuncionario;

    private Integer sociosIsentos;

    private Double adicionalFaturamento;

    private Double valorBaseFaturamento;

    private Double valorBaseAbertura;

    private SituacaoPlanoContabilEnum situacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getAdicionalSocio() {
        return adicionalSocio;
    }

    public void setAdicionalSocio(Double adicionalSocio) {
        this.adicionalSocio = adicionalSocio;
    }

    public Double getAdicionalFuncionario() {
        return adicionalFuncionario;
    }

    public void setAdicionalFuncionario(Double adicionalFuncionario) {
        this.adicionalFuncionario = adicionalFuncionario;
    }

    public Integer getSociosIsentos() {
        return sociosIsentos;
    }

    public void setSociosIsentos(Integer sociosIsentos) {
        this.sociosIsentos = sociosIsentos;
    }

    public Double getAdicionalFaturamento() {
        return adicionalFaturamento;
    }

    public void setAdicionalFaturamento(Double adicionalFaturamento) {
        this.adicionalFaturamento = adicionalFaturamento;
    }

    public Double getValorBaseFaturamento() {
        return valorBaseFaturamento;
    }

    public void setValorBaseFaturamento(Double valorBaseFaturamento) {
        this.valorBaseFaturamento = valorBaseFaturamento;
    }

    public Double getValorBaseAbertura() {
        return valorBaseAbertura;
    }

    public void setValorBaseAbertura(Double valorBaseAbertura) {
        this.valorBaseAbertura = valorBaseAbertura;
    }

    public SituacaoPlanoContabilEnum getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoPlanoContabilEnum situacao) {
        this.situacao = situacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoContabilDTO)) {
            return false;
        }

        PlanoContabilDTO planoContabilDTO = (PlanoContabilDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, planoContabilDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoContabilDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", adicionalSocio=" + getAdicionalSocio() +
            ", adicionalFuncionario=" + getAdicionalFuncionario() +
            ", sociosIsentos=" + getSociosIsentos() +
            ", adicionalFaturamento=" + getAdicionalFaturamento() +
            ", valorBaseFaturamento=" + getValorBaseFaturamento() +
            ", valorBaseAbertura=" + getValorBaseAbertura() +
            ", situacao='" + getSituacao() + "'" +
            "}";
    }
}
