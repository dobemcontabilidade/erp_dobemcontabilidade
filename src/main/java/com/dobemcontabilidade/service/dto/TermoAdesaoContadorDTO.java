package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.TermoAdesaoContador} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TermoAdesaoContadorDTO implements Serializable {

    private Long id;

    private Instant dataAdesao;

    @NotNull
    private ContadorDTO contador;

    @NotNull
    private TermoDeAdesaoDTO termoDeAdesao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataAdesao() {
        return dataAdesao;
    }

    public void setDataAdesao(Instant dataAdesao) {
        this.dataAdesao = dataAdesao;
    }

    public ContadorDTO getContador() {
        return contador;
    }

    public void setContador(ContadorDTO contador) {
        this.contador = contador;
    }

    public TermoDeAdesaoDTO getTermoDeAdesao() {
        return termoDeAdesao;
    }

    public void setTermoDeAdesao(TermoDeAdesaoDTO termoDeAdesao) {
        this.termoDeAdesao = termoDeAdesao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TermoAdesaoContadorDTO)) {
            return false;
        }

        TermoAdesaoContadorDTO termoAdesaoContadorDTO = (TermoAdesaoContadorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, termoAdesaoContadorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TermoAdesaoContadorDTO{" +
            "id=" + getId() +
            ", dataAdesao='" + getDataAdesao() + "'" +
            ", contador=" + getContador() +
            ", termoDeAdesao=" + getTermoDeAdesao() +
            "}";
    }
}
