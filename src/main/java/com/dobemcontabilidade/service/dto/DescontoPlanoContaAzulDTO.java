package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.DescontoPlanoContaAzul} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DescontoPlanoContaAzulDTO implements Serializable {

    private Long id;

    private Double percentual;

    @NotNull
    private PlanoContaAzulDTO planoContaAzul;

    @NotNull
    private PeriodoPagamentoDTO periodoPagamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPercentual() {
        return percentual;
    }

    public void setPercentual(Double percentual) {
        this.percentual = percentual;
    }

    public PlanoContaAzulDTO getPlanoContaAzul() {
        return planoContaAzul;
    }

    public void setPlanoContaAzul(PlanoContaAzulDTO planoContaAzul) {
        this.planoContaAzul = planoContaAzul;
    }

    public PeriodoPagamentoDTO getPeriodoPagamento() {
        return periodoPagamento;
    }

    public void setPeriodoPagamento(PeriodoPagamentoDTO periodoPagamento) {
        this.periodoPagamento = periodoPagamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DescontoPlanoContaAzulDTO)) {
            return false;
        }

        DescontoPlanoContaAzulDTO descontoPlanoContaAzulDTO = (DescontoPlanoContaAzulDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, descontoPlanoContaAzulDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DescontoPlanoContaAzulDTO{" +
            "id=" + getId() +
            ", percentual=" + getPercentual() +
            ", planoContaAzul=" + getPlanoContaAzul() +
            ", periodoPagamento=" + getPeriodoPagamento() +
            "}";
    }
}
