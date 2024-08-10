package com.dobemcontabilidade.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.GrupoCnae} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GrupoCnaeDTO implements Serializable {

    private Long id;

    @Size(max = 15)
    private String codigo;

    @Lob
    private String descricao;

    @NotNull
    private DivisaoCnaeDTO divisao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public DivisaoCnaeDTO getDivisao() {
        return divisao;
    }

    public void setDivisao(DivisaoCnaeDTO divisao) {
        this.divisao = divisao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrupoCnaeDTO)) {
            return false;
        }

        GrupoCnaeDTO grupoCnaeDTO = (GrupoCnaeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, grupoCnaeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GrupoCnaeDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", divisao=" + getDivisao() +
            "}";
    }
}
