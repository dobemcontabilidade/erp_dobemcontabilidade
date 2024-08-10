package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.Cidade} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CidadeDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private Boolean contratacao;

    private Boolean abertura;

    @NotNull
    private EstadoDTO estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getContratacao() {
        return contratacao;
    }

    public void setContratacao(Boolean contratacao) {
        this.contratacao = contratacao;
    }

    public Boolean getAbertura() {
        return abertura;
    }

    public void setAbertura(Boolean abertura) {
        this.abertura = abertura;
    }

    public EstadoDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoDTO estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CidadeDTO)) {
            return false;
        }

        CidadeDTO cidadeDTO = (CidadeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cidadeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CidadeDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", contratacao='" + getContratacao() + "'" +
            ", abertura='" + getAbertura() + "'" +
            ", estado=" + getEstado() +
            "}";
    }
}
