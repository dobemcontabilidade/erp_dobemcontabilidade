package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.AreaContabilContador} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AreaContabilContadorDTO implements Serializable {

    private Long id;

    private Double percentualExperiencia;

    private String descricaoExperiencia;

    private Double pontuacaoEntrevista;

    private Double pontuacaoAvaliacao;

    @NotNull
    private AreaContabilDTO areaContabil;

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

    public AreaContabilDTO getAreaContabil() {
        return areaContabil;
    }

    public void setAreaContabil(AreaContabilDTO areaContabil) {
        this.areaContabil = areaContabil;
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
        if (!(o instanceof AreaContabilContadorDTO)) {
            return false;
        }

        AreaContabilContadorDTO areaContabilContadorDTO = (AreaContabilContadorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, areaContabilContadorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaContabilContadorDTO{" +
            "id=" + getId() +
            ", percentualExperiencia=" + getPercentualExperiencia() +
            ", descricaoExperiencia='" + getDescricaoExperiencia() + "'" +
            ", pontuacaoEntrevista=" + getPontuacaoEntrevista() +
            ", pontuacaoAvaliacao=" + getPontuacaoAvaliacao() +
            ", areaContabil=" + getAreaContabil() +
            ", contador=" + getContador() +
            "}";
    }
}
