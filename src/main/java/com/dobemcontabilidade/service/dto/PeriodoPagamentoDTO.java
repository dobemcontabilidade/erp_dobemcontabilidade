package com.dobemcontabilidade.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.PeriodoPagamento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PeriodoPagamentoDTO implements Serializable {

    private Long id;

    private String periodo;

    private Integer numeroDias;

    private String idPlanGnet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Integer getNumeroDias() {
        return numeroDias;
    }

    public void setNumeroDias(Integer numeroDias) {
        this.numeroDias = numeroDias;
    }

    public String getIdPlanGnet() {
        return idPlanGnet;
    }

    public void setIdPlanGnet(String idPlanGnet) {
        this.idPlanGnet = idPlanGnet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PeriodoPagamentoDTO)) {
            return false;
        }

        PeriodoPagamentoDTO periodoPagamentoDTO = (PeriodoPagamentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, periodoPagamentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PeriodoPagamentoDTO{" +
            "id=" + getId() +
            ", periodo='" + getPeriodo() + "'" +
            ", numeroDias=" + getNumeroDias() +
            ", idPlanGnet='" + getIdPlanGnet() + "'" +
            "}";
    }
}
