package com.dobemcontabilidade.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.FormaDePagamento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FormaDePagamentoDTO implements Serializable {

    private Long id;

    private String forma;

    private Boolean disponivel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormaDePagamentoDTO)) {
            return false;
        }

        FormaDePagamentoDTO formaDePagamentoDTO = (FormaDePagamentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, formaDePagamentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormaDePagamentoDTO{" +
            "id=" + getId() +
            ", forma='" + getForma() + "'" +
            ", disponivel='" + getDisponivel() + "'" +
            "}";
    }
}
