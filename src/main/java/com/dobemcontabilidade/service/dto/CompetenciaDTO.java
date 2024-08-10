package com.dobemcontabilidade.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.Competencia} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompetenciaDTO implements Serializable {

    private Long id;

    private String nome;

    private Integer numero;

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

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompetenciaDTO)) {
            return false;
        }

        CompetenciaDTO competenciaDTO = (CompetenciaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, competenciaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompetenciaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", numero=" + getNumero() +
            "}";
    }
}
