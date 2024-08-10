package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.AdicionalRamo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AdicionalRamoDTO implements Serializable {

    private Long id;

    @NotNull
    private Double valor;

    @NotNull
    private RamoDTO ramo;

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

    public RamoDTO getRamo() {
        return ramo;
    }

    public void setRamo(RamoDTO ramo) {
        this.ramo = ramo;
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
        if (!(o instanceof AdicionalRamoDTO)) {
            return false;
        }

        AdicionalRamoDTO adicionalRamoDTO = (AdicionalRamoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adicionalRamoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdicionalRamoDTO{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", ramo=" + getRamo() +
            ", planoContabil=" + getPlanoContabil() +
            "}";
    }
}
