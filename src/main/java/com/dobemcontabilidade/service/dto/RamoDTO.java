package com.dobemcontabilidade.service.dto;

import jakarta.persistence.Lob;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.Ramo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RamoDTO implements Serializable {

    private Long id;

    private String nome;

    @Lob
    private String descricao;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RamoDTO)) {
            return false;
        }

        RamoDTO ramoDTO = (RamoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ramoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RamoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
