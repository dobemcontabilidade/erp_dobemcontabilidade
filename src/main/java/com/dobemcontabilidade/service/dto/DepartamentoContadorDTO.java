package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.DepartamentoContador} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DepartamentoContadorDTO implements Serializable {

    private Long id;

    private Double percentualExperiencia;

    private String descricaoExperiencia;

    private Double pontuacaoEntrevista;

    private Double pontuacaoAvaliacao;

    @NotNull
    private DepartamentoDTO departamento;

    @NotNull
    private ContadorDTO contador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPercentualExperiencia() {
        return percentualExperiencia;
    }

    public void setPercentualExperiencia(Double percentualExperiencia) {
        this.percentualExperiencia = percentualExperiencia;
    }

    public String getDescricaoExperiencia() {
        return descricaoExperiencia;
    }

    public void setDescricaoExperiencia(String descricaoExperiencia) {
        this.descricaoExperiencia = descricaoExperiencia;
    }

    public Double getPontuacaoEntrevista() {
        return pontuacaoEntrevista;
    }

    public void setPontuacaoEntrevista(Double pontuacaoEntrevista) {
        this.pontuacaoEntrevista = pontuacaoEntrevista;
    }

    public Double getPontuacaoAvaliacao() {
        return pontuacaoAvaliacao;
    }

    public void setPontuacaoAvaliacao(Double pontuacaoAvaliacao) {
        this.pontuacaoAvaliacao = pontuacaoAvaliacao;
    }

    public DepartamentoDTO getDepartamento() {
        return departamento;
    }

    public void setDepartamento(DepartamentoDTO departamento) {
        this.departamento = departamento;
    }

    public ContadorDTO getContador() {
        return contador;
    }

    public void setContador(ContadorDTO contador) {
        this.contador = contador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepartamentoContadorDTO)) {
            return false;
        }

        DepartamentoContadorDTO departamentoContadorDTO = (DepartamentoContadorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, departamentoContadorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartamentoContadorDTO{" +
            "id=" + getId() +
            ", percentualExperiencia=" + getPercentualExperiencia() +
            ", descricaoExperiencia='" + getDescricaoExperiencia() + "'" +
            ", pontuacaoEntrevista=" + getPontuacaoEntrevista() +
            ", pontuacaoAvaliacao=" + getPontuacaoAvaliacao() +
            ", departamento=" + getDepartamento() +
            ", contador=" + getContador() +
            "}";
    }
}
