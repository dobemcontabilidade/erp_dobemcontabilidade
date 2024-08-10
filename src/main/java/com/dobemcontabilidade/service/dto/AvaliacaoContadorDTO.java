package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.AvaliacaoContador} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AvaliacaoContadorDTO implements Serializable {

    private Long id;

    private Double pontuacao;

    @NotNull
    private ContadorDTO contador;

    @NotNull
    private AvaliacaoDTO avaliacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Double pontuacao) {
        this.pontuacao = pontuacao;
    }

    public ContadorDTO getContador() {
        return contador;
    }

    public void setContador(ContadorDTO contador) {
        this.contador = contador;
    }

    public AvaliacaoDTO getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(AvaliacaoDTO avaliacao) {
        this.avaliacao = avaliacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvaliacaoContadorDTO)) {
            return false;
        }

        AvaliacaoContadorDTO avaliacaoContadorDTO = (AvaliacaoContadorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, avaliacaoContadorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvaliacaoContadorDTO{" +
            "id=" + getId() +
            ", pontuacao=" + getPontuacao() +
            ", contador=" + getContador() +
            ", avaliacao=" + getAvaliacao() +
            "}";
    }
}
