package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.AdicionalEnquadramento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdicionalEnquadramentoDTO implements Serializable {

    private Long id;

    private Double valor;

    @NotNull
    private EnquadramentoDTO enquadramento;

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

    public EnquadramentoDTO getEnquadramento() {
        return enquadramento;
    }

    public void setEnquadramento(EnquadramentoDTO enquadramento) {
        this.enquadramento = enquadramento;
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
        if (!(o instanceof AdicionalEnquadramentoDTO)) {
            return false;
        }

        AdicionalEnquadramentoDTO adicionalEnquadramentoDTO = (AdicionalEnquadramentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adicionalEnquadramentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdicionalEnquadramentoDTO{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", enquadramento=" + getEnquadramento() +
            ", planoContabil=" + getPlanoContabil() +
            "}";
    }
}
