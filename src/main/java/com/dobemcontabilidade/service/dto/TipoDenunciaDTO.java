package com.dobemcontabilidade.service.dto;

import jakarta.persistence.Lob;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.TipoDenuncia} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TipoDenunciaDTO implements Serializable {

    private Long id;

    private String tipo;

    @Lob
    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        if (!(o instanceof TipoDenunciaDTO)) {
            return false;
        }

        TipoDenunciaDTO tipoDenunciaDTO = (TipoDenunciaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tipoDenunciaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoDenunciaDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
