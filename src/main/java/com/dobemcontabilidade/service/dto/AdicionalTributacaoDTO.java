package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.AdicionalTributacao} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdicionalTributacaoDTO implements Serializable {

    private Long id;

    @NotNull
    private Double valor;

    @NotNull
    private TributacaoDTO tributacao;

    @NotNull
    private PlanoContabilDTO planoContabil;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public TributacaoDTO getTributacao() {
        return tributacao;
    }

    public void setTributacao(TributacaoDTO tributacao) {
        this.tributacao = tributacao;
    }

    public PlanoContabilDTO getPlanoContabil() {
        return planoContabil;
    }

    public void setPlanoContabil(PlanoContabilDTO planoContabil) {
        this.planoContabil = planoContabil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdicionalTributacaoDTO)) {
            return false;
        }

        AdicionalTributacaoDTO adicionalTributacaoDTO = (AdicionalTributacaoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adicionalTributacaoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdicionalTributacaoDTO{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", tributacao=" + getTributacao() +
            ", planoContabil=" + getPlanoContabil() +
            "}";
    }
}
