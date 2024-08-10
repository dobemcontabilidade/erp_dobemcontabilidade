package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.ValorBaseRamo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ValorBaseRamoDTO implements Serializable {

    private Long id;

    @NotNull
    private Double valorBase;

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

    public Double getValorBase() {
        return valorBase;
    }

    public void setValorBase(Double valorBase) {
        this.valorBase = valorBase;
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
        if (!(o instanceof ValorBaseRamoDTO)) {
            return false;
        }

        ValorBaseRamoDTO valorBaseRamoDTO = (ValorBaseRamoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, valorBaseRamoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ValorBaseRamoDTO{" +
            "id=" + getId() +
            ", valorBase=" + getValorBase() +
            ", ramo=" + getRamo() +
            ", planoContabil=" + getPlanoContabil() +
            "}";
    }
}
