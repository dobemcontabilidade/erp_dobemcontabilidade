package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.DescontoPlanoContabil} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DescontoPlanoContabilDTO implements Serializable {

    private Long id;

    @NotNull
    private Double percentual;

    @NotNull
    private PeriodoPagamentoDTO periodoPagamento;

    @NotNull
    private PlanoContabilDTO planoContabil;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DescontoPlanoContabilDTO)) {
            return false;
        }

        DescontoPlanoContabilDTO descontoPlanoContabilDTO = (DescontoPlanoContabilDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, descontoPlanoContabilDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DescontoPlanoContabilDTO{" +
            "id=" + getId() +
            ", percentual=" + getPercentual() +
            ", periodoPagamento=" + getPeriodoPagamento() +
            ", planoContabil=" + getPlanoContabil() +
            "}";
    }
}
