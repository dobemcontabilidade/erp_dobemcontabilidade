package com.dobemcontabilidade.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.PrazoAssinatura} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrazoAssinaturaDTO implements Serializable {

    private Long id;

    private String prazo;

    private Integer meses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrazo() {
        return prazo;
    }

    public void setPrazo(String prazo) {
        this.prazo = prazo;
    }

    public Integer getMeses() {
        return meses;
    }

    public void setMeses(Integer meses) {
        this.meses = meses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrazoAssinaturaDTO)) {
            return false;
        }

        PrazoAssinaturaDTO prazoAssinaturaDTO = (PrazoAssinaturaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, prazoAssinaturaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrazoAssinaturaDTO{" +
            "id=" + getId() +
            ", prazo='" + getPrazo() + "'" +
            ", meses=" + getMeses() +
            "}";
    }
}
